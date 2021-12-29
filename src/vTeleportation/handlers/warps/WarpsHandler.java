package vTeleportation.handlers.warps;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import vCore.Config.Interface.IConfigBuilder;
import vTeleportation.Main;
import vTeleportation.handlers.warps.interfaces.IWarpsHandler;

public class WarpsHandler implements IWarpsHandler {

	private Main main = Main.getInstance();
	private IConfigBuilder cfg;

	public WarpsHandler(Main _main, IConfigBuilder _cfg) {
		main = _main;
		cfg = _cfg;
	}

	@Override
	public void delWarp(Player p, String warpName) {
		File file = getWarpFile(warpName);
		if (file.exists()) {
			file.delete();
		} else {
			p.sendMessage(main.msg("&cWarp: " + warpName + "&c has been deleted"));
		}
	}

	@Override
	public void createWarp(Player p, String warpName) {

		if (!doesWarpExist(warpName)) {
			p.sendMessage(main.msg("&6Creating warp for: &r" + warpName));
			createWarpFile(p, warpName);
		} else {
			p.sendMessage(main.msg("&cWarp already exists."));
		}

	}

	@Override
	public Location getWarp(String warpName) {
		FileConfiguration cfg = getWarpCfg(warpName);
		World w = main.getServer().getWorld(cfg.getString("world"));
		double x, y, z;
		x = cfg.getDouble("x");
		y = cfg.getDouble("y");
		z = cfg.getDouble("z");
		return new Location(w, x, y, z);
	}

	@Override
	public Boolean warpFolderExists() {
		File dir = new File(cfg.getMainFolder() + File.separator + "Warps");
		if (!dir.exists())
			return false;
		return true;
	}

	@Override
	public String getWarpsString() {
		StringBuilder sb = new StringBuilder();
		if (getWarpFolder().listFiles() == null) {
			sb.append("No warps found.");
		} else {
			for (File f : getWarpFolder().listFiles()) {
				sb.append(f.getName() + ", ");
			}
		}
		return sb.toString();
	}

	@Override
	public void setupWarps() {
		if (!warpFolderExists()) {
			createWarpFolder();
		} else {
			main.getLogger().info("Warps found......");
		}
	}

	private void createWarpFile(Player p, String warpName) {
		File file = getWarpFile(warpName);
		FileConfiguration cfg = getWarpCfg(warpName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				fillWarpCfg(p, warpName, cfg);
				p.sendMessage(main.msg("&6Warp created for: " + warpName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createWarpFolder() {
		File dir = new File(cfg.getMainFolder() + File.separator + "Warps");
		if (dir.mkdirs()) {
			main.getLogger().info("User WarpsFolder Created.");
		} else {
			main.getLogger().info("Failed to create User WarpsFolder.");
		}
	}

	private File getWarpFolder() {
		File dir = new File(cfg.getMainFolder() + File.separator + "Warps");
		if (!dir.exists())
			return null;
		return dir;
	}

	private File getWarpFile(String warpName) {
		File file = new File(getWarpFolder(), warpName + ".yml");
		if (!file.exists())
			return null;
		return file;
	}

	private FileConfiguration getWarpCfg(String warpName) {
		return YamlConfiguration.loadConfiguration(getWarpFile(warpName));
	}

	private void fillWarpCfg(Player p, String warpName, FileConfiguration cfg) {
		cfg.addDefault("world", p.getWorld());
		cfg.addDefault("x", p.getLocation().getBlockX());
		cfg.addDefault("y", p.getLocation().getBlockY());
		cfg.addDefault("z", p.getLocation().getBlockZ());
		cfg.options().copyDefaults(true);
		saveFile(cfg, warpName);
	}

	private Boolean doesWarpExist(String warpName) {
		File file = getWarpFile(warpName);
		if (!file.exists())
			return false;
		return true;
	}

	private void saveFile(FileConfiguration cfg, String warpName) {
		try {
			cfg.save(getWarpFile(warpName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
