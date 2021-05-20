import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;

public class Game {
    List<List> positions = new ArrayList<List>();
    final private int boardSize;
    private final Board board;
    private final String textColor = "\033[0;33m";
    private final String[] cheerleaders = {"Go %s!!!%n", "Your turn %s!%n", "You can win this %s!%n", "Let's go %s! %n"};

    public Game() {
        this.boardSize = getBoardSize();
        this.board = new Board(boardSize);
    }

    public void start() throws InterruptedException {
        int player = -1;
        while (checkForDominantWinner() == null) {
            System.out.println(board.toString());
            player = player == 1 ? 2 : 1;
            playRound(player);
            clearScreen();
        }
    }

    private void playRound(int player) throws InterruptedException {
        String startPosition = null;
        String endPosition = null;
        cheerCurrentPlayer(player);
        while((startPosition ==null) || !isMoveAccordingToRules(startPosition, endPosition)){
            startPosition = isValidFirstCoordinate(player);
            endPosition = isValidSecondCoordinate(startPosition);
        }
        tryToMakeMove(startPosition, endPosition);
    }

    private String isValidSecondCoordinate(String startPosition) {
        String endPosition;
        endPosition = getInput("endPositionInput");
        while(!getFullValidationForEndPosition(endPosition)) {
            endPosition = getInput("endPositionInput");
        }
        return endPosition;
    }


    public boolean getFullValidationForEndPosition(String endPosition){
        int[] coordinates = convertInputToIntArr(endPosition);
        Pawn targetPawn = board.getPawn(coordinates[0], coordinates[1]);
        CreateValidation getValidation = new CreateValidation(endPosition, board, boardSize);
        return getValidation.isValidEndPosition(endPosition, targetPawn);
    }


    private String isValidFirstCoordinate(int player) {
        String startPosition;
        startPosition = getInput("startPositionInput");
        while(!getFullValidationForStartPosition(player, startPosition)) {
            startPosition = getInput("startPositionInput");
        }
        return startPosition;
    }

    public boolean getFullValidationForStartPosition(int player, String startPosition){
        CreateValidation getValidation = new CreateValidation(startPosition, board, boardSize);
        return getValidation.isValidStartPosition(player, startPosition);
    }

    private String getInput(String inputType) {
        Scanner keyboardInput = new Scanner(System.in);
        switch (inputType) {
            case "boardSizeInput": System.out.print("Enter board size: "); break;
            case "startPositionInput": System.out.print("Enter start position coordinate: "); break;
            case "endPositionInput": System.out.print("Enter end position coordinate: "); break;
        }
        return keyboardInput.nextLine();
    }

    public boolean isBoardSizeInputNumber(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean isBoardSizeInputValid(int input) {
        return input >= 10 && input <= 20;
    }

    private void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getBoardSize() {
        int boardSizeInput = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = getInput("boardSizeInput");
            while (!isBoardSizeInputNumber(input)) {
                input = getInput("boardSizeInput");
            }
            boardSizeInput = Integer.parseInt(input);
            validInput = isBoardSizeInputValid(boardSizeInput);
        }
        return boardSizeInput;
    }

    private boolean isMoveAccordingToRules(String startPosition, String endPosition){
        int[] startCoor = convertInputToIntArr(startPosition);
        int[] endCoor = convertInputToIntArr(endPosition);
        if(board.isValidMoveOnBoard(startCoor, endCoor)) {
            return true;
        }
        System.out.println("Invalid move, please enter new coordinates!");
        return false;
    }


    private int[] convertInputToIntArr(String position) {
        int coordinatesArr[] = new int[2];
        int firstCoordinate = position.charAt(0) - 'A';
        int secondCoordinate = Integer.parseInt(position.substring(1)) - 1;
        coordinatesArr[0] = firstCoordinate;
        coordinatesArr[1] = secondCoordinate;
        return coordinatesArr;
    }

    private void tryToMakeMove(String startPosition, String endPosition) throws InterruptedException {
        int[] startCoor = convertInputToIntArr(startPosition);
        int[] endCoor = convertInputToIntArr(endPosition);
        Pawn pawn = board.getPawn(startCoor[0], startCoor[1]);
        board.movePawn(pawn, endCoor);
    }

    private void cheerCurrentPlayer(int player) {
        String animal = player==1 ? "giraffes":"lions";
        Random random = new Random();
        int index = random.nextInt(cheerleaders.length);
        System.out.printf(textColor+cheerleaders[index], animal);
    }

    public boolean checkForDrawWithKings() {
        int pawnCount = 0;
        int blackKingCount = 0;
        int whiteKingCount = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Pawn currentPawn = board.getPawn(row, col);
                if (currentPawn != null) {
                    if (currentPawn.getColor() == Color.black) {
                        if (currentPawn.getIsCrowned()) {
                            blackKingCount++;
                        }
                        else {
                            pawnCount++;
                        }
                    }
                    else if (currentPawn.getColor() == Color.white) {
                        if (currentPawn.getIsCrowned()) {
                            whiteKingCount++;
                        }
                        else {
                            pawnCount++;
                        }
                    }
                }
            }
        }
        return (pawnCount == 0 && blackKingCount == 1 && whiteKingCount == 1);
    }

    public boolean checkIfFieldIsOutOfBoard(int row, int col) {
        return (row > (boardSize-1) || row < 0 || col > (boardSize-1) || col < 0);
    }

    private int[][] generateFieldDistances(Pawn currentPawn) {
        int[][] fieldDistances = new int[4][2];
        if (currentPawn.getColor() == Color.black) {
            getBlackPawnFieldDistances(fieldDistances);
        }
        else if (currentPawn.getColor() == Color.white) {
            getWhitePawnFieldDistances(fieldDistances);
        }
        return fieldDistances;
    }

    private void getWhitePawnFieldDistances(int[][] fieldDistances) {
        fieldDistances[0][0] = -1;
        fieldDistances[0][1] = -1;
        fieldDistances[1][0] = -1;
        fieldDistances[1][1] = 1;
        fieldDistances[2][0] = 1;
        fieldDistances[2][1] = 1;
        fieldDistances[3][0] = 1;
        fieldDistances[3][1] = -1;
    }

    private void getBlackPawnFieldDistances(int[][] fieldDistances) {
        fieldDistances[0][0] = 1;
        fieldDistances[0][1] = 1;
        fieldDistances[1][0] = 1;
        fieldDistances[1][1] = -1;
        fieldDistances[2][0] = -1;
        fieldDistances[2][1] = -1;
        fieldDistances[3][0] = -1;
        fieldDistances[3][1] = 1;
    }

    private boolean areThereAnyPossibleMove(int row, int col, Pawn currentPawn) {
        boolean arePiecesBlocked = true;
        int[][] fieldDistances = generateFieldDistances(currentPawn);
        for (int i = 0; i < 4; i++) {
            if (!checkIfFieldIsOutOfBoard(row + fieldDistances[i][0], col + fieldDistances[i][1])) {
                if (i < 2) {
                    if (board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]) == null) {
                        arePiecesBlocked = false;
                    }
                }
                if ((board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]) != null)) {
                    if (currentPawn.getColor() != board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]).getColor()) {
                        if (!checkIfFieldIsOutOfBoard(row + (fieldDistances[i][0] * 2), col + (fieldDistances[i][1]*2))) {
                            if (board.getPawn(row + (fieldDistances[i][0]*2), col + (fieldDistances[i][1] * 2)) == null) {
                                arePiecesBlocked = false;
                            }
                        }
                    }
                }
            }
        }
        return arePiecesBlocked;
    }

    public String checkForTacticalWinner() {
        boolean areBlackPiecesBlocked = true;
        boolean areWhitePiecesBlocked = true;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (areBlackPiecesBlocked || areWhitePiecesBlocked) {
                    Pawn currentPawn = board.getPawn(row, col);
                    if (currentPawn != null) {
                        if (currentPawn.getColor() == Color.black) {
                            areBlackPiecesBlocked = areThereAnyPossibleMove(row, col, currentPawn);
                        }
                        else if (currentPawn.getColor() == Color.white) {
                            areWhitePiecesBlocked = areThereAnyPossibleMove(row, col, currentPawn);
                        }
                    }
                }
            }
        }
        if (areBlackPiecesBlocked && !areWhitePiecesBlocked) {
            return "White";
        }
        else if (!areBlackPiecesBlocked && areWhitePiecesBlocked) {
            return "Black";
        }
        return null;
    }


    private String checkForDominantWinner() {
        Color[] colorsOfPawns = new Color[2];
        collectFieldsPawns(colorsOfPawns);
        if (colorsOfPawns[0] == Color.black && colorsOfPawns[1] == null) {
            return "Black";
        }
        else if (colorsOfPawns[0] == null && colorsOfPawns[1] == Color.white) {
            return "White";
        }
        return null;
    }

    private void collectFieldsPawns(Color[] colorsOfPawns) {
        for (int row = 0; row < boardSize; row++) {
            checkColumns(colorsOfPawns, row);
        }
    }

    private void checkColumns(Color[] colorsOfPawns, int row) {
        for (int col = 0; col < boardSize; col++) {
            Pawn currentPawn = board.getPawn(row, col);
            if (currentPawn != null) {
                if (currentPawn.getColor() == Color.black) {
                    colorsOfPawns[0] = Color.black;
                } else if (currentPawn.getColor() == Color.white) {
                    colorsOfPawns[1] = Color.white;
                }
            }
        }
    }


    public boolean checkDrawForRepeatedPositions() {
        int occurrences = 0;
        for (int i = 0; i < positions.size(); i++) {
            List currentPosition = positions.get(i);
            for (int j = i; j < positions.size(); j++) {
                if (currentPosition.equals(positions.get(j))) {
                    occurrences += 1;
                }
                if (occurrences == 3) {
                    return true;
                }
            }
            occurrences = 0;
        }
        return false;
    }

    public void savePositions() {
        List<Byte> newPositions = new ArrayList<Byte>();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getPawn(row, col) == null) {
                    newPositions.add((byte) 0);
                }
                else if (board.getPawn(row, col) != null) {
                    Pawn currentPawn = board.getPawn(row, col);
                    if (currentPawn.getColor() == Color.black) {
                        if (!currentPawn.getIsCrowned()) {
                            newPositions.add((byte) 1);
                        }
                        else if (currentPawn.getIsCrowned()) {
                            newPositions.add((byte) 2);
                        }
                    }
                    if (currentPawn.getColor() == Color.white) {
                        if (!currentPawn.getIsCrowned()) {
                            newPositions.add((byte) 3);
                        }
                        else if (currentPawn.getIsCrowned()) {
                            newPositions.add((byte) 4);
                        }
                    }
                }
            }
        }
        positions.add(newPositions);
    }

    private boolean isValidCoordinateFormat(String position) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\d+");
        Matcher matcher = pattern.matcher(position);
        return matcher.matches();
    }



    public void automaticMoveManage(int[][] nextCoordinate) {
        System.out.println("Choose from these coordinates: " + Arrays.deepToString(nextCoordinate));
        Scanner keyboardInput = new Scanner(System.in);
        String input = keyboardInput.nextLine();
        int[] newCoordinate =  convertInputToIntArr(input);
        while (Arrays.asList(nextCoordinate).contains(input)) {
            System.out.println("Choose from these coordinates: " + Arrays.toString(nextCoordinate));
        }

    }


    public static class Validation {

    }
}
