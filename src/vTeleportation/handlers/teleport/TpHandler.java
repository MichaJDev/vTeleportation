package vTeleportation.handlers.teleport;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Player;

import vTeleportation.Main;
import vTeleportation.handlers.teleport.interfaces.ITpHandler;

public class TpHandler implements ITpHandler {

	private Main main = Main.getInstance();
	private Hashtable<Player, Player> tpaRequests = new Hashtable<Player, Player>();
	private List<Player> toggleList = new ArrayList<Player>();

	public TpHandler(Main _main) {
		main = _main;
	}

	@Override
	public void teleport(Player p, String target) {
		Player tp = main.getServer().getPlayer(target);
		if (tp != null && !toggleList.contains(tp)) {
			main.getLogger().info("");
			p.sendMessage(main.msg("&cTeleporting to " + tp.getName()));
			p.teleport(tp.getLocation());
		}
	}

	@Override
	public void teleport(Player p, Player target) {
		p.sendMessage(main.msg("&cTeleporting to " + target.getName()));
		p.teleport(target.getLocation());
	}

	@Override
	public void tpa(Player p, String target) {
		Player tp = main.getServer().getPlayer(target);
		if (tp != null && !toggleList.contains(tp)) {
			if (tpaRequests.get(p) == null) {
				tpaRequests.put(p, tp);
				tp.sendMessage(main.msg(p.getName() + " &6has send you a &r&4TPARequest&r"));
				tp.sendMessage(main.msg("&2/tpaccept &ror &4/tpdeny"));
				p.sendMessage(main.msg("&6Tpa request send to: &r" + tp.getName()));
				removeTpa(p, tp);
			} else {
				p.sendMessage(main.msg("&cYou've already send a Tpa request!"));
			}
		}
	}

	@Override
	public void tpaccept(Player p) {
		Set<Player> tpaKeys = tpaRequests.keySet();
		if (tpaRequests.containsValue(p)) {
			for (Player i : tpaKeys) {
				if (tpaRequests.get(i) == p) {
					teleport(p, i);
				}
			}
		} else {
			p.sendMessage(main.msg("&cNo tpa request found."));
		}

	}

	@Override
	public void tpdeny(Player p) {
		Set<Player> tpaKeys = tpaRequests.keySet();
		if (tpaRequests.containsValue(p)) {
			for (Player i : tpaKeys) {
				if (tpaRequests.get(i) == p) {
					tpaRequests.remove(i);
					p.sendMessage(main.msg("You have &cdenied&r " + i.getName() + "'s tpa request."));
					i.sendMessage(main.msg(p.getName() + "&c denied&r your tpa request"));
				}
			}
		} else {
			p.sendMessage(main.msg("&cNo tpa request found."));
		}

	}

	@Override
	public void tpToggle(Player p) {
		if (toggleList.contains(p)) {
			toggleList.remove(p);
			p.sendMessage(main.msg("TpToggle disabled."));
		} else {
			toggleList.add(p);
			p.sendMessage(main.msg("TpToggle enabled."));
		}
	}

	private void removeTpa(Player p, Player tp) {
		Runnable helloRunnable = new Runnable() {
			public void run() {
				tpaRequests.remove(p);
				p.sendMessage(main.msg(tp.getName() + " &cdid not respond, tpa request removed"));
				tp.sendMessage(main.msg(p.getName() + "'s &ctpa request ran out."));
			}
		};

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(helloRunnable, 0, 10, TimeUnit.SECONDS);

	}

	@Override
	public List<Player> getToggleList() {
		return toggleList;
	}

}
