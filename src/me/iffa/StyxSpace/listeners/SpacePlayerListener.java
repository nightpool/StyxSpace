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

public class SpacePlayerListener extends PlayerListener {
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
					spacePlayer.giveSpaceSuit(SpaceConfig.DEFAULT_ARMOR, player);
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

	public void usedSet(Player player) {
		if (isUsed.containsKey(player)) {
			if (isUsed.get(player) == false) {
				isUsed.put(player, true);
			}
		} else {
			isUsed.put(player, true);
		}
	}

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
			// IS IN AREA
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
				// REST OF CODE
				if (isUsed.containsKey(event.getPlayer())) {
					if (isUsed.get(event.getPlayer()) == true) {
						StyxSpace.scheduler.cancelTask(taskid.get(event
								.getPlayer()));
						isUsed.put(event.getPlayer(), false);
					}
				}

				// IS NOT IN AREA
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
				// REST OF CODE
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
					/**
					 * HELMET REQUIRED (ONLY)
					 */
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

					/**
					 * SUIT REQUIRED (ONLY)
					 */
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

	private boolean hasSuit(Player p, String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			/**
			 * armortype = "diamond"
			 */
			if (p.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			/**
			 * armortype = "chainmail"
			 */
			if (p.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("gold")) {
			/**
			 * armortype = "gold"
			 */
			if (p.getInventory().getBoots().getType() == Material.GOLD_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("iron")) {
			/**
			 * armortype = "iron"
			 */
			if (p.getInventory().getBoots().getType() == Material.IRON_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS) {
				return true;
			}
			return false;
		} else if (armortype.equalsIgnoreCase("leather")) {
			/**
			 * armortype = "leather"
			 */
			if (p.getInventory().getBoots().getType() == Material.LEATHER_BOOTS
					&& p.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE
					&& p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
				return true;
			}
			return false;
		}
		return false;
	}

	public void onPlayerQuit(PlayerQuitEvent event) {
		if (taskid.containsKey(event.getPlayer())) {
			if (StyxSpace.scheduler.isCurrentlyRunning(taskid.get(event
					.getPlayer()))) {
				StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
			}
		}
	}

	public void setPlayerSuit(Player player, String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			/**
			 * armortype = "diamond"
			 */
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			/**
			 * armortype = "chainmail"
			 */
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("gold")) {
			/**
			 * armortype = "gold"
			 */
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("iron")) {
			/**
			 * armortype = "iron"
			 */
			armorType.put(player, armortype);
		} else if (armortype.equalsIgnoreCase("leather")) {
			/**
			 * armortype = "leather"
			 */
			armorType.put(player, armortype);
		}
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		armorType.put(event.getPlayer(), SpaceConfig.DEFAULT_ARMOR);
	}
}