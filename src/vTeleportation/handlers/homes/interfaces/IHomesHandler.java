package vTeleportation.handlers.homes.interfaces;

import java.io.File;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IHomesHandler {

	public List<Location> getHomes(Player p);

	public String getHomesString(Player p);

	public void setHome(Player p, String homeName);

	public File getHomeFile(Player p, String homeName);

	public void setupHomes(Player p);

	public void tpHome(Player p, String homeName);

	public Boolean homesFolderExists(Player p);

	public void delHome(Player p, String homeName);

	public Boolean doesHomeExists(Player p, String homeName);
}
