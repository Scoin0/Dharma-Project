package me.scoin0.dharmaproject.util;

import me.scoin0.dharmaproject.DharmaProject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Consumer;

public class CountdownTimer implements Runnable {

    private DharmaProject plugin;
    private Integer assignedTaskId;

    private int seconds;
    private int secondsLeft;

    private Consumer<CountdownTimer> everySecond;

    private Runnable beforeTimer;
    private Runnable afterTimer;

    public CountdownTimer (DharmaProject plugin, int seconds, Runnable beforeTimer, Runnable afterTimer, Consumer<CountdownTimer> everySecond) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.secondsLeft = seconds;
        this.beforeTimer = beforeTimer;
        this.afterTimer = afterTimer;
        this.everySecond = everySecond;
    }

    public CountdownTimer () {

    }

    public void startTimer(String playerName) {
        CountdownTimer timer = new CountdownTimer(
                DharmaProject.getPlugin(),
                DharmaProject.getPlugin().getConfig().getInt("hatch.countdownTimer"),
                () -> sendMessage(playerName, DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&6 The Dharma Initiative has begun.")),
                () -> sendMessage(playerName, DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&c Congrats. Everyone is dead.")),
                (t) -> {
                    int x = DharmaProject.getPlugin().getConfig().getInt("signPos.x");
                    int y = DharmaProject.getPlugin().getConfig().getInt("signPos.y");
                    int z = DharmaProject.getPlugin().getConfig().getInt("signPos.z");

                    World world = Bukkit.getWorld(Bukkit.getServer().getPlayer(playerName).getWorld().getName());
                    Block block = world.getBlockAt(x, y, z);
                    BlockState state = block.getState();

                    if (!(state instanceof Sign)) {
                        return;
                    }

                    String[] testing = Utils.convertSecondsToReadableTime(t.getSecondsLeft()).split(",");
                    String td = testing[0];
                    String t2 = testing[1];
                    Sign sign = (Sign) state;
                    sign.setGlowingText(true);
                    sign.setColor(DyeColor.GREEN);
                    sign.setLine(0, "");
                    sign.setLine(1, td);
                    sign.setLine(2, t2);
                    sign.setLine(3, "");
                    sign.update();
                }
        );
        timer.scheduleTimer();
        int taskId = timer.getAssignedTaskId();
        Bukkit.getConsoleSender().sendMessage(DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&6 Dharma Initiative Task ID: &a" + taskId + "&6 has begun."));
    }

    public void resumeTimer(String playerName) {
        CountdownTimer timer = new CountdownTimer(
                DharmaProject.getPlugin(),
                DharmaProject.getPlugin().getConfig().getInt("countdownTimeLeft"),
                () -> sendMessage(playerName, DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&6 The Dharma Initiative has restarted.")),
                () -> sendMessage(playerName, DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&c Congrats. Everyone is dead.")),
                (t) -> {

                    int x = DharmaProject.getPlugin().getConfig().getInt("signPos.x");
                    int y = DharmaProject.getPlugin().getConfig().getInt("signPos.y");
                    int z = DharmaProject.getPlugin().getConfig().getInt("signPos.z");

                    World world = Bukkit.getWorld(Bukkit.getServer().getPlayer(playerName).getWorld().getName());
                    Block block = world.getBlockAt(x, y, z);
                    BlockState state = block.getState();

                    if (!(state instanceof Sign)) {
                        return;
                    }

                    String[] testing = Utils.convertSecondsToReadableTime(t.getSecondsLeft()).split(",");
                    String td = testing[0];
                    String t2 = testing[1];
                    Sign sign = (Sign) state;
                    sign.setLine(0, "");
                    sign.setLine(1, td);
                    sign.setLine(2, t2);
                    sign.setLine(3, "");
                    sign.update();
                }
        );
        timer.scheduleTimer();
        int taskId = timer.getAssignedTaskId();
        Bukkit.getConsoleSender().sendMessage(DharmaProject.getPlugin().prefix + ChatColor.translateAlternateColorCodes('&', "&6 Dharma Initiative Task ID: &a" + taskId + "&6 has restarted."));
    }

    @Override
    public void run() {
        if (secondsLeft < 1) {
            afterTimer.run();
            if (assignedTaskId != null) Bukkit.getScheduler().cancelTask(assignedTaskId);
            return;
        }
        if (secondsLeft == seconds) beforeTimer.run();
        everySecond.accept(this);
        secondsLeft--;
    }

    public int getTotalSeconds() {
        return seconds;
    }

    public int getSecondsLeft() {
        DharmaProject.getPlugin().getConfig().set("countdownTimeLeft", secondsLeft);
        DharmaProject.getPlugin().saveConfig();
        return secondsLeft;
    }

    public void scheduleTimer() {
        this.assignedTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 20L);
        setTaskId();
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(DharmaProject.getPlugin().getConfig().getInt("countdownTask"));
    }

    public void setTaskId() {
        DharmaProject.getPlugin().getConfig().set("countdownTask", assignedTaskId);
        DharmaProject.getPlugin().saveConfig();
    }

    public int getAssignedTaskId() {
        return assignedTaskId;
    }

    public void sendMessage(String player, String message) {
        Bukkit.getServer().getPlayer(player).sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}