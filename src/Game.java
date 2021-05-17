import java.awt.*;

public class Game {
    private int boardSize;
    private Board board;

    public Game (int boardSize) {
        this.boardSize = boardSize;
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

    private boolean isValidInput(String position) {
        // check if user's input has correct format
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
