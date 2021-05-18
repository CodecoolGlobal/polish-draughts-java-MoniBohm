import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Game {
    private int boardSize;
    private Board board;


    public String getBoardSizeInput() {
        Scanner keyboardInput = new Scanner(System.in);
        System.out.print("Enter board size: ");
        String input = keyboardInput.nextLine();
        return input;
    }

    public boolean isBoardSizeInputNumber(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        boolean result = matcher.matches();
        return result;
    }

    public boolean isBoardSizeInputValid(int input) {
        return input >= 10 && input <= 20;
    }

    public int getBoardSize() {
        int boardSizeInput = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = getBoardSizeInput();
            while (!isBoardSizeInputNumber(input)) {
                input = getBoardSizeInput();
            }
            boardSizeInput = Integer.parseInt(input);
            validInput = isBoardSizeInputValid(boardSizeInput);
        }
        return boardSizeInput;
    }

    public Game () {
        this.boardSize = getBoardSize();
        this.board = new Board(boardSize);
    }

    public void start() {
        System.out.println(board.toString());
    }

    private void playRound() {
    }

    private void tryToMakeMove() {

    }

    private boolean checkForWinner() {
        return false;
    }
}
