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


    public int[] isCouldmultipleJumps(int[] coordinates,boolean isCrowned, int n){
        int row = coordinates[0];
        int col = coordinates[1];
        CoordinateGenerator doNewCoord = new CoordinateGenerator(row, col, isCrowned, n);
        int[] nextCoordinate = doNewCoord.doNewCoord();
        return nextCoordinate;
    }

    public boolean isValidMove(Pawn pawn, int[] coordinates) {
        return false;

//        Pawn contains a method that validates given move
//        (checks whether it's according to the game's rules)
//        before making it.
    }



}


