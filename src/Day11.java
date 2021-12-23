import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    GridOfDumbos grid;
    int stepAllFlashed = 0;

    Day11(int[] dayData) {
        grid = new GridOfDumbos(dayData);
    }

    private void solve() {
        final int STEPS = 10000; // Set to 100 for soln 1
        for (int step = 1; step <= STEPS; step++) {
            grid.incrementEnergyLevels();
            while (grid.octopi.stream().anyMatch(dumboOctopus -> dumboOctopus.getEnergyLevel() > 9)) {
                grid.checkEnergyLevels();
            }
            if (step <= 10 || step % 10 == 0) printGrid(step);
            if (grid.octopi.stream().mapToInt(DumboOctopus::getEnergyLevel).sum() == 0) {
                stepAllFlashed = step;
                printGrid(step);
                break;
            }
        }
    }

    private void printGrid(int step) {
        int[] values = grid.octopi.stream().mapToInt(DumboOctopus::getEnergyLevel).toArray();
        StringBuilder sb = new StringBuilder("Step " + step + System.lineSeparator());
        for (int i = 0; i < values.length; i++) {
            if (i % grid.COLUMNS == 0 && i != 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(values[i]).append(" ");
        }
        System.out.println(sb);
        System.out.println();
    }


    private long getSolution() {
        return stepAllFlashed;
    }

    public static void main(String[] args)  {
        Day11 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day11.txt"));
            challenge = new Day11(input.lines()
                            .map(s -> s.split(""))
                            .flatMap(Arrays::stream)
                            .mapToInt(Integer::parseInt)
                            .toArray());
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}

class DumboOctopus {
    private int energyLevel;
    private int flashes = 0;

    DumboOctopus(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public void incrementEnergyLevel() {
        energyLevel++;
    }

    public void getEnergyFromSurroundingOctopi() {
        if (energyLevel != 0) {
            incrementEnergyLevel();
        }
    }

    public boolean tryFlash() {
        if (energyLevel > 9) {
            flashes++;
            energyLevel = 0;
            return true;
        }
        return false;
    }

    public int getFlashes() {
        return flashes;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }
}

class GridOfDumbos {
    List<DumboOctopus> octopi;
    final int COLUMNS = 10;

    enum NEIGHBOURS {
        UP(0),
        UPRIGHT(1),
        RIGHT(2),
        DOWNRIGHT(3),
        DOWN(4),
        DOWNLEFT(5),
        LEFT(6),
        UPLEFT(7);

        private final int val;

        NEIGHBOURS(int val) {
            this.val = val;
        }
    }

    GridOfDumbos(int[] dayData) {
        octopi = new ArrayList<>(dayData.length);
        for (int i : dayData) {
            octopi.add(new DumboOctopus(i));
        }
    }

    public void incrementEnergyLevels() {
        for (DumboOctopus oct : octopi) {
            oct.incrementEnergyLevel();
        }
    }

    public void checkEnergyLevels() {
        for (int i = 0; i < octopi.size(); i++) {
            if (octopi.get(i).tryFlash()) {
                passEnergyToSurroundingOcti(i);
            }
        }
    }

    private void passEnergyToSurroundingOcti(int index) {
        int[] octopiNeighbours = getSurrondingOctopiIndexes(index);
        for (int i : octopiNeighbours) {
            if (i == -1) continue;
            octopi.get(i).getEnergyFromSurroundingOctopi();
        }
    }

    private int[] getSurrondingOctopiIndexes(int index) {
        int[] neighbours = new int[8];
        int indexRow = index/COLUMNS;

        neighbours[NEIGHBOURS.UP.val] = index - COLUMNS >= 0 ? index - COLUMNS : -1; // up

        neighbours[NEIGHBOURS.UPRIGHT.val] = index - COLUMNS + 1 > 0 &&
            (index - COLUMNS + 1) % COLUMNS > index % COLUMNS &&
            ((index - COLUMNS + 1)/COLUMNS) == (index - COLUMNS)/COLUMNS  ? index - COLUMNS + 1 : -1; // up-right

        neighbours[NEIGHBOURS.RIGHT.val] = (index + 1)/COLUMNS == indexRow ? index + 1 : -1; // right

        neighbours[NEIGHBOURS.DOWNRIGHT.val] = index + COLUMNS + 1 < octopi.size() &&
            (index + COLUMNS + 1) % COLUMNS > index % COLUMNS &&
            (index + COLUMNS + 1)/COLUMNS == (index + COLUMNS)/COLUMNS  ? index + COLUMNS + 1 : -1; // down-right

        neighbours[NEIGHBOURS.DOWN.val] = index + COLUMNS < octopi.size() ? index + COLUMNS : -1; // down

        neighbours[NEIGHBOURS.DOWNLEFT.val] = index + COLUMNS - 1 < octopi.size() &&
            (index + COLUMNS - 1) % COLUMNS < index % COLUMNS &&
            (index + COLUMNS - 1)/COLUMNS == (index + COLUMNS)/COLUMNS ? index + COLUMNS - 1 : -1; // down-left

        neighbours[NEIGHBOURS.LEFT.val] = (index - 1)/COLUMNS == indexRow ? index - 1 : -1; // left

        neighbours[NEIGHBOURS.UPLEFT.val] = index - COLUMNS - 1 >= 0 &&
            (index - COLUMNS - 1) % COLUMNS < index % COLUMNS &&
            (index - COLUMNS - 1)/COLUMNS == (index - COLUMNS)/COLUMNS ? index - COLUMNS - 1 : -1; // up-left

        return neighbours;
    }
}