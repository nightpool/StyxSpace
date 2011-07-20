package me.iffa.styxspace.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.iffa.styxspace.StyxSpace;

/**
 * Listener for events of StyxSpace.
 * 
 * @author iffa
 * 
 */
public class SpaceListener {
	protected StyxSpace plugin;

	public SpaceListener() {
		StyxSpace.listeners.add(this);
	}

	/**
	 * Called when a player enters a breathable area.
	 * 
	 * @param player
	 *            Player that entered a breathable area
	 */
	public void onAreaEnter(Player player) {
	}

	/**
	 * Called when a player leaves a breathable area.
	 * 
	 * @param player
	 *            Player that left a breathable area
	 */
	public void onAreaLeave(Player player) {
	}

	/**
	 * Called when a player teleports to space.
	 * 
	 * @param player
	 *            Player that teleported to space
	 */
	public void onTeleportToSpace(Player player, Location destination) {
	}

	/**
	 * Called when a player starts suffocating (for having no helmet, suit or
	 * both).
	 * 
	 * @param player
	 *            Player that is suffocating
	 */
	public void onSpaceSuffocation(Player player) {
	}

	/**
	 * Called when a player uses a sign portal.
	 * 
	 * @param player
	 *            Player that used a sign portal
	 */
	public void onPortalUse(Player player, Location destination) {
	}

	/**
	 * Called when a player uses the space command
	 * 
	 * @param player
	 *            Player that used the space command
	 */
	public void onSpaceCommand(Player player) {
	}
}
