public class Game {
    private int boardSize;
    private Board board;

    public Game (int boardSize) {
        this.boardSize = boardSize;
        this.board = new Board(boardSize);
    }

    public void start() {
        System.out.println(board.toString());
    }

    private void playRound() {
        //moving
    }

    private void tryToMakeMove() {

    }

    private boolean checkForWinner() {
        return false;
    }
}
