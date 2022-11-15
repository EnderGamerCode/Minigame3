package de.endergamer.packages.listeners;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.GameState;
import de.endergamer.packages.GameStates.GameStateManager;
import de.endergamer.packages.GameStates.IngameState;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;

public class DieListener implements Listener {

    private EnderMain instance;


    public DieListener(EnderMain instance) {
        this.instance = instance;
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        instance.getPlayers().remove(1);
        if(instance.getPlayers().size() == 1){
            player.setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcastMessage("§a" + player.getDisplayName() + "§7 wurde getötet");
            ConfigLocationUtil locationUtil = new ConfigLocationUtil(instance, "Arenas.Mineigame.Spectator");
            Location spawn = locationUtil.loadLocation();
            player.teleport(spawn);
        }
    }
}