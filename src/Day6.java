import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day6 {
    final private byte MAX_DAY = 6;
    final private byte NEW_FISH = 8;

    long[][] graph = new long[NEW_FISH + 1][1];

    Day6(String data) {
        List<Long> initData = Arrays.stream(data.split(","))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
        for (Long i : initData) {
            graph[i.intValue()][0]++;
        }
    }

    private void solve() {
        int day = 0;
        System.out.print("For how many days? >");
        int FINAL_DAY = new Scanner(System.in).nextInt();
        long fishReadyToSpawn;
        System.out.println();
        System.out.println();
        System.out.println(Arrays.deepToString(graph));
        while (day++ < FINAL_DAY) {
            fishReadyToSpawn = graph[0][0];
            for (int i = 1; i <= NEW_FISH; i++) {
                graph[i - 1][0] = graph[i][0];
            }
            graph[MAX_DAY][0] += fishReadyToSpawn;
            graph[NEW_FISH][0] = fishReadyToSpawn;
            System.out.println(Arrays.deepToString(graph));
        }
    }

    private long getSolution() {
        long sum = 0;
        for (int i = 0; i < graph.length; i++) {
            sum += graph[i][0];
        }
        return sum;
    }

    public static void main(String[] args)  {
        Day6 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day6.txt"));
            challenge = new Day6(input.readLine());
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}
