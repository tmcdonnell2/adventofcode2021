import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    List<String> data;
    int depth = 0;
    int position = 0;
    int binaryDataLength;

    public int finalCalculation() {
        return depth * position;
    }

    public void setData(List<String> passedInData) {
        data = passedInData;
    }

    public void processSubMovements() {
        for (String s : data) {
            String[] command = s.split(" ");
            int delta = Integer.parseInt(command[1]);
            switch(command[0]) {
                case "forward":
                    position += delta;
                    break;
                case "up":
                    depth -= delta;
                    break;
                case "down":
                    depth += delta;
                    break;
                default:
                    System.out.println("End?");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("./data/Day2.txt"));
            List<String> lines = input.lines().collect(Collectors.toList());

            Day2 challenge = new Day2();
            challenge.setData(lines);

            challenge.processSubMovements();

            System.out.println(challenge.position);
            System.out.println(challenge.depth);
            System.out.println(challenge.finalCalculation());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
