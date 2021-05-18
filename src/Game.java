import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;

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
        //tryToMakeMove
    }

    private void tryToMakeMove() {
        //ezirányítja a validációt
        //isvalidInput();
    }

    private boolean checkForWinner() {
        //Norbi
        return false;
    }

    private boolean isValidInput(String position) {
        // check if user's input has correct format
        //Márta
        // két input - két switch
        //Switch():
        //case1: isPositionValid();
        //case2: isFieldOnBoard();
        //case3: isCorrectPawn();
        //case4: isCorrectPawn();

        return false;
    }

    private boolean isFieldOnBoard(String position) {
        // check if provided field is within board range
        return false;
    }

    private boolean isCorrectPawn(int player, String position){
        // check if pawn belongs to correct player (1: white, 2: black)
        int[] coordinates = changeInputToIntArr(position);
        Pawn chosenPawn = board.getPawn(coordinates[0], coordinates[1]);
        if(chosenPawn != null){
            Color pawnColor = chosenPawn.getColor();
            return (pawnColor == Color.white && player == 1) || (pawnColor == Color.black && player == 2);
        }
        return false;
    }

    private int[] changeInputToIntArr(String position){
        return null;
    }

    private boolean isStartPositionValid(int player, String position) {
        return isValidInput(position) && isFieldOnBoard(position) && isCorrectPawn(player, position);
    }

    private boolean isEndPositionValid(int player, String position) {
        if(isValidInput(position) && isFieldOnBoard(position)) {
            //check if player can make requested move
        }
        return false;
    }
}
