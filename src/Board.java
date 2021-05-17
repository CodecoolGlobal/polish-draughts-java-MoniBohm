import java.awt.*;

public class Board {

    private Pawn[][] fields;

    public Board(int n) {
        //between 10 and 20, place pawn
        fields = new Pawn[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                addPawn(row, col, n);
            }
        }
    }

    private void addPawn(int row, int col, int n) {
        Color color = determinePawnColor(row, n);

        if ((col + row) % 2 == 0 && (row > n / 2 || row < n / 2 - 1)) {
            fields[row][col] = new Pawn(color);
        } else {
            fields[row][col] = null;
        }
    }

    private Color determinePawnColor(int row, int n) {
        Color color = null;

        if (n / 2 < row) {
            color = Color.white;
        } else if (n / 2 > row) {
            color = Color.black;
        }
        return color;
    }

    public void removePawn(int[] indexes) {
        //removes pawn at the given index
    }

    public void movePawn(int[] indexes) {
        //moves pawn at the given index
    }

    public String toString() {
        StringBuilder board = new StringBuilder();

        for (Pawn[] row : fields) {
            rowToString(board, row);
        }
        return board.toString();
    }

    private void rowToString(StringBuilder board, Pawn[] row) {
        final String newLine = "\n";

        for (Pawn col : row) {
            colToString(board, col);
        }
        board.append(newLine);
    }

    private void colToString(StringBuilder board, Pawn col) {
        final String emptyField = "_ ";
        final String blackField = "B ";
        final String whiteField = "W ";

        if (col == null) {
            board.append(emptyField);
        } else {
            String color = (col.getColor() == Color.black ? blackField : whiteField);
            board.append(color);
        }
    }
}
