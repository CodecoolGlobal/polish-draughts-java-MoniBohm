import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateValidation {

    String endPositon;
    Board board;
    int boardSize;

    public CreateValidation(String endPositon, Board board, int boardSize) {
        this.board = board;
        this.endPositon = endPositon;
        this.boardSize = boardSize;
    }

    public boolean isValidStartPosition(int player, String position) {
        return isValidInput(position) && isCorrectPawn(player, position);
    }

    public boolean isValidEndPosition(String position, Pawn choosenField) {
        return isValidInput(position) && isChosenFieldEmpty(choosenField);
    }

    private boolean isValidInput(String position) {
        if (!isValidCoordinateFormat(position)) {
            return invalidInput();
        } else if (!isFieldOnBoard(position)) {
            return outOfBoardPosition();
        }
        return true;
    }

    private boolean isChosenFieldEmpty(Pawn chosenField) {
        if (chosenField == null) {
            return true;
        } else {
            return fieldIsEmpty();
        }
    }

    private boolean isCorrectPawn(int player, String position) {
        // check if pawn belongs to correct player (1: white, 2: black)
        int[] coordinates = convertInputToIntArr(position);
        Pawn chosenPawn = board.getPawn(coordinates[0], coordinates[1]);
        if (chosenPawn == null) {
            return fieldIsEmpty();
        } else {
            return enemyOrIncorrectPawn(player, chosenPawn);
        }
    }

    private boolean isValidCoordinateFormat(String position) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\d+");
        Matcher matcher = pattern.matcher(position);
        return matcher.matches();
    }

    private boolean invalidInput() {
        System.out.println("Please make sure format is like A1");
        return false;
    }

    public boolean isFieldOnBoard(String position) {
        int[] coordinates = convertInputToIntArr(position);
        return ((coordinates[0] < boardSize && coordinates[0] >= 0) && (coordinates[1] < boardSize && coordinates[1] >= 0));
    }

    private boolean outOfBoardPosition() {
        System.out.println("Chosen field is out of board");
        return false;
    }

    private int[] convertInputToIntArr(String position) {
        int[] coordinatesArr = new int[2];
        int firstCoordinate = position.charAt(0) - 'A';
        int secondCoordinate = Integer.parseInt(position.substring(1)) - 1;
        coordinatesArr[0] = firstCoordinate;
        coordinatesArr[1] = secondCoordinate;
        return coordinatesArr;
    }

    private boolean fieldIsEmpty() {
        System.out.println("Chosen field is empty");
        return false;
    }

    private boolean enemyOrIncorrectPawn(int player, Pawn chosenPawn) {
        Color pawnColor = chosenPawn.getColor();
        if ((pawnColor == Color.white && player == 1) || (pawnColor == Color.black && player == 2)) {
            return true;
        } else {
            System.out.println("Chosen pawn is incorrect");
            return false;
        }
    }
}
