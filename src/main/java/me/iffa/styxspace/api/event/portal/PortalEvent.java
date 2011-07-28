package me.iffa.styxspace.api.event.portal;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for various portal related events.
 * 
 * @author iffa
 * 
 */
public abstract class PortalEvent extends Event implements Cancellable {
	private static final long serialVersionUID = -316124458220245924L;
	protected Player player;
	protected String portal;
	protected boolean cancelled;

	public PortalEvent(String event, Player player, String portal) {
		super(event);
		this.player = player;
		this.portal = portal;
	}

	/**
	 * Gets the player associated with this event.
	 * 
	 * @return player associated with this event
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Gets the portal associated with this event.
	 * 
	 * @return portal associated with this event
	 */
	public String getPortal() {
		return this.portal;
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