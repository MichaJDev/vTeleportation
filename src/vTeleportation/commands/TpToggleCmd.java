package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;

public class TpToggleCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main = Main.getInstance();
	private ITpHandler tp;

	public TpToggleCmd(Main _main, ITpHandler _tp) {
		main = _main;
		tp = _tp;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (s.getName().equalsIgnoreCase("tptoggle")) {
			if (p.hasPermission("vCore.teleport.*") || p.hasPermission("vCore.teleport.tptoggle")) {
				tp.tpToggle(p);
				return false;
			}
			return false;
		}
		return false;
	}

}
