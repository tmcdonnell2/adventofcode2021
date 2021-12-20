import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 1028926
public class Day8Part2 {

    String[] digitData;
    Integer[] digitsFromOutput;

    Day8Part2(String[] dayData) {
        digitData = dayData;
        digitsFromOutput = new Integer[digitData.length];
    }

    private void solve() {
        int i = 0;
        for (String s : digitData) {
            String[] splitDigitOutput = s.split(" \\| ");
            Map<Integer, String> wiringForOutput = setUniqueNumbers(splitDigitOutput[0]);
            deduceWiring(splitDigitOutput[0], wiringForOutput);

            String output = "";
            for (String digitWiring : splitDigitOutput[1].split(" ")) {
                output += produceOutputFromWiring(digitWiring, wiringForOutput);
            }
            digitsFromOutput[i++] = Integer.parseInt(output);
        }
    }

    private Map<Integer, String> setUniqueNumbers(String wiringSchematic) {
        Map<Integer, String> wiringForOutput = new HashMap<>();
        String[] outputs = wiringSchematic.split(" ");
        for (String output : outputs) {
            int numberByWires = -1;
            switch (output.length()) {
                case 2: // 1
                    numberByWires = 1;
                    break;
                case 3: // 7
                    numberByWires = 7;
                    break;
                case 4: // 4
                    numberByWires = 4;
                    break;
                case 7: // 8
                    numberByWires = 8;
                    break;
            }
            if (numberByWires != -1) {
                wiringForOutput.put(numberByWires, output);
            }
        }
        return wiringForOutput;
    }

    private void deduceWiring(String wiringSchematic, Map<Integer, String> wiringToDigitMapping) {
        String one = wiringToDigitMapping.get(1);
        String four = wiringToDigitMapping.get(4);

        for (String s : wiringSchematic.split(" ")) {
            String reducedString;
            int num = -1;
            if (s.length() == 5) { // 2, 3, 5
                reducedString = s.replaceAll("[" + one + "]", "");
                if (reducedString.length() == 3) { // 3
                    num = 3;
                } else if (reducedString.replaceAll("[" + four + "]", "").length() == 3) { // 2
                    num = 2;
                } else {
                    num = 5;
                }
            } else if (s.length() == 6) { // 0, 6, 9
                reducedString = s.replaceAll("[" + one + "]", "");
                if (reducedString.length() == 5) { // 6
                    num = 6;
                } else if (reducedString.replaceAll("[" + four + "]", "").length() == 3) { // 0
                    num = 0;
                } else {
                    num = 9;
                }
            }
            if (num != -1) {
                wiringToDigitMapping.put(num, s);
            }
        }
    }

    private int produceOutputFromWiring(String output, Map<Integer, String> wiringToDigitMapping) {
        String sortedString = Arrays.stream(output.split("")).sorted().collect(Collectors.joining());
        for (Map.Entry<Integer, String> mapping : wiringToDigitMapping.entrySet()) {
            String sortedMapValue = Arrays.stream(mapping.getValue().split("")).sorted().collect(Collectors.joining());
            if (sortedString.equals(sortedMapValue)) {
                return mapping.getKey();
            }
        }
        return -1;
    }

    private int getSolution() {
        return Arrays.stream(digitsFromOutput).reduce(Integer::sum).orElse(-1);
    }

    public static void main(String[] args)  {
        Day8Part2 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day8.txt"));
            challenge = new Day8Part2(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}
