package de.endergamer.packages.voting;

import de.endergamer.packages.EnderMain;
import de.endergamer.packages.GameStates.LobbyState;
import de.endergamer.packages.util.ConfigLocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class Map {

    private EnderMain instance;
    private String name;
    private String builder;
    private Location[] spawnlocations = new Location[LobbyState.MAX_PLAYERS];
    private Location spectatorLocation;
    private int votes;

    public Map(EnderMain instance, String name){
        this.instance = instance;
        this.name = name.toUpperCase();
    }

    public void load(){
        for (int i = 0; i < spawnlocations.length; i++)
            spawnlocations[i] = new ConfigLocationUtil(instance, "Arenas."+ name + "." + (i + 1)).loadLocation();
        spectatorLocation = new ConfigLocationUtil(instance, "Arenas." + name + ".Spectator").loadLocation();
    }

    public boolean exists(){
        return (instance.getConfig().getString("Arenas." + name + ".Builder") != null);
    }

    public boolean playable(){
        ConfigurationSection configurationSection = instance.getConfig().getConfigurationSection("Arenas." + name);
        if(!configurationSection.contains("Spectator")) return false;
        if(!configurationSection.contains("Builder")) return false;
        for (int i = 1; i < LobbyState.MAX_PLAYERS + 1; i++){
    if(!configurationSection.contains(Integer.toString(i))) return false;

        }
        return true;
    }
    public void create(String builder){
        this.builder = builder;
        instance.getConfig().set("Arenas." + name + ".Builder", builder);
        instance.saveConfig();
    }

    public void setSpawnlocation(int spawnnumber, Location location){
        spawnlocations[spawnnumber - 1] = location;
        new ConfigLocationUtil(instance, location, "Arenas." + name + "." + spawnnumber).saveLoc();
    }

    public void setSpectatorLocation(Location location){
        spectatorLocation = location;
        new ConfigLocationUtil(instance, location, "Arenas." + name + ".Spectator").saveLoc();
    }

    public String getName() {
        return name;
    }

    public String getBuilder() {
        return builder;
    }

    public Location[] getSpawnlocations() {
        return spawnlocations;
    }

    public Location getSpectatorLocation() {
        return spectatorLocation;
    }
    public int getVotes() {
        return votes;
    }
}
