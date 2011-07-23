package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a player is teleported to space.
 * 
 * @author iffa
 */
public class TeleportToSpaceEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = 8744071438699676557L;
	private boolean canceled = false;
	private Player player = null;
	private Location destination = null;

	public TeleportToSpaceEvent(Object caller, Player p, Location dest) {
		super(caller);
		this.player = p;
		this.destination = dest;
	}

	/**
	 * Gets the player that was teleported to space.
	 * 
	 * @return Player that was teleported to space
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the destination of the teleport.
	 * 
	 * @return Destination of the teleport
	 */
	public Location getTo() {
		return this.destination;
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
