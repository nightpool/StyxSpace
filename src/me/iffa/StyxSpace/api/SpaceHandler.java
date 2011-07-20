package me.iffa.styxspace.api;

import me.iffa.styxspace.StyxSpace;

import org.bukkit.World;

/**
 * SpacePlayer for player related methods (for usage with other plugins that use
 * the StyxSpace API).
 * 
 * @author iffa
 * 
 */
public class SpaceHandler {
	/**
	 * Gets the space world.
	 * 
	 * @return the space world
	 */
	public World getSpaceWorld() {
		return StyxSpace.getSpace();
	}
}
