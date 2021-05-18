import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;

public class Game {
    private int boardSize;
    private Board board;


    public String getInput(String inputType) {
        Scanner keyboardInput = new Scanner(System.in);
        switch(inputType) {
            case "boardSizeInput":
                System.out.print("Enter board size: ");
                break;
            case "startPositionInput":
                System.out.print("Enter start position coordinate: ");
                break;
        }
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
            String input = getInput("boardSizeInput");
            while (!isBoardSizeInputNumber(input)) {
                input = getInput("boardSizeInput");
            }
            boardSizeInput = Integer.parseInt(input);
            validInput = isBoardSizeInputValid(boardSizeInput);
        }
        return boardSizeInput;
    }

    public Game() {
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

    private boolean isValidCoordinateFormat(String position) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\d+");
        Matcher matcher = pattern.matcher(position);
        boolean result = matcher.matches();
        return result;
    }

//    play Round:
//        int player = 1;
//        String startPosition = userInput();
//        while(!isValidStartPosition(player, startPosition)) {
//            String startPosition = userInput();
//        }
//        String endPosition = userInput();
//        while(!isValidEndPosition(endPosition)) {
//            String endPosition = userInput();
//            //check for rules
//        }
//        make move(startPosition, endPosition)


    private boolean isValidInput(String position) {
        if (!isValidFormat(position)) {
            System.out.println("Please make sure format is like A1");
            return false;
        } else if (!isFieldOnBoard(position)) {
            System.out.println("Chosen field is out of board");
            return false;
        }
        return true;
    }


    private boolean isValidStartPosition(int player, String position) {
        return isValidFormat(position) && isCorrectPawn(player, position);
    }

    private boolean isValidEndPosition(String position) {
        return isValidInput(position) && isChosenFieldEmpty(position);
    }

    private boolean isChosenFieldEmpty(String position) {
        int[] coordinates = changeInputToIntArr(position);
        Pawn chosenField = board.getPawn(coordinates[0], coordinates[1]);
        if(chosenField == null) {
            return true;
        } else {
            System.out.println("You must choose an empty field");
            return false;
        }
    }

    private boolean isValidFormat(String position) {
        // check if provided field is within board range
        return false;
    }

    private int[] convertInputToIntArr(String position) {
        int coordinatesArr[] = new int[2];
        int firstCoordinate = position.charAt(0) - 'A';
        int secondCoordinate = Integer.parseInt(position.substring(1)) - 1;
        coordinatesArr[0] = firstCoordinate;
        coordinatesArr[1] = secondCoordinate;
        return coordinatesArr;
    }

    public boolean isFieldOnBoard(String position) {
        int coordinates[] = convertInputToIntArr(position);
        return (coordinates[0] < boardSize && coordinates[1] < boardSize);
    }

    private boolean isCorrectPawn(int player, String position) {
        // check if pawn belongs to correct player (1: white, 2: black)
        int[] coordinates = changeInputToIntArr(position);
        Pawn chosenPawn = board.getPawn(coordinates[0], coordinates[1]);
        if (chosenPawn == null) {
            System.out.println("Chosen field is empty");
            return false;
        } else {
            Color pawnColor = chosenPawn.getColor();
            if ((pawnColor == Color.white && player == 1) || (pawnColor == Color.black && player == 2)) {
                return true;
            } else {
                System.out.println("Chosen pawn is incorrect");
                return false;
            }
        }
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
