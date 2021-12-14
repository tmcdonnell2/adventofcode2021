import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7Part2 {

    List<Integer> crabInitialPositions;
    int[] fuelSpent;

    Day7Part2(String data) {
        crabInitialPositions = Arrays.stream(data.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        fuelSpent = new int[crabInitialPositions.stream().max(Integer::compareTo).get() + 1];
        crabInitialPositions = crabInitialPositions.stream().sorted().collect(Collectors.toList());
    }

    private void solve() {
        int testingNumber = 0;
        while(testingNumber++ < crabInitialPositions.size()) {
            // Testing number in terms of what number to "normalize" all other numbers too.
            for (Integer crabInitialPosition : crabInitialPositions) {
                fuelSpent[testingNumber] += gaussianSum(Math.abs(testingNumber - crabInitialPosition));
            }
        }
    }

    private int gaussianSum(int num) {
        int sum = (num*(num + 1))/2;
        return sum;
    }

    private int getSolution() {
        return Arrays.stream(fuelSpent).filter(i -> i > 0).min().getAsInt();
    }

    public static void main(String[] args)  {
        Day7Part2 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day7.txt"));
            challenge = new Day7Part2(input.readLine());
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}
