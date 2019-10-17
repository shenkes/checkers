import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CheckersGame {

    private final char[] PLAYER1 = {'X', 'K'}, PLAYER2 = {'O', '@'};
    private final char[][] DEFAULT_BOARD =
            {
                    {'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O'},
                    {'/', '/', '/', '/'},
                    {'/', '/', '/', '/'},
                    {'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X'},
                    {'X', 'X', 'X', 'X'}
            };

    private int player1Difficulty, player2Difficulty;
    private Scanner scan;
    private Random rand;

    public CheckersGame(Scanner scanner, Random random, int player1, int player2) {
        player1Difficulty = player1;
        player2Difficulty = player2;
        scan = scanner;
        rand = random;
    }

    public void start(boolean player1First) {

        char[][] board = DEFAULT_BOARD;

        printBoard(board);

    }

    public void printMoves(Move[] moves) {
        System.out.println("\nAvailable Moves:");
        for (Move move : moves) {

            if (move.getStartRow() % 2 == 0)
                System.out.print(move.getStartCol());
            else
                System.out.print(move.getStartCol() + 1);
            System.out.print(move.getStartRow());
            if (move.getNewRow() % 2 == 0)
                System.out.print(move.getNewCol());
            else
                System.out.print(move.getNewCol() + 1);
            System.out.print(move.getNewRow() + "\t");

            System.out.print("(");
            if (move.getStartRow() % 2 == 0)
                System.out.print(move.getStartCol());
            else
                System.out.print(move.getStartCol() + 1);
            System.out.print(", " + move.getStartRow() + ") => (");
            if (move.getNewRow() % 2 == 0)
                System.out.print(move.getNewCol());
            else
                System.out.print(move.getNewCol() + 1);
            System.out.println(", " + move.getNewRow() + ")");
        }
    }

    private void printBoard(char[][] board) {
        System.out.println("_____________________");
        System.out.println("|                   |");
        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print("|  ");
            if (i % 2 != 0) {
                for (char piece : board[i]) {
                    System.out.print(". " + piece + " ");
                }
            } else {
                for (char piece : board[i]) {
                    System.out.print(piece + " . ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("|___________________|");
    }

}

