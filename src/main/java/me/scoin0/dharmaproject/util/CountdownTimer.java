package me.scoin0.dharmaproject.util;

import me.scoin0.dharmaproject.DharmaProject;
import org.bukkit.Bukkit;

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
}

