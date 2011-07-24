package me.iffa.styxspace.api;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

// StyxSpace Imports
import me.iffa.styxspace.api.event.area.AreaEnterEvent;
import me.iffa.styxspace.api.event.area.AreaLeaveEvent;
import me.iffa.styxspace.api.event.misc.AntiMobSpawnEvent;
import me.iffa.styxspace.api.event.misc.SpaceCommandEvent;
import me.iffa.styxspace.api.event.misc.SpaceSuffocationEvent;
import me.iffa.styxspace.api.event.misc.TeleportToSpaceEvent;
import me.iffa.styxspace.api.event.portal.PortalCreateEvent;
import me.iffa.styxspace.api.event.portal.PortalRemoveEvent;
import me.iffa.styxspace.api.event.portal.PortalUseEvent;

/**
 * Listener for events of StyxSpace.
 * 
 * @author iffa
 * 
 */
public class SpaceListener extends CustomEventListener implements Listener {
	// InventoryCloseEvent event = new
	// InventoryCloseEvent((Player)this.player.getBukkitEntity(), inventory,
	// getDefaultInventory(), activeLocation);
	// Bukkit.getServer().getPluginManager().callEvent(event);
	/**
	 * Called when a player enters a breathable area.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAreaEnter(AreaEnterEvent event) {
	}

	/**
	 * Called when a player leaves a breathable area.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAreaLeave(AreaLeaveEvent event) {
	}

	/**
	 * Called when a player teleports to space.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onTeleportToSpace(TeleportToSpaceEvent event) {
	}

	/**
	 * Called when a player starts suffocating (for having no helmet, suit or
	 * both).
	 * 
	 * @param event
	 *            Event data
	 */
	public void onSpaceSuffocation(SpaceSuffocationEvent event) {
	}

	/**
	 * Called when a player uses a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalUse(PortalUseEvent event) {
	}

	/**
	 * Called when a player creates a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalCreate(PortalCreateEvent event) {
	}

	/**
	 * Called when a player destroys/removes a sign portal.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPortalRemove(PortalRemoveEvent event) {
	}

	/**
	 * Called when a player uses the space command.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onSpaceCommand(SpaceCommandEvent event) {
	}

	/**
	 * Called when an entity is not allowed to spawn in space.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onAntiMobSpawn(AntiMobSpawnEvent event) {
	}

	/**
	 * Handles calling the events (like a boss).
	 */
	@Override
	public void onCustomEvent(Event event) {
		if (event instanceof AreaEnterEvent) {
			onAreaEnter((AreaEnterEvent) event);
		} else if (event instanceof AreaLeaveEvent) {
			onAreaLeave((AreaLeaveEvent) event);
		} else if (event instanceof PortalCreateEvent) {
			onPortalCreate((PortalCreateEvent) event);
		} else if (event instanceof PortalUseEvent) {
			onPortalUse((PortalUseEvent) event);
		} else if (event instanceof PortalRemoveEvent) {
			onPortalRemove((PortalRemoveEvent) event);
		} else if (event instanceof TeleportToSpaceEvent) {
			onTeleportToSpace((TeleportToSpaceEvent) event);
		} else if (event instanceof SpaceCommandEvent) {
			onSpaceCommand((SpaceCommandEvent) event);
		} else if (event instanceof AntiMobSpawnEvent) {
			onAntiMobSpawn((AntiMobSpawnEvent) event);
		} else if (event instanceof SpaceSuffocationEvent) {
			onSpaceSuffocation((SpaceSuffocationEvent) event);
		}
	}
}
