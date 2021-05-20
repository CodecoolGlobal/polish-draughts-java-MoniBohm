import java.awt.Color;

public class CoordinateGenerator {
    int row;
    int col;
    boolean isCrowned;
    int boardSize;
    int[][] directions = {{-1, 1}, {-1, -1}, {1, 1}, {1, -1}};
    Pawn[][] board;

    public CoordinateGenerator(int row, int col, boolean isCrowned, Pawn[][] board) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.boardSize = board.length;
    }

    public boolean isFieldOnBoard(int newRow, int newCol) {
        return ((newRow < boardSize && newRow >= 0) && (newCol < boardSize && newCol >= 0));
    }

    public int[][] getPossibleMoves() {
        int[][] possibleMoves = {{}};
        int index = 0;
        for (int[] direction : directions) {
            if (isPossibleMove(direction)) {
                int[] move = {row + 2 * (direction[0]), col + 2 * (direction[1])};
                possibleMoves[index] = move;
                index++;
            }
        }

        return possibleMoves;
    }

    private boolean isPossibleMove(int[] direction) {
        int nextRow = row + direction[0];
        int nextCol = col + direction[1];
        if (isFieldOnBoard(nextRow, nextCol)) {
            Pawn field = board[nextRow][nextCol];
            Color playerColor = board[row][col].getColor();
            if (field != null && field.getColor() != playerColor) {
                int secondRow = row + 2 * (direction[0]);
                int secondCol = col + 2 * (direction[1]);
                if (isFieldOnBoard(secondRow, secondCol)) {
                    Pawn secondField = board[secondRow][secondCol];
                    return secondField == null;
                }
            }
        }
        return false;
    }
}
