package de.endergamer.packages.listeners;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.IngameState;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.countdowns.LobbyCountdown;
import de.endergamer.packages.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLobbyConnectionListener implements Listener {


    private EnderMain instance;
    public PlayerLobbyConnectionListener(EnderMain instance){
        this.instance = instance;
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(instance.getGameStateManager().getCurrentgamestate() instanceof IngameState){
            event.getPlayer().kickPlayer("Das Spiel läuft schon!");
        }
        Player player = event.getPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        instance.getPlayers().add(player);
        if(!event.getPlayer().hasPlayedBefore()){
            event.setJoinMessage(EnderMain.PREFIX + ChatColor.AQUA + "Sagt Willkommen zu " + ChatColor.GOLD + player.getDisplayName() + ChatColor.AQUA + "!");
        }else {
            event.setJoinMessage(EnderMain.PREFIX + ChatColor.GOLD + player.getDisplayName() + ChatColor.AQUA + " hat den Server betreten! [" +
                    instance.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS + "]");
            ConfigLocationUtil locationUtil = new ConfigLocationUtil(instance, "Lobby");
            if(locationUtil.loadLocation() != null){
                player.teleport(locationUtil.loadLocation());
            }else
                Bukkit.getConsoleSender().sendMessage("§cDie Lobby-Location wurde noch nicht gesetzt!");
        }
        LobbyState lobbyState = (LobbyState) instance.getGameStateManager().getCurrentgamestate();
        LobbyCountdown countdown = lobbyState.getCountdown();

        if(instance.getPlayers().size() >= LobbyState.MIN_PLAYERS){
            if(!countdown.isRunning()){
                countdown.stopIdle();
                countdown.start();
            }
        }

    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        instance.getPlayers().remove(player);
        event.setQuitMessage(EnderMain.PREFIX + ChatColor.GOLD + player.getDisplayName() + ChatColor.AQUA + " hat den Server verlassen! [" +
                instance.getPlayers().size() + "/" + LobbyState.MAX_PLAYERS + "]");

        LobbyState lobbyState = (LobbyState) instance.getGameStateManager().getCurrentgamestate();
        LobbyCountdown countdown = lobbyState.getCountdown();
        if (instance.getPlayers().size() < LobbyState.MIN_PLAYERS){
            if(countdown.isRunning()){
                countdown.stop();
                countdown.startIdle();
            }
        }

    }
}
