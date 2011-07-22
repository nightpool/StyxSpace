package me.iffa.styxspace.utils;

import java.util.HashMap;
import java.util.Map;

import me.iffa.styxspace.StyxSpace;
import me.iffa.styxspace.api.SpaceListener;
import me.iffa.styxspace.api.player.SpacePlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * CommandExecutor for 'space'
 * 
 * @author iffa
 * 
 */
public class SpaceCommandExec implements CommandExecutor {

	// Variables
	private Map<Player, Location> exitDest;
	private Map<Player, Location> enterDest;
	private Plugin plugin;
	private SpacePlayer spacePlayer = new SpacePlayer();

	public SpaceCommandExec(Plugin plugin) {
		super();
		this.plugin = plugin;
		exitDest = new HashMap<Player, Location>();
		enterDest = new HashMap<Player, Location>();
	}

	/**
	 * Called when a player uses the command
	 */
	public boolean onCommand(CommandSender sender, Command command,
			String label, String args[]) {
		Player sex = (Player) sender;
		Player player = (Player) sender;
		// Notify listeners.
		for (SpaceListener listener : StyxSpace.listeners)
			listener.onSpaceCommand(sex);
		if (args.length == 0 && sender instanceof Player) {
			if (spacePlayer.hasPermission("styxspace.teleport.enter", player)) {
				if (player.getWorld() == StyxSpace.getSpace()) {
					player.sendMessage(ChatColor.RED
							+ "[StyxSpace] You are already in space!");
					return true;
				}
				exitDest.put(player, player.getLocation());
				Location location;
				if (enterDest.containsKey(player)) {
					location = enterDest.get(player);
				} else {
					location = StyxSpace.getSpace().getSpawnLocation();
				}

				Chunk spaceSpawnChunk = StyxSpace.getSpace().getChunkAt(
						location);
				if (!StyxSpace.getSpace().isChunkLoaded(spaceSpawnChunk)) {
					StyxSpace.getSpace().loadChunk(spaceSpawnChunk);
				}
				player.teleport(location);
				return true;
			}
			sender.sendMessage(ChatColor.RED
					+ "[StyxSpace] You don't have permissions to go to space!");
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("back")
				&& sender instanceof Player) {
			if (player.getWorld() == StyxSpace.getSpace()) {
				PluginManager pm = Bukkit.getServer().getPluginManager();
				boolean permission = pm.isPluginEnabled("Permissions");
				if (permission) {
					if (!StyxSpace.permissionHandler.has((Player) sender,
							"styxspace.teleport.exit")) {
						permission = false;
					}
				} else if (sender.isOp()) {
					permission = true;
				}
				if (permission)
					enterDest.put(player, player.getLocation());
				Location location;
				if (permission) {
					if (exitDest.containsKey(player)) {
						location = exitDest.get(player);

						player.teleport(location);
						return true;
					} else {
						exitDest.put(player, plugin.getServer().getWorlds()
								.get(0).getSpawnLocation());
						sender.sendMessage(ChatColor.RED
								+ "[StyxSpace] Exit destination not found, setting to default world spawn location.");
						sender.sendMessage(ChatColor.RED
								+ "Type '/space back' again to go there.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "[StyxSpace] You don't have permissions to leave space!");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED
						+ "[StyxSpace] You are not in space!");
				return true;
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.RED + "[StyxSpace] Usage:");
			sender.sendMessage(ChatColor.RED + "/space - Go to space");
			sender.sendMessage(ChatColor.RED + "/space back - Leave space");
			if (spacePlayer.hasPermission("StyxSpace.teleport.enterothers",
					player)) {
				sender.sendMessage(ChatColor.RED
						+ "/space playername - Teleport others to space");
			}
			sender.sendMessage(ChatColor.RED
					+ "/space help - Brings up this help message");
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (spacePlayer.hasPermission("StyxSpace.admin.reload", player)) {
				SpaceConfig.WORLD_NAME = (String) SpaceConfig.myConfig
						.getProperty("styxspace.worldname");
				SpaceConfig.ALWAYS_NIGHT = SpaceConfig.myConfig.getBoolean(
						"styxspace.alwaysnight", true);
				SpaceConfig.WEATHER = SpaceConfig.myConfig.getBoolean(
						"styxspace.weather", false);
				SpaceConfig.HOSTILE_MOBS = SpaceConfig.myConfig.getBoolean(
						"styxspace.hostilemobs", false);
				SpaceConfig.NEUTRAL_MOBS = SpaceConfig.myConfig.getBoolean(
						"styxspace.neutralmobs", true);
				SpaceConfig.GIVEHELMET = SpaceConfig.myConfig.getBoolean(
						"styxspace.helmet.givehelmet", false);
				SpaceConfig.SPACEHELMET = SpaceConfig.myConfig.getInt(
						"styxspace.helmet.blockid", 86);
				SpaceConfig.HELMET_REQUIRED = SpaceConfig.myConfig.getBoolean(
						"styxspace.helmet.required", false);
				SpaceConfig.GIVESUIT = SpaceConfig.myConfig.getBoolean(
						"styxspace.suit.givesuit", false);
				SpaceConfig.DEFAULT_ARMOR = SpaceConfig.myConfig.getString(
						"styxspace.suit.armortype", "iron");
				SpaceConfig.SUIT_REQUIRED = SpaceConfig.myConfig.getBoolean(
						"styxspace.suit.required", false);
				SpaceConfig.ROOM_HEIGHT = SpaceConfig.myConfig.getInt(
						"styxspace.breathingarea.maxroomheight", 5);
				SpaceConfig.FLYING = SpaceConfig.myConfig.getBoolean(
						"styxspace.enablegravity", true);
				player.sendMessage(ChatColor.GREEN + StyxSpace.prefix
						+ " Reloaded configuration file!");
				return true;
			}

			return true;
		} else if (args.length == 1 && !args[0].equalsIgnoreCase("back")) {
			if (spacePlayer.hasPermission("StyxSpace.teleport.enterothers",
					player)) {
				if (Bukkit.getServer().getPlayer(args[0]) != null) {
					Player player2 = Bukkit.getServer().getPlayer(args[0]);
					player2.teleport(StyxSpace.getSpace().getSpawnLocation());
					sender.sendMessage(ChatColor.GREEN + "[StyxSpace] '"
							+ player2.getName() + "' was sent to space!");
					player.sendMessage(ChatColor.GREEN
							+ "[StyxSpace] You have been sent to space by '"
							+ player.getName() + "'!");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED
							+ "[StyxSpace] The player you were looking for was not found!");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "[StyxSpace] You don't have permissions to teleport others to space!");
				return true;
			}

		} else {
			sender.sendMessage(ChatColor.RED + "[StyxSpace] Usage:");
			sender.sendMessage(ChatColor.RED + "/space - Go to space");
			sender.sendMessage(ChatColor.RED + "/space back - Leave space");
			if (spacePlayer.hasPermission("StyxSpace.teleport.enterothers",
					player)) {
				sender.sendMessage(ChatColor.RED
						+ "/space playername - Teleport others to space");
			}
			sender.sendMessage(ChatColor.RED
					+ "/space help - Brings up this help message");
			return true;
		}
	}
}
