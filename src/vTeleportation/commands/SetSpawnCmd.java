package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.spawn.interfaces.ISpawnHandler;

public class SetSpawnCmd implements CommandExecutor {

	private Main main = Main.getInstance();
	private ISpawnHandler spawn;

	public SetSpawnCmd(Main _main, ISpawnHandler _spawn) {
		main = _main;
		spawn = _spawn;

	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("setspawn")) {
			if (p.hasPermission("vCore.spawn.*") || p.hasPermission("vCore.spawn.setspawn")) {
				p.sendMessage(main.msg("&cSetting spawn"));
				main.getLogger()
						.info("Setting new spawn at location of: " + p.getName() + "World: " + p.getWorld() + " x:"
								+ p.getLocation().getBlockX() + " y: " + p.getLocation().getBlockY() + " z: "
								+ p.getLocation().getBlockZ());
				spawn.setSpawn(p);
			}
			return false;
		}
		return false;
	}

}
