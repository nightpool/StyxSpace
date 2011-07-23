package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player creates a portal.
 * 
 * @author iffa
 */
public class PortalCreateEvent extends EventObject {
	private static final long serialVersionUID = -4605072744240246826L;
	private boolean canceled = false;
	private Player player = null;
	private String portalname = null;

	public PortalCreateEvent(Object caller, Player p, String portal) {
		super(caller);
		this.player = p;
		this.portalname = portal;
	}

	/**
	 * Gets the player that created a portal.
	 * 
	 * @return Player that created a portal
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the portal associated with this event.
	 * 
	 * @return Name of the portal that was created
	 */
	public String getPortal() {
		return this.portalname;
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
	 * Sets the canceled state of this event.
	 * 
	 * @param cancel
	 */
	public void setCancelled(boolean cancel) {
		this.canceled = cancel;
	}

}
