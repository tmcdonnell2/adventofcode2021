import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    private final int GRID_LENGTH = 1024;

    int[] grid = new int[GRID_LENGTH];
    List<Line> lines = new ArrayList<>();

    Day5(String[] data) {
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
            lineCoords[i++ % 2] = new Coordinate(parsedCoords[0],parsedCoords[1]);
            if (lineCoords[1] != null) {
                lines.add(new Line(lineCoords[0], lineCoords[1]));
                lineCoords[1] = null;
            }
        }

        // get horz or vert lines only
        lines = lines.stream().filter(l -> l.start.getX() == l.end.getX() || l.start.getY() == l.end.getY())
                .collect(Collectors.toList());
    }

    private void solve() {
        int startPoint;
        int endPoint;
        for (Line line : lines) {
            if (line.isVertical) {
                startPoint = line.start.getY();
                endPoint = line.end.getY();
                while (startPoint != endPoint) {
                    mark(line.start.getX(), startPoint);
                    startPoint += line.direction * 32;
                }
            } else { // horz
                startPoint = line.start.getX();
                endPoint = line.end.getX();
                while (startPoint != endPoint) {
                    mark(startPoint, line.start.getY());
                    startPoint += line.direction;
                }
            }
        }
    }

    public void mark(int x, int y) {
        int col = x % 32, row = y / 32;
        grid[(col + (row * 32))]++;
    }

    private int getSolution() {
        return Arrays.stream(grid).filter(i -> i >= 2).toArray().length;
    }

    public static void main(String[] args) {
        Day5 challenge = null;
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader("./data/Day5.txt"));
            challenge = new Day5(input.lines().toArray(String[]::new));
        } catch (IOException e) {
            System.out.println("?_? where filegon");
        }
        challenge.solve();
        System.out.println(challenge.getSolution());
    }
}

class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Line {
    Coordinate start;
    Coordinate end;
    boolean isVertical;
    int direction;

    Line(Coordinate... coords) {
        if (coords.length > 2) return;
        this.start = coords[0];
        this.end = coords[1];

        // True being vertical (same x at points, y varies)
        if ((isVertical = start.getX() == end.getX())) {
            direction = (start.getY() < end.getY() ? 1 : -1);
        } else {
            direction = (start.getX() < end.getX() ? 1 : -1);
        }
    }
}
