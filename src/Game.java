public class Game {
    int boardSize;


    public Game (int boardSize) {
        this.boardSize = boardSize;
    }

    public Pawn[][] initBoard() {
        Board board = new Board(boardSize);
        return board.fields;
    }

    public void play() {
        //initBoard();
        //start();
        //playRound();
        //checkForWinner();
        //tryToMakeMove();
        //isValidInput();
    }

    public void start() {

    }

    public int playRound() {
        return 1;
    }

    public boolean checkForWinner() {
        return false;
    }

    public void tryToMakeMove() {

    }

    public boolean isValidInput() {
        return false;
    }
}
