import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day4Part2 {
    List<Integer> gameData;
    List<BingoBoard> bingoBoards;

    Day4Part2(List<String> data) {
        bingoBoards = new ArrayList<>();
        this.gameData = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        data.remove(0);

        int currentBingoBoardRow = 0;
        List<List<Integer>> currentBoardNumbers = new ArrayList<>();
        BingoBoard currentBoard;

        for (String s : data) {
            if (s.matches("")) continue;
            List<Integer> line = Arrays.stream(s.strip().split(" +"))
                    .map(Integer::parseInt).collect(Collectors.toList());
            currentBoardNumbers.add(line);
            currentBingoBoardRow++;
            if (currentBingoBoardRow % 5 == 0) {
                currentBoard = new BingoBoard(currentBoardNumbers);
                bingoBoards.add(currentBoard);

                currentBoardNumbers = new ArrayList<>();
                currentBingoBoardRow = 0;
            }
        }
    }

    public int solve() {
        BingoBoard currentBoard = null;
        int call;
        do {
            call = gameData.remove(0);
            Iterator<BingoBoard> it = bingoBoards.iterator();
            while (it.hasNext()) {
                currentBoard = it.next();
                currentBoard.mark(call);
                if (currentBoard.checkIfWon()) {
                    it.remove();
                }
            }
        } while (!bingoBoards.isEmpty());
        try {
            return getSolution(currentBoard, call);
        } catch(NullPointerException e) {
            e.printStackTrace();
            System.out.println("Got empty board");
        }
        return 0;
    }

    public int getSolution(BingoBoard winner, int call) {
        if (winner == null) throw new NullPointerException();
        winner.normalizeBoard();

        List<List<Integer>> board = winner.getBoard();
        int sum = 0;

        for (List<Integer> line : board) {
            sum += line.stream().reduce(0, Integer::sum);
        }

        return call * sum;
    }

    public static void main(String[] args) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("./data/Day4.txt"));
            Day4Part2 challenge = new Day4Part2(input.lines().collect(Collectors.toList()));
            System.out.println(challenge.solve());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}