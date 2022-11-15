package de.endergamer.packages.GameStates;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.countdowns.EndingCountdown;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class EndingState extends GameState{
    private EndingCountdown endingCountdown;

    public EndingState(EnderMain instance) {
        endingCountdown = new EndingCountdown(instance);
    }

    @Override
    public void start() {
        endingCountdown.start();

    }

    @Override
    public void stop() {
        for (Player current: Bukkit.getOnlinePlayers()){
            current.kickPlayer("Das Spiel hat geendet!");
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
    }
}
