package me.grovre.altalerter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetAddress;
import java.util.Collection;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void OnPlayerJoinServer(PlayerJoinEvent event) {
        // Collects player info, also ensures the IP address isn't null (shouldn't be)
        Player joiningPlayer = event.getPlayer();
        String joiningPlayerName = joiningPlayer.getName();
        System.out.println("Joining player: " + joiningPlayerName);
        InetAddress joiningPlayerIpAddress = joiningPlayer.getAddress().getAddress();
        if(joiningPlayerIpAddress == null) {
            System.out.println(joiningPlayer.getName() + "'s IP address is null?");
            return;
        }

        // Collects all online players, checking the joining IP against all online players' IP addresses
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for(Player onlinePlayer : onlinePlayers) {
            // Collects online player's info for checking against joining player
            InetAddress onlinePlayerIpAddress = onlinePlayer.getAddress().getAddress();
            String onlinePlayerName = onlinePlayer.getName();
            if(onlinePlayer == joiningPlayer) continue;
            if(onlinePlayerIpAddress == null) {
                System.out.println(onlinePlayer.getName() + "'s IP address is null?");
                continue;
            }
            // When there's a hit and an IP DOES match
            if(joiningPlayerIpAddress.equals(onlinePlayerIpAddress)) {
                System.out.println("HIT!");
                System.out.println(ChatColor.RED + joiningPlayerName + " has the same IP address as "  + onlinePlayerName + "!");
                sendToPermitted(joiningPlayer,onlinePlayer, joiningPlayerIpAddress);
            } else System.out.println("No match against " + onlinePlayerName);
        }
    }

    // Sends a scary message to all online players with the permission
    public void sendToPermitted(Player joiningPlayer, Player onlinePlayer, InetAddress ip) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission("altAlerter.getAlerts")) {
                p.sendMessage(ChatColor.RED + joiningPlayer.getName() + " has the same IP address as "  + onlinePlayer.getName() + "(" + ip + ")!");
            }
        }
    }

}
