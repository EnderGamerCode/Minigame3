package de.endergamer.packages.commands;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.util.ConfigLocationUtil;
import de.endergamer.packages.voting.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private EnderMain instance;

    public SetupCommand(EnderMain instance){
        this.instance = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("Mineigame.setup")){
                if(args.length == 0){
                    player.sendMessage(EnderMain.PREFIX + "§cBitte nutze §6/setup <LOBBY>");
                }else {
                    if (args[0].equalsIgnoreCase("lobby")){
                        if (args.length == 1){
                            new ConfigLocationUtil(instance, player.getLocation(), "Lobby").saveLoc();
                            player.sendMessage(EnderMain.PREFIX + "§aDie Lobby wurde neu gesetzt!");
                        }else {
                            player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §6/setup lobby§c!");
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        if(args.length == 3){
                            Map map = new Map(instance, args[1]);
                            if(!map.exists()){
                                map.create(args[2]);
                                player.sendMessage(EnderMain.PREFIX + "§aDie Map §c" + map.getName() + "§a wurde erstellt!");
                            }else {
                                player.sendMessage(EnderMain.PREFIX + "§cDiese Map existiert bereits!");
                            }
                        }else {
                            player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §c/setup create <NAME> <ERBAUER>§c!");
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        if(args.length == 3){
                            Map map = new Map(instance, args[1]);
                            if(map.exists()){
                                try {
                                    int spawnNumber = Integer.parseInt(args[2]);
                                    if(spawnNumber > 0 && spawnNumber <= LobbyState.MAX_PLAYERS){
                                            map.setSpawnlocation(spawnNumber, player.getLocation());
                                            player.sendMessage(EnderMain.PREFIX + "§aDu hast die Spawn-Location §a" + spawnNumber + "§a für die Map §6" + map.getName() + "§a gesetzt");
                                    }else {
                                        player.sendMessage(EnderMain.PREFIX + "§cBitte gib eine Zahl zwischen 1 und §a" + LobbyState.MAX_PLAYERS + "§can");
                                    }
                                }catch (NumberFormatException e){
                                    if(args[2].equalsIgnoreCase("spectator")){
                                        map.setSpectatorLocation(player.getLocation());
                                        player.sendMessage(EnderMain.PREFIX + "§aDu hast die §6Spectator-Location §afür die Map §6" + map.getName() + "§a gesetzt");
                                    }else {
                                        player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §c/setup set <NAME> <1-" + LobbyState.MAX_PLAYERS + " //SPECTATOR>");
                                    }
                                }
                            }else {
                                player.sendMessage(EnderMain.PREFIX + "§cDiese Map existiert noch nicht!");
                            }
                        }else
                            player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §c/setup set <NAME> <1-" + LobbyState.MAX_PLAYERS + " //SPECTATOR>");
                    }else {
                        player.sendMessage(EnderMain.PREFIX + "§cBitte benutze §6/setup...");
                }
                }
            }else {
                player.sendMessage(EnderMain.NO_PERMISSION);
            }
        }
        return false;
    }
}
