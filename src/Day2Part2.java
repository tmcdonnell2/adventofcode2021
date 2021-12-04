import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day2Part2 {
    List<String> data;
    int depth = 0;
    int position = 0;
    int aim = 0;

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
                    if (aim != 0) {
                        depth += aim * delta;
                    }
                    break;
                case "up":
                    aim -= delta;
                    break;
                case "down":
                    aim += delta;
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

            Day2Part2 challenge = new Day2Part2();
            challenge.setData(lines);

            challenge.processSubMovements();

            System.out.println("Pos: " + challenge.position);
            System.out.println("Aim: " + challenge.aim);
            System.out.println("Depth: " + challenge.depth);
            System.out.println("Final: " + challenge.finalCalculation());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
