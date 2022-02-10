package me.grovre.altalerter;

import me.grovre.altalerter.listeners.OnPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AltAlerter extends JavaPlugin {

    private static AltAlerter plugin;

    @Override
    public void onEnable() {
        plugin = this;
        /*
        perms:
        altAlerter.getAlerts
         */
        this.saveDefaultConfig();

        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        System.out.println("AltAlerter now notifying and logging anybody who joins from the same IP!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static AltAlerter getPlugin() {
        return plugin;
    }
}
