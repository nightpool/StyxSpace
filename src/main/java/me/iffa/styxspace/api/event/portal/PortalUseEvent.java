// Package Declaration
package me.iffa.styxspace.api.event.portal;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player uses a portal.
 * 
 * @author iffa
 */
public class PortalUseEvent extends PortalEvent {
    // Variables
    private static final long serialVersionUID = -8001714047884495968L;

    // Constructor
    public PortalUseEvent(Object caller, Player player, String portal) {
        super("PortalUseEvent", player, portal);
    }
}
