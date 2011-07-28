package me.iffa.styxspace.schedulers;

// Bukkit Imports
import org.bukkit.entity.Player;

/**
 * A runnable class for suffocating.
 * 
 * @author iffa
 * 
 */
public class SpaceRunnable2 implements Runnable {
	public Player player;

	public SpaceRunnable2(Player player) {
		this.player = player;
	}

	/**
	 * Suffocates the player when repeated every second.
	 */
	public void run() {
		player.setHealth(player.getHealth() - 2);
	}

}
