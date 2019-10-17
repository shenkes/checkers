public class CheckersGame {

    private int player1, player2;

    public CheckersGame(int player1, int player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public void Start(boolean player1First){

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

    private void printBoard(char[][] board) {
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
