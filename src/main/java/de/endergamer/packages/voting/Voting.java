package de.endergamer.packages.voting;

import de.endergamer.packages.EnderMain;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Voting {

    private EnderMain instance;
    private ArrayList<Map> maps;
    private Map[] votingMaps;
    private HashMap<String, Integer> playerVotes;
    public static final int MAP_AMOUNT = 1;

    public Voting(EnderMain instance, ArrayList<Map> maps){
        this.instance = instance;
        this.maps = maps;
        this.votingMaps = new Map[Voting.MAP_AMOUNT];

        chooseRandomMaps();
    }

    private void chooseRandomMaps(){
        for (int i = 0; i < votingMaps.length; i++){
            Collections.shuffle(maps);
            votingMaps[i] = maps.remove(0);

        }
    }

    public Map getWinnerMap(){
        Map winnerMap = votingMaps[0];
        for (int i = 1; i < votingMaps.length; i++){
            if(votingMaps[i].getVotes() >= winnerMap.getVotes())
                winnerMap = votingMaps[i];
        }
        return winnerMap;
    }

    public HashMap<String, Integer> getPlayerVotes() {
        return playerVotes;
    }
}
