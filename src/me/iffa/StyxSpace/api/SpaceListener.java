package me.iffa.StyxSpace.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.iffa.StyxSpace.StyxSpace;

/**
 * Listener for events of StyxSpace Note: you can't cancel these. :)
 * 
 * @author iffa
 * 
 */
public class SpaceListener {
	protected StyxSpace plugin;

	public SpaceListener() {
		plugin = (StyxSpace) Bukkit.getServer().getPluginManager()
				.getPlugin("StyxSpace");
		plugin.listeners.add(this);
	}

	/**
	 * Called when a player enters a breathable area
	 * 
	 * @param player
	 */
	public void onAreaEnter(Player player) {
	}

	/**
	 * Called when a player leaves a breathable area
	 * 
	 * @param player
	 */
	public void onAreaLeave(Player player) {
	}

	/**
	 * Called when a player teleports to space
	 * 
	 * @param player
	 */
	public void onTeleportToSpace(Player player, Location destination) {
	}

	/**
	 * Called when the /space command is used
	 * out of order for unknown amount of time
	 * @param player
	 * @param args
	 */
	//public void onSpaceCommand(Player player, String args[]) {
	//}
}
