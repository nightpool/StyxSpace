package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a player suffocates.
 * 
 * @author iffa
 */
public class SpaceSuffocationEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = 8772846319048911013L;
	private boolean canceled = false;
	private Player player = null;

	public SpaceSuffocationEvent(Object caller, Player p) {
		super(caller);
		this.player = p;
	}

	/**
	 * Gets the player that is suffocating.
	 * 
	 * @return Player that is suffocating
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
