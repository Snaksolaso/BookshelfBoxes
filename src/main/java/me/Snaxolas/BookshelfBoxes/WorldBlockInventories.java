package me.Snaxolas.BookshelfBoxes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class WorldBlockInventories {

    private static final String hmLoc = Bukkit.getPluginManager().getPlugin("BookshelfBoxes").getDataFolder().getPath() + File.separator + "BlocksInv.data";

    private static HashMap<Location, Boolean> locked = new HashMap<Location, Boolean>();

    public static ItemStack[] getInvOf(Location cx){
        HashMap<Location, ItemStack[]> hm = loadHashMap(hmLoc);
        if(locked.get(cx) == null || !locked.get(cx)) {
            locked.put(cx, true);
            if (hm.get(cx) != null) {
                return hm.get(cx);
            } else {
                ItemStack[] inv = new ItemStack[18];
                setInvOf(cx, inv);
                return inv;
            }
        }
        return null;
    }

    public static void setInvOf(Location cx, ItemStack[] i){
        HashMap<Location, ItemStack[]> hm = loadHashMap(hmLoc);

       if (hm == null){
            hm = new HashMap<>();
        }
       hm.put(cx, i);
       locked.put(cx, false);
       saveHashMap(hmLoc, hm);
    }


    public static boolean saveHashMap(String filePath, HashMap<Location, ItemStack[]> hm) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(hm);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public static HashMap<Location, ItemStack[]> loadHashMap(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            HashMap<Location, ItemStack[]> data = (HashMap<Location, ItemStack[]>) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
