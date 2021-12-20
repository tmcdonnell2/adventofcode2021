import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    String[] digitData;
    int amountUniqueFor1478;

    Day8(String[] dayData) {
        List<String> dataAsList = Arrays.stream(dayData).collect(Collectors.toList());
        digitData = new String[dataAsList.size()];
        for (String s : dataAsList) {
            digitData[dataAsList.indexOf(s)] = s.split("\\| ")[1];
        }
        System.out.println(Arrays.toString(digitData));
    }

    private void solve() {
        for (String s : digitData) {
            String [] splitDigitOutput = s.split(" ");
            for (String z : splitDigitOutput) {
                switch (z.length()) {
                    case 2: // 1
                    case 3: // 7
                    case 4: // 4
                    case 7: // 8
                        amountUniqueFor1478++;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private int getSolution() {
        return amountUniqueFor1478;
    }

    public static void main(String[] args)  {
        Day8 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day8.txt"));
            challenge = new Day8(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}
