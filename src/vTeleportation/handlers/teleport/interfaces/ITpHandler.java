package vTeleportation.handlers.teleport.interfaces;

import java.util.List;

import org.bukkit.entity.Player;

public interface ITpHandler {

	public void teleport(Player p, String target);
	public void teleport(Player p, Player target);

	public void tpa(Player p, String target);

	public void tpaccept(Player p);

	public void tpdeny(Player p);

	public void tpToggle(Player p);
	
	public List<Player> getToggleList();
}
