// Package Declaration
package me.iffa.styxspace.schedulers;

// Bukkit Imports
import org.bukkit.World;

/**
 * A runnable class for forcing night.
 * 
 * @author iffa
 * 
 */
public class SpaceRunnable implements Runnable {
    // Variables
    private World world;

    // Constructor
    public SpaceRunnable(World world) {
        this.world = world;
    }

    /**
     * Forces night when repeated every 8399 ticks.
     */
    @Override
    public void run() {
        world.setTime(13801);
    }
}
