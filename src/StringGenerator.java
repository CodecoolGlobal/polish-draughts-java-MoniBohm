import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StringGenerator {
    Pawn[][] fields;
    final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    final String YELLOW_BACKGROUND = "\033[43m";
    final String YELLOW = "\033[0;33m";

    final String emptyBlackField = ANSI_BLACK_BACKGROUND + "    " + ANSI_RESET;
    final String emptyWhiteField = YELLOW_BACKGROUND + "    " + ANSI_RESET;
    final String blackPawnBlackField = ANSI_BLACK_BACKGROUND + " \uD83E\uDD81 " + ANSI_RESET;
    final String whitePawnBlackField = ANSI_BLACK_BACKGROUND + " \uD83E\uDD92 " + ANSI_RESET;


    public StringGenerator(Pawn[][] fields) {
        this.fields = fields;
    }

    public String toString() {
        final String newLine = "\n";
        StringBuilder board = new StringBuilder();
        StringBuilder header = createHeader();
        board.append(header);
        for (int i = 0; i < fields.length; i++) {
            String letter = YELLOW + alphabet[i] + " ";
            board.append(letter);
            rowToString(board, fields[i], i);
            board.append(newLine);
        }
        return board.toString();
    }

    private void rowToString(StringBuilder board, Pawn[] row, int rowNumber) {
        int boardSize = fields[0].length;
        if (rowNumber % 2 == 0) {
            evenRows(board, row);
        } else {
            oddRows(board, row);
        }
    }

    private void evenRows(StringBuilder board, Pawn[] row) {
        for (int i = 0; i < row.length; i++) {
            if (i % 2 == 0) { // black fields
                createColumns(board, row, i);
            } else { // white fields
                board.append(emptyWhiteField);
            }
        }
    }

    private void oddRows(StringBuilder board, Pawn[] row) {
        for (int i = 0; i < row.length; i++) {
            if (i % 2 != 0) { // black fields
                createColumns(board, row, i);
            } else { // white fields
                board.append(emptyWhiteField);
            }
        }
    }

    private void createColumns(StringBuilder board, Pawn[] row, int i) {
        if (row[i] == null) {
            board.append(emptyBlackField);
        } else {
            String pawnAndField = (row[i].getColor() == Color.black ? blackPawnBlackField : whitePawnBlackField);
            board.append(pawnAndField);
        }
    }

    private StringBuilder createHeader() {
        int width = fields[0].length;
        StringBuilder header = new StringBuilder("  ");
        for (int i = 1; i <= width; i++) {
            String element = (i < 10) ? "  " + YELLOW + i + " " : " " + YELLOW + i + " ";
            header.append(element);
        }
        return header.append("\n");
    }

}
