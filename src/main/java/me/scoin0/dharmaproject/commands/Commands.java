package me.scoin0.dharmaproject.commands;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
import me.scoin0.dharmaproject.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!label.equalsIgnoreCase("dharma") && !label.equalsIgnoreCase("dharmaproject")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.AQUA + DharmaProject.plugin.getDescription().getName() + " written by " + ChatColor.LIGHT_PURPLE + DharmaProject.plugin.getDescription().getAuthors());
            sender.sendMessage(ChatColor.GOLD + "You are running " + DharmaProject.plugin.getDescription().getName() + " version: " + DharmaProject.plugin.getDescription().getVersion());
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            sender.sendMessage(ChatColor.GOLD + "Preparing the countdown...");

            if (DharmaProject.plugin.getConfig().getInt("countdownTimeLeft") == 0) {
                CountdownTimer timer = new CountdownTimer();
                timer.startTimer(sender.getName());
            } else {
                sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.RED + " You've already used this command. Use the argument of stop to end the mission.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("stop")) {
            if (sender.hasPermission("dharma.stop")) {
                CountdownTimer timer = new CountdownTimer();
                timer.cancelTask();
                DharmaProject.getPlugin().getConfig().set("countdownTimeLeft", 0);
                DharmaProject.getPlugin().saveConfig();
                sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.RED + " Stopped all current operations");
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("dharma.reload")) {
                DharmaProject.plugin.reloadConfiguration();
                sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.GOLD + " Reload Complete.");
                return true;
            }
        }

        sender.sendMessage(DharmaProject.plugin.prefix + DharmaProject.plugin.getConfig().getString("no-Permission"));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tab = new LinkedList<>();

        if (!alias.equalsIgnoreCase("dharma") && !alias.equalsIgnoreCase("dharmaproject")) {
            return tab;
        }

        switch (args.length) {

            case 0:

                if (sender.hasPermission("dharma.reload")) {
                    tab.add("reload");
                }

                if (sender.hasPermission("dharma.start")) {
                    tab.add("start");
                }

                if (sender.hasPermission("dharma.stop")) {
                    tab.add("stop");
                }

                return tab;

            case 1:

                if (sender.hasPermission("dharma.reload")) {
                    if ("reload".startsWith((args[0].toLowerCase()))) {
                        tab.add("reload");
                    }
                }

                if (sender.hasPermission("dharma.start")) {
                    if ("start".startsWith((args[0].toLowerCase()))) {
                        tab.add("start");
                    }
                }

                if (sender.hasPermission("dharma.stop")) {
                    if ("stop".startsWith((args[0].toLowerCase()))) {
                        tab.add("stop");
                    }
                }
                return tab;

            default:
                return tab;

        }
    }
}
