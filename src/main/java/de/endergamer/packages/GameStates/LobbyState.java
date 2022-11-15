package de.endergamer.packages.GameStates;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.countdowns.LobbyCountdown;
import de.endergamer.packages.voting.Voting;
import org.bukkit.Bukkit;

public class LobbyState extends GameState{

    public static final int MIN_PLAYERS = 1,
                            MAX_PLAYERS = 5;


    private LobbyCountdown countdown;

    public LobbyState(GameStateManager manager){
        countdown = new LobbyCountdown(manager);
    }
    @Override
    public void start() {
        countdown.startIdle();
    }

    @Override
    public void stop() {
        Bukkit.broadcastMessage("§cAlle Spieler werden teleportiert ");
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
