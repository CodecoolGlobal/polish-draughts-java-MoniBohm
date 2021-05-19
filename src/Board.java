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
        if ((col + row) % 2 == 0 && (row > n / 2 || row < n / 2 - 1)){
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
        int[] removeIndex = pawn.getCoordinates();
        fields[removeIndex[0]][removeIndex[1]] = null;
    }

    public void movePawn(Pawn pawn, int[] coordinates) throws InterruptedException {
        //collect new data
        if (pawn.isValidMove(coordinates, fields)) {
            Color color = pawn.getColor();
            boolean isCrowned = pawn.getIsCrowned();
            Pawn.Coordinates position = new Pawn.Coordinates(coordinates[0], coordinates[1]);
            //remove pawn oldPosition
            removePawn(pawn);
            //put new Pawn
            fields[coordinates[0]][coordinates[1]] = new Pawn(color, position, isCrowned);
            enemyCaptured(pawn, coordinates);
        }
    }

    private void enemyCaptured(Pawn pawn, int[] endPosition) throws InterruptedException {
        int [] startPosition = pawn.getCoordinates();
        if(Math.abs(startPosition[0] - endPosition[0]) == 2){
            if(startPosition[1] - endPosition[1] == -2){
                moveToLeft(endPosition, startPosition);
            }
            if(startPosition[1] - endPosition[1] == 2){
                movetToRight(endPosition, startPosition);
            }
            multipleJumps(pawn, endPosition);
        }
    }

    private void movetToRight(int[] endPosition, int[] startPosition) {
        if(startPosition[0] - endPosition[0] == -2){
            removePawn(fields[startPosition[0]+1][startPosition[1]-1]);
        }
        if(startPosition[0] - endPosition[0] == 2){
            removePawn(fields[startPosition[0]-1][startPosition[1]-1]);
        }
    }

    private void moveToLeft(int[] endPosition, int[] startPosition) {
        if(startPosition[0] - endPosition[0] == -2){
            removePawn(fields[startPosition[0]+1][startPosition[1]+1]);
        }
        if(startPosition[0] - endPosition[0] == 2){
            removePawn(fields[startPosition[0]-1][startPosition[1]+1]);
        }
    }

    private void multipleJumps(Pawn pawn, int[] endPosition) throws InterruptedException {
        int[][] nextCoordinate = pawn.isCouldmultipleJumps(fields, endPosition, n);
        Pawn pawnOnEndPosition = fields[endPosition[0]][endPosition[1]];
        int optinalMove = nextCoordinate[0].length;
        switch (optinalMove){
            case 0:
                break;
            case 2:
                doAutomaticJump(pawnOnEndPosition, nextCoordinate[0]);
                break;
            default:
//                chooseFromTheseCoordinates(nextCoordinate, pawn);
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
    }

    public String toString() {
        final String newLine = "\n";
        StringBuilder board = new StringBuilder();
        StringBuilder header = createHeader();
        board.append(header);
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < fields.length; i++) {
            String letter = "\u001B[35m" + alphabet[i] + " ";
            board.append(letter);
            rowToString(board, fields[i], i);
            board.append(newLine);
        }
        return board.toString();
    }

    private void rowToString(StringBuilder board, Pawn[] row, int rowNumber) {

        final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        final String ANSI_GREEN_BACKGROUND = "\033[42m";
        final String YELLOW_BACKGROUND = "\033[43m";

        final String emptyBlackField = ANSI_BLACK_BACKGROUND+"    "+ANSI_RESET;
        final String emptyWhiteField = ANSI_WHITE_BACKGROUND+"    "+ANSI_RESET;
        final String blackPawnBlackField = ANSI_BLACK_BACKGROUND+" \uD83E\uDD81 "+ANSI_RESET;
        final String whitePawnBlackField = ANSI_BLACK_BACKGROUND+" \uD83E\uDD92 "+ANSI_RESET;
        // elephant: final String blackPawnBlackField = ANSI_BLACK_BACKGROUND+" \uD83D\uDC18 "+ANSI_RESET;

        if(rowNumber%2==0){ // even rows
            for(int i=0; i<row.length; i++){
                if(i%2==0){ // black fields
                    if(row[i] == null ){
                        board.append(emptyBlackField);
                    } else {
                        String pawnAndField = (row[i].getColor() == Color.black ? blackPawnBlackField : whitePawnBlackField);
                        board.append(pawnAndField);
                    }
                } else { // white fields
                        board.append(emptyWhiteField);
                }
            }
        } else { // odd rows
            for(int i=0; i<row.length; i++){
                if(i%2!=0){ // black fields
                    if(row[i] == null ){
                        board.append(emptyBlackField);
                    } else {
                        String pawnAndField = (row[i].getColor() == Color.black ? blackPawnBlackField : whitePawnBlackField);
                        board.append(pawnAndField);
                    }
                } else { // white fields
                        board.append(emptyWhiteField);
                    }
                }
            }
        }

    public Pawn getPawn(int row, int col){
        return fields[row][col];
    }

    private StringBuilder createHeader() {
        int width = fields[0].length;
        StringBuilder header = new StringBuilder("  ");
        for (int i = 1; i <= width; i++) {
            String element = (i<10) ? "  "+ "\u001B[35m" + i + " " : " "+ "\u001B[35m" + i + " ";
            header.append(element);
        }
        return header.append("\n");
    }
}
