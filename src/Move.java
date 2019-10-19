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
        while (workingMove > 9999) {
            int x;
            int y = workingMove % 10;
            if (y % 2 == 0) {
                x = ((workingMove / 10) % 10) / 2;
            } else {
                x = (((workingMove / 10) % 10) - 1) / 2;
            }
            doubleJumps.add(0, new Point(x, y));
            workingMove /= 100;
        }

        startRow = (workingMove / 100) % 10;
        if (startRow % 2 == 0) {
            startCol = ((workingMove / 1000) % 10) / 2;
        } else {
            startCol = (((workingMove / 1000) % 10) - 1) / 2;
        }
        newRow = workingMove % 10;
        if (newRow % 2 == 0) {
            newCol = ((workingMove / 10) % 10) / 2;
        } else {
            newCol = (((workingMove / 10) % 10) - 1) / 2;
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

    public int getComparableMove() {
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

        if (doubleJumps != null) {
            for (Point jump : doubleJumps) {
                result *= 100;
                if (jump.getY() % 2 == 0)
                    result += jump.getX() * 2 * 10;
                else
                    result += (jump.getX() * 2 + 1) * 10;
                result += jump.getY();
            }
        }
        return result;
    }

    public int getPrintableMove() {
        int result = 0;
        if(startCol == 0){
            result += 8000;
        }
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

        if (doubleJumps != null) {
            for (Point jump : doubleJumps) {
                result *= 100;
                if (jump.getY() % 2 == 0)
                    result += jump.getX() * 2 * 10;
                else
                    result += (jump.getX() * 2 + 1) * 10;
                result += jump.getY();
            }
        }

        return result;
    }

    public ArrayList<Point> getDoubleJumps() {
        if(doubleJumps == null){
            return new ArrayList<Point>();
        }
        return doubleJumps;
    }
}
