import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    List<Integer> crabInitialPositions;
    int[] fuelSpent;

    Day7(String data) {
        crabInitialPositions = Arrays.stream(data.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        fuelSpent = new int[crabInitialPositions.stream().max(Integer::compareTo).get() + 1];
        crabInitialPositions = crabInitialPositions.stream().sorted().collect(Collectors.toList());
    }

    private void solve() {
        List<Integer> testCrabPositions = crabInitialPositions.stream().distinct().collect(Collectors.toList());

        // Testing number in terms of what number to "normalize" all other numbers too.
        int testIndex = 0;
        while(testCrabPositions.size() > 0) {
            int testingNumber = testCrabPositions.remove(0);
            for (int i = 0; i < crabInitialPositions.size(); i++) {
                fuelSpent[testingNumber] += Math.abs(testingNumber - crabInitialPositions.get(i));
            }
        }
    }

    // 329389

    private int getSolution() {
        return Arrays.stream(fuelSpent).filter(i -> i > 0).min().getAsInt();
    }

    public static void main(String[] args)  {
        Day7 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day7.txt"));
            challenge = new Day7(input.readLine());
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}
