package me.iffa.styxspace.listeners;

import java.util.HashMap;
import java.util.Map;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceListener;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.schedulers.SpaceRunnable2;
import me.iffa.styxspace.utils.SpaceConfig;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 * PlayerListener.
 * 
 * @author iffa
 * 
 */
public class SpacePlayerListener extends PlayerListener {
	// Variables
	public static StyxSpace plugin;
	public SpacePlayer spacePlayer = new SpacePlayer();
	public static Map<Player, Boolean> isUsed = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> inArea = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> fixDupe = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> taskid = new HashMap<Player, Integer>();
	public static Map<Player, String> armorType = new HashMap<Player, String>();
	public static int taskInt;
	public static String msgResult = null;

	public SpacePlayerListener(StyxSpace instance) {
		plugin = instance;
	}

	/**
	 * Called when a player attempts to teleport.
	 */
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		if (!fixDupe.containsKey(event.getPlayer())) {
			if (event.getTo().getWorld() == StyxSpace.getSpace()) {
				if (SpaceConfig.GIVEHELMET == true) {
					event.getPlayer()
							.getInventory()
							.setHelmet(
									new ItemStack(SpaceConfig.SPACEHELMET, 1));
				}
				if (SpaceConfig.GIVESUIT == true) {
					spacePlayer
							.giveSpaceSuit(SpaceConfig.DEFAULT_ARMOR, player);
				}
				if (event.getTo() == StyxSpace.getSpace().getSpawnLocation()) {
					Location loc = event.getTo();
					loc.setY(loc.getY() - 1);
					if (loc.getBlock().getType() == Material.AIR) {
						loc.getBlock().setType(Material.GLOWSTONE);
					}
				}
				// Notify listeners.
				for (SpaceListener listener : StyxSpace.listeners)
					listener.onTeleportToSpace(event.getPlayer(), event.getTo());
				fixDupe.put(event.getPlayer(), true);
			} else if (event.getTo().getWorld() != StyxSpace.getSpace()
					&& event.getFrom().getWorld() == StyxSpace.getSpace()) {
				if (SpaceConfig.GIVEHELMET == true) {
					event.getPlayer().getInventory()
							.setHelmet(new ItemStack(null, 1));
				}
				if (SpaceConfig.GIVESUIT == true) {
					spacePlayer.giveSpaceSuit("null", player);
				}
			}
		} else {
			fixDupe.clear();
		}
	}

	/**
	 * Does something.
	 * 
	 * @param player
	 */
	public void usedSet(Player player) {
		if (isUsed.containsKey(player)) {
			if (isUsed.get(player) == false) {
				isUsed.put(player, true);
			}
		} else {
			isUsed.put(player, true);
		}
	}

	/**
	 * Called when a player attempts to move.
	 */
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled())
			return;
		if (event.getPlayer().getWorld() == StyxSpace.getSpace()) {
			int i = 0;
			Block block = event.getPlayer().getLocation().getBlock()
					.getRelative(BlockFace.UP);
			boolean b = false;
			while (i < SpaceConfig.ROOM_HEIGHT) {
				if (block.getTypeId() != 0) {
					b = true;
					i = 0;
					break;
				}
				i++;
				block = block.getRelative(BlockFace.UP);
			}
			// Player is in area.
			if (b == true) {
				if (inArea.containsKey(event.getPlayer())) {
					if (inArea.get(event.getPlayer()) == false) {
						inArea.put(event.getPlayer(), true);
						for (SpaceListener listener : StyxSpace.listeners)
							listener.onAreaEnter(event.getPlayer());
					}
				} else {
					inArea.put(event.getPlayer(), true);
					for (SpaceListener listener : StyxSpace.listeners)
						listener.onAreaEnter(event.getPlayer());
				}
				if (isUsed.containsKey(event.getPlayer())) {
					if (isUsed.get(event.getPlayer()) == true) {
						StyxSpace.scheduler.cancelTask(taskid.get(event
								.getPlayer()));
						isUsed.put(event.getPlayer(), false);
					}
				}

				// Player is not in an area.
			} else {
				if (inArea.containsKey(event.getPlayer())) {
					if (inArea.get(event.getPlayer()) == true) {
						inArea.put(event.getPlayer(), false);
						for (SpaceListener listener : StyxSpace.listeners)
							listener.onAreaLeave(event.getPlayer());
					}
				} else {
					inArea.put(event.getPlayer(), false);
					for (SpaceListener listener : StyxSpace.listeners)
						listener.onAreaLeave(event.getPlayer());
				}
				// Both suit & helmet required
				if (SpaceConfig.HELMET_REQUIRED == true
						&& SpaceConfig.SUIT_REQUIRED == true) {
					if (isUsed.containsKey(event.getPlayer())) {
						if (isUsed.get(event.getPlayer()) == true
								&& event.getPlayer().getInventory().getHelmet()
										.getTypeId() == SpaceConfig.SPACEHELMET
								&& hasSuit(event.getPlayer(),
										SpaceConfig.DEFAULT_ARMOR)) {
							StyxSpace.scheduler.cancelTask(taskid.get(event
									.getPlayer()));
							isUsed.put(event.getPlayer(), false);
						} else if (isUsed.get(event.getPlayer()) == false
								&& event.getPlayer().getInventory().getHelmet()
										.getTypeId() != SpaceConfig.SPACEHELMET
								|| !hasSuit(event.getPlayer(),
										SpaceConfig.DEFAULT_ARMOR)) {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());
						}
					} else {
						if (event.getPlayer().getInventory().getHelmet()
								.getTypeId() == SpaceConfig.SPACEHELMET
								&& hasSuit(event.getPlayer(),
										SpaceConfig.DEFAULT_ARMOR)) {
							isUsed.put(event.getPlayer(), false);
						} else {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());
						}
					}
					// Only helmet required
				} else if (SpaceConfig.HELMET_REQUIRED == true) {
					if (isUsed.containsKey(event.getPlayer())) {
						if (isUsed.get(event.getPlayer()) == true
								&& event.getPlayer().getInventory().getHelmet()
										.getTypeId() == SpaceConfig.SPACEHELMET) {
							StyxSpace.scheduler.cancelTask(taskid.get(event
									.getPlayer()));
							isUsed.put(event.getPlayer(), false);
						} else if (isUsed.get(event.getPlayer()) == false
								&& event.getPlayer().getInventory().getHelmet()
										.getTypeId() != SpaceConfig.SPACEHELMET) {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());

						}
					} else {
						if (event.getPlayer().getInventory().getHelmet()
								.getTypeId() == SpaceConfig.SPACEHELMET) {
							isUsed.put(event.getPlayer(), false);
						} else {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());
						}
					}

					// Only suit required
				} else if (SpaceConfig.SUIT_REQUIRED == true) {
					if (isUsed.containsKey(event.getPlayer())) {
						if (isUsed.get(event.getPlayer()) == true
								&& hasSuit(event.getPlayer(),
										SpaceConfig.DEFAULT_ARMOR)) {
							StyxSpace.scheduler.cancelTask(taskid.get(event
									.getPlayer()));
							isUsed.put(event.getPlayer(), false);
						} else if (isUsed.get(event.getPlayer()) == false
								&& !hasSuit(event.getPlayer(),
										SpaceConfig.DEFAULT_ARMOR)) {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());

						}
					} else {
						if (hasSuit(event.getPlayer(),
								SpaceConfig.DEFAULT_ARMOR)) {
							isUsed.put(event.getPlayer(), false);
						} else {
							SpaceRunnable2 task = new SpaceRunnable2(
									event.getPlayer());
							taskInt = StyxSpace.scheduler
									.scheduleSyncRepeatingTask(plugin, task,
											20L, 20L);
							taskid.put(event.getPlayer(), taskInt);
							isUsed.put(event.getPlayer(), true);
							// Notify listeners.
							for (SpaceListener listener : StyxSpace.listeners)
								listener.onSpaceSuffocation(event.getPlayer());
						}
					}

				}
			}
		} else {
			if (isUsed.containsKey(event.getPlayer())) {
				if (isUsed.get(event.getPlayer()) == true) {
					StyxSpace.scheduler
							.cancelTask(taskid.get(event.getPlayer()));
				}
			}
		}
	}

	/**
	 * Checks if a player has a spacesuit (of the given armortype)
	 * 
	 * @param p
	 *            Player.
	 * @param armortype
	 *            Can be diamond, chainmail, gold, iron or leather.
	 * @return true if the player has a spacesuit of the type
	 */
	private boolean hasSuit(Player p, String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			// Diamond
			if (p.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			// Chainmail
			if (p.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("gold")) {
			// Gold
			if (p.getInventory().getBoots().getType() == Material.GOLD_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("iron")) {
			// Iron
			if (p.getInventory().getBoots().getType() == Material.IRON_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("leather")) {
			// Leather
			if (p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Called when a player quits the game.
	 */
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (taskid.containsKey(event.getPlayer())) {
			if (StyxSpace.scheduler.isCurrentlyRunning(taskid.get(event
					.getPlayer()))) {
				StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
			}
		}
	}

	/**
	 * Sets the armortype of a player to the specified armortype.
	 * 
	 * @param player
	 * @param armortype
	 *            Can be diamond, chainmail, gold, iron or leather.
	 */
	public void setPlayerSuit(Player player, String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			// Diamond
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			// Chainmail
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("gold")) {
			// Gold
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("iron")) {
			// Iron
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("leather")) {
			// Leather
			armorType.put(player, armortype);
		}
	}

	/**
	 * Called when a player joins the game.
	 */
	public void onPlayerJoin(PlayerJoinEvent event) {
		armorType.put(event.getPlayer(), SpaceConfig.DEFAULT_ARMOR);
	}
}