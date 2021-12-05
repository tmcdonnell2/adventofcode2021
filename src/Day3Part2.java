import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day3Part2 {
    int oxygenRating;
    int scrubbingRating;

    List<String> binaryData;

    Day3Part2(List<String> binaryData) {
        this.binaryData = binaryData;
    }

    public void solve() {
        oxygenRating = Integer.parseInt(findO2Rating(),2);
        scrubbingRating = Integer.parseInt(findCO2ScrubbingRating(), 2);
    }

    private int getLifeSupportRating() {
        return oxygenRating * scrubbingRating;
    }

    private List<String> getCommonListOnPredicate(int index, BiPredicate<List, List> pred, List<String> data) {
        List<String> setBits = data.stream().filter((s -> s.charAt(index) == '1')).collect(Collectors.toList());
        List<String> unsetBits = data.stream().filter((s -> s.charAt(index) == '0')).collect(Collectors.toList());

        if (pred.test(setBits, unsetBits)) {
            return setBits;
        }
        return unsetBits;
    }

    /*
     * To find oxygen generator rating, determine the most common value (0 or 1) in the current bit position,
     * and keep only numbers with that bit in that position.
     * If 0 and 1 are equally common, keep values with a 1 in the position being considered.
     */
    private String findO2Rating() {
        BiPredicate<List, List> mostCommonFactor = new BiPredicate<List, List>() {
            @Override
            public boolean test(List list, List otherList) {
                return list.size() >= otherList.size();
            }
        };


        List<String> leftovers = new ArrayList<>(binaryData);
        for (int i = 0; i < 12; i++) {
            leftovers = getCommonListOnPredicate(i, mostCommonFactor, leftovers);
            if (leftovers.size() == 1) break;
        }
        return leftovers.get(0);
    }

    /*
    * To find CO2 scrubber rating, determine the least common value (0 or 1) in the current bit position,
    * and keep only numbers with that bit in that position.
    * If 0 and 1 are equally common, keep values with a 0 in the position being considered.
    * */
    private String findCO2ScrubbingRating() {
        BiPredicate<List, List> leastCommonFactor = (list, otherList) -> list.size() < otherList.size();

        List<String> leftovers = new ArrayList<>(binaryData);
        for (int i = 0; i < 12; i++) {
            leftovers = getCommonListOnPredicate(i, leastCommonFactor, leftovers);
            if (leftovers.size() == 1) break;
        }
        return leftovers.get(0);
    }

    public static void main(String[] args) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("./data/Day3.txt"));
            Day3Part2 challenge = new Day3Part2(input.lines().collect(Collectors.toList()));
            challenge.solve();

            System.out.println(challenge.getLifeSupportRating());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
