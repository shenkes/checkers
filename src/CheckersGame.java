import java.util.ArrayList;
import java.util.Arrays;
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
            Move nextMove;

            if (isPlayer1Turn) {
                if (player1Difficulty == 0) {
                    nextMove = getPlayerMove(availableMoves);
                } else {
                    nextMove = minimax.minimaxMove(this, board, true, player1Difficulty);
                    System.out.println("Player 1 moved:");
                    printMove(nextMove);
                }
            } else {
                if (player2Difficulty == 0) {
                    nextMove = getPlayerMove(availableMoves);
                } else {
                    nextMove = minimax.minimaxMove(this, board, false, player2Difficulty);
                    System.out.println("Player 2 moved:");
                    printMove(nextMove);
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

    public int boardEvaluation(char[][] board) {
        int eval = 0;
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                switch (board[row][col]) {
                    case BLANK:
                        break;
                    case PLAYER1PIECE:
                        eval += 2;
                        if (row == 0)
                            eval++;
                        break;
                    case PLAYER1KING:
                        eval += 4;
                        break;
                    case PLAYER2PIECE:
                        eval -= 2;
                        if (row == BOARD_HEIGHT - 1)
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
        if (nextMove.getNewRow() == 0 || nextMove.getNewRow() == BOARD_HEIGHT - 1) {
            if (isPlayer1Turn) {
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

            int baseRow = nextMove.getNewRow();
            int baseCol = nextMove.getNewCol();
            for (Point point : nextMove.getDoubleJumps()) {
                Move next = new Move(baseRow, baseCol, point.getY(), point.getX(), null);
                makeMove(board, next, isPlayer1Turn);
                baseRow = point.getY();
                baseCol = point.getX();
            }
        }


    }

    private Move getPlayerMove(ArrayList<Move> availableMoves) {
        printMoves(availableMoves);
        if (isPlayer1Turn)
            System.out.println("\nIt is Player 1's turn." +
                    "\nType in a 4+ digit move as seen above.");
        else
            System.out.println("\nIt is Player 2's turn." +
                    "\nType in a 4+ digit move as seen above.");
        int proposedMove = scan.nextInt();
        while (!containsMove(availableMoves, proposedMove)) {
            System.out.println("Type in a 4+ digit move as seen above.");
            proposedMove = scan.nextInt();
        }
        return new Move(proposedMove);
    }

    private boolean containsMove(ArrayList<Move> moveList, int matchingMove) {
        for (Move move : moveList) {
            if (
                    matchingMove == move.getComparableMove()
            )
                return true;
        }
        return false;
    }

    public ArrayList<Move> getMoves(char[][] board, boolean isPlayer1) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
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
                                    addJumpMove(board, isPlayer1Turn, 1, enemyPieces, possibleMoves, row, col, row + 2, col - 1);
                            } else {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 1, enemyPieces, possibleMoves, row, col, row + 2, col - 1);
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row + 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 1, enemyPieces, possibleMoves, row, col, row + 2, col + 1);
                            } else {
                                if (board[row + 1][col + 1] == enemyPieces[0] || board[row + 1][col + 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 1, enemyPieces, possibleMoves, row, col, row + 2, col + 1);
                            }
                        }
                    } else {
                        if (row <= 1) {
                            continue;
                        }
                        if (col != 0 && board[row - 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col - 1] == enemyPieces[0] || board[row - 1][col - 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, -1, enemyPieces, possibleMoves, row, col, row - 2, col - 1);
                            } else {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, -1, enemyPieces, possibleMoves, row, col, row - 2, col - 1);
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row - 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, -1, enemyPieces, possibleMoves, row, col, row - 2, col + 1);
                            } else {
                                if (board[row - 1][col + 1] == enemyPieces[0] || board[row - 1][col + 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, -1, enemyPieces, possibleMoves, row, col, row - 2, col + 1);
                            }
                        }
                    }
                }
                if (playerPieces[1] == board[row][col]) { // This spot is the current players king
                    if (!(row >= BOARD_HEIGHT - 2)) {
                        if (col != 0 && board[row + 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col - 1] == enemyPieces[0] || board[row + 1][col - 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row + 2, col - 1);
                            } else {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row + 2, col - 1);
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row + 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row + 1][col] == enemyPieces[0] || board[row + 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row + 2, col + 1);
                            } else {
                                if (board[row + 1][col + 1] == enemyPieces[0] || board[row + 1][col + 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row + 2, col + 1);
                            }
                        }
                    }
                    if (!(row <= 1)) {
                        if (col != 0 && board[row - 2][col - 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col - 1] == enemyPieces[0] || board[row - 1][col - 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row - 2, col - 1);
                            } else {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row - 2, col - 1);
                            }
                        }
                        if (col != BOARD_WIDTH - 1 && board[row - 2][col + 1] == BLANK) {
                            if (row % 2 == 0) {
                                if (board[row - 1][col] == enemyPieces[0] || board[row - 1][col] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row - 2, col + 1);
                            } else {
                                if (board[row - 1][col + 1] == enemyPieces[0] || board[row - 1][col + 1] == enemyPieces[1])
                                    addJumpMove(board, isPlayer1Turn, 0, enemyPieces, possibleMoves, row, col, row - 2, col + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addJumpMove(char[][] board, boolean isPlayer1Turn, int direction, char[] enemyPieces, ArrayList<Move> possibleMoves, int startRow, int startCol, int newRow, int newCol) {
        ArrayList<ArrayList<Point>> terminalDoubleJumpSequences = new ArrayList<>();
        searchNextJump(board, isPlayer1Turn, direction, enemyPieces, startRow, startCol, newRow, newCol, terminalDoubleJumpSequences);
        if (terminalDoubleJumpSequences.isEmpty()) {
            possibleMoves.add(new Move(startRow, startCol, newRow, newCol, null));
        } else {
            for (ArrayList<Point> jumpSequence : terminalDoubleJumpSequences) {
                possibleMoves.add(new Move(startRow, startCol, newRow, newCol, jumpSequence));
            }
        }
    }

    private void getTerminalDoubleJumpSequences(char[][] board, boolean isPlayer1Turn, int direction, char[] enemyPieces, int baseRow, int baseCol, int newRow, int newCol, ArrayList<ArrayList<Point>> terminalDoubleJumpSequences) {
        searchNextJump(board, isPlayer1Turn, direction, enemyPieces, baseRow, baseCol, newRow, newCol, terminalDoubleJumpSequences);

        Point currentPoint = new Point(newCol, newRow);
        if (terminalDoubleJumpSequences.isEmpty()) {
            ArrayList<Point> newList = new ArrayList<>();
            newList.add(currentPoint);
            terminalDoubleJumpSequences.add(newList);
        } else {
            for (ArrayList<Point> jumpSequence : terminalDoubleJumpSequences) {
                jumpSequence.add(0, currentPoint);
            }
        }
    }

    private void searchNextJump(char[][] board, boolean isPlayer1Turn, int direction, char[] enemyPieces, int baseRow, int baseCol, int newRow, int newCol, ArrayList<ArrayList<Point>> terminalDoubleJumpSequences) {
        char[][] boardClone = Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
        makeMove(boardClone, new Move(baseRow, baseCol, newRow, newCol, null), isPlayer1Turn);

        if (direction >= 0) {
            if (!(newRow >= BOARD_HEIGHT - 2)) {
                if (newCol != 0 && boardClone[newRow + 2][newCol - 1] == BLANK) {
                    if (newRow % 2 == 0) {
                        if (boardClone[newRow + 1][newCol - 1] == enemyPieces[0] || boardClone[newRow + 1][newCol - 1] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow + 2, newCol - 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    } else {
                        if (boardClone[newRow + 1][newCol] == enemyPieces[0] || boardClone[newRow + 1][newCol] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow + 2, newCol - 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    }
                }
                if (newCol != BOARD_WIDTH - 1 && boardClone[newRow + 2][newCol + 1] == BLANK) {
                    if (newRow % 2 == 0) {
                        if (boardClone[newRow + 1][newCol] == enemyPieces[0] || boardClone[newRow + 1][newCol] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow + 2, newCol + 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    } else {
                        if (boardClone[newRow + 1][newCol + 1] == enemyPieces[0] || boardClone[newRow + 1][newCol + 1] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow + 2, newCol + 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    }
                }
            }
        }
        if (direction <= 0) {
            if (!(newRow <= 1)) {
                if (newCol != 0 && boardClone[newRow - 2][newCol - 1] == BLANK) {
                    if (newRow % 2 == 0) {
                        if (boardClone[newRow - 1][newCol - 1] == enemyPieces[0] || boardClone[newRow - 1][newCol - 1] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow - 2, newCol - 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    } else {
                        if (boardClone[newRow - 1][newCol] == enemyPieces[0] || boardClone[newRow - 1][newCol] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow - 2, newCol - 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    }
                }
                if (newCol != BOARD_WIDTH - 1 && boardClone[newRow - 2][newCol + 1] == BLANK) {
                    if (newRow % 2 == 0) {
                        if (boardClone[newRow - 1][newCol] == enemyPieces[0] || boardClone[newRow - 1][newCol] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow - 2, newCol + 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
                        }
                    } else {
                        if (boardClone[newRow - 1][newCol + 1] == enemyPieces[0] || boardClone[newRow - 1][newCol + 1] == enemyPieces[1]) {
                            ArrayList<ArrayList<Point>> jumpSequences = new ArrayList<>();
                            getTerminalDoubleJumpSequences(boardClone, isPlayer1Turn, direction, enemyPieces, newRow, newCol, newRow - 2, newCol + 1, jumpSequences);
                            terminalDoubleJumpSequences.addAll(jumpSequences);
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

    private void printMoves(ArrayList<Move> moves) {
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
            System.out.print(move.getNewRow());
            for (Point point : move.getDoubleJumps()) {
                if (point.getY() % 2 == 0)
                    System.out.print(point.getX() * 2);
                else
                    System.out.print(point.getX() * 2 + 1);
                System.out.print(point.getY());
            }
            System.out.print("\t");

            printMove(move);
        }
    }

    private void printMove(Move move) {
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
        System.out.print(", " + move.getNewRow() + ")");
        for (Point point : move.getDoubleJumps()) {
            System.out.print(" => (");
            if (point.getY() % 2 == 0)
                System.out.print(point.getX() * 2);
            else
                System.out.print(point.getX() * 2 + 1);
            System.out.print(", " + point.getY() + ")");
        }
        System.out.println();
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

