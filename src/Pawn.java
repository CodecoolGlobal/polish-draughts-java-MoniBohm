import java.awt.Color;

public class Pawn {
    private boolean isCrowned;
    private Coordinates position;
    private final Color color;

    public Pawn(Color color, Coordinates position, boolean isCrowned){
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

        Coordinates(int row, int col){
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
        return new int[] {pawn.position.x, pawn.position.y};
    }


    public boolean getIsCrowned(Pawn pawn) {
        return this.isCrowned;
    }

    public int[] isCouldmultipleJumps(int[] coordinates, int n){
        return new int[]{0,0};
    }


    private int[] checkCoordinates(int[] coordinates, int n){
        int row = coordinates[0];
        int col = coordinates[1];
        int leftNext;
        if(row ==0 && col == n -1)
        {return topRightPosition(row, col)};
        if(row ==n-1 && col == n-1){bottomRightPosition};
        if(row ==0 && col == 0){bottomLeftPosition};
        if(row ==n-1 && col == 0){bottomLeftPosition};
        if(col == 0){leftColPosition};
        if(col == n-1){rightColPosition};
        if(row == 0){bottomRowPoisiton};
        if(row == n-1){topRowPoisiton};
//        notSpecialPosition();
        return new int[]{0,0};
    }

    private boolean topRightPosition(int row, int col) {
        return false;
    }


    public boolean isValidMove(Pawn pawn, int[] coordinates) {
        return false;

//        Pawn contains a method that validates given move
//        (checks whether it's according to the game's rules)
//        before making it.
    }



}


