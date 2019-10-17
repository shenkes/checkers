import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        int player1 = -1, player2 = -1;

        System.out.println("Choose a difficulty for Player 1.\nType 0 for a human player.\nType 1-5 for a computer player.\n1 is the easiest. 5 is the hardest.");
        player1 = scan.nextInt();
        while (player1 < 0 || player1 > 5){
            System.out.println("Please put a number between 0 and 5\n");
            System.out.println("Choose a difficulty for Player 1.\nType 0 for a human player.\nType 1-5 for a computer player.\n1 is the easiest. 5 is the hardest.");
            player1 = scan.nextInt();
        }
        if(player1 == 0){
            System.out.println("Player 1 is a human player.\n");
        }
        else{
            System.out.println("Player 1 is an AI of difficulty " + player1 + ".\n");
        }
        System.out.println("Choose a difficulty for Player 2.\nType 0 for a human player.\nType 1-5 for a computer player.\n1 is the easiest. 5 is the hardest.");
        player2 = scan.nextInt();
        while (player2 < 0 || player2 > 5){
            System.out.println("Please put a number between 0 and 5\n");
            System.out.println("Choose a difficulty for Player 2.\nType 0 for a human player.\nType 1-5 for a computer player.\n1 is the easiest. 5 is the hardest.");
            player2 = scan.nextInt();
        }
        if(player2 == 0){
            System.out.println("Player 2 is a human player.\n");
        }
        else{
            System.out.println("Player 2 is an AI of difficulty " + player2 + ".\n");
        }

        char[][] board =
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

        printBoard(board);

    }

    public static void printBoard(char[][] board) {
        System.out.println("_____________________");
        System.out.println("|                   |");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|  ");
            if (i % 2 == 0) {
                for (char piece : board[i]) {
                    System.out.print(". " + piece + " ");
                }
            }

            if (i % 2 != 0) {
                for (char piece : board[i]) {
                    System.out.print(piece + " . ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("|___________________|");
    }

}
