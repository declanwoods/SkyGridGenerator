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

        int closest = -1;

        for (int i = 0; i < probabilities.size(); i++) {
            if ((closest == -1 || probabilities.get(i) < probabilities.get(closest)) && probabilities.get(i) > d) {
                closest = i;
            }
        }

        return blocks.get(closest);
    }
}
