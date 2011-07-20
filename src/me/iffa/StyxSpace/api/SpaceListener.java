package me.iffa.styxspace.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
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
	 * Called when a player creates a sign portal.
	 * 
	 * @param player
	 *            Player that created a sign portal
	 * @param portalname
	 *            Name of the portal created
	 */
	public void onPortalCreate(Player player, String portalname) {
	}

	/**
	 * Called when a player destroys/removes a sign portal.
	 * 
	 * @param player
	 *            Player that removed a sign portal
	 * @param portalname
	 *            Name of the portal removed
	 */
	public void onPortalRemove(Player player, String portalname) {
	}

	/**
	 * Called when a player uses the space command.
	 * 
	 * @param player
	 *            Player that used the space command
	 */
	public void onSpaceCommand(Player player) {
	}

	/**
	 * Called when an entity is not allowed to spawn in space.
	 * 
	 * @param entity
	 *            Entity that wasn't allowed to spawn
	 * @param isHostile
	 *            If the entity was hostile
	 */
	public void onAntiMobSpawn(Entity entity, boolean isHostile) {
	}
}
