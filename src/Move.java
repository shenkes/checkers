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

    public Move[] getDoubleJumps() {
        return doubleJumps;
    }
}
