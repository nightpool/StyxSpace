package me.iffa.styxspace.listeners;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.utils.SpaceConfig;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class SpaceGravityListener extends PlayerListener {
	SpacePlayer spacePlayer = new SpacePlayer();

	public SpaceGravityListener(StyxSpace instance) {
	}

	/**
	 * Called when a player tries to move.
	 */
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (spacePlayer.isInSpace(event.getPlayer())) {
			return;
		}
		if (SpacePlayerListener.inArea.get(event.getPlayer()) == true) {
			return;
		}
		Player player = event.getPlayer();
		Location playerLocation = player.getLocation();
		Vector playerDirection = playerLocation.getDirection();
		int speed = 2;
		playerDirection.multiply(speed);
		player.setVelocity(playerDirection);
	}

	public void onPlayerKick(PlayerKickEvent event) {

	}

	public void onPlayerJoin(PlayerJoinEvent event) {
	}

	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
	}
}
