package vTeleportation.handlers.spawn;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import vCore.Config.Interface.IConfigBuilder;
import vCore.Data.Builders.Users.Interface.IUserBuilder;
import vTeleportation.Main;
import vTeleportation.handlers.spawn.interfaces.ISpawnHandler;

public class SpawnHandler implements ISpawnHandler {

	Main main = Main.getInstance();
	IUserBuilder user;
	IConfigBuilder cfg;

	public SpawnHandler(Main _main, IUserBuilder _user, IConfigBuilder _cfg) {
		main = _main;
		user = _user;
		cfg = _cfg;
	}

	@Override
	public Location getSpawn(Player p) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(getSpawnFile());
		World w = main.getServer().getWorld(cfg.getString("Spawn." + p.getWorld()));
		double x = cfg.getDouble("Spawn" + p.getWorld() + ".x");
		double y = cfg.getDouble("Spawn" + p.getWorld() + ".y");
		double z = cfg.getDouble("Spawn" + p.getWorld() + ".z");
		return new Location(w, x, y, z);
	}

	@Override
	public void setSpawn(Player p) {
		FileConfiguration cfg = getSpawnCfg();
		cfg.set("Spawn." + p.getWorld(), p.getWorld());
		cfg.set("Spawn" + p.getWorld() + ".x", p.getLocation().getBlockX());
		cfg.set("Spawn" + p.getWorld() + ".y", p.getLocation().getBlockY());
		cfg.set("Spawn" + p.getWorld() + ".z", p.getLocation().getBlockZ());
		cfg.options().copyDefaults(true);
		try {
			cfg.save(getSpawnFile());
			p.sendMessage(main.msg("&cSpawn succesfully set!"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getSpawnFolder() {
		return new File(cfg.getMainFolder() + File.separator + "Spawn");
	}

	private File getSpawnFile() {
		return new File(getSpawnFolder(), "spawn.yml");
	}

	private FileConfiguration getSpawnCfg() {
		return YamlConfiguration.loadConfiguration(getSpawnFile());
	}

	@Override
	public void setupSpawn() {
		if (!getSpawnFolder().exists()) {
			createSpawnFolder();
			createSpawnFile();
		}
	}

	private void createSpawnFolder() {
		File dir = new File(cfg.getMainFolder() + File.separator + "Spawn");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	private void createSpawnFile() {
		File file = new File(getSpawnFolder(), "spawn.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Boolean spawnFilesExist() {
		if (!getSpawnFile().exists() && !getSpawnFolder().exists())
			return false;
		return true;
	}
}
