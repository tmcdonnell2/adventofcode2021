import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Day4 {
    List<String> gameData;
    List<BingoBoard> bingoBoards;

    class BingoBoard {
        // FIXME: Make List of Lists
        private List<Integer> board;

        BingoBoard(List<Integer> numbers) {
            this.board = numbers;
        }

        public void mark(int calledNumber) {
            board.set(board.indexOf(calledNumber), 0);
        }

        public boolean checkIfWon() {
            return checkColumns() || checkRows();
        }

        public boolean checkRows() {
            return false;
        }

        public boolean checkColumns() {
            return false;
        }
    }

    Day4(List<String> data) {
        this.gameData = Arrays.stream(data.get(0).split(",")).collect(Collectors.toList());
        data.remove(0);

        int currentBingoBoardRow = 0;
        List<Integer> currentBoardNumbers = new ArrayList<>();
        BingoBoard currentBoard;

        // FIXME: finish to produce list of lists
        for (String s : data) {
            if (s.matches("")) continue;
            currentBoardNumbers.addAll(Arrays.stream(s.split(" +"))
                                                .map(Integer::parseInt)
                                                .collect(Collectors.toList()));
            currentBingoBoardRow++;
            if (currentBingoBoardRow % 5 == 0) {
                currentBoard = new BingoBoard(currentBoardNumbers);
                bingoBoards.add(currentBoard);
                currentBingoBoardRow = 0;
            }
        }
    }

    public void solve() {

    }

    public int getSolution() {
        return 0;
    }

    public static void main(String[] args) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("./data/Day4.txt"));
            Day4 challenge = new Day4(input.lines().collect(Collectors.toList()));
            challenge.solve();

            System.out.println(challenge.getSolution());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
