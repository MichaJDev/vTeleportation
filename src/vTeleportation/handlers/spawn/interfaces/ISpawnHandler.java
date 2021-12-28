package vTeleportation.handlers.spawn.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ISpawnHandler {

	public void setupSpawn();

	public Location getSpawn(Player p);

	public void setSpawn(Player p);

	public Boolean spawnFilesExist();

}
