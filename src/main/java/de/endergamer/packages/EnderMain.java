package de.endergamer.packages;

import de.endergamer.packages.GameStates.GameState;
import de.endergamer.packages.GameStates.GameStateManager;
import de.endergamer.packages.commands.SetupCommand;
import de.endergamer.packages.commands.StartCommand;
import de.endergamer.packages.listeners.DieListener;
import de.endergamer.packages.listeners.PlayerLobbyConnectionListener;
import de.endergamer.packages.listeners.RandomDrops;
import de.endergamer.packages.voting.Map;
import de.endergamer.packages.voting.Voting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class EnderMain extends JavaPlugin {

    public static final String PREFIX = "§7[§cMINERGAME§7] ",
                                NO_PERMISSION = PREFIX + "§cDazu hast du keine Rechte!";
    private static EnderMain instance;
    private GameStateManager gameStateManager;
    private ArrayList<Player> players;
    private Voting voting;
    private ArrayList<Map> maps;
    private ArrayList<Player> dead;
    @Override
    public void onEnable() {
        gameStateManager = new GameStateManager(this);
        players = new ArrayList<>();
        dead = new ArrayList<>();
       //instance
        instance = this;
        //PluginManager
        init(Bukkit.getPluginManager());
    }
    private void init(PluginManager manager){
        manager.registerEvents(new PlayerLobbyConnectionListener(this), this);
        manager.registerEvents(new DieListener(this), this);
        manager.registerEvents(new RandomDrops(this), this);
        System.out.println("[GAME] wurde gestartet");
        gameStateManager.setGameState(GameState.LOBBY_STATE);
        getCommand("setup").setExecutor(new SetupCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        initVoting();
    }

    private void initVoting(){
        maps = new ArrayList<>();
        for (String current : getConfig().getConfigurationSection("Arenas").getKeys(false)){
            Map map = new Map(this, current);
            if ((map.playable()))
                maps.add(map);
            else
                Bukkit.getConsoleSender().sendMessage("§cDie map §4" + map.getName() + "§c ist noch nicht fertig eingerichtet.");
        }
        if(maps.size() >= Voting.MAP_AMOUNT)
            voting = new Voting(this, maps);
        else {
            Bukkit.getConsoleSender().sendMessage("§cFür das Voting müssen mindestens §4" + Voting.MAP_AMOUNT + "§c Maps eingerichtet sein");
            voting = null;
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[GAME] wurde gestoppt");
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public static EnderMain getInstance() {
        return instance;
    }

    public Voting getVoting() {
        return voting;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public ArrayList<Player> getDead() {
        return dead;
    }
}
