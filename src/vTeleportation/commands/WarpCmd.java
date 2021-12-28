package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.warps.interfaces.IWarpsHandler;

public class WarpCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IWarpsHandler warps;

	public WarpCmd(Main _main, IWarpsHandler _warps) {
		main = _main;
		warps = _warps;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("warp")) {
			if (p.hasPermission("vCore.warps.*") || p.hasPermission("vCore.warps.warp")) {
				if (args.length == 0) {
					p.sendMessage(main.msg("&cToo few arguments:&r /warp <name>"));
				} else if (args.length == 1) {
					if (warps.getWarp(args[0]) != null) {
						p.teleport(warps.getWarp(args[0]));
					} else {
						p.sendMessage(main.msg("&cWarp &r" + args[0] + "&c not found!"));
					}
				} else {
					p.sendMessage("&cToo many argugments.");
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
