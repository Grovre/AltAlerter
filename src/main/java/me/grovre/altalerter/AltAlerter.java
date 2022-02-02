package me.grovre.altalerter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class AltAlerter extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // sometimes just dumb like that, putting this event register into onDisable like retard
        this.saveDefaultConfig();

        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        System.out.println("AltAlerter now notifying and logging anybody who joins from the same IP!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
