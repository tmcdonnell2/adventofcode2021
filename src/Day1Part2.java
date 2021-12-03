import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Day1Part2 {
    BufferedReader depthData;
    int[] window = new int[3];

    Day1Part2() {
        try {
            depthData = new BufferedReader(new FileReader("./data/Day1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < window.length; i++) {
            window[i] = getNextDepth();
        }
    }

    public int findNumberOfLargerDepthWindows() {
        int previousWindowSum = window[0] + window[1] + window[2];
        int numOfLarger = 0;
        int i = 0;
        int nextDepth;
        while ((nextDepth = getNextDepth()) != -1) {
            window[i++ % 3] = nextDepth;
            numOfLarger += compareDepthWindows(previousWindowSum);
            previousWindowSum = window[0] + window [1] + window[2];
        }
        return numOfLarger;
    }

    private int compareDepthWindows(int previousWindowSum) {
        return previousWindowSum < (window[0] + window[1] + window[2]) ? 1 : 0;
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
        var challenge = new Day1Part2();
        int numberOfLargerDepthWindows = challenge.findNumberOfLargerDepthWindows();
        System.out.println(numberOfLargerDepthWindows);
    }
}