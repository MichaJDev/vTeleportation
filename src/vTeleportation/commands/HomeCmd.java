package vTeleportation.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;

public class HomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IHomesHandler homes;

	public HomeCmd(Main _main, IHomesHandler _homes) {
		main = _main;
		homes = _homes;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		List<Location> homeList = homes.getHomes(p);
		if (c.getName().equalsIgnoreCase("home")) {
			if (p.hasPermission("vCore.homes.*") || p.hasPermission("vCore.homes.home")) {
				if (args.length == 0) {
					p.sendMessage(main.msg("&6Teleporting to home."));
					p.teleport(homeList.get(0));
				} else if (args.length == 1) {
					if (isInt(args[0])) {
						if (homeList.get(Integer.parseInt(args[0])) != null) {
							p.sendMessage(main.msg("&6teleporting you to home: " + args[0]));
							p.teleport(homeList.get(Integer.parseInt(args[0])));
							return false;
						} else {
							p.sendMessage(main.msg("&cHome not found"));
						}
						return false;
					} else {
						if (homeList.contains(args[0])) {
							homes.tpHome(p, args[0]);
						} else {
							p.sendMessage(main.msg("&cHome doesnt exist."));
						}
					}
				} else {
					p.sendMessage(main.msg("&cToo many arguments!"));
				}
				return false;
			}
			return false;
		}
		return false;
	}

	public boolean isInt(String args) {
		try {
			Integer.parseInt(args);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
