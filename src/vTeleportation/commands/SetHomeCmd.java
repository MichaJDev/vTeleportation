package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;

public class SetHomeCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IHomesHandler homes;

	public SetHomeCmd(Main _main, IHomesHandler _homes) {
		main = _main;
		homes = _homes;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("sethome")) {
			if (p.hasPermission("vCore.homes.*") || p.hasPermission("vCore.homes.sethome")) {
				if (args.length == 0) {
					homes.setHome(p, "0");
				} else if (args.length == 1) {
					homes.setHome(p, args[0]);
				} else {
					p.sendMessage(main.msg("&cToo many arguments."));
				}
			}
		}
		return false;
	}

}
