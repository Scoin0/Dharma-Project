package me.scoin0.dharmaproject.event;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.Timer;
import java.util.TimerTask;

public class ButtonInteract implements Listener {

    BlockFace face;

    @EventHandler
    public void ButtonInteract (PlayerInteractEvent event) {
        Plugin plugin = DharmaProject.getPlugin();
        CountdownTimer oldTime = new CountdownTimer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() == Material.STONE_BUTTON && event.getClickedBlock().getLocation().getBlockX() == plugin.getConfig().getInt("buttonPos.x") && event.getClickedBlock().getLocation().getBlockY() == plugin.getConfig().getInt("buttonPos.y") && event.getClickedBlock().getLocation().getBlockZ() == plugin.getConfig().getInt("buttonPos.z")) {
                face = event.getBlockFace().getOppositeFace();
                if (plugin.getConfig().getInt("countdownTimeLeft") != 0) {
                    oldTime.cancelTask();
                    DharmaProject.getPlugin().getConfig().set("countdownTimeLeft", 0);
                    DharmaProject.getPlugin().saveConfig();
                    event.getPlayer().sendMessage("Task ID: " + plugin.getConfig().getInt("countdownTask") + " stopped.");
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            CountdownTimer timer = new CountdownTimer();
                            timer.startTimer(event.getPlayer().getName());
                        }
                    },1000);
                } else {
                    event.getPlayer().sendMessage("There is no task running.");
                }
            }
        }
    }

    @EventHandler
    public void ButtonDestroyCancel (BlockBreakEvent event) {
        Plugin plugin = DharmaProject.getPlugin();
        if (event.getBlock().getType() == Material.STONE_BUTTON && event.getBlock().getLocation().getBlockX() == plugin.getConfig().getInt("buttonPos.x") && event.getBlock().getLocation().getBlockY() == plugin.getConfig().getInt("buttonPos.y") && event.getBlock().getLocation().getBlockZ() == plugin.getConfig().getInt("buttonPos.z")) {
            event.getPlayer().sendMessage("You cannot break this block.");
            event.setCancelled(true);
        }
    }
}