// Package Declaration
package me.iffa.styxspace.listeners;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// StyxSpace Imports
import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceConfigHandler;
import me.iffa.styxspace.api.event.area.AreaEnterEvent;
import me.iffa.styxspace.api.event.area.AreaLeaveEvent;
import me.iffa.styxspace.api.event.misc.SpaceSuffocationEvent;
import me.iffa.styxspace.api.event.misc.TeleportToSpaceEvent;
import me.iffa.styxspace.api.player.SpacePlayer;
import me.iffa.styxspace.schedulers.SpaceRunnable2;

// Bukkit Imports
import org.bukkit.Bukkit;
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
 * PlayerListener for general space related actions.
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

    // Constructor
    public SpacePlayerListener(StyxSpace instance) {
        plugin = instance;
    }

    /**
     * Called when a player attempts to teleport.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        if (!fixDupe.containsKey(event.getPlayer())) {
            if (StyxSpace.worldHandler.isSpaceWorld(event.getTo().getWorld()) && event.getTo().getWorld() != player.getWorld()) {
                if (SpaceConfigHandler.isHelmetGiven()) {
                    event.getPlayer().getInventory().setHelmet(
                            new ItemStack(SpaceConfigHandler.getHelmetBlock(), 1));
                }
                if (SpaceConfigHandler.isSuitGiven()) {
                    spacePlayer.giveSpaceSuit(SpaceConfigHandler.getArmorType(), player);
                }
                // Notify listeners.
                TeleportToSpaceEvent e = new TeleportToSpaceEvent(
                        "TeleportToSpaceEvent", event.getPlayer(), event.getTo());
                Bukkit.getServer().getPluginManager().callEvent(e);
                if (e.isCancelled()) {
                    event.setCancelled(true);
                }
                fixDupe.put(event.getPlayer(), true);
            } else if (!StyxSpace.worldHandler.isSpaceWorld(event.getTo().getWorld())
                    && StyxSpace.worldHandler.isSpaceWorld(event.getFrom().getWorld())) {
                if (SpaceConfigHandler.isHelmetGiven()) {
                    event.getPlayer().getInventory().setHelmet(new ItemStack(null, 1));
                }
                if (SpaceConfigHandler.isSuitGiven()) {
                    spacePlayer.giveSpaceSuit("null", player);
                }
            }
        } else {
            fixDupe.clear();
        }
    }

    /**
     * Old method for setting "isUsed", can't remember what this is used for exactly.
     * 
     * @param player Player
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
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (StyxSpace.worldHandler.isInAnySpace(event.getPlayer())) {
            int i = 0;
            Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.UP);
            boolean b = false;
            while (i < SpaceConfigHandler.getRoomHeight(event.getPlayer().getWorld())) {
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
                        // Notify listeners.
                        AreaEnterEvent e = new AreaEnterEvent(
                                event.getPlayer());
                        Bukkit.getServer().getPluginManager().callEvent(e);
                    }
                } else {
                    inArea.put(event.getPlayer(), true);
                    // Notify listeners.
                    AreaEnterEvent e = new AreaEnterEvent(
                            event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                }
                if (isUsed.containsKey(event.getPlayer())) {
                    if (isUsed.get(event.getPlayer()) == true) {
                        StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
                        isUsed.put(event.getPlayer(), false);
                    }
                }

                // Player is not in an area.
            } else {
                if (inArea.containsKey(event.getPlayer())) {
                    if (inArea.get(event.getPlayer()) == true) {
                        inArea.put(event.getPlayer(), false);
                        // Notify listeners.
                        AreaLeaveEvent e = new AreaLeaveEvent(
                                event.getPlayer());
                        Bukkit.getServer().getPluginManager().callEvent(e);
                    }
                } else {
                    inArea.put(event.getPlayer(), false);
                    // Notify listeners.
                    AreaLeaveEvent e = new AreaLeaveEvent(
                            event.getPlayer());
                    Bukkit.getServer().getPluginManager().callEvent(e);
                }
                // Both suit & helmet required
                if (SpaceConfigHandler.getRequireHelmet(event.getPlayer().getWorld())
                        && SpaceConfigHandler.getRequireSuit(event.getPlayer().getWorld())) {
                    if (isUsed.containsKey(event.getPlayer())) {
                        if (isUsed.get(event.getPlayer()) == true
                                && event.getPlayer().getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()
                                && hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
                            isUsed.put(event.getPlayer(), false);
                        } else if (isUsed.get(event.getPlayer()) == false
                                && event.getPlayer().getInventory().getHelmet().getTypeId() != SpaceConfigHandler.getHelmetBlock()
                                || !hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);
                        }
                    } else {
                        if (event.getPlayer().getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()
                                && hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            isUsed.put(event.getPlayer(), false);
                        } else {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);
                        }
                    }
                    // Only helmet required
                } else if (SpaceConfigHandler.getRequireHelmet(event.getPlayer().getWorld())) {
                    if (isUsed.containsKey(event.getPlayer())) {
                        if (isUsed.get(event.getPlayer()) == true
                                && event.getPlayer().getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()) {
                            StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
                            isUsed.put(event.getPlayer(), false);
                        } else if (isUsed.get(event.getPlayer()) == false
                                && event.getPlayer().getInventory().getHelmet().getTypeId() != SpaceConfigHandler.getHelmetBlock()) {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);

                        }
                    } else {
                        if (event.getPlayer().getInventory().getHelmet().getTypeId() == SpaceConfigHandler.getHelmetBlock()) {
                            isUsed.put(event.getPlayer(), false);
                        } else {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);
                        }
                    }

                    // Only suit required
                } else if (SpaceConfigHandler.getRequireSuit(event.getPlayer().getWorld())) {
                    if (isUsed.containsKey(event.getPlayer())) {
                        if (isUsed.get(event.getPlayer()) == true
                                && hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
                            isUsed.put(event.getPlayer(), false);
                        } else if (isUsed.get(event.getPlayer()) == false
                                && !hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);

                        }
                    } else {
                        if (hasSuit(event.getPlayer(),
                                SpaceConfigHandler.getArmorType())) {
                            isUsed.put(event.getPlayer(), false);
                        } else {
                            SpaceRunnable2 task = new SpaceRunnable2(
                                    event.getPlayer());
                            taskInt = StyxSpace.scheduler.scheduleSyncRepeatingTask(plugin, task,
                                    20L, 20L);
                            taskid.put(event.getPlayer(), taskInt);
                            isUsed.put(event.getPlayer(), true);
                            // Notify listeners.
                            SpaceSuffocationEvent e = new SpaceSuffocationEvent(
                                    "SpaceSuffocationEvent", event.getPlayer());
                            Bukkit.getServer().getPluginManager().callEvent(e);
                        }
                    }

                }
            }
        } else {
            if (isUsed.containsKey(event.getPlayer())) {
                if (isUsed.get(event.getPlayer()) == true) {
                    StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
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
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (taskid.containsKey(event.getPlayer())) {
            if (StyxSpace.scheduler.isCurrentlyRunning(taskid.get(event.getPlayer()))) {
                StyxSpace.scheduler.cancelTask(taskid.get(event.getPlayer()));
            }
        }
    }

    /**
     * Called when a player joins the game.
     * 
     * @param event Event data
     */
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!armorType.containsKey(event.getPlayer())) {
            armorType.put(event.getPlayer(), SpaceConfigHandler.getArmorType());
        }
    }
}