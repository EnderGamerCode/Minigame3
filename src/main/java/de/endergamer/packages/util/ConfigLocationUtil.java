package de.endergamer.packages.util;

import de.endergamer.packages.EnderMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLocationUtil {

    private EnderMain instance;
    private Location location;
    private String root;


    public ConfigLocationUtil(EnderMain instance, Location location, String root){
        this.instance = instance;
        this.root = root;
        this.location = location;
    }

    public ConfigLocationUtil(EnderMain instance, String root){
        this.instance = instance;
        this.root = root;
    }

    public void saveLoc(){
        FileConfiguration config = EnderMain.getInstance().getConfig();
        config.set(root + ".World", location.getWorld().getName());
        config.set(root + ".X", location.getX());
        config.set(root + ".Y", location.getY());
        config.set(root + ".Z", location.getZ());
        config.set(root + ".Yaw", location.getYaw());
        config.set(root + ".Pitch", location.getPitch());
        instance.saveConfig();
    }

    public Location loadLocation() {
        FileConfiguration configuration = instance.getConfig();
        if (configuration.contains(root)) {
            World world = Bukkit.getWorld(configuration.getString(root + ".World"));
            double x = configuration.getDouble(root + ".X"),
                    y = configuration.getDouble(root + ".Y"),
                    z = configuration.getDouble(root + ".Z");
            float yaw = (float) configuration.getDouble(root + ".Yaw"),
                    pitch = (float) configuration.getDouble(root + ".Pitch");

            return new Location(world, x, y, z, yaw, pitch);
        }
        return null;
    }
}
