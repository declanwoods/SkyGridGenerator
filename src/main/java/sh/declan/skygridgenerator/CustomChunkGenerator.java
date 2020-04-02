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

        undergroundBlocks.add(Material.STONE, 83);
        undergroundBlocks.add(Material.COAL_ORE, 8);
        undergroundBlocks.add(Material.IRON_ORE, 3.6);
        undergroundBlocks.add(Material.REDSTONE_ORE, 1);
        undergroundBlocks.add(Material.GOLD_ORE, 0.8);
        undergroundBlocks.add(Material.DIAMOND_ORE, 0.2);
        undergroundBlocks.add(Material.EMERALD_ORE, 0.4);
        undergroundBlocks.add(Material.LAPIS_ORE, 1);
        undergroundBlocks.add(Material.OBSIDIAN, 2);
        undergroundBlocks.add(Material.SLIME_BLOCK, 1);

        groundBlocks.add(Material.DIRT, 30); // 30
        groundBlocks.add(Material.OAK_LOG, 3); // 33
        groundBlocks.add(Material.GRASS_BLOCK, 1); // 34
        groundBlocks.add(Material.OAK_LEAVES, 2); // 36
        groundBlocks.add(Material.SNOW_BLOCK, 1); // 37
        groundBlocks.add(Material.SPRUCE_LOG, 3); // 40
        groundBlocks.add(Material.BIRCH_LOG, 3); // 43
        groundBlocks.add(Material.COBBLESTONE, 1); //44
        groundBlocks.add(Material.WATER, 1); // 45
        groundBlocks.add(Material.SAND, 4); // 49
        groundBlocks.add(Material.GRAVEL, 2); // 51
        groundBlocks.add(Material.PACKED_ICE, 1); // 52
        groundBlocks.add(Material.COAL_ORE, 1); // 53
        groundBlocks.add(Material.WHITE_WOOL, 1.5); // 54.5
        groundBlocks.add(Material.CACTUS, 1.5); // 56
        groundBlocks.add(Material.CLAY, 2.5); // 58.5
        groundBlocks.add(Material.MELON, 1); // 59.5
        groundBlocks.add(Material.TERRACOTTA, 3); // 62.5
        groundBlocks.add(Material.HAY_BLOCK, 1.5); // 64
        groundBlocks.add(Material.ANDESITE, 1.5); // 65.5
        groundBlocks.add(Material.GRANITE, 1.5); // 67
        groundBlocks.add(Material.DIORITE, 1.5); // 68.5
        groundBlocks.add(Material.SOUL_SAND, 1); // 69.5
        groundBlocks.add(Material.NETHERRACK, 1); // 70.5
        groundBlocks.add(Material.HONEYCOMB_BLOCK, 1.5); // 72

        airBlocks.add(Material.AIR, 84);
        airBlocks.add(Material.DIAMOND_BLOCK, 0.1);
        airBlocks.add(Material.END_STONE, 3);
        airBlocks.add(Material.GOLD_BLOCK, 0.5);
        airBlocks.add(Material.CAKE, 1);
        airBlocks.add(Material.QUARTZ_BLOCK, 1);
        airBlocks.add(Material.GLOWSTONE, 2);
        airBlocks.add(Material.SEA_LANTERN, 0.5);
        airBlocks.add(Material.BONE_BLOCK, 1);
        airBlocks.add(Material.WHITE_CONCRETE, 1);
        airBlocks.add(Material.DRIED_KELP_BLOCK, 1);
        airBlocks.add(Material.BRAIN_CORAL, 1);
        airBlocks.add(Material.BOOKSHELF, 1);
        airBlocks.add(Material.SPONGE, 1.3);
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