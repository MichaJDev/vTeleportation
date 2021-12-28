package vTeleportation.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.spawn.interfaces.ISpawnHandler;

public class SpawnCmd implements CommandExecutor {

	private Main main;
	private ISpawnHandler spawn;

	public SpawnCmd(Main _main, ISpawnHandler _spawn) {
		main = _main;
		spawn = _spawn;
	}

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] args) {
		Player p = (Player) s;
		if (c.getName().equalsIgnoreCase("spawn")) {
			if (p.hasPermission("vCore.spawn.*") || p.hasPermission("vCore.spawn.spawn")) {

				p.teleport(spawn.getSpawn(p));
				return false;
			} else {
				p.sendMessage(main.msg("&cSorry you don't have permissions to use that command."));
			}
		}
		return false;
	}

}
