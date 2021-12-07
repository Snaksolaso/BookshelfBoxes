package me.Snaxolas.BookshelfBoxes;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

/*
TODO:
    - Fix piston pushing bookshelf bug
    - Fix TNT not dropping items.

 */


public class Main extends JavaPlugin {

    private void onFirstRun(){
        String hmLoc = this.getDataFolder().getPath() + File.separator + "BlocksInv.data";
        WorldBlockInventories.saveHashMap(hmLoc, new HashMap<Location, ItemStack[]>());
    }

    @Override
    public void onEnable(){
        String hmLoc = this.getDataFolder().getPath() + File.separator + "BlocksInv.data";
        if(WorldBlockInventories.loadHashMap(hmLoc) == null){
            this.getDataFolder().mkdir();
            WorldBlockInventories.saveHashMap(hmLoc, new HashMap<Location, ItemStack[]>());
        }

        getServer().getPluginManager().registerEvents(new BookListener(),this);
        registerCmds();
    }

    @Override
    public void onDisable(){
    }

    public void registerCmds(){
    }


}