import java.awt.*;

public class Board {

    private Pawn[][] fields;
    private int n;

    public Board(int n) {
        //between 10 and 20, place pawn
        this.n = n;
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
        StringBuilder header = createHeader();
        board.append(header);
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < fields.length; i++) {
            String letter = "\u001B[35m" + alphabet[i] + " ";
            board.append(letter);
            rowToString(board, fields[i]);
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
        String CYAN_BOLD = "\033[1;36m";   // CYAN
        String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        String WHITE = "\033[0;107m";   // WHITE
        String WHITE_BACKGROUND_BRIGHT = "\\e[1;30m";   // GRAY


        final String emptyField = "\u001b[0m" + "⬛";
        final String blackField = CYAN_BOLD + "⚫";
        final String whiteField = YELLOW_BOLD + "⚫";

        if (col == null) {
            board.append(emptyField);
        } else {
            String color = (col.getColor() == Color.black ? blackField : whiteField);
            board.append(color);
        }
    }

    private void createEmptyRow(StringBuilder board, Pawn[] row) {
        final String emptyField =  "⬛";
        for(int i=0; i< row.length; i++){
            board.append(emptyField);
        }
    }

    private StringBuilder createHeader() {
        int width = fields[0].length;
        StringBuilder header = new StringBuilder("  ");
        for (int i = 1; i <= width; i++) {
            String element = "\u001B[35m" + i + "";
            header.append(element);
        }
        return header.append("\n");
    }
}
