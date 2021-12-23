import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 {



    Day12(String[] dayData) {

    }

    private void solve() {

    }

    private long getSolution() {
        return 0;
    }

    public static void main(String[] args)  {
        Day12 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day12.txt"));
            challenge = new Day12(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}

class Graph {
    List<String>[] adjacencyList;

    Graph() {

    }
}

