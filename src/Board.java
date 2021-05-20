import java.awt.Color;
import java.util.concurrent.TimeUnit;


public class Board {

    private final Pawn[][] fields;

    public Board(int n) {
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

    @Override
    public String toString(){
        StringGenerator boardString = new StringGenerator(fields);
        return boardString.toString();
    }

    private void removePawn(Pawn pawn) {
        int[] removeIndex = pawn.getCoordinates();
        fields[removeIndex[0]][removeIndex[1]] = null;
    }

    public void movePawn(Pawn pawn, int[] endPosition) throws InterruptedException {
        printBoardForAutomaticJump();
        Color color = pawn.getColor();
        boolean isCrowned = pawn.getIsCrowned();
        Pawn.Coordinates position = new Pawn.Coordinates(endPosition[0], endPosition[1]);
        removePawn(pawn);
        boolean newQueen = (endPosition[0]==fields.length-1 && color == Color.black) || (endPosition[0]==0 && color == Color.white);
        if(newQueen){
            fields[endPosition[0]][endPosition[1]] = new Pawn(color, position, true);
        } else {
            fields[endPosition[0]][endPosition[1]] = new Pawn(color, position, isCrowned);
        }
        if(!pawn.getIsCrowned()){
        getEnemyPosition(pawn, endPosition);}
        else {

        }
    }

    private void getEnemyPosition(Pawn pawn, int[] endPosition) throws InterruptedException {
        int [] startPosition = pawn.getCoordinates();
        if(Math.abs(startPosition[0] - endPosition[0]) == 2){
            if(startPosition[1] - endPosition[1] == -2){
                moveLeft(endPosition, startPosition);
            }
            if(startPosition[1] - endPosition[1] == 2){
                moveRight(endPosition, startPosition);
            }
            multipleJumps(pawn, endPosition);
        }
    }

    private void moveRight(int[] endPosition, int[] startPosition) {
        if(startPosition[0] - endPosition[0] == -2){
            removePawn(fields[startPosition[0]+1][startPosition[1]-1]);
        }
        if(startPosition[0] - endPosition[0] == 2){
            removePawn(fields[startPosition[0]-1][startPosition[1]-1]);
        }
    }

    private void moveLeft(int[] endPosition, int[] startPosition) {
        if(startPosition[0] - endPosition[0] == -2){
            removePawn(fields[startPosition[0]+1][startPosition[1]+1]);
        }
        if(startPosition[0] - endPosition[0] == 2){
            removePawn(fields[startPosition[0]-1][startPosition[1]+1]);
        }
    }

    private void multipleJumps(Pawn pawn, int[] endPosition) throws InterruptedException {
        int[][] nextCoordinate = pawn.isCouldMultipleJumps(fields, endPosition);
        Pawn pawnOnEndPosition = fields[endPosition[0]][endPosition[1]];
        int optionalMove = nextCoordinate[0].length;
        switch (optionalMove) {
            case 0:
                break;
            case 2:
                doAutomaticJump(pawnOnEndPosition, nextCoordinate[0]);
                break;
            default:
                chooseFromTheseCoordinates(nextCoordinate);
                break;

        }
    }

    private void doAutomaticJump(Pawn pawn,int[] coordinates) throws InterruptedException {
        movePawn(pawn, coordinates);
    }

    private void chooseFromTheseCoordinates(int[][] nextCoordinate) {
        Game game = new Game();
        game.automaticMoveManage(nextCoordinate);
    }

    public Pawn getPawn(int row, int col){
        return fields[row][col];
    }

    public boolean isValidMoveOnBoard(int[] startPosition, int[] endPosition){
        Pawn pawn = fields[startPosition[0]][startPosition[1]];
        return pawn.isValidMove(endPosition, fields);
    }

    private void printBoardForAutomaticJump() throws InterruptedException {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if(stackTraceElements[3].getMethodName() == "doAutomaticJump"){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println(toString());
            System.out.println("\033[0;33m"+"Automatic jump!"+"\u001B[0m");
            TimeUnit.SECONDS.sleep(3);
        }
    }

}
