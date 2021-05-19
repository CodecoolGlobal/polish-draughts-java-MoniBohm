import java.awt.Color;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Board {

    private Pawn[][] fields;
    private int n;

    public Board(int n) {
        this.n = n;
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

    public void movePawn(Pawn pawn, int[] coordinates) throws InterruptedException {
        //collect new data
        if (pawn.isValidMove(coordinates, fields)) {
            Color color = pawn.getColor();
            boolean isCrowned = pawn.getIsCrowned(pawn);
            Pawn.Coordinates position = new Pawn.Coordinates(coordinates[0], coordinates[1]);
            //remove pawn oldPosition
            removePawn(pawn);
            enemyCaptured(pawn, coordinates);
            //put new Pawn
            fields[coordinates[0]][coordinates[1]] = new Pawn(color, position, isCrowned);
        }
    }
    private void enemyCaptured(Pawn pawn, int[] coordinates) throws InterruptedException {
        int row = coordinates[0];
        int col = coordinates[1];
        if(fields[row][col] != null){
            Pawn enemyPawn = fields[row][col];
            removePawn(enemyPawn);
        }
        multipleJumps(pawn, coordinates);
    }

    private void multipleJumps(Pawn pawn, int[] coordinates) throws InterruptedException {
        int[] nextCoordinate = pawn.isCouldmultipleJumps(fields, coordinates, n);
        int numberOfOptions = nextCoordinate.length;
        switch (numberOfOptions){
            case 0:
                break;
            case 2:
                doAutomaticJump(pawn, nextCoordinate);
                break;
            default:
                chooseFromTheseCoordinates(nextCoordinate, pawn);
                break;
        }
    }

    private void chooseFromTheseCoordinates(int[] nextCoordinate, Pawn pawn) {
        Game game = new Game();
        game.automaticMoveManage(nextCoordinate);
    }

    private void doAutomaticJump(Pawn pawn,int[] coordinates) throws InterruptedException {
        movePawn(pawn, coordinates);
        //new boardPrint with delay --ebben még nem vagyok biztos hogy így lesz teljesen :D
        System.out.println("Automatic jump!");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(toString());
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


    private StringBuilder createHeader() {
        int width = fields[0].length;
        StringBuilder header = new StringBuilder("  ");
        for (int i = 1; i <= width; i++) {
            if(i>9)
            {
                String element = "\u001B[35m" + i + "|";
                header.append(element);
            }
            else
            {
            String element = "\u001B[35m" + i + "|";
            header.append(element);
            }

        }
        return header.append("\n");
    }

    public Pawn getPawn(int row, int col){
        return fields[row][col];
    }
}
