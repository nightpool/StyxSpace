package me.iffa.styxspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for various area related events.
 * 
 * @author iffa
 * 
 */
public abstract class AreaEvent extends Event implements Cancellable {
	private static final long serialVersionUID = -316124458220245924L;
	protected Player player;
	protected boolean cancelled;

	public AreaEvent(String event, Player player) {
		super(event);
		this.player = player;
	}

	/**
	 * Gets the player associated with this event.
	 * 
	 * @return player associated with this event
	 */
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
