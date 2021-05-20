import java.awt.Color;
import java.util.concurrent.TimeUnit;


public class Board {

    private Pawn[][] fields;
    private int boardsize;

    //board creation
    public Board(int boardsize) {
        this.boardsize = boardsize;
        //between 10 and 20, place pawn
        fields = new Pawn[boardsize][boardsize];
        for (int row = 0; row < boardsize; row++) {
            for (int col = 0; col < boardsize; col++) {
                addPawn(row, col, boardsize);
            }
        }
    }

    private void addPawn(int row, int col, int boardsize) {
        Color color = determinePawnColor(row, boardsize);
        if ((col + row) % 2 == 0 && (row > boardsize / 2 || row < boardsize / 2 - 1)) {
                //&& (row > boardsize / 2 || row < boardsize / 2 - 1)){
            Pawn.Coordinates position = new Pawn.Coordinates(row, col);
            fields[row][col] = new Pawn(color, position, false);
        } else {
            fields[row][col] = null;
        }
    }

    private Color determinePawnColor(int row, int boardsize) {
        Color color = null;

        if (boardsize / 2 < row) {
            color = Color.white;
        } else if (boardsize/ 2 > row) {
            color = Color.black;
        }
        return color;
    }

    public String toString(){
        StringGenerator boardString = new StringGenerator(fields);
        return boardString.toString();
    }
    // move on board
    private void removePawn(Pawn pawn) {
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
            collectEnemyAround(pawn, coordinates);
        }
        //királynő elágazása -Móni
    }

    private void collectEnemyAround(Pawn pawn, int[] endPosition) throws InterruptedException {
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
        int[][] nextCoordinate = pawn.isCouldmultipleJumps(fields, endPosition, boardsize);
        Pawn pawnOnEndPosition = fields[endPosition[0]][endPosition[1]];
        int optinalMove = nextCoordinate[0].length;
        switch (optinalMove){
            case 0: // no enemy around pawn
                break;
            case 2: // 1 enemy around pawn
                doAutomaticJump(pawnOnEndPosition, nextCoordinate[0]);
                break;
            default: // more than 1 enemy around pawn
                chooseFromTheseCoordinates(nextCoordinate);
                break;
        }
    }

    private void doAutomaticJump(Pawn pawn,int[] coordinates) throws InterruptedException {
        movePawn(pawn, coordinates);
        System.out.println(toString());
        System.out.println("Automatic jump!");
        TimeUnit.SECONDS.sleep(1);
    }

    private void chooseFromTheseCoordinates(int[][] nextCoordinate) {
        Game game = new Game();
        game.automaticMoveManage(nextCoordinate);
    }

    public Pawn getPawn(int row, int col){
        return fields[row][col];
    }

//    public boolean isMoveAccordingToRules(Pawn pawn, int[] endposition){
//        pawn.isValidMove(endposition, pawn);
//        return false;
//    }
}
