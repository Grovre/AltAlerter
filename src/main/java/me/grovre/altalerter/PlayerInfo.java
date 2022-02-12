package me.grovre.altalerter;

import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerInfo {

    private String player;
    private final UUID uuid;
    private String lastIp;
    private ArrayList<String> ipHistory;

    public PlayerInfo(Player player) {
        this.player = player.getName();
        this.uuid = player.getUniqueId();
        this.lastIp = player.getAddress().getAddress().toString();
        this.ipHistory = new ArrayList<>();
        ipHistory.add(lastIp);
    }

    public String compareIps(PlayerInfo other) {
        for(String ip1 : ipHistory) {
            for(String ip2 : other.ipHistory) {
                if(ip1.equals(ip2)) {
                    return ip1;
                }
            }
        }
        return null;
    }

    public boolean hasUsedIp(PlayerInfo other) {
        return compareIps(other) != null;
    }

    public boolean compareUUIDs(PlayerInfo other) {
        return this.uuid == other.uuid;
    }

    public void setNewLastIp(InetAddress ip) {
        if(!isLastIpInHistory()) {
            ipHistory.add(lastIp);
        }
        lastIp = ip.toString();
    }

    public boolean isLastIpInHistory() {
        for(String ip : ipHistory) {
            if(ip.equals(lastIp)) return true;
        }
        return false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLastIp() {
        return lastIp;
    }

    public ArrayList<String> getIpHistory() {
        return ipHistory;
    }

    public String getPlayer() {
        return player;
    }
}
