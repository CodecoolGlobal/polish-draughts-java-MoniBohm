import java.awt.Color;

public class Pawn {
    private final boolean isCrowned;
    private final Coordinates position;
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
        else if(this.getIsCrowned()){
            return moveMentWithCrownedPawn(endPosition, board);
        }
        return false;
    }

    private boolean moveMentWithCrownedPawn(int[] endPosition, Pawn[][] board) {
        int startRow = this.position.x;
        int startCol = this.position.y;
        int endRow = endPosition[0];
        int endCol = endPosition[1];
        int[][] routes = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        int[] route = {endRow - startRow, endCol - startCol};
        int distance = Math.abs(endRow - startRow);
        int [] currentRoute = new int[2];
        if ((Math.abs(endRow - startRow)) == (Math.abs(endCol - startCol))) {
            createNewCurrentRoute(routes, route, currentRoute);
        }
        return !isCanGoQueen(board, startRow, startCol, distance, currentRoute);
    }

    private boolean isCanGoQueen(Pawn[][] board, int startRow, int startCol, int distance, int[] currentRoute) {
        for (int i = 0; i <= distance; i++) {
            int currentRow = startRow + (currentRoute[0] * i);
            int currentCol = startCol + (currentRoute[1] * i);
            if ((board[currentRow][currentCol] != null) && (board[currentRow][currentCol].getColor() != this.color)) {
                if (board[currentRoute[0] * (i+1)][currentRoute[0] * (i+1)] != null) {
                    return true;
                }
            }

        }
        return false;
    }

    private void createNewCurrentRoute(int[][] routes, int[] route, int[] currentRoute) {
        if (route[0] > 0 && route[1] > 0) {
            currentRoute[0] = routes[0][0];
            currentRoute[1] = routes[0][1];
        }
        else if (route[0] > 0 && route[1] < 0) {
            currentRoute[0] = routes[1][0];
            currentRoute[1] = routes[1][1];
        }
        else if (route[0] < 0 && route[1] > 0) {
            currentRoute[0] = routes[2][0];
            currentRoute[1] = routes[2][1];
        }
        else if (route[0] < 0 && route[1] < 0) {
            currentRoute[0] = routes[3][0];
            currentRoute[1] = routes[3][1];

        }
    }

    private boolean notCrownedPawnValidation(int[] endPosition, Pawn[][] board) {
        int startRow = this.position.x;
        int startCol = this.position.y;
        int endRow = endPosition[0];
        int endCol = endPosition[1];
        boolean isNextField = (this.color.equals(Color.white ) && startRow - endRow == 1) || (this.color.equals(Color.black) && startRow - endRow == -1);
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



