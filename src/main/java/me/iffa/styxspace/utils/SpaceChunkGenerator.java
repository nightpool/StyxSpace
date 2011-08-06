// Package Declaration
package me.iffa.styxspace.utils;

// Java Imports
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Bukkit Imports
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

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
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator) new SpaceGlowstonePopulator(), new SpaceAsteroidPopulator());
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
