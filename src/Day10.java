import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

    final Map<Character, Integer> POINT_MAPPING;
    final Map<Character, Integer> COMPLETION_POINTS;
    final Character[][] brackets;
    final List<Character> incorrectBrackets;
    final Stack<Character> bracketStack;
    final List<Long> pointsForCompletingLines;

    final List<Character> LEFT_BRACKETS = new ArrayList<>(Arrays.asList('(','{','[','<'));
    final List<Character> RIGHT_BRACKETS = new ArrayList<>(Arrays.asList(')','}',']','>'));

    Day10(String[] dayData) {
        POINT_MAPPING = new HashMap<>();
        POINT_MAPPING.put(')', 3);
        POINT_MAPPING.put(']', 57);
        POINT_MAPPING.put('}', 1197);
        POINT_MAPPING.put('>', 25137);

        COMPLETION_POINTS = new HashMap<>();
        COMPLETION_POINTS.put(')', 1);
        COMPLETION_POINTS.put(']', 2);
        COMPLETION_POINTS.put('}', 3);
        COMPLETION_POINTS.put('>', 4);

        pointsForCompletingLines = new ArrayList<>();

        brackets = new Character[dayData.length][Arrays.stream(dayData)
                    .max((a, b) -> Math.max(a.length(), b.length()))
                    .orElse("").length() + 1];
        for (int i = 0; i < dayData.length; i++) {
            for (int j = 0; j < dayData[i].length(); j++) {
                brackets[i][j] = dayData[i].charAt(j);
            }
        }
        bracketStack = new Stack<>();
        incorrectBrackets = new ArrayList<>();
    }

    private void solve() {
        removeIncorrectLine();

        for (Character[] line : brackets) {
            if (null != line) {
                pointsForCompletingLines.add(completeBracketLine(line));
            }
        }
        getSolution();
    }

    private void removeIncorrectLine() {
        int lineNum = 1;
        for (Character[] line : brackets) {
            for (Character c : line) {
                if (LEFT_BRACKETS.contains(c)) {
                    bracketStack.add(c);
                    continue;
                }
                if (RIGHT_BRACKETS.contains(c)) {
                    char lastBracketAdded = bracketStack.pop();
                    if (!matchingBrackets(lastBracketAdded, c)) {
                        reportBrackets(lastBracketAdded, c, lineNum);
                        incorrectBrackets.add(c);
                        brackets[lineNum - 1] = null; // One way to do it...
                        break;
                    }
                }
            }
            lineNum++;
        }
    }

    private long completeBracketLine(Character[] line) {
        for (Character c : line) {
            if (LEFT_BRACKETS.contains(c)) {
                bracketStack.add(c);
                continue;
            }
            if (RIGHT_BRACKETS.contains(c)) {
                bracketStack.pop();
            }
        }

        long points = 0;
        while (!bracketStack.isEmpty()) {
            char currentBracket = bracketStack.pop();
            int matchingIndexForBracket = LEFT_BRACKETS.indexOf(currentBracket);
            char missingBracketInPair = RIGHT_BRACKETS.get(matchingIndexForBracket);

            points *= 5;
            points += COMPLETION_POINTS.get(missingBracketInPair);
        }

        return points;
    }

    private boolean matchingBrackets(char left, char right) {
        return LEFT_BRACKETS.indexOf(left) == RIGHT_BRACKETS.indexOf(right);
    }

    private void reportBrackets(char lastBracketAdded, char currentBracket, int line) {
        System.out.printf("Was expecting %s, got %s instead | Line %s" + System.lineSeparator(),
                RIGHT_BRACKETS.get(LEFT_BRACKETS.indexOf(lastBracketAdded)), currentBracket, line);
    }

// Old getSolution
//    private int getSolution() {
//        int sum = 0;
//        for (char c : incorrectBrackets) {
//            sum += POINT_MAPPING.get(c);
//        }
//        return sum;
//    }

    private long getSolution() {
        List<Long> sortedPoints = pointsForCompletingLines.stream().sorted().collect(Collectors.toList());

        return sortedPoints.get((int)Math.ceil(sortedPoints.size()/2));
    }

    public static void main(String[] args)  {
        Day10 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day10.txt"));
            challenge = new Day10(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}