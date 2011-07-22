package me.iffa.styxspace.schedulers;

import org.bukkit.entity.Player;

public class SpaceRunnable2 implements Runnable {
	public Player player;

	public SpaceRunnable2(Player player) {
		this.player = player;
	}

	public void run() {
		player.setHealth(player.getHealth() - 2);
	}

}
