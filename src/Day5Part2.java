import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5Part2 {

    private final int GRID_LENGTH = 1000;

    int[][] grid = new int[GRID_LENGTH][GRID_LENGTH];
    List<LineP2> lines = new ArrayList<>();

    Day5Part2(String[] data) {
        List<String> splitCoordinateData = new ArrayList<>();
        for (String d : data) {
            splitCoordinateData.addAll(Arrays.stream(d.split(" "))
                    .filter(s -> s.matches("[0-9]*,[0-9]*"))
                    .collect(Collectors.toList()));
        }

        Coordinate[] lineCoords = new Coordinate[2];
        int i = 0;
        for (String coord : splitCoordinateData) {
            Integer[] parsedCoords = Arrays.stream(coord.split(","))
                    .mapToInt(Integer::parseInt)
                    .boxed().toArray(Integer[]::new);
            lineCoords[i++] = new Coordinate(parsedCoords[0],parsedCoords[1]);
            if (lineCoords[1] != null) {
                lines.add(new LineP2(lineCoords[0], lineCoords[1]));
                lineCoords[1] = null;
                i = 0;
            }
        }
    }

    private void solve() {
        int startPointX;
        int endPointX;

        int startPointY;
        int endPointY;
        for (LineP2 line : lines) {
            startPointX = line.start.getX();
            endPointX = line.end.getX();

            startPointY = line.start.getY();
            endPointY = line.end.getY();
            mark(startPointX, startPointY);
            while (startPointX != endPointX ||
                    startPointY != endPointY) {
                if (startPointX != endPointX + line.xDirection) {
                    startPointX += line.xDirection;
                }
                if (startPointY != endPointY + line.yDirection) {
                    startPointY += line.yDirection;
                }
                mark(startPointX, startPointY);
            }
        }
    }

    public void mark(int x, int y) {
        grid[y][x]++;
    }

    private int getSolution() {
        int sum = 0;
        for (int[] row : grid) {
            for (int col = 0; col < GRID_LENGTH; col++) {
                if (row[col] >= 2) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Day5Part2 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day5.txt"));
            challenge = new Day5Part2(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}

class LineP2 {
    Coordinate start;
    Coordinate end;
    int xDirection;
    int yDirection;

    LineP2(Coordinate... coords) {
        if (coords.length > 2) {
            return;
        }
        this.start = coords[0];
        this.end = coords[1];

        yDirection = Integer.compare(end.getY(), start.getY());
        xDirection = Integer.compare(end.getX(), start.getX());
    }
}
