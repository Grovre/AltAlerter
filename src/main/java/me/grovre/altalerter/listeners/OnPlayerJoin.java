package me.grovre.altalerter.listeners;

import me.grovre.altalerter.AltAlerter;
import me.grovre.altalerter.PlayerInfo;
import me.grovre.altalerter.dbUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void OnPlayerJoinServer(PlayerJoinEvent event) throws IOException {
        FileConfiguration config = AltAlerter.getPlugin().getConfig();
        dbUtil db = new dbUtil();

        // Collects player info
        Player joiningPlayer = event.getPlayer();
        String joiningPlayerName = joiningPlayer.getName();
        PlayerInfo joiningPi = db.hasJoinedBefore(joiningPlayer);
        if(joiningPi == null) {
            joiningPi = new PlayerInfo(joiningPlayer);
            db.addToDatabase(joiningPi);
        }
        joiningPi.setNewLastIp(joiningPlayer.getAddress().getAddress());

        // Collects all online players, checking the joining IP against all online players' IP addresses
        for(PlayerInfo dbPlayerInfo : db.getPis()) {
            if(joiningPi.getUuid().equals(dbPlayerInfo.getUuid())) continue;
            InetAddress matchingIp = null;
            if(joiningPi.hasUsedIp(dbPlayerInfo)) {
                matchingIp = joiningPlayer.getAddress().getAddress();
            }
            // When there's a hit and an IP DOES match
            if(matchingIp != null) {
                if(config.getBoolean("showAlertInConsole")) {
                    System.out.println(joiningPlayerName + " has the same IP address as " + dbPlayerInfo.getPlayer() + "!" + "(" + matchingIp + ")");
                }
                if(config.getBoolean("sendAlertToPermitted")) {
                    sendToPermitted(joiningPi.getUuid(), dbPlayerInfo.getUuid(), matchingIp);
                }
            }
        }
    }

    // Sends a scary message to all online players with the permission, otherwise just staff or just ops
    public void sendToPermitted(UUID player1uuid, UUID player2uuid, InetAddress ip) {
        OfflinePlayer joiningPlayer = Bukkit.getOfflinePlayer(player1uuid);
        OfflinePlayer onlinePlayer = Bukkit.getOfflinePlayer(player2uuid);
        FileConfiguration config = AltAlerter.getPlugin().getConfig();
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.hasPermission("altAlerter.getAlerts")) {
                String message = config.getString("alertMessage");
                if(message == null) {
                    System.out.println("alertMessage is NULL!");
                    return;
                }
                try {
                    message = message.replace("%p", onlinePlayer.getName());
                } catch (Exception ignored) {}
                try {
                    message = message.replace("%j", joiningPlayer.getName());
                } catch (Exception ignored) {}
                if(config.getBoolean("showIpInAlertMessage")) {
                    message = message + " (" + ip + ")";
                }
                p.sendMessage(ChatColor.RED + message);
            }
        }
    }

}
