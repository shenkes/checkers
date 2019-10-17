public class Main {

    public static void main(String[] args) {

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
