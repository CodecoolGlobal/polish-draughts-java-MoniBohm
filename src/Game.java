import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.Color;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    final private int boardSize;
    private final Board board;

    public String getInput(String inputType) {
        Scanner keyboardInput = new Scanner(System.in);
        switch(inputType) {
            case "boardSizeInput":
                System.out.print("Enter board size: ");
                break;
            case "startPositionInput":
                System.out.print("Enter start position coordinate: ");
                break;
            case "endPositionInput":
                System.out.print("Enter end position coordinate: ");
                break;
        }
        String input = keyboardInput.nextLine();
        return input;
    }

    public boolean isBoardSizeInputNumber(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        boolean result = matcher.matches();
        return result;
    }

    public boolean isBoardSizeInputValid(int input) {
        return input >= 10 && input <= 20;
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

    public Game() {
        this.boardSize = getBoardSize();
        this.board = new Board(boardSize);
    }

    public void start() throws InterruptedException {
        int player = -1;
        while(checkForDominantWinner() == null){
            System.out.println(board.toString());
            player = player==1? 2:1;
            playRound(player);
        }
    }

    private void playRound(int player) throws InterruptedException {
        cheerCurrentPlayer(player);
        String startPosition = getInput("startPositionInput");
        while(!isValidStartPosition(player, startPosition)) {
            startPosition = getInput("startPositionInput");
        }
        String endPosition = getInput("endPositionInput");
        while(!isValidEndPosition(endPosition)) {
            endPosition = getInput("endPositionInput");
        }
        tryToMakeMove(startPosition, endPosition);
    }

    private void tryToMakeMove(String startPosition, String endPosition) throws InterruptedException {
        int[] startCoor = convertInputToIntArr(startPosition);
        int[] endCoor = convertInputToIntArr(endPosition);
        Pawn pawn = board.getPawn(startCoor[0], startCoor[1]);
        board.movePawn(pawn, endCoor);
    }

    private void cheerCurrentPlayer(int player) {
        String[] cheerleaders = {"Go %s!!!%n", "Your turn %s!%n", "You can win this %s!%n", "Let's go %s! %n"};
        String animal = player==1 ? "giraffes":"lions";
        Random random = new Random();
        int index = random.nextInt(cheerleaders.length);
        System.out.printf("\033[0;33m"+cheerleaders[index], animal);
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
            fieldDistances[0][0] = 1;
            fieldDistances[0][1] = 1;
            fieldDistances[1][0] = 1;
            fieldDistances[1][1] = -1;
            fieldDistances[2][0] = -1;
            fieldDistances[2][1] = -1;
            fieldDistances[3][0] = -1;
            fieldDistances[3][1] = 1;
        }
        else if (currentPawn.getColor() == Color.white) {
            fieldDistances[0][0] = -1;
            fieldDistances[0][1] = -1;
            fieldDistances[1][0] = -1;
            fieldDistances[1][1] = 1;
            fieldDistances[2][0] = 1;
            fieldDistances[2][1] = 1;
            fieldDistances[3][0] = 1;
            fieldDistances[3][1] = -1;
        }
        return fieldDistances;
    }

    private boolean areThereAnyPossibleMove(int row, int col, Pawn currentPawn) {
        boolean arePiecesBlocked = true;
        int[][] fieldDistances = generateFieldDistances(currentPawn);
        for (int i = 0; i < 2; i++) {
            if (!checkIfFieldIsOutOfBoard(row + fieldDistances[i][0], col + fieldDistances[i][1])) {
                if (board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]) == null) {
                    arePiecesBlocked = false;
                }
                else if ((board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]) != null)) {
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
        for (int i = 2; i <= 3; i++) {
            if (!checkIfFieldIsOutOfBoard(row + fieldDistances[i][0], col + fieldDistances[i][1])) {
                if ((board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]) != null)) {
                    if (currentPawn.getColor() != board.getPawn(row + fieldDistances[i][0], col + fieldDistances[i][1]).getColor()) {
                        if (!checkIfFieldIsOutOfBoard(row + (fieldDistances[i][0] * 2), col + (fieldDistances[i][1] * 2))) {
                            if (board.getPawn(row + (fieldDistances[i][0] * 2), col + (fieldDistances[i][1] * 2)) == null) {
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
        for (int row = 0; row < boardSize; row++) {
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
        if (colorsOfPawns[0] == Color.black && colorsOfPawns[1] == null) {
            return "Black";
        }
        else if (colorsOfPawns[0] == null && colorsOfPawns[1] == Color.white) {
            return "White";
        }
        return null;
    }

    private boolean isValidCoordinateFormat(String position) {
        Pattern pattern = Pattern.compile("[a-zA-Z]\\d+");
        Matcher matcher = pattern.matcher(position);
        boolean result = matcher.matches();
        return result;
    }

    private boolean isValidInput(String position) {
        if (!isValidCoordinateFormat(position)) {
            System.out.println("Please make sure format is like A1");
            return false;
        } else if (!isFieldOnBoard(position)) {
            System.out.println("Chosen field is out of board");
            return false;
        }
        return true;
    }

    private boolean isValidStartPosition(int player, String position) {
        return isValidInput(position) && isCorrectPawn(player, position);
    }

    private boolean isValidEndPosition(String position) {
        return isValidInput(position) && isChosenFieldEmpty(position);
    }

    private boolean isChosenFieldEmpty(String position) {
        int[] coordinates = convertInputToIntArr(position);
        Pawn chosenField = board.getPawn(coordinates[0], coordinates[1]);
        if(chosenField == null) {
            return true;
        } else {
            System.out.println("You must choose an empty field");
            return false;
        }
    }

    private int[] convertInputToIntArr(String position) {
        int coordinatesArr[] = new int[2];
        int firstCoordinate = position.charAt(0) - 'A';
        int secondCoordinate = Integer.parseInt(position.substring(1)) - 1;
        coordinatesArr[0] = firstCoordinate;
        coordinatesArr[1] = secondCoordinate;
        return coordinatesArr;
    }

    public boolean isFieldOnBoard(String position) {
        int coordinates[] = convertInputToIntArr(position);
        return ((coordinates[0] < boardSize && coordinates[0] >= 0) && (coordinates[1] < boardSize && coordinates[1] >= 0));
    }

    private boolean isCorrectPawn(int player, String position) {
        // check if pawn belongs to correct player (1: white, 2: black)
        int[] coordinates = convertInputToIntArr(position);
        Pawn chosenPawn = board.getPawn(coordinates[0], coordinates[1]);
        if (chosenPawn == null) {
            System.out.println("Chosen field is empty");
            return false;
        } else {
            Color pawnColor = chosenPawn.getColor();
            if ((pawnColor == Color.white && player == 1) || (pawnColor == Color.black && player == 2)) {
                return true;
            } else {
                System.out.println("Chosen pawn is incorrect");
                return false;
            }
        }
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
}
