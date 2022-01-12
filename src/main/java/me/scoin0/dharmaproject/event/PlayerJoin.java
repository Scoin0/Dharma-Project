package me.scoin0.dharmaproject.event;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
import me.scoin0.dharmaproject.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome to the island...");

        if (DharmaProject.plugin.getConfig().getInt("countdownTimeLeft") != 0) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "Resuming Time");
            CountdownTimer timer = new CountdownTimer(DharmaProject.getPlugin(), DharmaProject.plugin.getConfig().getInt("countdownTimeLeft"),
                    () -> event.getPlayer().sendMessage("Countdown begun"),
                    () -> {event.getPlayer().sendMessage("Timer up. Boom.");},
                    (t) -> event.getPlayer().sendMessage(ChatColor.YELLOW + "Time left: " + Utils.convertSecondsToReadableTime(t.getSecondsLeft()))
            );
            timer.scheduleTimer();
            int time = timer.getAssignedTaskId();
            Bukkit.getConsoleSender().sendMessage(DharmaProject.plugin.prefix + " Begun timer with id of " + time);
        }
    }
}
