package vTeleportation.handlers.homes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import vCore.Data.Builders.Users.Interface.IUserBuilder;
import vTeleportation.Main;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;

public class HomesHandler implements IHomesHandler {

	private Main main = Main.getInstance();
	private IUserBuilder user;

	public HomesHandler(Main _main, IUserBuilder _user) {
		main = _main;
		user = _user;
	}

	@Override
	public void delHome(Player p, String homeName) {
		File file = getHomeFile(p, homeName);
		if (file.exists()) {
			file.delete();
		}else {
			p.sendMessage(main.msg("&cHome not found."));
		}
	}

	@Override
	public List<Location> getHomes(Player p) {
		List<Location> homes = new ArrayList<Location>();
		for (File f : getHomesFolder(p).listFiles()) {
			FileConfiguration cfg = getHomeCfg(p, f.getName());
			World w = main.getServer().getWorld(cfg.getString("world"));
			double x, y, z;
			x = cfg.getDouble("x");
			y = cfg.getDouble("y");
			z = cfg.getDouble("z");
			Location l = new Location(w, x, y, z);
			homes.add(l);
		}
		return homes;
	}

	@Override
	public String getHomesString(Player p) {
		StringBuilder sb = new StringBuilder();
		for (File f : getHomesFolder(p).listFiles()) {
			sb.append(f.getName() + ",");
		}
		return sb.toString();
	}

	@Override
	public void setHome(Player p, String homeName) {
		FileConfiguration cfg = getHomeCfg(p, homeName);
		if (!doesHomeExists(p, homeName)) {
			createHomeFile(p, homeName, cfg);
			p.sendMessage(main.msg("&6Home:&r " + homeName + " &6created."));
		} else {
			p.sendMessage(main.msg("&cHome already exists! changing location"));
			editHomeCfg(p, homeName, cfg);
		}
	}

	@Override
	public File getHomeFile(Player p, String homeName) {
		File f = new File(getHomesFolder(p), homeName + ".yml");
		if (!f.exists())
			return null;
		return f;
	}

	@Override
	public void setupHomes(Player p) {
		if (!homesFolderExists(p)) {
			createHomeFolder(p);
		}
	}

	@Override
	public void tpHome(Player p, String homeName) {
		FileConfiguration cfg = getHomeCfg(p, homeName);
		World w = main.getServer().getWorld(cfg.getString("world"));
		double x, y, z;
		x = cfg.getDouble("x");
		y = cfg.getDouble("y");
		z = cfg.getDouble("z");
		Location l = new Location(w, x, y, z);
		p.teleport(l);
		p.sendMessage(main.msg("&6Teleported you to: " + homeName));
	}

	private File getHomesFolder(Player p) {
		File dir = new File(user.getUserFolder(p.getUniqueId()) + File.separator + "Homes");
		if (!dir.exists())
			return null;
		return dir;
	}

	public Boolean homesFolderExists(Player p) {
		if (getHomesFolder(p).exists())
			return true;
		return false;
	}

	private void createHomeFolder(Player p) {
		File dir = new File(user.getUserFolder(p.getUniqueId()) + File.separator + "Homes");
		if (!dir.exists()) {
			if (dir.mkdirs()) {
				main.getLogger().info("User HomeFolder Created.");
			} else {
				main.getLogger().info("Failed to create User HomeFolder.");
			}
		}
	}

	private FileConfiguration getHomeCfg(Player p, String homeName) {
		return YamlConfiguration.loadConfiguration(getHomeFile(p, homeName));
	}

	public Boolean doesHomeExists(Player p, String homeName) {
		for (File f : getHomesFolder(p).listFiles()) {
			if (f.getName() == homeName)
				return true;
		}
		return false;
	}

	private void createHomeFile(Player p, String homeName, FileConfiguration cfg) {
		File f = new File(getHomesFolder(p), homeName + ".yml");
		if (!f.exists()) {
			try {
				f.createNewFile();
				fillHomeCfg(p, homeName, cfg);
				p.sendMessage(main.msg("&6Home created:&r " + homeName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void fillHomeCfg(Player p, String homeName, FileConfiguration cfg) {
		cfg.addDefault("world", p.getWorld());
		cfg.addDefault("x", p.getLocation().getBlockX());
		cfg.addDefault("y", p.getLocation().getBlockY());
		cfg.addDefault("z", p.getLocation().getBlockZ());
		cfg.options().copyDefaults(true);
		saveFile(cfg, getHomeFile(p, homeName));
	}

	private void editHomeCfg(Player p, String homeName, FileConfiguration cfg) {
		cfg.set("world", p.getWorld());
		cfg.set("x", p.getLocation().getBlockX());
		cfg.set("y", p.getLocation().getBlockY());
		cfg.set("z", p.getLocation().getBlockZ());
		cfg.options().copyDefaults(true);
		saveFile(cfg, getHomeFile(p, homeName));
	}

	private void saveFile(FileConfiguration cfg, File f) {
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
