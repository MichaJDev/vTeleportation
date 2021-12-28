package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;

public class TpCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private ITpHandler tp;

	public TpCmd(Main _main, ITpHandler _tp) {
		main = _main;
		tp = _tp;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String arlg2, String[] args) {
		Player p = (Player) s;
		Player target = main.getServer().getPlayer(args[0]);
		if (c.getName().equalsIgnoreCase("tp")) {
			if (p.hasPermission("vCore.teleport.tp")) {
				if (!tp.getToggleList().contains(target)) {
					if (args.length > 0 && args.length < 1) {
						main.getLogger().info("Teleporting: " + p.getName() + " to: " + args[0]);
						tp.teleport(p, args[0]);
						return false;
					}
					return false;
				}
			} else {
				p.sendMessage(main.msg("&cYou have no permission for that"));
				return false;
			}
			return false;
		}
		return false;
	}

}
