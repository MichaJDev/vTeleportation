package vTeleportation.handlers.warps.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IWarpsHandler {

	public void createWarp(Player p, String warpName);

	public Location getWarp(String warpName);

	public Boolean warpFolderExists();

	public String getWarpsString();

	public void setupWarps();

	public void delWarp(Player p, String warpName);
}
