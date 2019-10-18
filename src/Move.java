import java.util.ArrayList;

public class Move {
    private final int startRow, startCol, newRow, newCol;
    private final ArrayList<Point> doubleJumps;

    public Move(int startRow, int startCol, int newRow, int newCol, ArrayList<Point> doubleJumps) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.newRow = newRow;
        this.newCol = newCol;
        this.doubleJumps = doubleJumps;
    }

    public Move(int move) {
        doubleJumps = new ArrayList<>();
        int workingMove = move;
        while(workingMove > 9999){
            doubleJumps.add(0, new Point((workingMove / 10) % 10, workingMove % 10));
            workingMove /= 100;
        }

        startRow = (move / 100) % 10;
        if(startRow % 2 == 0){
            startCol = ((move / 1000) % 10) / 2;
        }else{
            startCol = (((move / 1000) % 10) - 1) / 2;
        }
        newRow = move % 10;
        if(newRow % 2 == 0){
            newCol = ((move / 10) % 10) / 2;
        }else{
            newCol = (((move / 10) % 10) - 1) / 2;
        }
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

        for(Point jump : doubleJumps){
            result *= 100;
            result += jump.getX() * 10;
            result += jump.getY();
        }

        return result;
    }

    public ArrayList<Point> getDoubleJumps() {
        return doubleJumps;
    }
}
