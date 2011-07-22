package me.iffa.styxspace.utils;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;

/**
 * Generates space world (without planets)
 * 
 * @author iffa
 * @author Canis85
 * 
 */
public class SpaceChunkGenerator extends ChunkGenerator {

	/**
	 * Generates a space world.
	 */
	public byte[] generate(World world, Random random, int cx, int cz) {
		byte[] result = new byte[32768];
		Arrays.fill(result, (byte) 0);

		// If 0,0 chunk, generate spawn block here
		if (cx == 0 && cz == 0) {
			result[64] = (byte) Material.GLOWSTONE.getId();
		}
		return result;
	}

	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 0, 64);
	}
}
