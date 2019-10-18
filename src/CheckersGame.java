import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CheckersGame {

    public static char[][] board;

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
    private Scanner scan;
    private Random rand;

    public CheckersGame(Scanner scanner, Random random, int player1, int player2) {
        player1Difficulty = player1;
        player2Difficulty = player2;
        scan = scanner;
        rand = random;
    }

    public void start(boolean player1First) {

        board = DEFAULT_BOARD;

        printBoard(board);
        printMoves(getMoves(board, true));
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
                        if (col != 0 && board[row + 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col - 1] == enemyPieces[0] || board[row - 1][col - 1] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            } else {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    possibleMoves.add(new Move(row, col, row - 2, col - 1, null));
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row + 2][col + 1] == BLANK) {
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

            if (move.getStartRow() % 2 == 0)
                System.out.print(move.getStartCol() * 2);
            else
                System.out.print(move.getStartCol() * 2 + 1);
            System.out.print(move.getStartRow());
            if (move.getNewRow() % 2 == 0)
                System.out.print(move.getNewCol() * 2);
            else
                System.out.print(move.getNewCol() * 2 + 1);
            System.out.print(move.getNewRow() + "\t");

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

