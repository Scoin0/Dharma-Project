package me.scoin0.dharmaproject.event;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
import me.scoin0.dharmaproject.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerDisconnect implements Listener {

    @EventHandler
    public void onPlayerDisconnect (PlayerQuitEvent event) {

        CountdownTimer timer = new CountdownTimer();

        // Hold off of checking players 1 second after they leave.
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (Utils.getOnlinePlayers().size() == 0) {
                    Bukkit.getConsoleSender().sendMessage(DharmaProject.plugin.prefix + " All players have left. Halting Timer...");
                    timer.cancelTask();
                    Bukkit.getConsoleSender().sendMessage(DharmaProject.plugin.prefix + " Fully Halted. There is " + Utils.convertSecondsToReadableTime(DharmaProject.plugin.getConfig().getInt("countdownTimeLeft")) + " remaining.");
                }
            }
        },1000);
    }
}
