import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {
    int gammaRate = 0;
    int epsilonRate = 0;

    List<String> binaryData;
    int[] amountSet = new int[12];

    Day3(List<String> binaryData) {
        this.binaryData = binaryData;
    }

    public void solve() {
        processBinaryData();
        setRates();
    }

    private void processBinaryData() {
        for (String line : binaryData) {
            for (int i = 0; i < line.length(); i++)  {
                if (line.charAt(i) == '1') {
                    amountSet[line.length() - 1 - i]++;
                }
            }
        }
    }

    private void setRates() {
        int len = binaryData.size();
        int mask = (int)Math.pow(2, 11);
        int i = 11;
        for (; i >= 0; mask >>= 1)  {
            int amount = amountSet[i--];
            if (amount > len - amount) {
                gammaRate |= mask;
            } else {
                epsilonRate |= mask;
            }
        }
    }

    public int getGammaByEpsilon() {
        return gammaRate * epsilonRate;
    }

    public static void main(String[] args) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("./data/Day3.txt"));
            Day3 challenge = new Day3(input.lines().collect(Collectors.toList()));
            challenge.solve();

            System.out.println(challenge.getGammaByEpsilon());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
