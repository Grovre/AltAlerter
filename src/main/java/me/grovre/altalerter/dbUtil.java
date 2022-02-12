package me.grovre.altalerter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonToken;
import org.bukkit.entity.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;
import java.util.jar.JarOutputStream;

public class dbUtil {

    private ArrayList<PlayerInfo> pis;
    private final Gson gson;
    private final AltAlerter plugin;
    private final File f;

    public dbUtil() throws IOException {
        System.out.println("Creating dbUtil o");
        plugin = AltAlerter.getPlugin();
        f = new File(plugin.getDataFolder().getAbsolutePath() + "/PlayerData.json");
        if(f.getParentFile().mkdir()) System.out.println("Making AltAlerter folder");
        if(f.createNewFile()) System.out.println("Creating PlayerData.json");
        gson = new Gson();
        pis = new ArrayList<>();
    }

    public void saveJson() throws IOException {
        Writer fw = new FileWriter(f, false);
        System.out.println("Saving pis to json db");
        PlayerInfo[] pisArray = new PlayerInfo[pis.size()];
        for (int i = 0; i < pis.size(); i++) {
            PlayerInfo pi = pis.get(i);
            pisArray[i] = pi;
        }
        gson.toJson(pisArray, fw);
        fw.close();
    }

    public void addToDatabase(PlayerInfo pi) throws IOException {
        System.out.println("Adding pi to pis db");
        refreshPis();
        int toReplace = getIndexOfPlayerInfo(pi.getUuid());
        if(toReplace != -1) {
            pis.remove(toReplace);
            pis.add(toReplace, pi);
        } else {
            pis.add(pi);
        }
        saveJson();
    }

    public void refreshPis() throws IOException {
        System.out.println("Refreshing pis");
        Reader fr = new FileReader(f);
        PlayerInfo[] playerInfos = new PlayerInfo[0];
        playerInfos = gson.fromJson(fr, playerInfos.getClass());
        pis = new ArrayList<>();
        if(playerInfos == null || playerInfos.length < 1) {
            System.out.println("Nothing in json");
        } else {
            pis.addAll(Arrays.asList(playerInfos));
        }
        fr.close();
    }

    public ArrayList<PlayerInfo> getPis() throws IOException {
        System.out.println("Getting pis");
        refreshPis();
        return pis;
    }

    public int getIndexOfPlayerInfo(UUID uuid) throws IOException {
        refreshPis();
        for (int i = 0; i < pis.size(); i++) {
            PlayerInfo pi = pis.get(i);
            if(pi.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public UUID[] getUUIDs() throws IOException {
        refreshPis();
        UUID[] uuids = new UUID[pis.size()];
        for (int i = 0; i < pis.size(); i++) {
            uuids[i] = pis.get(i).getUuid();
        }
        return uuids;
    }

    public PlayerInfo hasJoinedBefore(Player player) throws IOException {
        refreshPis();
        for(PlayerInfo pi : pis) {
            if(pi.getUuid() == player.getUniqueId()) {
                return pi;
            }
        }
        return null;
    }

    public boolean hasJoinedBefore(UUID uuid) throws IOException {
        UUID[] uuids = getUUIDs();
        for(UUID otherUuid : uuids) {
            if(otherUuid.equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
