import java.util.ArrayList;
import java.util.Arrays;

public class Minimax {

    public Minimax(){

    }

    public Move minimaxMove(CheckersGame manager, char[][] board, boolean isPlayer1Turn, int difficulty){
        ArrayList<Move> moves = manager.getMoves(board, isPlayer1Turn);

        int bestValue = Integer.MIN_VALUE;
        Move bestMove = moves.get(0);

        for (int i = 0; i < moves.size(); i++){
            char[][] boardClone = Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
            manager.makeMove(boardClone, moves.get(i), isPlayer1Turn);
            int value = minimaxValue(manager, boardClone, isPlayer1Turn, !isPlayer1Turn, 1, difficulty, -bestValue);
            if(value > bestValue) {
                bestValue = value;
                bestMove = moves.get(i);
            }
        }

        return bestMove;
    }

    private int minimaxValue(CheckersGame manager, char[][] board, boolean ogPlayerTurn, boolean curPlayerTurn, int curDepth, int difficulty, int pruningValue){
        ArrayList<Move> moves = manager.getMoves(board, curPlayerTurn);
        if(moves.isEmpty())
            return Integer.MIN_VALUE;
        if(curDepth == difficulty){
            if((ogPlayerTurn == curPlayerTurn && ogPlayerTurn) || (ogPlayerTurn != curPlayerTurn && !ogPlayerTurn)){
                return manager.boardEvaluation(board) * -1;
            } else {
                return manager.boardEvaluation(board);
            }
        }
        int bestValue = Integer.MIN_VALUE;
//        if(ogPlayerTurn != curPlayerTurn){
//            bestValue = Integer.MAX_VALUE;
//        }

        for (int i = 0; i < moves.size(); i++){
            char[][] boardClone = Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());
            manager.makeMove(boardClone, moves.get(i), curPlayerTurn);
            int value = minimaxValue(manager, boardClone, ogPlayerTurn, !curPlayerTurn, curDepth + 1, difficulty, -bestValue);
            if(value >= pruningValue){
                return -value;
            }
            if(value > bestValue){
                bestValue = value;
            }
        }

        return -bestValue * -1;

    }


}
