package me.iffa.styxspace.api.event.misc;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player is teleported to space.
 * 
 * @author iffa
 */
public class TeleportToSpaceEvent extends Event implements Cancellable {
	private static final long serialVersionUID = 8744071438699676557L;
	protected boolean canceled = false;
	protected Player player = null;
	protected Location destination = null;

	public TeleportToSpaceEvent(String event, Player p, Location dest) {
		super(event);
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
