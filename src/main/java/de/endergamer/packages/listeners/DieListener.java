package de.endergamer.packages.listeners;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.*;
import de.endergamer.packages.countdowns.EndingCountdown;
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
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.ArrayList;

public class DieListener implements Listener {

    private EnderMain instance;

    private ArrayList<Player> spectators;

    private ArrayList<Player> alive;
private EndingCountdown endingCountdown;

    public DieListener(EnderMain instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getEntity().setGameMode(GameMode.SPECTATOR);
        event.getEntity().teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().equalsIgnoreCase("world")) {
                if (p.getGameMode().equals(GameMode.SPECTATOR)) spectators.add(p);
                else if (p.getGameMode().equals(GameMode.SURVIVAL)) alive.add(p); // whatever other gamemode
            }
        }
        if(!(spectators == null || alive == null)){
        if (spectators.size() <= 1 && alive.size() == 0) {
            IngameState ingameState = (IngameState) instance.getGameStateManager().getCurrentgamestate();
            ingameState.stop();
        }
        }
        alive.clear();
        spectators.clear();
    }

}