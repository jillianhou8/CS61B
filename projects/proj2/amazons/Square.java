package amazons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import static amazons.Utils.*;

/** Represents a position on an Amazons board.  Positions are numbered
 *  from 0 (lower-left corner) to 99 (upper-right corner).  Squares
 *  are immutable and unique: there is precisely one square created for
 *  each distinct position.  Clients create squares using the factory method
 *  sq, not the constructor.  Because there is a unique Square object for each
 *  position, you can freely use the cheap == operator (rather than the
 *  .equals method) to compare Squares, and the program does not waste time
 *  creating the same square over and over again.
 *  @author Jillian Hou
 */
final class Square {

    /** My index position. */
    private final int _index;

    /** My row and column (redundant, since these are determined by _index). */
    private final int _row, _col;

    /** My String denotation. */
    private final String _str;

    /** The regular expression for a square designation (e.g.,
     *  a3). For convenience, it is in parentheses to make it a
     *  group.  This subpattern is intended to be incorporated into
     *  other pattern that contain square designations (such as
     *  patterns for moves). */
    static final String SQ = "([a-j](?:[1-9]|10))";

    /** Hashmap of integers mapped to characters, columns of board. */
    static final HashMap<Integer, Character> LETTERS =
            new HashMap<Integer, Character>();

    /** Set up hashmap of integers mapped to column characters. */
    void setupmap() {
        String s = "abcdefghij";
        char[] chars = s.toCharArray();
        for (int i = 0; i < 10; i += 1) {
            LETTERS.put(i, chars[i]);
        }
    }

    /** Return my row position, where 0 is the bottom row. */
    int row() {
        return (_row - 1);
    }

    /** Return my column position, where 0 is the leftmost column. */
    int col() {
        return _col;
    }

    /** Return my index position (0-99).  0 represents square a1, and 99
     *  is square j10. */
    int index() {
        return _index;
    }

    /** Return INDEX, the index of the square at COL, ROW. */
    static int getindex(int col, int row) {
        int index = (row * 10) + col;
        return index;
    }

    /** Return true iff THIS - TO is a valid queen move. */
    boolean isQueenMove(Square to) {
        if (exists(to.col(), to.row())) {
            int horizdiff = this.col() - to.col();
            int vertdiff = this.row() - to.row();
            if (this != to) {
                if ((horizdiff == 0)
                        && (vertdiff != 0)) {
                    return true;
                } else if ((vertdiff == 0)
                        && (horizdiff != 0)) {
                    return true;
                } else if ((horizdiff == vertdiff)
                        || (horizdiff == -vertdiff)) {
                    return true;
                }
            }
        }
        return false;
    }


    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 },
            { 1, -1 }, { 0, -1 }, { -1, -1 },
            { -1, 0 }, { -1, 1 }
    };

    /** Return the Square that is STEPS away from me in direction DIR,
     * or null if there is none.
     */
    Square queenMove(int dir, int steps) {
        if (dir < 0 || dir > 7) {
            return null;
        }
        int[] arr = DIR[dir];
        Square curr = this;
        for (int i = 0; i < steps; i += 1) {
            if (!exists(curr.col() + arr[0], curr.row() + arr[1])) {
                return null;
            }
            curr = sq(curr.col() + arr[0], curr.row() + arr[1]);
        }
        return curr;
    }

    /** Return the direction (an int as defined in the documentation
     *  for queenMove) of the queen move THIS-TO. */
    int direction(Square to) {
        assert isQueenMove(to);
        int horizdiff = to.col() - this.col();
        int vertdiff = to.row() - this.row();
        int dir = 0;

        if ((horizdiff == 0) && (vertdiff != 0)) {
            if (vertdiff > 0) {
                dir = 0;
            } else {
                dir = 4;
            }
        } else if ((vertdiff == 0) && (horizdiff != 0)) {
            if (horizdiff > 0) {
                dir = 2;
            } else {
                dir = 6;
            }
        } else if ((horizdiff == vertdiff) || (horizdiff == -vertdiff)) {
            if (horizdiff > 0 && vertdiff > 0) {
                dir = 1;
            } else if (horizdiff > 0 && vertdiff < 0) {
                dir = 3;
            } else if (horizdiff < 0 && vertdiff > 0) {
                dir = 7;
            } else {
                dir = 5;
            }
        }
        return dir;
    }

    @Override
    public String toString() {
        return _str;
    }

    /** Return true iff COL ROW is a legal square. */
    static boolean exists(int col, int row) {
        return row >= 0 && col >= 0 && row < Board.SIZE && col < Board.SIZE;
    }

    /** Return the (unique) Square denoting COL ROW. */
    static Square sq(int col, int row) {
        if (!exists(row, col)) {
            throw error("row or column out of bounds");
        }
        return sq(getindex(col, row));
    }

    /** Return the (unique) Square denoting the position with index INDEX. */
    static Square sq(int index) {
        return SQUARES[index];
    }

    /** Return the (unique) Square denoting the position COL ROW, where
     *  COL ROW is the standard text format for a square (e.g., a4). */
    static Square sq(String col, String row) {
        return sq(col.charAt(0) - 'a', Integer.parseInt(row) - 1);
    }

    /** Return the (unique) Square denoting the position in POSN, in the
     *  standard text format for a square (e.g. a4). POSN must be a
     *  valid square designation. */
    static Square sq(String posn) {
        assert posn.matches(SQ);
        return sq(Character.toString(posn.charAt(0)), (posn.substring(1)));
    }

    /** Return an iterator over all Squares. */
    static Iterator<Square> iterator() {
        return SQUARE_LIST.iterator();
    }

    /** Return the Square with index INDEX. */
    private Square(int index) {
        setupmap();
        _index = index;
        _row = (index / 10) + 1;
        _col = index % 10;
        _str = (LETTERS.get(_col) + Integer.toString(_row));
    }

    /** The cache of all created squares, by index. */
    private static final Square[] SQUARES =
        new Square[Board.SIZE * Board.SIZE];

    /** SQUARES viewed as a List. */
    private static final List<Square> SQUARE_LIST = Arrays.asList(SQUARES);

    static {
        for (int i = Board.SIZE * Board.SIZE - 1; i >= 0; i -= 1) {
            SQUARES[i] = new Square(i);
        }
    }

}
