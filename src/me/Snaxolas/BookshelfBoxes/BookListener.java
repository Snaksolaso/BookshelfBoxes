package me.Snaxolas.BookshelfBoxes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


public class BookListener implements Listener{

    static final Material[] okItems = {Material.BOOK, Material.WRITTEN_BOOK, Material.WRITABLE_BOOK, Material.ENCHANTED_BOOK,
            Material.MAP, Material.FILLED_MAP, Material.PIGLIN_BANNER_PATTERN, Material.FLOWER_BANNER_PATTERN,
            Material.GLOBE_BANNER_PATTERN, Material.MOJANG_BANNER_PATTERN, Material.SKULL_BANNER_PATTERN, Material.CREEPER_BANNER_PATTERN, Material.KNOWLEDGE_BOOK};

    /*
            Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WAIT, Material.MUSIC_DISC_11,
            Material.MUSIC_DISC_13, Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CAT, Material.MUSIC_DISC_CHIRP,
            Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_PIGSTEP,
            Material.MUSIC_DISC_STAL, Material.PAPER
     */

    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent e) {

        Action a = e.getAction();
        Player p = e.getPlayer();

        if (a.equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.BOOKSHELF) && !p.isSneaking()) {
            e.setCancelled(true);
            Block bookshelf = e.getClickedBlock();

            Inventory inv = Bukkit.createInventory(new BlockInventoryHolder() {
                @Override
                public Block getBlock() {
                    return bookshelf;
                }

                //this is absolutely awful, but I dont know how to get a reference to the object this is being created in the constructor of?
                @Override
                public Inventory getInventory() {
                    return null;
                }
            }, 18, "Bookshelf");


            ItemStack[] itemStacks = WorldBlockInventories.getInvOf(bookshelf.getLocation());
            if(itemStacks != null) {
                inv.setContents(itemStacks);
                p.openInventory(inv);

            }else if((p.getOpenInventory().getType().equals(InventoryType.CRAFTING) || p.getOpenInventory().getType().equals(InventoryType.CREATIVE))){
                p.sendMessage(ChatColor.DARK_RED + "This bookshelf is unavaliable at the moment!");
            }

        }

    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent e) {
        Block b = e.getBlock();

        if (b.getType().equals(Material.BOOKSHELF)) {
            ItemStack[] itemStackArr = WorldBlockInventories.getInvOf(b.getLocation());

            if (itemStackArr == null) {
                //bookshelf contents locked, prevent breaking.
                ((BlockBreakEvent) e).setCancelled(true);
            }else{
                WorldBlockInventories.setInvOf(b.getLocation(), null);
                for (ItemStack is : itemStackArr) {
                    if (is != null) {
                        b.getWorld().dropItemNaturally(b.getLocation(), is);
                    }
                }
            }
        }

    }

    public boolean isOK(Material m){
        for (Material mat : okItems){
            if(mat.equals(m)){
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick (InventoryClickEvent e) {

        InventoryHolder h = e.getView().getTopInventory().getHolder();
        if (h instanceof BlockInventoryHolder) {

            BlockInventoryHolder blockHolder = (BlockInventoryHolder) h;
            if (blockHolder.getBlock().getType().equals(Material.BOOKSHELF)) {

                InventoryAction a = e.getAction();
                Bukkit.getConsoleSender().sendMessage("Action type: " + a);

                if(a.name().equals("HOTBAR_SWAP") || a.name().equals("HOTBAR_MOVE_AND_READD")){
                    e.setCancelled(true);
                }else if(e.getCurrentItem() == null){
                    return;
                }else if(isOK(e.getCurrentItem().getType())){
                    if(a.name().equals("NOTHING")) {
                        e.setCurrentItem(e.getCursor().clone());
                        e.getCursor().setAmount(0);
                    }
                }else{
                    e.setCancelled(true);
                }
            }

        }

    }

    /*
    @EventHandler
    public void onPistonMove (BlockPistonEvent e) {
        Block b = e.getBlock();
        BlockFace bf = e.getDirection();
        Vector v = bf.getDirection();

        Bukkit.getConsoleSender().sendMessage("test");

    }
     */

    @EventHandler
    public void onInventoryClose (InventoryCloseEvent e) {
        Inventory i = e.getInventory();

        if (i.getHolder() instanceof BlockInventoryHolder && ((BlockInventoryHolder) i.getHolder()).getBlock().getType().equals(Material.BOOKSHELF) ){
            BlockInventoryHolder holder = (BlockInventoryHolder)i.getHolder();
            Block bookshelf = holder.getBlock();

            WorldBlockInventories.setInvOf(bookshelf.getLocation(), i.getContents());
        }
    }


}

