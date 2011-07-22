package me.iffa.styxspace.listeners;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceListener;
import me.iffa.styxspace.utils.SpaceConfig;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

/**
 * EntityListener.
 * 
 * @author iffa
 * 
 */
public class SpaceEntityListener extends EntityListener {
	public static StyxSpace plugin;

	public SpaceEntityListener(StyxSpace instance) {

		plugin = instance;
	}

	/**
	 * Called when a creature spawns.
	 */
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (SpaceConfig.HOSTILE_MOBS == false
				&& event.getLocation().getWorld() == StyxSpace.getSpace()) {
			if (event.getCreatureType() == CreatureType.CREEPER
					|| event.getCreatureType() == CreatureType.GHAST
					|| event.getCreatureType() == CreatureType.GIANT
					|| event.getCreatureType() == CreatureType.MONSTER
					|| event.getCreatureType() == CreatureType.SKELETON
					|| event.getCreatureType() == CreatureType.PIG_ZOMBIE
					|| event.getCreatureType() == CreatureType.SPIDER
					|| event.getCreatureType() == CreatureType.ZOMBIE
					|| event.getCreatureType() == CreatureType.SLIME) {
				event.setCancelled(true);
				// Notify listeners.
				for (SpaceListener listener : StyxSpace.listeners)
					listener.onAntiMobSpawn(event.getEntity(), true);
			}
		}
		if (SpaceConfig.NEUTRAL_MOBS == false
				&& event.getLocation().getWorld() == StyxSpace.getSpace()) {
			if (event.getCreatureType() == CreatureType.CHICKEN
					|| event.getCreatureType() == CreatureType.COW
					|| event.getCreatureType() == CreatureType.PIG
					|| event.getCreatureType() == CreatureType.SHEEP
					|| event.getCreatureType() == CreatureType.SQUID
					|| event.getCreatureType() == CreatureType.WOLF) {
				event.setCancelled(true);
				// Notify listeners.
				for (SpaceListener listener : StyxSpace.listeners)
					listener.onAntiMobSpawn(event.getEntity(), false);
			}
		}
	}

	/**
	 * Called when an entity takes damage.
	 */
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getEntity() instanceof Player
				&& event.getEntity().getWorld() == StyxSpace.getSpace()) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == DamageCause.VOID) {
				Player killhim = (Player) event.getEntity();
				killhim.setHealth(0);
			}
			if (event.getCause() == DamageCause.FALL && SpaceConfig.FLYING
					&& !SpacePlayerListener.inArea.get(player)) {
				event.setCancelled(true);
			}
		}
	}

	/**
	 * Called when an entity dies.
	 */
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (SpacePlayerListener.taskid.containsKey(p)) {
				if (StyxSpace.scheduler
						.isCurrentlyRunning(SpacePlayerListener.taskid.get(p))) {
					StyxSpace.scheduler.cancelTask(SpacePlayerListener.taskid
							.get(p));
				}
			}
		}
	}
}
