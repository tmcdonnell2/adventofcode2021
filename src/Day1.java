import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Day1 {
    BufferedReader depthData;
    int currentDepth;

    Day1() {
        try {
            depthData = new BufferedReader(new FileReader("./data/Day1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        currentDepth = getNextDepth();
    }

    public int findNumberOfLargerDepths() {
        int nextDepth = getNextDepth();
        int numOfLarger = 0;
        do {
            numOfLarger += compareDepths(nextDepth);
            currentDepth = nextDepth;
            nextDepth = getNextDepth();
        }
        while (nextDepth != -1);
        return numOfLarger;
    }

    private int compareDepths(int nextDepth) {
        return currentDepth < nextDepth ? 1 : 0;
    }

    private int getNextDepth() {
        try {
            String data = depthData.readLine();
            if (data != null) {
                return Integer.parseInt(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        var challenge = new Day1();
        int numberOfLargerDepths = challenge.findNumberOfLargerDepths();
        System.out.println(numberOfLargerDepths);
    }
}