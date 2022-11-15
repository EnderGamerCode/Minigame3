package de.endergamer.packages.listeners;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.IngameState;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomDrops implements Listener {

    private EnderMain instance;
    private ArrayList<ItemStack> Ditems = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> Gitems = new ArrayList<ItemStack>();
    private ArrayList<ItemStack> Ritems = new ArrayList<ItemStack>();


    public RandomDrops(EnderMain instance){
        this.instance = instance;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setDropItems(false);
        World world = Bukkit.getWorld("world");
        if(instance.getGameStateManager().getCurrentgamestate() instanceof LobbyState){
            event.setCancelled(true);
        }
        if(instance.getGameStateManager().getCurrentgamestate() instanceof IngameState){
            if(event.getBlock().getType() == Material.DIAMOND_ORE){
                Ditems.add(new ItemBuilder(Material.DIAMOND).build());
                Ditems.add(new ItemBuilder(Material.DIAMOND_SWORD).build());
                Ditems.add(new ItemBuilder(Material.DIAMOND_LEGGINGS).build());
                Ditems.add(new ItemBuilder(Material.DIAMOND_HELMET).build());
                Ditems.add(new ItemBuilder(Material.DIAMOND_BLOCK).build());
                Random random = new Random();
                ItemStack randomitem = Ditems.get(random.nextInt(Ditems.size()));
                world.dropItem(event.getBlock().getLocation(), randomitem);
            }
            if(event.getBlock().getType() == Material.GOLD_ORE){
                Gitems.add(new ItemBuilder(Material.GOLD_INGOT).build());
                Gitems.add(new ItemBuilder(Material.GOLDEN_SWORD).build());
                Gitems.add(new ItemBuilder(Material.GOLDEN_CHESTPLATE).build());
                Gitems.add(new ItemBuilder(Material.GOLDEN_HELMET).build());
                Gitems.add(new ItemBuilder(Material.GOLD_BLOCK).build());
                Random random = new Random();
                ItemStack randomitem = Gitems.get(random.nextInt(Gitems.size()));
                world.dropItem(event.getBlock().getLocation(), randomitem);

            }
            if(event.getBlock().getType() == Material.REDSTONE_ORE){
                Ritems.add(new ItemBuilder(Material.REDSTONE).build());
                Ritems.add(new ItemBuilder(Material.FLINT_AND_STEEL).build());
                Ritems.add(new ItemBuilder(Material.WATER_BUCKET).build());
                Ritems.add(new ItemBuilder(Material.DIAMOND_HELMET).build());
                Ritems.add(new ItemBuilder(Material.DIAMOND_BLOCK).build());
                Random random = new Random();
                ItemStack randomitem = Ritems.get(random.nextInt(Ritems.size()));
                world.dropItem(event.getBlock().getLocation(), randomitem);


            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(instance.getGameStateManager().getCurrentgamestate() instanceof  LobbyState){
            event.setCancelled(true);
        }
    }
}
