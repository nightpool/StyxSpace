package me.iffa.styxspace.api.event.misc;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player uses the 'space'-command.
 * 
 * @author iffa
 */
public class SpaceCommandEvent extends Event implements Cancellable {
	private static final long serialVersionUID = -7384621557571433605L;
	private boolean canceled = false;
	private Player player = null;
	private String arguments[] = null;

	public SpaceCommandEvent(String event, Player player, String args[]) {
		super(event);
		this.player = player;
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

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.canceled = cancel;
	}
}
