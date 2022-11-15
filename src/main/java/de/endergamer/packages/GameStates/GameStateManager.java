package de.endergamer.packages.GameStates;

import de.endergamer.packages.EnderMain;

public class GameStateManager {

    private EnderMain instance;
    private GameState[] gameStates;
    private GameState currentgamestate;

    public GameStateManager(EnderMain instance){
        this.instance = instance;
        gameStates = new GameState[3];
        gameStates[GameState.LOBBY_STATE] = new LobbyState(this);
        gameStates[GameState.INGAME_STATE] = new IngameState(instance);
        gameStates[GameState.ENDING_STATE] = new EndingState(instance);

    }

    public void setGameState(int gameStateID){
        if(currentgamestate != null) currentgamestate.stop();
            currentgamestate = gameStates[gameStateID];
            currentgamestate.start();

    }

    public void stopCurrentGameState(){
        if(currentgamestate != null){
            currentgamestate.stop();
            currentgamestate = null;
        }
    }

    public GameState getCurrentgamestate() {
        return currentgamestate;
    }

    public EnderMain getInstance() {
        return instance;
    }
}
