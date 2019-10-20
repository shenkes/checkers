import java.util.ArrayList;
import java.util.Arrays;

public class Minimax {

    public Minimax(){

    }

    public Move minimaxMove(CheckersGame manager, char[][] board, boolean isPlayer1Turn, int difficulty){
        ArrayList<Move> moves = manager.getMoves(board, isPlayer1Turn);

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = moves.get(0);

        for (Move move : moves){
            char[][] boardClone = Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
            manager.makeMove(boardClone, move, isPlayer1Turn);
            int value = minimaxValue(manager, boardClone, isPlayer1Turn, !isPlayer1Turn, 1, difficulty, bestValue * -1);
            if(value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int minimaxValue(CheckersGame manager, char[][] board, boolean ogPlayerTurn, boolean curPlayerTurn, int curDepth, int difficulty, int pruningValue){
        ArrayList<Move> moves = manager.getMoves(board, curPlayerTurn);
        if(moves.isEmpty())
            return Integer.MAX_VALUE;
        if(curDepth == difficulty){
            if((ogPlayerTurn != curPlayerTurn && ogPlayerTurn) || (ogPlayerTurn == curPlayerTurn && !ogPlayerTurn)){
                return manager.boardEvaluation(board);
            } else {
                return manager.boardEvaluation(board) * -1;
            }
        }
        int bestValue = Integer.MIN_VALUE;
        for (Move move : moves) {
            char[][] boardClone = Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
            manager.makeMove(boardClone, move, curPlayerTurn);
            int value = minimaxValue(manager, boardClone, ogPlayerTurn, !curPlayerTurn, curDepth + 1, difficulty, bestValue * -1);
            if (pruningValue > Integer.MIN_VALUE && value >= pruningValue) {
                return value * -1;
            }
            if (value > bestValue) {
                bestValue = value;
            }
        }

        return bestValue * -1;

    }


}
