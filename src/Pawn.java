import java.awt.Color;

public class Pawn {
    private boolean isCrowned;
    private Coordinates position;
    private final Color color;

    public Pawn(Color color){
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    private class Coordinates {
        int x;
        int y;
    }

    public int getCoordinates() {
        return 1;
    }

    public void setCoordinates() {

    }

    private boolean isValidMove(int[] coordinates) {
        return false;
    }


}


