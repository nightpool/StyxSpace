package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a player uses the 'space'-command.
 * 
 * @author iffa
 */
public class SpaceCommandEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = -7384621557571433605L;
	private boolean canceled = false;
	private Player player = null;
	private String arguments[] = null;

	public SpaceCommandEvent(Object caller, Player p, String args[]) {
		super(caller);
		this.player = p;
		this.arguments = args;
	}

	/**
	 * Gets the arguments of the command.
	 * 
	 * @return Given arguments
	 */
	public String[] getArgs() {
		return this.arguments;
	}

	/**
	 * Gets the player that used the command.
	 * 
	 * @return Player that used the command
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
