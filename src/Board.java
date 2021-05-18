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
            Pawn.Coordinates position = new Pawn.Coordinates(row, col);
            fields[row][col] = new Pawn(color, position, false);
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

    public void removePawn(Pawn pawn) {
        int[] removeIndex = pawn.getCoordinates(pawn);
        fields[removeIndex[0]][removeIndex[1]] = null;
    }

    public void movePawn(Pawn pawn, int[] coordinates) {
        //collect new data
        Color color = pawn.getColor();
        boolean isCrowned = pawn.getIsCrowned(pawn);
        Pawn.Coordinates position = new Pawn.Coordinates(coordinates[0], coordinates[1]);
        //remove pawn oldPosition
        removePawn(pawn);
        //put new Pawn
        fields[coordinates[0]][coordinates[1]] = new Pawn(color, position, isCrowned);
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
        //színes tábla
//        String CYAN_BOLD = "\033[1;36m";   // CYAN
//        String YELLOW_BOLD = "\033[1;33m"; // YELLOW
//        String WHITE = "\033[0;107m";   // WHITE
//        String WHITE_BACKGROUND_BRIGHT = "\\e[1;30m";   // GRAY
//
//
//        final String emptyField = "\u001b[0m" + "⬛";
//        final String blackField = CYAN_BOLD + "⚫";
//        final String whiteField = YELLOW_BOLD + "⚫";

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

//    private void createEmptyRow(StringBuilder board, Pawn col) {
//        final String emptyField =  "⬛";
//        board.append(emptyField);
//    }

    public Pawn getPawn(int row, int col){
        return fields[row][col];
    }
}
