package de.endergamer.packages.commands;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.countdowns.LobbyCountdown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {


    private EnderMain instance;
    private static final int START_SECONDS = 10;

    public StartCommand(EnderMain instance){
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("Mineigame.Start")){
                if(args.length == 0){
                    if(instance.getGameStateManager().getCurrentgamestate() instanceof LobbyState){
                        LobbyState state = (LobbyState) instance.getGameStateManager().getCurrentgamestate();
                        if(state.getCountdown().isRunning() && state.getCountdown().getSeconds() > START_SECONDS){
                            state.getCountdown().setSeconds(START_SECONDS);
                            Bukkit.broadcastMessage(EnderMain.PREFIX + "§aDer Spielstart wurde beschleunigt");
                        }else {
                            player.sendMessage(EnderMain.PREFIX + "§cDas spiel ist bereits gestartet oder du bist zu spät!");
                        }
                    }else {
                        player.sendMessage(EnderMain.PREFIX + "§cDas Spiel ist bereits gestartet!");
                    }
                }else{
                    player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §6/start§c!");
                }
            }else {
                player.sendMessage(EnderMain.NO_PERMISSION);
            }
        }
        return false;
    }
}
