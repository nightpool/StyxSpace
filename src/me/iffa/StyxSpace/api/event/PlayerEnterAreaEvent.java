package me.iffa.styxspace.api.event;

// Java Imports
import java.util.EventObject;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player enters a breathable area.
 * 
 * @author iffa
 */
public class PlayerEnterAreaEvent extends EventObject {
	private static final long serialVersionUID = 8533622463870713905L;
	private final Player player;

	public PlayerEnterAreaEvent(Object caller, Player p) {
		super(caller);
		this.player = p;
	}

	/**
	 * Gets the player that entered an area.
	 * 
	 * @return player that entered an area
	 */
	public Player getPlayer() {
		return this.player;
	}
}
