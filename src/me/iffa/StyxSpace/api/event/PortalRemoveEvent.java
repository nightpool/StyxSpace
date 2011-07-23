package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a player removes a portal.
 * 
 * @author iffa
 */
public class PortalRemoveEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = -1531300267726262510L;
	private boolean canceled = false;
	private Player player = null;
	private String portalname = null;

	public PortalRemoveEvent(Object caller, Player p, String portal) {
		super(caller);
		this.player = p;
		this.portalname = portal;
	}

	/**
	 * Gets the portal associated with this event.
	 * 
	 * @return Portal that was removed
	 */
	public String getPortalName() {
		return this.portalname;
	}

	/**
	 * Gets the player that removed a portal.
	 * 
	 * @return Player that removed a portal
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the canceled state of this event.
	 * 
	 * @return true if the event is canceled
	 */
	public boolean isCancelled() {
		return canceled;
	}

	/**
	 * Sets the canceled state of this event
	 * 
	 * @param cancel
	 */
	public void setCancelled(boolean cancel) {
		this.canceled = cancel;
	}

}
