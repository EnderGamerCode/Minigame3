package de.endergamer.packages.countdowns;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.GameState;
import de.endergamer.packages.GameStates.GameStateManager;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.voting.Map;
import de.endergamer.packages.voting.Voting;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;

public class LobbyCountdown  extends Countdown{

    private static final int COUNTDOWN_TIME = 30,IDLE_TIME = 20;
    private boolean isRunning;


    private int seconds;
    private int idleID;
    private boolean isIdling;
    private GameStateManager manager;
    public LobbyCountdown(GameStateManager manager){
        this.manager = manager;
        seconds = COUNTDOWN_TIME;
    }


    @Override
    public void start() {
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(manager.getInstance(), new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 30: case 20: case 10: case 3: case 2: case 1:
                        Bukkit.broadcastMessage(EnderMain.PREFIX + "§7Das Spiel startet in §a " + seconds + " Sekunden");

                        if(seconds == 3){
                            Voting voting = manager.getInstance().getVoting();
                            Map winningMap;
                            if(voting != null){
                                winningMap = voting.getWinnerMap();
                            }else {
                                ArrayList<Map> maps = manager.getInstance().getMaps();
                                Collections.shuffle(maps);
                                winningMap = maps.get(0);
                            }
                            Bukkit.broadcastMessage(EnderMain.PREFIX + "§aSieger des Votings ist: §6" + winningMap.getName());
                        }

                        break;
                    case 0:
                        manager.setGameState(GameState.INGAME_STATE);
                        break;
                    default:
                        break;
                }
                seconds--;
            }
        },0, 20);
    }

    @Override
    public void stop() {
        if(isRunning){
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = COUNTDOWN_TIME;
        }
    }
    public void startIdle(){
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(manager.getInstance(), new Runnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(EnderMain.PREFIX + "§7Bis zum Spielstart fehlen noch §6" + (LobbyState.MIN_PLAYERS - manager.getInstance().getPlayers().size()) + " Spieler.");
            }
        },0, 20 * IDLE_TIME);
    }

    public void stopIdle(){
        if(isIdling){
            Bukkit.getScheduler().cancelTask(idleID);
            isIdling=false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
