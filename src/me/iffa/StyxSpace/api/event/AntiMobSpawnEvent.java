package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

/**
 * Event data for when a mob is not allowed to spawn in space.
 * 
 * @author iffa
 */
public class AntiMobSpawnEvent extends EventObject implements Cancellable {
	private static final long serialVersionUID = 5531881415618226310L;
	private boolean canceled = false;
	private Entity entity = null;

	public AntiMobSpawnEvent(Object caller, Entity e) {
		super(caller);
		this.entity = e;

	}

	/**
	 * Gets the entity associated with this event.
	 * 
	 * @return Entity associated with this event
	 */
	public Entity getEntity() {
		return this.entity;
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
