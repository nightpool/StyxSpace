// Package Declaration
package me.iffa.styxspace.api.event.misc;

// Bukkit Imports
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a mob is not allowed to spawn in space.
 * 
 * @author iffa
 */
public class AntiMobSpawnEvent extends Event implements Cancellable {
    // Variables
    private static final long serialVersionUID = 5531881415618226310L;
    protected boolean canceled = false;
    protected Entity entity = null;

    // Constructor
    public AntiMobSpawnEvent(String event, Entity entity) {
        super(event);
        this.entity = entity;

    }

    /**
     * Gets the entity associated with this event.
     * 
     * @return Entity associated with this event
     */
    public Entity getEntity() {
        return this.entity;
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
