import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CheckersGame {

    private char[][] board;
    private boolean isPlayer1Turn;

    private final char PLAYER1PIECE = 'O';
    private final char PLAYER1KING = '@';
    private final char PLAYER2PIECE = 'X';
    private final char PLAYER2KING = 'K';
    private final char[] PLAYER1 = {'O', '@'}, PLAYER2 = {'X', 'K'};
    private final char BLANK = '/';
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
    private final int BOARD_WIDTH = 4, BOARD_HEIGHT = 8;

    private int player1Difficulty, player2Difficulty;
    private Minimax minimax = new Minimax();
    private Scanner scan;
    private Random rand;

    public CheckersGame(Scanner scanner, Random random, int player1, int player2) {
        player1Difficulty = player1;
        player2Difficulty = player2;
        scan = scanner;
        rand = random;
    }

    public void run(boolean player1First) {

        board = DEFAULT_BOARD;
        isPlayer1Turn = player1First;

        ArrayList<Move> availableMoves;
        availableMoves = getMoves(board, isPlayer1Turn);
        do {
            printBoard(board);
            Move nextMove = null;

            if (isPlayer1Turn) {
                if (player1Difficulty == 0) {
                    nextMove = getPlayerMove(availableMoves);
                } else {
                    nextMove = minimax.minimaxMove(this, board, true, player1Difficulty);
                }
            } else {
                if (player2Difficulty == 0) {
                    nextMove = getPlayerMove(availableMoves);
                } else {
                    nextMove = minimax.minimaxMove(this, board, false, player2Difficulty);
                }
            }

            makeMove(board, nextMove, isPlayer1Turn);
            isPlayer1Turn = !isPlayer1Turn;
            availableMoves = getMoves(board, isPlayer1Turn);
        } while (!availableMoves.isEmpty());

        printBoard(board);

        if (isPlayer1Turn)
            System.out.println("\n\n\nGAME OVER! PLAYER 2 WINS!");
        else
            System.out.println("\n\n\nGAME OVER! PLAYER 1 WINS!");

    }

    public int boardEvaluation(char[][] board){
        int eval = 0;
        for (int row = 0; row < BOARD_HEIGHT; row++){
            for (int col = 0; col < BOARD_WIDTH; col++){
                switch (board[row][col]){
                    case BLANK:
                        break;
                    case PLAYER1PIECE:
                        eval++;
                        if(row == 0)
                            eval++;
                        break;
                    case PLAYER1KING:
                        eval += 4;
                        break;
                    case PLAYER2PIECE:
                        eval --;
                        if(row == BOARD_HEIGHT - 1)
                            eval--;
                        break;
                    case PLAYER2KING:
                        eval -= 4;
                        break;
                }
            }
        }
        return eval;
    }

    public void makeMove(char[][] board, Move nextMove, boolean isPlayer1Turn) {
        // If crowned
        if (nextMove.getNewRow() == 0 || nextMove.getNewRow() == BOARD_HEIGHT - 1){
            if(isPlayer1Turn){
                board[nextMove.getNewRow()][nextMove.getNewCol()] = PLAYER1KING;
            } else {
                board[nextMove.getNewRow()][nextMove.getNewCol()] = PLAYER2KING;
            }
        } else {
            board[nextMove.getNewRow()][nextMove.getNewCol()] = board[nextMove.getStartRow()][nextMove.getStartCol()];
        }
        board[nextMove.getStartRow()][nextMove.getStartCol()] = BLANK;

        // If jump move
        if ((Math.abs(nextMove.getNewRow() - nextMove.getStartRow())) != 1) {
            if (nextMove.getStartRow() % 2 == 0) {
                if (nextMove.getNewCol() > nextMove.getStartCol()) {
                    if (nextMove.getNewRow() > nextMove.getStartRow())
                        board[nextMove.getStartRow() + 1][nextMove.getStartCol()] = BLANK;
                    else
                        board[nextMove.getStartRow() - 1][nextMove.getStartCol()] = BLANK;
                } else {
                    if (nextMove.getNewRow() > nextMove.getStartRow())
                        board[nextMove.getStartRow() + 1][nextMove.getStartCol() - 1] = BLANK;
                    else
                        board[nextMove.getStartRow() - 1][nextMove.getStartCol() - 1] = BLANK;
                }
            } else {
                if (nextMove.getNewCol() > nextMove.getStartCol()) {
                    if (nextMove.getNewRow() > nextMove.getStartRow())
                        board[nextMove.getStartRow() + 1][nextMove.getStartCol() + 1] = BLANK;
                    else
                        board[nextMove.getStartRow() - 1][nextMove.getStartCol() + 1] = BLANK;
                } else {
                    if (nextMove.getNewRow() > nextMove.getStartRow())
                        board[nextMove.getStartRow() + 1][nextMove.getStartCol()] = BLANK;
                    else
                        board[nextMove.getStartRow() - 1][nextMove.getStartCol()] = BLANK;
                }
            }
        }


    }

    private Move getPlayerMove(ArrayList<Move> availableMoves) {
        printMoves(availableMoves);
        if (isPlayer1Turn)
            System.out.println("\nIt is Player 1's turn." +
                    "\nType in a 4 digit move as seen above.");
        else
            System.out.println("\nIt is Player 2's turn." +
                    "\nType in a 4 digit move as seen above.");
        int proposedMove = scan.nextInt();
        while (proposedMove < 10 || proposedMove > 9999 || !containsMove(availableMoves, proposedMove)) {
            System.out.println("Type in a 4 digit move as seen above.");
            proposedMove = scan.nextInt();
        }
        return new Move(proposedMove, null);
    }

    private boolean containsMove(ArrayList<Move> moveList, int matchingMove) {
        for (Move move : moveList) {
            if (
                    matchingMove == move.getPrintableMove()
            )
                return true;
        }
        return false;
    }

    //TODO: Figure out double jumping
    public ArrayList<Move> getMoves(char[][] board, boolean isPlayer1) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        boolean jumpFound = false;
        final char[] playerPieces;
        final char[] enemyPieces;
        if (isPlayer1) {
            playerPieces = PLAYER1;
            enemyPieces = PLAYER2;
        } else {
            enemyPieces = PLAYER1;
            playerPieces = PLAYER2;
        }

        getJumpMoves(board, isPlayer1, possibleMoves, playerPieces, enemyPieces);

        if (possibleMoves.isEmpty())
            getRegularMoves(board, isPlayer1, possibleMoves, playerPieces);

        return possibleMoves;
    }

    private void getJumpMoves(char[][] board, boolean isPlayer1, ArrayList<Move> possibleMoves, char[] playerPieces, char[] enemyPieces) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (playerPieces[0] == board[row][col]) { // This spot is the current players piece
                    if (isPlayer1) {
                        if (row >= BOARD_HEIGHT - 2) {
                            continue;
                        }
                        if (col != 0 && board[row + 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col - 1] == enemyPieces[0] || board[row + 1][col - 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col - 1, null));
                            } else {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col - 1, null));
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row + 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col + 1, null));
                            } else {
                                if (board[row + 1][col + 1] == enemyPieces[0] || board[row + 1][col + 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col + 1, null));
                            }
                        }
                    } else {
                        if (row <= 1) {
                            continue;
                        }
                        if (col != 0 && board[row - 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col - 1] == enemyPieces[0] || board[row - 1][col - 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            } else {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row - 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col + 1, null));
                            } else {
                                if (board[row - 1][col + 1] == enemyPieces[0] || board[row - 1][col + 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col + 1, null));
                            }
                        }
                    }
                }
                if (playerPieces[1] == board[row][col]) { // This spot is the current players king
                    if (!(row >= BOARD_HEIGHT - 2)) {
                        if (col != 0 && board[row + 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col - 1] == enemyPieces[0] || board[row + 1][col - 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col - 1, null));
                            } else {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col - 1, null));
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row + 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col + 1, null));
                            } else {
                                if (board[row + 1][col + 1] == enemyPieces[0] || board[row + 1][col + 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row + 2, col + 1, null));
                            }
                        }
                    }
                    if (!(row <= 1)) {
                        if (col != 0 && board[row - 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col - 1] == enemyPieces[0] || board[row - 1][col - 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            } else {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row - 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col + 1, null));
                            } else {
                                if (board[row - 1][col + 1] == enemyPieces[0] || board[row - 1][col + 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col + 1, null));
                            }
                        }
                    }
                }
            }
        }
    }

    private void getRegularMoves(char[][] board, boolean isPlayer1, ArrayList<Move> possibleMoves, char[] playerPieces) {
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (playerPieces[0] == board[row][col]) { // This spot is the current players piece
                    if (isPlayer1) {
                        if (row == BOARD_HEIGHT - 1) {
                            continue;
                        }
                        if (row % 2 == 0) {
                            if (col != 0 && board[row + 1][col - 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col - 1, null));
                            }
                            if (board[row + 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col, null));
                            }
                        } else {
                            if (board[row + 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col, null));
                            }
                            if (col != BOARD_WIDTH - 1 && board[row + 1][col + 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col + 1, null));
                            }
                        }
                    } else {
                        if (row == 0) {
                            continue;
                        }
                        if (row % 2 == 0) {
                            if (col != 0 && board[row - 1][col - 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col - 1, null));
                            }
                            if (board[row - 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col, null));
                            }
                        } else {
                            if (board[row - 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col, null));
                            }
                            if (col != BOARD_WIDTH - 1 && board[row - 1][col + 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col + 1, null));
                            }
                        }
                    }
                }
                if (playerPieces[1] == board[row][col]) { // This spot is the current players king
                    if (row != BOARD_HEIGHT - 1) {
                        if (row % 2 == 0) {
                            if (col != 0 && board[row + 1][col - 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col - 1, null));
                            }
                            if (board[row + 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col, null));
                            }
                        } else {
                            if (board[row + 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col, null));
                            }
                            if (col != BOARD_WIDTH - 1 && board[row + 1][col + 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row + 1, col + 1, null));
                            }
                        }
                    }
                    if (row != 0) {
                        if (row % 2 == 0) {
                            if (col != 0 && board[row - 1][col - 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col - 1, null));
                            }
                            if (board[row - 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col, null));
                            }
                        } else {
                            if (board[row - 1][col] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col, null));
                            }
                            if (col != BOARD_WIDTH - 1 && board[row - 1][col + 1] == BLANK) {
                                possibleMoves.add(new Move(row, col, row - 1, col + 1, null));
                            }
                        }
                    }
                }
            }
        }
    }

    public void printMoves(ArrayList<Move> moves) {
        System.out.println("\nAvailable Moves:");
        for (Move move : moves) {

            int printableMove = move.getPrintableMove();
            if (printableMove < 1000) {
                System.out.print(0);
            }
            if (printableMove < 100) {
                System.out.print(0);
            }
            System.out.print(move.getPrintableMove() + "\t");

            System.out.print("(");
            if (move.getStartRow() % 2 == 0)
                System.out.print(move.getStartCol() * 2);
            else
                System.out.print(move.getStartCol() * 2 + 1);
            System.out.print(", " + move.getStartRow() + ") => (");
            if (move.getNewRow() % 2 == 0)
                System.out.print(move.getNewCol() * 2);
            else
                System.out.print(move.getNewCol() * 2 + 1);
            System.out.println(", " + move.getNewRow() + ")");
        }
    }

    private void printBoard(char[][] board) {
        System.out.println("_____________________");
        System.out.println("|                   |");
        for (int row = board.length - 1; row >= 0; row--) {
            System.out.print("|  ");
            if (row % 2 != 0) {
                for (char piece : board[row]) {
                    System.out.print(". " + piece + " ");
                }
            } else {
                for (char piece : board[row]) {
                    System.out.print(piece + " . ");
                }
            }
            System.out.println(" |");
        }
        System.out.println("|___________________|");
    }

}

