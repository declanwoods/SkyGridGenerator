package sh.declan.skygridgenerator;

import org.bukkit.Material;

import java.util.ArrayList;

public class BlockSet {
    private double curMax = 0;
    private ArrayList<Material> blocks;
    private ArrayList<Double> probabilities;

    public BlockSet() {
        blocks = new ArrayList<>();
        probabilities = new ArrayList<>();
    }

    public void add(Material block, double probability) {
        curMax += probability;
        probabilities.add(curMax);
        blocks.add(block);
    }

    public Material getWith(double randomDouble) {
        double d = curMax * randomDouble;

        for (int i = 1; i < probabilities.size(); i++) {
            if (probabilities.get(i-1) < d && probabilities.get(i) > d) {
                return blocks.get(i-1);
            }
        }

        return blocks.get(blocks.size() - 1);
    }
}
