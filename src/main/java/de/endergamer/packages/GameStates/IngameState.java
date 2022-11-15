package de.endergamer.packages.GameStates;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.countdowns.AlmostCountdown;
import de.endergamer.packages.voting.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;


public class IngameState extends GameState{

    private Map map;
    private EnderMain instance;
    private ArrayList<Player> players;
    private ArrayList<Player> dead;
    private AlmostCountdown almostCountdown;

    public IngameState(EnderMain instance){
        this.instance = instance;
        almostCountdown = new AlmostCountdown(instance);
    }




    @Override
    public void start() {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/world world");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos1 -28,92,-20");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos2 -58,76,-49 ");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/set 2%diamond_ore,5%gold_ore,5%redstone_ore,88%stone");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -43 89 -37 -41 91 -35 air");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -29 84 -34 -31 86 -36 air");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -42 84 -44 -44 86 -46 air");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -43 87 -21 -45 85 -23 air");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "fill -55 87 -32 -57 85 -34 air");
            almostCountdown.start();
            Collections.shuffle(instance.getPlayers());
            players = instance.getPlayers();
            dead = instance.getDead();
            map = instance.getVoting().getWinnerMap();
            map.load();
            for (int i = 0; i < players.size(); i++)
                players.get(i).teleport(map.getSpawnlocations()[i]);
            for (Player current: players){
                current.getInventory().clear();
                current.setHealth(20);
            }
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=!player]");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=!player]");

    }

    @Override
    public void stop() {
        FileConfiguration configuration = instance.getConfig();
        for (Player current: players){
            World world = Bukkit.getWorld(configuration.getString("world"));
            double x = configuration.getDouble( "Lobby.X"),
                    y = configuration.getDouble( "Lobby.Y"),
                    z = configuration.getDouble("Lobby.Z");
            float yaw = (float) configuration.getDouble( "Lobby.Yaw"),
                    pitch = (float) configuration.getDouble( "Lobby.Pitch");
            current.teleport(new Location(world,x,y,z,yaw,pitch));
            instance.getGameStateManager().setGameState(GameState.LOBBY_STATE);
            new LobbyState(instance.getGameStateManager()).start();
        }
    }
}
