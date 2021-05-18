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

    public int getCoordinates() {
        return 1;
    }

    public void setCoordinates() {
        //Móni
    }

    public boolean isValidMove(Pawn pawn, int[] endPosition) {
        pawn.position
        return false;

//        Pawn contains a method that validates given move
//        (checks whether it's according to the game's rules)
//        before making it.
    }


}


