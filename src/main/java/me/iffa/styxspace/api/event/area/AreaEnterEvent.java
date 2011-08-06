// Package Declaration
package me.iffa.styxspace.api.event.area;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * Event data for when a player enters a breathable area.
 * 
 * @author iffa
 */
public class AreaEnterEvent extends AreaEvent {
    // Variables
    private static final long serialVersionUID = 8533622463870713905L;

    // Constructor
    public AreaEnterEvent(Player player) {
        super("AreaEnterEvent", player);
    }
}
