package me.grovre.altalerter;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerInfo {

    private Player player;
    private InetAddress lastIp;
    private ArrayList<InetAddress> ipHistory;
    private UUID uuid;

    public PlayerInfo(Player player) {
        this.player = player;
        this.lastIp = player.getAddress().getAddress();
        if(lastIp == null) {
            lastIp = ipHistory.get(ipHistory.size() - 1);
        }
        this.uuid = player.getUniqueId();
    }

    public void moveIpToHistory() {
        for(InetAddress ip : ipHistory) {
            if(lastIp.equals(ip)) {
                return;
            }
        }
        ipHistory.add(lastIp);
    }

    public void setNewIp(InetAddress ip) {
        moveIpToHistory();
        lastIp = ip;
    }

    public boolean compareIps(InetAddress ip) {
        return lastIp.equals(ip);
    }

    public boolean compareUUIDs(UUID uuid) {
        return this.uuid.equals(uuid);
    }

    public boolean comparePlayers(Player player) {
        return this.player.equals(player);
    }

    public Player getPlayer() {
        return player;
    }

    public InetAddress getLastIp() {
        return lastIp;
    }

    public ArrayList<InetAddress> getIpHistory() {
        return ipHistory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setLastIp(InetAddress lastIp) {
        this.lastIp = lastIp;
    }

    public void setIpHistory(ArrayList<InetAddress> ipHistory) {
        this.ipHistory = ipHistory;
    }
}
