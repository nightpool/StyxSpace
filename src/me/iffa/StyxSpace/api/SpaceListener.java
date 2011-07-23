package me.iffa.styxspace.api;

// StyxSpace Imports
import me.iffa.styxspace.api.event.AntiMobSpawnEvent;
import me.iffa.styxspace.api.event.PlayerEnterAreaEvent;
import me.iffa.styxspace.api.event.PlayerLeaveAreaEvent;
import me.iffa.styxspace.api.event.PortalCreateEvent;
import me.iffa.styxspace.api.event.PortalRemoveEvent;
import me.iffa.styxspace.api.event.PortalUseEvent;
import me.iffa.styxspace.api.event.SpaceCommandEvent;
import me.iffa.styxspace.api.event.SpaceSuffocationEvent;
import me.iffa.styxspace.api.event.TeleportToSpaceEvent;

/**
 * Listener for events of StyxSpace.
 * 
 * @author iffa
 * 
 */
public interface SpaceListener {
	/**
	 * Called when a player enters a breathable area.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAreaEnter(PlayerEnterAreaEvent event);

	/**
	 * Called when a player leaves a breathable area.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAreaLeave(PlayerLeaveAreaEvent event);

	/**
	 * Called when a player teleports to space.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onTeleportToSpace(TeleportToSpaceEvent event);

	/**
	 * Called when a player starts suffocating (for having no helmet, suit or
	 * both).
	 * 
	 * @param event
	 *            Event data
	 */
	public void onSpaceSuffocation(SpaceSuffocationEvent event);

	/**
	 * Called when a player uses a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalUse(PortalUseEvent event);

	/**
	 * Called when a player creates a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalCreate(PortalCreateEvent event);

	/**
	 * Called when a player destroys/removes a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalRemove(PortalRemoveEvent event);

	/**
	 * Called when a player uses the space command.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onSpaceCommand(SpaceCommandEvent event);

	/**
	 * Called when an entity is not allowed to spawn in space.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAntiMobSpawn(AntiMobSpawnEvent event);
}
