import com.sun.source.tree.BinaryTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {

    Grid grid;
    List<Integer> lowestPoints;
    List<Integer> basinSizes;
    Set<Integer> testedBasinIndexes;

    Day9(String[] dayData) {
        String[] splitDayData = Arrays.stream(dayData).map(s -> s.split("")).flatMap(Arrays::stream).toArray(String[]::new);
        grid = new Grid(Arrays.stream(splitDayData).mapToInt(Integer::parseInt).toArray(), dayData[0].length());
        lowestPoints = new ArrayList<>();
        basinSizes = new ArrayList<>();
        testedBasinIndexes = new HashSet<>();
    }

    private void solve() {
        int height;
        for (int i = 0; i < grid.grid.length; i++) {
            height = determineSpotHeight(i);
            if (height > -1) {
                lowestPoints.add(height);
            }
        }

        for (int lowPointIndex : lowestPoints) {
            basinSizes.add(determineBasinSize(lowPointIndex));
        }
    }

    private int determineSpotHeight(int i) {
        int testValue = grid.getGridValue(i);
        int sum = 0;
        int numberOfNeighbours = grid.neighbours.length;
        grid.setNeighbours(i);
        for (Neighbour neighbour : grid.neighbours) {
            if (neighbour.value < testValue) return -1; // test value not the lowest around.
            if (neighbour.value == Grid.DUMMY_VALUE) {
                numberOfNeighbours--;
            } else {
                sum += neighbour.value;
            }
        }
        if (sum == testValue * numberOfNeighbours) return -1; // all the same surrounding values
        return i;
    }

    private Integer determineBasinSize(int lowPointIndex) {
        // Hold indexes of current basin, size returned for size of basin
        List<Integer> basinBuddies = new ArrayList<>();
        basinBuddies.add(lowPointIndex);

        // Hold values for testing current low point neighbours via index
        List<Integer> testIndexes = new ArrayList<>();
        testIndexes.add(lowPointIndex);

        while (!testIndexes.isEmpty()) {
            int testIndex = testIndexes.remove(0);
            int testValue = grid.getGridValue(testIndex);
            grid.setNeighbours(testIndex);
            for (Neighbour neighbour : grid.neighbours) {
                if (neighbour.value >= Grid.DUMMY_VALUE - 1) continue; // instant disqualification

                if (neighbour.value > testValue &&
                    !basinBuddies.contains(neighbour.index) && // not already in current considered basin
                    !testedBasinIndexes.contains(neighbour.index)) { // not already in some other basin
                    basinBuddies.add(neighbour.index);
                    testedBasinIndexes.add(neighbour.index);
                    testIndexes.add(neighbour.index);
                }
            }
        }
        return basinBuddies.size();
    }

    private int getSolution() {
        return basinSizes.stream().sorted().skip(basinSizes.size() - 3).reduce(1, Math::multiplyExact);
    }

    public static void main(String[] args)  {
        Day9 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day9.txt"));
            challenge = new Day9(input.lines().map(s -> String.join("", s.split(""))).toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}

class Grid {
    int[] grid;
    int columns;

    final Neighbour[] neighbours = new Neighbour[4];

    public static final int DUMMY_VALUE = 10;

    public enum NEIGHBOURS {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    Grid(int[] grid, int columns) {
        this.grid = grid;
        this.columns = columns;
    }

    public int getGridValue(int index) {
        if (index < 0 || index > grid.length - 1) return DUMMY_VALUE;
        return grid[index];
    }

    public void setNeighbours(int index) {
        int indexRow = findRow(index);

        Neighbour upNeighbour = new Neighbour(index - columns >= 0 ? getGridValue(index - columns) : DUMMY_VALUE, index - columns);
        neighbours[NEIGHBOURS.UP.ordinal()] = upNeighbour;

        int forwardOneIndexRow = findRow(index + 1);
        Neighbour rightNeighbour = new Neighbour(forwardOneIndexRow == indexRow ? getGridValue(index + 1) : DUMMY_VALUE, index + 1);
        neighbours[NEIGHBOURS.RIGHT.ordinal()] = rightNeighbour;

        Neighbour downNeighbour = new Neighbour(index + columns < grid.length ? getGridValue(index + columns) : DUMMY_VALUE, index + columns);
        neighbours[NEIGHBOURS.DOWN.ordinal()] = downNeighbour;

        int backOneIndexRow = findRow(index - 1);
        Neighbour leftNeighbour = new Neighbour(backOneIndexRow == indexRow ? getGridValue(index - 1) : DUMMY_VALUE, index - 1);
        neighbours[NEIGHBOURS.LEFT.ordinal()] = leftNeighbour;
    }

    private int findRow(int index) {
        return index / columns;
    }
}

class Neighbour {
    int value;
    int index;

    Neighbour(int value, int index) {
        this.value = value;
        this.index = index;
    }
}