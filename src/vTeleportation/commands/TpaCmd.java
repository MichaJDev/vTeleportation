package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;

public class TpaCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private ITpHandler tp;

	public TpaCmd(Main _main, ITpHandler _tp) {
		main = _main;
		tp = _tp;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		Player target = main.getServer().getPlayer(args[0]);
		if (c.getName().equalsIgnoreCase("tpa")) {
			if (p.hasPermission("vCore.teleport.*") || p.hasPermission("vCore.teleportation.tpa")) {
				if (!tp.getToggleList().contains(target)) {
					main.getLogger().info(p.getName() + " &6send a tpa request to: &r " + args[0]);
					tp.tpa(p, args[0]);
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

}
