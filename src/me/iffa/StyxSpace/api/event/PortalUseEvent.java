package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a player uses a portal.
 * 
 * @author iffa
 */
public class PortalUseEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = -8001714047884495968L;
	private boolean canceled = false;
	private Player player = null;
	private String portalname = null;

	public PortalUseEvent(Object caller, Player p, String portal) {
		super(caller);
		this.player = p;
		this.portalname = portal;
	}

	/**
	 * Gets the player that used a portal.
	 * 
	 * @return Player that used a portal
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the portal associated with this event.
	 * 
	 * @return Name of the portal that was used
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
