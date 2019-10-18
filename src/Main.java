import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        int player1 = -1, player2 = -1, first = -1;

        System.out.println("Choose a difficulty for Player 1.\n" +
                "Type 0 for a human player.\n" +
                "Type 1-5 for a computer player.\n" +
                "1 is the easiest. 5 is the hardest.");
        player1 = scan.nextInt();
        while (player1 < 0 || player1 > 15) {
            System.out.println("Please put a number between 0 and 5\n");
            System.out.println("Choose a difficulty for Player 1.\n" +
                    "Type 0 for a human player.\n" +
                    "Type 1-5 for a computer player.\n" +
                    "1 is the easiest. 5 is the hardest.");
            player1 = scan.nextInt();
        }
        if (player1 == 0) {
            System.out.println("Player 1 is a human player.\n");
        } else {
            System.out.println("Player 1 is an AI of difficulty " + player1 + ".\n");
        }

        System.out.println("Choose a difficulty for Player 2.\n" +
                "Type 0 for a human player.\n" +
                "Type 1-5 for a computer player.\n" +
                "1 is the easiest. 5 is the hardest.");
        player2 = scan.nextInt();
        while (player2 < 0 || player2 > 15) {
            System.out.println("Please put a number between 0 and 5\n");
            System.out.println("Choose a difficulty for Player 2.\n" +
                    "Type 0 for a human player.\n" +
                    "Type 1-5 for a computer player.\n" +
                    "1 is the easiest. 5 is the hardest.");
            player2 = scan.nextInt();
        }
        if (player2 == 0) {
            System.out.println("Player 2 is a human player.\n");
        } else {
            System.out.println("Player 2 is an AI of difficulty " + player2 + ".\n");
        }

        System.out.println("Choose who starts first.\n" +
                "Type 0 for random." +
                "\nType 1 for player 1 or 2 for player 2.");
        first = scan.nextInt();
        while (first < 0 || first > 2) {
            System.out.println("Please put a number between 0 and 5\n");
            System.out.println("Choose who starts first.\n" +
                    "Type 0 for random.\n" +
                    "Type 1 for player 1 or 2 for player 2.");
            first = scan.nextInt();
        }
        if (first == 0) {
            first = (rand.nextInt(2) + 1);
            System.out.println("Player " + first + " was randomly chosen to move first.\n");
        } else {
            System.out.println("Player " + first + " will move first.\n");
        }

        CheckersGame checkersGame = new CheckersGame(scan, rand, player1, player2);
        if(first == 1){
            checkersGame.run(true);
        }
        else{
            checkersGame.run(false);
        }

    }

}
