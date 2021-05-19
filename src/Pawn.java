import java.awt.Color;

public class Pawn {
    private boolean isCrowned;
    private Coordinates position;
    private final Color color;

    public Pawn(Color color, Coordinates position, boolean isCrowned) {
        this.color = color;
        this.position = position;
        this.isCrowned = isCrowned;
    }

    public static class Coordinates {
        int x;
        int y;

        Coordinates(int row, int col) {
            this.x = row;
            this.y = col;
        }
    }
    //getInformation about Pawn
    public Color getColor() {
        return this.color;
    }

    public int[] getCoordinates() {
        return new int[]{this.position.x, this.position.y};
    }

    public boolean getIsCrowned() {
        return this.isCrowned;
    }
    //action
    public int[][] isCouldmultipleJumps(Pawn[][] board, int[] endPosition, int boardSize) {
        int row = endPosition[0];
        int col = endPosition[1];
        boolean isCrowned = getIsCrowned();
        CoordinateGenerator doNewCoord = new CoordinateGenerator(row, col, isCrowned, board);
        int[][] enemyNextCoordinate = doNewCoord.getPossibleMoves();
        return enemyNextCoordinate;
    }
    // validation

    public boolean isValidMove(int[] endPosition, Pawn[][] board) {
        if (!this.getIsCrowned()) {
            return notCrownedPawnValidation(endPosition, board);
        }
        return false;
    }

    private boolean notCrownedPawnValidation(int[] endPosition, Pawn[][] board) {
        int startRow = this.position.x;
        int startCol = this.position.y;
        int endRow = endPosition[0];
        int endCol = endPosition[1];
        boolean isNextField = (this.getColor() == Color.white && startRow - endRow == 1) || (this.getColor() == Color.black && startRow - endRow == -1);
        boolean isFurtherField = (Math.abs(startRow - endRow) == 2);
        if (isNextField) {
            if (Math.abs(startCol - endCol) == 1) {
                return true;
            }
        }
        if(isFurtherField){
            return isEnemyInTheMiddle(board, startRow, endRow, startCol, endCol);
        }
        return  false;
    }

    private boolean isEnemyInTheMiddle(Pawn[][] board, int startRow, int endRow, int startCol, int endCol) {
        Pawn middleField = null;
        if (endCol < startCol) {
            if (endRow > startRow) {
                middleField = board[startRow + 1][startCol - 1];
            } else if (endRow < startRow) {
                middleField = board[startRow - 1][startCol - 1];
            }
        } else if (endCol > startCol) {
            if (endRow > startRow) {
                middleField = board[startRow + 1][startCol + 1];
            } else if (endRow < startRow) {
                middleField = board[startRow - 1][startCol + 1];
            }
        }
    return middleField != null && middleField.getColor() != this.getColor();
    }
}



