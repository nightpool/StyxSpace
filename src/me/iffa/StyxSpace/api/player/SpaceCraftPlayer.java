package me.iffa.styxspace.api.player;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceHandler;
import me.iffa.styxspace.listeners.SpacePlayerListener;
import me.iffa.styxspace.utils.SpaceConfig;
import net.minecraft.server.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.inventory.ItemStack;

public class SpaceCraftPlayer extends org.bukkit.craftbukkit.entity.CraftPlayer
		implements SpacePlayer {
	// Variables for methods
	SpaceHandler sHandler = new SpaceHandler();

	public SpaceCraftPlayer(CraftServer server, EntityPlayer entity) {
		super(server, entity);
	}

	/* Interface overriden Public Methods */

	@Override
	public boolean isInSpace() {
		if (this.getWorld() == sHandler.getSpaceWorld()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAnyPermission(String permission) {
		boolean permissionPlugin = Bukkit.getServer().getPluginManager()
				.isPluginEnabled("Permissions");
		if (permissionPlugin) {
			if (StyxSpace.permissionHandler.has(this, permission)) {
				return true;
			}
		} else if (this.isOp() || this.hasPermission(permission)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasSpaceHelmet() {
		if (this.getInventory().getHelmet().getTypeId() == SpaceConfig.SPACEHELMET) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setSpaceHelmet(Integer blockid) {
		SpaceConfig.SPACEHELMET = blockid;
	}

	@Override
	public void setSpaceSuit(String armortype) {
		SpacePlayerListener.armorType.put(this, armortype);
	}

	@Override
	public void giveSpaceSuit(String armortype) {
		if (armortype.equalsIgnoreCase("diamond")) {
			this.getInventory().setBoots(
					new ItemStack(Material.DIAMOND_BOOTS, 1));
			this.getInventory().setChestplate(
					new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
			this.getInventory().setLeggings(
					new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("chainmail")) {
			this.getInventory().setBoots(
					new ItemStack(Material.CHAINMAIL_BOOTS, 1));
			this.getInventory().setChestplate(
					new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
			this.getInventory().setLeggings(
					new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("gold")) {
			this.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS, 1));
			this.getInventory().setChestplate(
					new ItemStack(Material.GOLD_CHESTPLATE, 1));
			this.getInventory().setLeggings(
					new ItemStack(Material.GOLD_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("iron")) {
			this.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS, 1));
			this.getInventory().setChestplate(
					new ItemStack(Material.IRON_CHESTPLATE, 1));
			this.getInventory().setLeggings(
					new ItemStack(Material.IRON_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("leather")) {
			this.getInventory().setBoots(
					new ItemStack(Material.LEATHER_BOOTS, 1));
			this.getInventory().setChestplate(
					new ItemStack(Material.LEATHER_CHESTPLATE, 1));
			this.getInventory().setLeggings(
					new ItemStack(Material.LEATHER_LEGGINGS, 1));
		} else if (armortype.equalsIgnoreCase("null")) {
			this.getInventory().setBoots(new ItemStack(Material.AIR, 1));
			this.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
			this.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
		}
	}
}
