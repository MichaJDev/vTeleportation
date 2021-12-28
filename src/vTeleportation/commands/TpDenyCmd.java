package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;

public class TpDenyCmd implements CommandExecutor {

	@SuppressWarnings("unused")
	private Main main = Main.getInstance();
	private ITpHandler tp;

	public TpDenyCmd(Main _main, ITpHandler _tp) {
		main = _main;
		tp = _tp;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		Player target = main.getServer().getPlayer(args[0]);
		if (c.getName().equalsIgnoreCase("tpaccept")) {
			if (!tp.getToggleList().contains(target)) {
				if (p.hasPermission("vCore.teleport.*") || p.hasPermission("vCore.teleport.tpaccept")) {
					tp.tpaccept(p);
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
