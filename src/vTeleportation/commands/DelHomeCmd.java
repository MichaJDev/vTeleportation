package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;

public class DelHomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IHomesHandler homes;

	public DelHomeCmd(Main _main, IHomesHandler _homes) {
		main = _main;
		homes = _homes;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("delhome")) {
			if (p.hasPermission("vCore.homes.*") || p.hasPermission("vCore.homes.delhome")) {
				if (homes.doesHomeExists(p, args[0])) {
					main.getLogger().info("Deleting home for: " + p.getName() + " Home: " + args[0]);
					homes.delHome(p, args[0]);
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
