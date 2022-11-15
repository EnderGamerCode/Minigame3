package de.endergamer.packages.countdowns;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class AlmostCountdown extends Countdown{

    private int seconds = 10;
    private EnderMain instance;

    public AlmostCountdown(EnderMain instance){
        this.instance = instance;
    }

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 5: case 3: case 2:
                        Bukkit.broadcastMessage(EnderMain.PREFIX + "§7Das Spiel startet in §a" + seconds + "§7 Sekunden");
                        break;
                    case 1:
                        Bukkit.broadcastMessage(EnderMain.PREFIX + "§7Das Spiel startet in §aeiner§7 Sekunde");
                        break;
                    case 0:
                        stop();
                        Bukkit.broadcastMessage(EnderMain.PREFIX + "§aViel Spaß!");
                        for (Player current: Bukkit.getOnlinePlayers()){
                            current.getInventory().addItem(new ItemBuilder(Material.DIAMOND_PICKAXE).setUnbreakable(true).build());
                        }
                        break;
                    default:
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

}
