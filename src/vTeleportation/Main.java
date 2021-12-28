package vTeleportation;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import vCore.Config.ConfigBuilder;
import vCore.Config.Interface.IConfigBuilder;
import vCore.Data.Builders.Users.UserBuilder;
import vCore.Data.Builders.Users.Interface.IUserBuilder;
import vTeleportation.commands.DelHomeCmd;
import vTeleportation.commands.DelWarpCmd;
import vTeleportation.commands.HomeCmd;
import vTeleportation.commands.HomesCmd;
import vTeleportation.commands.SetHomeCmd;
import vTeleportation.commands.SetSpawnCmd;
import vTeleportation.commands.SetWarpCmd;
import vTeleportation.commands.SpawnCmd;
import vTeleportation.commands.TpAcceptCmd;
import vTeleportation.commands.TpCmd;
import vTeleportation.commands.TpDenyCmd;
import vTeleportation.commands.TpToggleCmd;
import vTeleportation.commands.TpaCmd;
import vTeleportation.commands.WarpCmd;
import vTeleportation.commands.WarpsCmd;
import vTeleportation.handlers.homes.HomesHandler;
import vTeleportation.handlers.homes.interfaces.IHomesHandler;
import vTeleportation.handlers.spawn.SpawnHandler;
import vTeleportation.handlers.spawn.interfaces.ISpawnHandler;
import vTeleportation.handlers.teleport.TpHandler;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;
import vTeleportation.handlers.warps.WarpsHandler;
import vTeleportation.handlers.warps.interfaces.IWarpsHandler;
import vTeleportation.listeners.LoginListener;

public class Main extends JavaPlugin {

	private static Main main;
	private static IUserBuilder user;
	private static ISpawnHandler spawn;
	private static IConfigBuilder cfg;
	private static ITpHandler tp;
	private static IHomesHandler homes;
	private static IWarpsHandler warps;
	private static vCore.Main vMain = vCore.Main.getInstance();

	public static Main getInstance() {
		return main;

	}

	@Override
	public void onEnable() {
		getLogger().info("checking for vCore.......");
		if (getServer().getPluginManager().getPlugin("vCore") != null) {
			getLogger().info("vCore found, nesting.......");
			user = new UserBuilder(vMain);
			cfg = new ConfigBuilder(vMain);
			getLogger().info("vCore nested.......");
		} else {
			getLogger().severe("vCore note found...... disabling!");
			getServer().getPluginManager().disablePlugin(this);
		}
		getLogger().info("Loading commands.......");
		getCommands();
		getLogger().info("Loading Listeners........");
		getListeners();
		spawn = new SpawnHandler(main, user, cfg);
		tp = new TpHandler(main);
		homes = new HomesHandler(main, user);
		warps = new WarpsHandler(main, cfg);
		warps.setupWarps();
		spawn.setupSpawn();
	}

	@Override
	public void onDisable() {
		getLogger().severe("vTeleportation disabled.");
	}

	private void getCommands() {
		this.getCommand("spawn").setExecutor(new SpawnCmd(this, spawn));
		this.getCommand("setspawn").setExecutor(new SetSpawnCmd(this, spawn));
		this.getCommand("tp").setExecutor(new TpCmd(this, tp));
		this.getCommand("tpa").setExecutor(new TpaCmd(this, tp));
		this.getCommand("tpaccept").setExecutor(new TpAcceptCmd(this, tp));
		this.getCommand("tpdeny").setExecutor(new TpDenyCmd(this, tp));
		this.getCommand("tptoggle").setExecutor(new TpToggleCmd(this, tp));
		this.getCommand("home").setExecutor(new HomeCmd(this, homes));
		this.getCommand("sethome").setExecutor(new SetHomeCmd(this, homes));
		this.getCommand("homes").setExecutor(new HomesCmd(this, homes));
		this.getCommand("delhome").setExecutor(new DelHomeCmd(this, homes));
		this.getCommand("warp").setExecutor(new WarpCmd(this, warps));
		this.getCommand("setwarp").setExecutor(new SetWarpCmd(this, warps));
		this.getCommand("warps").setExecutor(new WarpsCmd(this, warps));
		this.getCommand("delwarp").setExecutor(new DelWarpCmd(this, warps));

	}

	private void getListeners() {
		getServer().getPluginManager().registerEvents(new LoginListener(this, spawn, homes), this);
		getLogger().info("Listeners Loaded.......");
	}

	public String msg(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
