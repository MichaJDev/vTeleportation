package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;

import vTeleportation.handlers.warps.interfaces.IWarpsHandler;

public class WarpsCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private IWarpsHandler warps;

	public WarpsCmd(Main _main, IWarpsHandler _warps) {
		main = _main;
		warps = _warps;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("warps")) {
			if (p.hasPermission("vCore.warps.*") || p.hasPermission("vCore.warps.warps")) {
				if (p.hasPermission("vCore.warps.*") || p.hasPermission("vCore.warps.warps")) {
					p.sendMessage(main.msg("&6" + warps.getWarpsString()));
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
