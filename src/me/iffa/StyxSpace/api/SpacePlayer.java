package me.iffa.StyxSpace.api;

import me.iffa.StyxSpace.StyxSpace;
import me.iffa.StyxSpace.listeners.SpacePlayerListener;
import me.iffa.StyxSpace.utils.SpaceConfig;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * SpacePlayer for player related methods (for usage with other plugins that use the StyxSpace API)
 * 
 * @author iffa
 * 
 */
public class SpacePlayer {
	/**
	 * Checks if the Player is in space - returns true if is
	 * 
	 * @return
	 */
	public boolean isInSpace(Player player) {
		if (player.getWorld() == StyxSpace.getSpace()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the player has permission
	 * 
	 * @param permission
	 *            Permission node to check
	 * @param player
	 * @return true if the player has permission
	 */
	public boolean hasPermission(String permission, Player player) {
		boolean permissionPlugin = Bukkit.getServer().getPluginManager()
				.isPluginEnabled("Permissions");
		if (permissionPlugin) {
			if (StyxSpace.permissionHandler.has(player, permission)) {
				return true;
			}
		} else if (player.isOp() || player.hasPermission(permission)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the Player has a space helmet - returns true if is
	 * 
	 * @return
	 */
	public boolean hasSpaceHelmet(Player player) {
		if (player.getInventory().getHelmet().getTypeId() == SpaceConfig.SPACEHELMET) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the player's spacehelmet to the given block id.
	 * 
	 * @param player
	 * @param blockid
	 *            any block id
	 */
	public void setSpaceHelmet(Player player, Integer blockid) {
		SpacePlayerListener.helmetType.put(player, blockid);
	}

	/**
	 * Sets the player's spacesuit to the given armortype
	 * 
	 * @param player
	 * @param armortype
	 *            Diamond, chainmail, gold, iron or leather
	 */
	public void setSpaceSuit(Player player, String armortype) {
		SpacePlayerListener.armorType.put(player, armortype);
	}

	/**
	 * Gives the player a set of armor
	 * 
	 * @param player
	 * @param armortype
	 *            Diamond, chainmail, gold, iron, leather or null
	 */
	public void giveSpaceSuit(Player player, String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			/**
			 * armortype = "diamond"
			 */
			player.getInventory().setBoots(
					new ItemStack(Material.DIAMOND_BOOTS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			/**
			 * armortype = "chainmail"
			 */
			player.getInventory().setBoots(
					new ItemStack(Material.CHAINMAIL_BOOTS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("gold")) {
			/**
			 * armortype = "gold"
			 */
			player.getInventory().setBoots(
					new ItemStack(Material.GOLD_BOOTS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.GOLD_CHESTPLATE, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.GOLD_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("iron")) {
			/**
			 * armortype = "iron"
			 */
			player.getInventory().setBoots(
					new ItemStack(Material.IRON_BOOTS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.IRON_CHESTPLATE, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.IRON_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("leather")) {
			/**
			 * armortype = "leather"
			 */
			player.getInventory().setBoots(
					new ItemStack(Material.LEATHER_BOOTS, 1));
			player.getInventory().setChestplate(
					new ItemStack(Material.LEATHER_CHESTPLATE, 1));
			player.getInventory().setLeggings(
					new ItemStack(Material.LEATHER_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("null")) {
			/**
			 * armortype = "null"
			 */
			player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
			player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
			player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
		}
	}
}
