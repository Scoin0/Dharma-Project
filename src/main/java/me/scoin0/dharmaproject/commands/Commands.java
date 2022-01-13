package me.scoin0.dharmaproject.commands;

import me.scoin0.dharmaproject.DharmaProject;
import me.scoin0.dharmaproject.util.CountdownTimer;
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

    DharmaProject plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!label.equalsIgnoreCase("dharma") && !label.equalsIgnoreCase("dharmaproject")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.GREEN + " Welcome to the Dharma Initiative.");
            sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.BLUE + " Author: " + ChatColor.LIGHT_PURPLE + DharmaProject.getPlugin().getDescription().getAuthors());
            sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.BLUE + " Version: " + ChatColor.GOLD + DharmaProject.getPlugin().getDescription().getVersion());
            return true;
        }

        // Start the Dharma Initiative
        if (args[0].equalsIgnoreCase("start")) {
            if (DharmaProject.plugin.getConfig().getInt("countdownTimeLeft") == 0) {
                CountdownTimer timer = new CountdownTimer();
                timer.startTimer(sender.getName());
            } else {
                sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.RED + " You've already used this command. Use the argument of 'stop' to end the mission.");
            }
            return true;
        }

        // Stop the Dharma Initiative
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

        // Reload configuration
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("dharma.reload")) {
                DharmaProject.plugin.reloadConfiguration();
                sender.sendMessage(DharmaProject.plugin.prefix + ChatColor.GOLD + " Reload Complete.");
                return true;
            }
        }

        // Select the sign to be the Timer
        if (args[0].equalsIgnoreCase("sign")) {
            if (sender.hasPermission("dharma.sign")) {
                sender.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', " &aPlease interact with the sign."));
                return true;
            }
        }

        // Select the button you need to press to extend the timer
        if (args[0].equalsIgnoreCase("button")) {
            if (sender.hasPermission("dharma.button")) {
                sender.sendMessage(plugin.prefix + ChatColor.translateAlternateColorCodes('&', " &aPlease interact with the button."));
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

                if (sender.hasPermission("dharma.sign")) {
                    tab.add("sign");
                }

                if (sender.hasPermission("dharma.button")) {
                    tab.add("button");
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

                if (sender.hasPermission("dharma.sign")) {
                    if ("sign".startsWith((args[0].toLowerCase()))) {
                        tab.add("sign");
                    }
                }

                if (sender.hasPermission("dharma.sign")) {
                    if ("sign".startsWith((args[0].toLowerCase()))) {
                        tab.add("sign");
                    }
                }
                return tab;

            default:
                return tab;

        }
    }
}
