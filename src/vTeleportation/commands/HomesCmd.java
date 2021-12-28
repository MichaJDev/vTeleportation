package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;

public class HomesCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IHomesHandler homes;

	public HomesCmd(Main _main, IHomesHandler _homes) {
		main = _main;
		homes = _homes;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("homes")) {
			if (p.hasPermission("vCore.homes.*") || p.hasPermission("vCore.homes.homes")) {
				if (args.length > 0)
					p.sendMessage(main.msg("&cToo many arguments."));
				p.sendMessage(homes.getHomesString(p));
				return false;
			}
			return false;
		}
		return false;
	}

}
