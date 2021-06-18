package me.Snaxolas.BookshelfBoxes;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/*
TODO:
    - Fix piston pushing bookshelf bug
    - Fix TNT not dropping items.

 */


public class Main extends JavaPlugin {
    private void onFirstRun(){
        WorldBlockInventories.saveHashMap("BlocksInv.data", new HashMap<>());
    }

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new BookListener(),this);
        registerCmds();
    }

    @Override
    public void onDisable(){
    }

    public void registerCmds(){
    }


}