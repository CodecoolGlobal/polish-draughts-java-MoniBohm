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
        /*
        * inputFromUser
        * tryToMakeMove()
        * checkForWinner
        *clearBoard
        * PrintBoard
        * */
    }

    private boolean tryToMakeMove() {
//         * validateInput -- board.movePawn() --> isValidInput()
        return false;

    }

    private boolean checkForWinner() {
        return false;
    }

    public boolean isValidInput() {
        //visszatettem, mert megint végig mentem
        // a lelkifolyamaton h megértsem a feladatot és rájöttem hogy igazatok van :D
        return false;
    }
}
