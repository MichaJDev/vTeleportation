package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.warps.interfaces.IWarpsHandler;

public class SetWarpCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IWarpsHandler warps;

	public SetWarpCmd(Main _main, IWarpsHandler _warps) {
		main = _main;
		warps = _warps;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("setwarp")) {
			if (p.hasPermission("vCore.warps.*") || p.hasPermission("vCore.warps.setwarp")) {
				if (args.length == 0) {
					p.sendMessage(main.msg("&cToo few arguments"));
					return false;
				}
				if (args.length == 1) {
					warps.createWarp(p, args[0]);
				} else {
					p.sendMessage(main.msg("&cToo many arguments"));
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}
}
