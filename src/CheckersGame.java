import java.util.Random;
import java.util.Scanner;

public class CheckersGame {

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

    private int player1, player2;
    private Scanner scan;
    private Random rand;

    public CheckersGame(Scanner scanner, Random random, int player1, int player2) {
        this.player1 = player1;
        this.player2 = player2;
        scan = scanner;
        rand = random;
    }

    public void start(boolean player1First) {

        char[][] board = DEFAULT_BOARD;

        printBoard(board);

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
