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

    public Color getColor() {
        return this.color;
    }

    public static class Coordinates {
        int x;
        int y;

        Coordinates(int row, int col) {
            this.x = row;
            this.y = col;
        }
    }
//
//    public Coordinates getCoordinates() {
//        return position;
//    }

    public int getRow() {
        return position.x;
    }

    public int[] getCoordinates(Pawn pawn) {
        return new int[]{pawn.position.x, pawn.position.y};
    }


    public boolean getIsCrowned() {
        return this.isCrowned;
    }

    public void setCoordinates() {
        //Móni
    }

    public boolean isValidMove(int[] endPosition, Pawn[][] board) {
        if (!this.getIsCrowned()) {
            int startRow = this.position.x;
            int startCol = this.position.y;
            int endRow = endPosition[0];
            int endCol = endPosition[1];

            // check if pawn wants to move on a field next to it
            boolean isNextField = (this.getColor() == Color.white && startRow - endRow == 1) || (this.getColor() == Color.black && startRow - endRow == -1);
            // check if pawn wants to move by 2 fields
            boolean isFurtherField = (Math.abs(startRow - endRow) == 2);
            if (isNextField) {
                if (Math.abs(startCol - endCol) == 1) {
                    return true;
                }
            } else if(isFurtherField) {
                if(endCol < startCol){
                    Pawn middleField = board[startRow+1][startCol-1];
                    return middleField != null && middleField.getColor() != this.getColor();
                } else if(endCol > startCol){
                    Pawn middleField = board[startRow+1][startCol+1];
                    return middleField != null && middleField.getColor() != this.getColor();
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    return false;}
}



