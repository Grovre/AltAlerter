package me.grovre.altalerter;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class dbUtil {

    private ArrayList<?> infos;
    private Type t;
    private AltAlerter plugin;
    private final File f;
    private String fdir;
    private Gson gson;
    private FileWriter fw;
    private FileReader fr;

    public dbUtil(File f) throws IOException {
        infos = new ArrayList<>();
        plugin = AltAlerter.getPlugin();
        gson = new Gson();
        this.f = f;
        if(f.getParentFile().mkdir()) System.out.println("Creating AltAlerter parent folder"); // In case folder wasn't already made
        fdir = f.getAbsolutePath();
        fw = new FileWriter(f, false);
        fr = new FileReader(f);
        t = (Type) infos;
    }

    public String getJson() {
        System.out.println(fr.toString());
        return fr.toString();
    }

    public ArrayList<?> getInfos() {
        refreshInfos();
        System.out.println(infos);
        return infos;
    }

    public void refreshInfos() {
        infos = gson.fromJson(fr, t);
    }

    public void saveJson() throws IOException {
        fw.write(gson.toJson(infos, t));
        fw.close();
    }
}
