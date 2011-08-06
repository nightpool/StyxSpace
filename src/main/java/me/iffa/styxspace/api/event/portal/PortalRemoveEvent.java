// Package Declaration
package me.iffa.styxspace.api.event.portal;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player removes a portal.
 * 
 * @author iffa
 */
public class PortalRemoveEvent extends PortalEvent {
    // Variables
    private static final long serialVersionUID = -8001714047884495968L;
    protected boolean removedByPlayer;

    // Constructor
    public PortalRemoveEvent(Object caller, Player player, String portal,
            Boolean removedByPlayer) {
        super("PortalRemoveEvent", player, portal);
        this.removedByPlayer = removedByPlayer;
    }

    /**
     * Checks if the portal was removed as a physics event.
     * 
     * @return true if the removal was a physics event
     */
    public boolean isPhysicsEvent() {
        return this.removedByPlayer;
    }
}
