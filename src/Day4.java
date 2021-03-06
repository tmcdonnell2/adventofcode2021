import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day4 {
    List<Integer> gameData;
    List<BingoBoard> bingoBoards;

    Day4(List<String> data) {
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
        boolean won = false;
        BingoBoard currentBoard = null;
        int call;
        do {
            call = gameData.remove(0);
            for (BingoBoard bingoBoard : bingoBoards) {
                currentBoard = bingoBoard;
                currentBoard.mark(call);
                if ((won = currentBoard.checkIfWon())) {
                    break;
                }
            }
        } while (!won);
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
            Day4 challenge = new Day4(input.lines().collect(Collectors.toList()));
            System.out.println(challenge.solve());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class BingoBoard {
    final private List<List<Integer>> board;
    final int MARK = -1;
    final int WIN_CONDITION = -5;
    public boolean won;

    BingoBoard(List<List<Integer>> numbers) {
        this.board = numbers;
    }

    public void mark(int calledNumber) {
        for (List<Integer> line: board) {
            if (line.contains(calledNumber)) {
                line.set(line.indexOf(calledNumber), MARK);
            }
        }
    }

    public boolean checkIfWon() {
        won = checkColumns() || checkRows();
        return won;
    }

    public boolean checkRows() {
        for (List<Integer> line : board) {
            if (line.stream().reduce(0, Integer::sum) == WIN_CONDITION) {
                return true;
            }
        }
        return false;
    }

    public boolean checkColumns() {
        int sum = 0;
        for (int col = 0; col < 5; col++) {
            for (List<Integer> line : board) {
                sum += line.get(col);
            }

            if (sum == WIN_CONDITION) return true;
            sum = 0;
        }
        return false;
    }

    public void normalizeBoard() {
        for (List<Integer> line : board) {
            for (int i = 0; i < line.size(); i++) {
                if (line.get(i) == MARK) {
                    line.set(i, 0);
                }
            }
        }
    }

    public List<List<Integer>> getBoard() {
        return board;
    }
}