package me.scoin0.dharmaproject;

import me.scoin0.dharmaproject.commands.Commands;
import me.scoin0.dharmaproject.event.ButtonInteract;
import me.scoin0.dharmaproject.event.PlayerDisconnect;
import me.scoin0.dharmaproject.event.PlayerJoin;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DharmaProject extends JavaPlugin {

    private final static Logger log = Logger.getLogger("Minecraft");
    public static DharmaProject plugin;

    public String prefix = "&6[&aDharma&6]";
    public String reloadMessage = "&aConfiguration Reloaded";
    public String nopermission = "&cYou do not have permission to use that command";
    public String invalidBlockBreak = "&cYou cannot break that block";
    public int countdownTimer = 6480;
    public int health = 500;
    public int coneOfVision = 25;
    public int minSpawn = 5;
    public int maxSpawn = 15;
    public int spawnChance = 15;

    public static DharmaProject getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        registerEvents();
        reloadConfiguration();
    }

    public void registerEvents() {
        getCommand("dharma").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new ButtonInteract(), this);
    }

    public void reloadConfiguration() {
        saveDefaultConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
        reloadMessage = ChatColor.translateAlternateColorCodes('&', getConfig().getString("reloadMessage"));
        nopermission = ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-Permission"));
        invalidBlockBreak = ChatColor.translateAlternateColorCodes('&', getConfig().getString("invalid-Block-Break"));
        countdownTimer = getConfig().getInt("hatch.countdownTimer");
        health = getConfig().getInt("monster.health");
        coneOfVision = getConfig().getInt("monster.coneOfVision");
        minSpawn = getConfig().getInt("others.minSpawn");
        maxSpawn = getConfig().getInt("others.maxSpawn");
        spawnChance = getConfig().getInt("others.spawnChance");
        reloadConfig();
        log.info("Configuration File Reloaded.");
    }
}
