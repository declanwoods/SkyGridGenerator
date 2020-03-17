package sh.declan.skygridgenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
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

    private BlockSet undergroundBlocks = new BlockSet();
    private BlockSet groundBlocks = new BlockSet();
    private BlockSet airBlocks = new BlockSet();

    CustomChunkGenerator(String inWorldName, String inId, Logger inLogger) {
        id = inId;
        worldName = inWorldName;
        logger = inLogger;

        undergroundBlocks.add(Material.STONE, 92);
        undergroundBlocks.add(Material.COAL_ORE, 5);
        undergroundBlocks.add(Material.IRON_ORE, 3);
        undergroundBlocks.add(Material.GOLD_ORE, 0.8);
        undergroundBlocks.add(Material.DIAMOND_ORE, 0.2);
    }

    private Material getRandomBlock(Random random, int y) {
        Material block;
        double randomDouble = random.nextDouble();

        if (y < 64) {
            block = undergroundBlocks.getWith(randomDouble);
        } else if (y < 216) {
            block = groundBlocks.getWith(randomDouble);
        } else {
            block = airBlocks.getWith(randomDouble);
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

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < chunk.getMaxHeight(); y++) {
                    world.setBiome(x, y, z, Biome.PLAINS);
                    if (x % div == 0 && y % div == 0 && z % div == 0) {
                        chunk.setBlock(x, y, z, getRandomBlock(random, y));
                    }
                }
            }
        }

        if (chunkX == 0 && chunkZ == 0) {
            chunk.setBlock(0, 60, 0, Material.BEDROCK);
        }

        return chunk;
    }
}