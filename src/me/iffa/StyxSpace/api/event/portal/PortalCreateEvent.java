package me.iffa.styxspace.api.event.portal;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player creates a portal.
 * 
 * @author iffa
 */
public class PortalCreateEvent extends PortalEvent {
	private static final long serialVersionUID = -4605072744240246826L;

	public PortalCreateEvent(Object caller, Player player, String portal) {
		super("PortalCreateEvent", player, portal);
	}
}
