public class Move {
    private final int startRow, startCol, newRow, newCol;
    private final Move[] doubleJumps;

    public Move(int startRow, int startCol, int newRow, int newCol, Move[] doubleJumps) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.newRow = newRow;
        this.newCol = newCol;
        this.doubleJumps = doubleJumps;
    }

    public Move(int move, Move[] doubleJumps) {
        startRow = (move / 100) % 10;
        if(startRow % 2 == 0){
            startCol = (move / 1000) / 2;
        }else{
            startCol = ((move / 1000) - 1) / 2;
        }
        newRow = move % 10;
        if(newRow % 2 == 0){
            newCol = ((move / 10) % 10) / 2;
        }else{
            newCol = (((move / 10) % 10) - 1) / 2;
        }
        this.doubleJumps = doubleJumps;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getNewRow() {
        return newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getPrintableMove(){
        int result = 0;
        if (startRow % 2 == 0)
            result += startCol * 2 * 1000;
        else
            result += (startCol * 2 + 1) * 1000;
        result += startRow * 100;
        if (newRow % 2 == 0)
            result += newCol * 2 * 10;
        else
            result += (newCol * 2 + 1) * 10;
        result += newRow;

        return result;
    }

    public Move[] getDoubleJumps() {
        return doubleJumps;
    }
}
