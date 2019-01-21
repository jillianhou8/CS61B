package amazons;

import org.junit.Test;

import static amazons.Piece.*;
import static amazons.Piece.WHITE;
import static org.junit.Assert.*;
import ucb.junit.textui;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/** The suite of all JUnit tests for the enigma package.
 *  @author Jillian Hou
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /** Tests basic correctness of put and get on the initialized board. */
    @Test
    public void testBasicPutGet() {
        Board b = new Board();
        System.out.println(b.toString());
        b.put(BLACK, Square.sq(3, 5));
        assertEquals(b.get(3, 5), BLACK);
        b.put(WHITE, Square.sq(9, 9));
        assertEquals(b.get(9, 9), WHITE);
        b.put(EMPTY, Square.sq(3, 5));
        assertEquals(b.get(3, 5), EMPTY);
    }

    /** Tests proper identification of legal/illegal queen moves. */
    @Test
    public void testIsQueenMove() {
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(1, 5)));
        assertFalse(Square.sq(1, 5).isQueenMove(Square.sq(2, 7)));
        assertFalse(Square.sq(0, 0).isQueenMove(Square.sq(5, 1)));
        assertTrue(Square.sq(1, 1).isQueenMove(Square.sq(9, 9)));
        assertTrue(Square.sq(2, 7).isQueenMove(Square.sq(8, 7)));
        assertTrue(Square.sq(3, 0).isQueenMove(Square.sq(3, 4)));
        assertTrue(Square.sq(7, 9).isQueenMove(Square.sq(0, 2)));
    }

    /** Tests toString for initial board state and a smiling board state. :) */
    @Test
    public void testToString() {
        Board b = new Board();
        assertEquals(INIT_BOARD_STATE, b.toString());
        makeSmile(b);
        assertEquals(SMILE, b.toString());
    }

    private void makeSmile(Board b) {
        b.put(EMPTY, Square.sq(0, 3));
        b.put(EMPTY, Square.sq(0, 6));
        b.put(EMPTY, Square.sq(9, 3));
        b.put(EMPTY, Square.sq(9, 6));
        b.put(EMPTY, Square.sq(3, 0));
        b.put(EMPTY, Square.sq(3, 9));
        b.put(EMPTY, Square.sq(6, 0));
        b.put(EMPTY, Square.sq(6, 9));
        for (int col = 1; col < 4; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(2, 7));
        for (int col = 6; col < 9; col += 1) {
            for (int row = 6; row < 9; row += 1) {
                b.put(SPEAR, Square.sq(col, row));
            }
        }
        b.put(EMPTY, Square.sq(7, 7));
        for (int lip = 3; lip < 7; lip += 1) {
            b.put(WHITE, Square.sq(lip, 2));
        }
        b.put(WHITE, Square.sq(2, 3));
        b.put(WHITE, Square.sq(7, 3));
    }

    static final String INIT_BOARD_STATE =
                    "   - - - B - - B - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   B - - - - - - - - B\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   W - - - - - - - - W\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - W - - W - - -\n";

    static final String SMILE =
                    "   - - - - - - - - - -\n"
                            + "   - S S S - - S S S -\n"
                            + "   - S - S - - S - S -\n"
                            + "   - S S S - - S S S -\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - W - - - - W - -\n"
                            + "   - - - W W W W - - -\n"
                            + "   - - - - - - - - - -\n"
                            + "   - - - - - - - - - -\n";


    @Test
    public void testisQueenMove() {
        assertTrue(Square.sq(2, 0).isQueenMove(Square.sq(2, 3)));
        assertTrue(Square.sq(2, 0).isQueenMove(Square.sq(8, 0)));
        assertTrue(Square.sq(2, 0).isQueenMove(Square.sq(9, 7)));
        assertFalse(Square.sq(2, 0).isQueenMove(Square.sq(9, 1)));
        assertFalse(Square.sq(2, 0).isQueenMove(Square.sq(6, 3)));
        assertFalse(Square.sq(2, 0).isQueenMove(Square.sq(1, 6)));
        assertFalse(Square.sq(2, 0).isQueenMove(Square.sq(9, 9)));
        assertFalse(Square.sq(2, 0).isQueenMove(Square.sq(0, 8)));
    }


    @Test
    public void testReachableFrom() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 4), null);
        System.out.println(Square.sq(5, 4).toString());
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQS.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQS.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQS.size(), squares.size());
    }



    @Test
    public void testReachableFrom1() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(8, 2), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQS1.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQS1.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQS1.size(), squares.size());
    }



    @Test
    public void testReachableFrom2() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(9, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQS2.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQS2.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQS2.size(), squares.size());
    }


    @Test
    public void testReachableFrom3() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(4, 3), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQS3.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQS3.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQS3.size(), squares.size());
    }


    @Test
    public void testReachableFrom4() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(9, 9), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQS4.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQS4.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQS4.size(), squares.size());
    }


        /** Tests legalMovesIterator to make sure it returns all legal Moves.
         *  This method needs to be finished and may need to be changed
         *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        buildBoard(b, INITIALTESTBOARD);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
        System.out.println(numMoves);
        assertEquals(2176, numMoves);
        assertEquals(2176, moves.size());
    }

    @Test
    public void testLegalMovessmiley() {
        Board b = new Board();
        buildBoard(b, SMILEYTESTBOARD);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(1942, numMoves);
        assertEquals(1942, moves.size());
    }


    @Test
    public void testLegalMovesahh() {
        Board b = new Board();
        buildBoard(b, SMOLTESTBOARD);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            numMoves += 1;
            moves.add(m);
        }
        System.out.println(numMoves);
        assertEquals(1, numMoves);
        assertEquals(1, moves.size());
    }



    @Test
    public void testReachableFromsmol() {
        Board b = new Board();
        buildBoard(b, SMOLTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(4, 5), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(1, numSquares);
        assertEquals(1, squares.size());
    }


    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                b.put(piece, Square.sq(col, row));
            }
        }
        System.out.println(b);
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] REACHABLEFROMTESTBOARD = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, W, W },
            { E, E, E, E, E, E, E, S, E, S },
            { E, E, E, S, S, S, S, E, E, S },
            { E, E, E, S, E, E, E, E, B, E },
            { E, E, E, S, E, W, E, E, B, E },
            { E, E, E, S, S, S, B, W, B, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
    };

    static final Piece[][] INITIALTESTBOARD = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { W, E, E, E, E, E, E, E, E, W },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, W, E, E, W, E, E, E },
    };

    static final Piece[][] SMOLTESTBOARD = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, S, S, S, E, E, E },
            { E, E, S, S, E, E, S, E, E, E },
            { E, E, S, S, S, S, S, E, E, E },
            { E, E, S, S, W, E, S, E, E, E },
            { E, E, E, S, S, S, S, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
    };

    static final Piece[][] AJTESTBOARD = {
            { E, S, E, E, E, E, E, E, E, E },
            { E, S, E, E, E, E, E, E, E, E },
            { S, S, E, E, E, E, E, E, E, E },
            { E, S, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, B, E },
            { E, E, E, E, E, E, B, E, B, E },
            { S, S, E, E, E, E, E, E, E, E },
            { E, S, S, E, E, E, E, E, E, E },
            { W, E, S, E, E, E, E, E, E, E },
    };


    static final Piece[][] SMILEYTESTBOARD = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, S, S, S, E, E, S, S, S, E },
            { E, S, E, S, E, E, S, E, S, E },
            { E, S, S, S, E, E, S, S, S, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, W, E, E, E, E, W, E, E },
            { E, E, E, W, W, W, W, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
    };

    static final Piece[][] WINNERTESTBOARD = {
            { E, S, S, S, S, S, B, E, E, E },
            { S, E, E, S, S, S, S, S, S, S },
            { E, E, S, S, S, S, S, S, B, S },
            { E, S, B, S, S, W, S, S, S, S },
            { S, E, E, E, S, S, S, S, S, S },
            { E, E, E, E, E, S, S, W, S, S },
            { E, E, E, E, S, E, S, S, S, W },
            { E, E, E, S, E, E, S, E, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { B, W, S, S, S, S, S, S, S, S },
    };



    static final Set<Square> REACHABLEFROMTESTSQS =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));

    static final Set<Square> REACHABLEFROMTESTSQS1 =
            new HashSet<>(Arrays.asList(
                    Square.sq(8, 1),
                    Square.sq(8, 0),
                    Square.sq(9, 2),
                    Square.sq(9, 1),
                    Square.sq(9, 3),
                    Square.sq(7, 1),
                    Square.sq(6, 0),
                    Square.sq(7, 2),
                    Square.sq(6, 2),
                    Square.sq(5, 2),
                    Square.sq(4, 2),
                    Square.sq(3, 2),
                    Square.sq(2, 2),
                    Square.sq(1, 2),
                    Square.sq(0, 2)));

    static final Set<Square> REACHABLEFROMTESTSQS2 =
            new HashSet<>(Arrays.asList(
                    Square.sq(9, 5),
                    Square.sq(9, 3),
                    Square.sq(9, 2),
                    Square.sq(9, 1),
                    Square.sq(9, 0)));

    static final Set<Square> REACHABLEFROMTESTSQS3 =
            new HashSet<>(Arrays.asList(
                    Square.sq(4, 4),
                    Square.sq(4, 5),
                    Square.sq(4, 2),
                    Square.sq(4, 1),
                    Square.sq(4, 0),
                    Square.sq(5, 2),
                    Square.sq(6, 1),
                    Square.sq(7, 0),
                    Square.sq(3, 2),
                    Square.sq(2, 1),
                    Square.sq(1, 0)));

    static final Set<Square> REACHABLEFROMTESTSQS4 =
            new HashSet<>(Arrays.asList(
                    Square.sq(8, 9),
                    Square.sq(7, 9),
                    Square.sq(6, 9),
                    Square.sq(5, 9),
                    Square.sq(4, 9),
                    Square.sq(3, 9),
                    Square.sq(2, 9),
                    Square.sq(1, 9),
                    Square.sq(0, 9)));


    /** A dummy test as a placeholder for real ones. */
    @Test
    public void dummyTest() {
        assertTrue("There are no unit tests!", true);
    }

}




