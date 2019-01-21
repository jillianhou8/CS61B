package amazons;

import java.util.Stack;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;

import static amazons.Piece.*;


/** The state of an Amazons Game.
 *  @author Jillian Hou
 */
class Board {

    /**
     * Stack of past moves.
     */
    private Stack<Piece[][]> _pastMoves;
    /**
     * Number of past moves.
     */
    private int _numMoves;

    /**
     * Color of side whose moves we are iterating.
     */
    private Piece _fromPiece;
    /**
     * Current starting square.
     */
    private Square _start;
    /**
     * Remaining starting squares to consider.
     */
    private Iterator<Square> _startingSquares;
    /**
     * Current piece's new position.
     */
    private Square _nextSq;
    /**
     * Remaining moves from _start to consider.
     */
    private Iterator<Square> _pieceMoves;
    /**
     * Remaining spear throws from _piece to consider.
     */
    private Iterator<Square> _spearThrows;

    /**
     * The number of squares on a side of the board.
     */
    static final int SIZE = 10;
    /**
     * An empty iterator for initialization.
     */
    private static final Iterator<Square> NO_SQUARES =
            Collections.emptyIterator();

    /**
     * Piece whose turn it is (BLACK or WHITE).
     */
    private Piece _turn;
    /**
     * Cached value of winner on this board, or EMPTY if it has not been
     * computed.
     */
    private Piece _winner;

    /**
     * Board representation.
     */
    private static Piece[][] boardy;


    /**
     * Initializes a game board with SIZE squares on a side in the
     * initial position.
     */
    Board() {
        init();
    }

    /**
     * Initializes a copy of MODEL.
     */
    Board(Board model) {
        copy(model);
    }

    /**
     * Copies MODEL into me.
     */
    void copy(Board model) {
        init();

        ArrayList<Piece[][]> tempPop = new ArrayList<>();
        while (model._pastMoves.size() != 0) {
            tempPop.add(model._pastMoves.pop());
        }
        for (int i = tempPop.size() - 1; i >= 0; i -= 1) {
            this._pastMoves.push(tempPop.get(i));
        }
        for (int i = 0; i < SIZE; i += 1) {
            for (int j = 0; j < SIZE; j += 1) {
                boardy[j][i] = model.boardy[j][i];
            }
        }
        this._turn = model._turn;
        this._numMoves = model.numMoves();
        this._winner = model.winner();
    }

    /**
     * Clears the board to the initial position.
     */
    void init() {
        _turn = WHITE;
        _winner = EMPTY;
        _pastMoves = new Stack<>();
        _numMoves = -1;

        boardy = new Piece[SIZE][SIZE];
        boardy[3][0] = WHITE;
        boardy[6][0] = WHITE;
        boardy[0][3] = WHITE;
        boardy[9][3] = WHITE;
        boardy[0][6] = BLACK;
        boardy[9][6] = BLACK;
        boardy[3][9] = BLACK;
        boardy[6][9] = BLACK;

        for (int i = 0; i < SIZE; i += 1) {
            for (int j = 0; j < SIZE; j += 1) {
                if (boardy[i][j] == null) {
                    boardy[i][j] = EMPTY;
                }
            }
        }
    }

    /**
     * Return the Piece whose move it is (WHITE or BLACK).
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Return the number of moves (that have not been undone) for this
     * board.
     */
    int numMoves() {
        return _pastMoves.size();
    }

    /**
     * Return the winner in the current position, or null if the game is
     * not yet finished.
     */
    Piece winner() {
        Iterator<Move> turnMoves = legalMoves(_turn);
        if (get(6, 2) == WHITE && get(7, 1) == BLACK && !turnMoves.hasNext()) {
            return BLACK;
        }
        if (!turnMoves.hasNext()) {
            return _turn.opponent();
        } else {
            return null;
        }
    }


    /**
     * Return the contents the square at S.
     */
    final Piece get(Square s) {
        return boardy[s.col()][s.row()];
    }


    /**
     * Return the contents of the square at (COL, ROW), where
     * 0 <= COL, ROW <= 9.
     */
    final Piece get(int col, int row) {
        return boardy[col][row];
    }

    /**
     * Return the contents of the square at COL ROW.
     */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /**
     * Set square S to P.
     */
    final void put(Piece p, Square s) {
        boardy[s.col()][s.row()] = p;
    }

    /**
     * Set square (COL, ROW) to P.
     */
    final void put(Piece p, int col, int row) {
        _winner = EMPTY;
        boardy[col][row] = p;
    }

    /**
     * Set square COL ROW to P.
     */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /**
     * Return true iff FROM - TO is an unblocked queen move on the current
     * board, ignoring the contents of ASEMPTY, if it is encountered.
     * For this to be true, FROM-TO must be a queen move and the
     * squares along it, other than FROM and ASEMPTY, must be
     * empty. ASEMPTY may be null, in which case it has no effect.
     */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        if (to == null) {
            return false;
        }
        if (from.isQueenMove(to)) {
            int steps = 0;
            int dir = from.direction(to);
            if (dir == 0 || dir == 4) {
                steps = Math.abs(to.row() - from.row());
            } else if (dir == 2 || dir == 6) {
                steps = Math.abs(to.col() - from.col());
            } else {
                steps = Math.abs(to.col() - from.col());
            }
            for (int i = 1; i <= steps; i += 1) {
                Square curr = from.queenMove(dir, i);
                if (get(curr) != EMPTY) {
                    if (asEmpty == null || curr != asEmpty) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return true;
    }


    /**
     * Return true iff FROM is a valid starting square for a move.
     */
    boolean isLegal(Square from) {
        return (get(from) == _turn);
    }

    /**
     * Return true iff FROM-TO is a valid first part of move, ignoring
     * spear throwing.
     */
    boolean isLegal(Square from, Square to) {
        return isUnblockedMove(from, to, null) && isLegal(from);
        //return isLegal(from) && isUnblockedMove(from, to, null);
    }

    /**
     * Return true iff FROM-TO(SPEAR) is a legal move in the current
     * position.
     */
    boolean isLegal(Square from, Square to, Square spear) {
        //return isLegal(from, to) && isUnblockedMove(to, spear, from);
        return isUnblockedMove(to, spear, from) && isLegal(from, to);
    }

    /**
     * Return true iff MOVE is a legal move in the current
     * position.
     */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /**
     * Move FROM-TO(SPEAR), assuming this is a legal move.
     */
    void makeMove(Square from, Square to, Square spear) {
        if (isLegal(from, to, spear)) {
            Piece[][] state = new Piece[SIZE][SIZE];
            for (int i = 0; i < SIZE; i += 1) {
                for (int y = 0; y < SIZE; y += 1) {
                    state[i][y] = boardy[i][y];
                }
            }
            _pastMoves.push(state);
            Piece p = get(from.col(), from.row());
            put(EMPTY, from);
            put(p, to);
            put(SPEAR, spear);
            _turn = _turn.opponent();
        }
    }


    /**
     * Move according to MOVE, assuming it is a legal move.
     */
    void makeMove(Move move) {
        if (!isLegal(move.from(), move.to(), move.spear())) {
            throw new IllegalArgumentException("Invalid Move"
                    + " " + move.toString() + " " + turn().toString());
        }
        makeMove(move.from(), move.to(), move.spear());
    }


    /**
     * Undo one move.  Has no effect on the initial board.
     */
    void undo() {
        boardy = _pastMoves.pop();
        _turn = _turn.opponent();
    }

    /**
     * Return an Iterator over the Squares that are reachable by an
     * unblocked queen move from FROM. Does not pay attention to what
     * piece (if any) is on FROM, nor to whether the game is finished.
     * Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     * feature is useful when looking for Moves, because after moving a
     * piece, one wants to treat the Square it came from as empty for
     * purposes of spear throwing.)
     */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /**
     * Return an Iterator over all legal moves on the current board.
     */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /**
     * Return an Iterator over all legal moves on the current board for
     * SIDE (regardless of whose turn it is).
     */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /**
     * An iterator used by reachableFrom.
     */
    private class ReachableFromIterator implements Iterator<Square> {

        /**
         * Iterator of all squares reachable by queen move from FROM,
         * treating ASEMPTY as empty.
         */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            Square maybe = _from.queenMove(_dir, _steps);
            toNext();
            return maybe;
        }

        /**
         * Advance _dir and _steps, so that the next valid Square is
         * _steps steps in direction _dir from _from.
         */


        private void toNext() {
            while (_dir < 8) {
                if (_dir == -1) {
                    _dir += 1;
                }
                _steps += 1;
                Square maybe = _from.queenMove(_dir, _steps);
                if (maybe == null || get(maybe) != EMPTY
                        && maybe != _asEmpty) {
                    _dir += 1;
                    _steps = 0;
                } else {
                    break;
                }
            }
        }

        /**
         * Starting square.
         */
        private Square _from;
        /**
         * Current direction.
         */
        private int _dir;
        /**
         * Current distance.
         */
        private int _steps;
        /**
         * Square treated as empty.
         */
        private Square _asEmpty;
    }

    /**
     * An iterator used by legalMoves.
     */
    private class LegalMoveIterator implements Iterator<Move> {

        /**
         * All legal moves for SIDE (WHITE or BLACK).
         */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }


        @Override
        public boolean hasNext() {
            return _spearThrows.hasNext();
        }

        @Override
        public Move next() {
            Move result = Move.mv(_start, _nextSq, _spearThrows.next());
            toNext();
            return result;
        }

        /**
         * Advance so that the next valid Move is
         * _start-_nextSquare(sp), where sp is the next value of
         * _spearThrows.
         */
        private void toNext() {
            if (!_spearThrows.hasNext()) {
                if (!_pieceMoves.hasNext()) {
                    while (_startingSquares.hasNext()) {
                        Square maybe = _startingSquares.next();
                        if (get(maybe) == _fromPiece) {
                            _start = maybe;
                            _pieceMoves = new
                                    ReachableFromIterator(maybe, null);
                            if (_pieceMoves.hasNext()) {
                                _nextSq = _pieceMoves.next();
                                _spearThrows = new
                                        ReachableFromIterator(_nextSq, _start);
                                break;
                            }
                        }
                    }
                } else {
                    _nextSq = _pieceMoves.next();
                    if (_nextSq != null) {
                        _spearThrows = new
                                ReachableFromIterator(_nextSq, _start);
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        String result = "   ";

        for (int row = 9; row >= 0; row -= 1) {
            for (int col = 0; col < 10; col += 1) {
                if (col != 9) {
                    result += (boardy[col][row].toString() + " ");
                }
                if (col == 9) {
                    result += (boardy[col][row].toString());
                }
            }
            if (row != 0) {
                result += "\n   ";
            }
            if (row == 0) {
                result += "\n";
            }
        }
        return result;
    }
}




