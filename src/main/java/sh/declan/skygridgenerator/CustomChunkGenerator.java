package sh.declan.skygridgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class CustomChunkGenerator extends ChunkGenerator {
    private String worldName;
    private String id;
    private Logger logger;

    CustomChunkGenerator(String inWorldName, String inId, Logger inLogger) {
        id = inId;
        worldName = inWorldName;
        logger = inLogger;
    }

    private int getRangeWithMax(Random random, int max) {
        return random.nextInt(max + 1);
    }

    private Material getUndergroundBlock(Random random) {
        int choice = getRangeWithMax(random,300);

        HashMap<Integer, Material> blocks = new HashMap<>();
        blocks.put(10, Material.DIAMOND_ORE);
        blocks.put(25, Material.GOLD_ORE);
        blocks.put(100, Material.IRON_ORE);
        blocks.put(150, Material.CAKE);
        blocks.put(300, Material.STONE);

        int key = Collections.max(blocks.keySet());
        for (Integer i : blocks.keySet()) {
            if (i > choice && i < key) {
                key = i;
            }
        }

        return blocks.get(key);
    }

    private Material getRandomBlock(Random random, int y) {
        Material block;

        if (y < 64) {
            block = getUndergroundBlock(random);
        } else if (y < 216) {
            block = Material.DIRT;
        } else {
            block = Material.WHITE_WOOL;
        }

        return block;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 61 ,0);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        int div = 4;

        for (int x = 0; x < 16/div; x++) {
            for (int z = 0; z < 16/div; z++) {
                for (int y = 0; y < chunk.getMaxHeight()/div; y++) {
                    chunk.setBlock(x*div, y*div, z*div, getRandomBlock(random, y*div));
                }
            }
        }

        if (chunkX == 0 && chunkZ == 0) {
            chunk.setBlock(0, 60, 0, Material.BEDROCK);
        }

        return chunk;
    }
}