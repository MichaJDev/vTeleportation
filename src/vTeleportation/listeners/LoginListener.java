package vTeleportation.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;
import vTeleportation.handlers.spawn.interfaces.ISpawnHandler;

public class LoginListener implements Listener {

	private Main main = Main.getInstance();

	private ISpawnHandler spawn;
	private IHomesHandler homes;

	public LoginListener(Main _main, ISpawnHandler _spawn, IHomesHandler _homes) {
		main = _main;
		spawn = _spawn;
		homes = _homes;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		// Tp player to setspawn instead of main spawn on first join
		if (!p.hasPlayedBefore()) {
			main.getLogger().info(p.getName() + " logged in for the first time teleporting to set spawnpoint");

			p.teleport(spawn.getSpawn(e.getPlayer()));

		}
		if (!homes.homesFolderExists(p)) {
			homes.setupHomes(p);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (p.getBedSpawnLocation() == null || !e.isBedSpawn()) {
			p.sendMessage(main.msg("&cNo bed location found"));
			p.teleport(spawn.getSpawn(p));
		} else {
			p.teleport(p.getBedLocation());
		}
	}
}
