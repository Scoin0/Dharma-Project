package me.scoin0.dharmaproject.event;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
import me.scoin0.dharmaproject.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(ChatColor.GOLD + "Welcome to the island...");

        if (DharmaProject.plugin.getConfig().getInt("countdownTimeLeft") != 0) {
            CountdownTimer timer = new CountdownTimer();
            timer.resumeTimer(event.getPlayer().getName());
        }
    }
}
