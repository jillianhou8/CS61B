package amazons;

import java.util.Iterator;
import static amazons.Piece.WHITE;
import static amazons.Piece.BLACK;

/** A Player that automatically generates moves.
 *  @author Jillian Hou
 */
class AI extends Player {

    /**
     * A position magnitude indicating a win (for white if positive, black
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;
    /** Help me. */
    private static final int FORTY = 40;
    /** One. F u. */
    private static final int ONE = 1;
    /**
     * A new AI with no piece or controller (intended to produce
     * a template).
     */
    AI() {
        this(null, null);
    }

    /**
     * A new AI playing PIECE under control of CONTROLLER.
     */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = _controller.board();
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /**
     * The move found by the last call to one of the ...FindMove methods
     * below.
     */
    private Move _lastFoundMove;

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _lastFoundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _lastMoveFound.
     */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        int value = 0;
        Iterator<Move> kids;
        if (sense == 1) {
            value = -INFTY;
            kids = board.legalMoves(WHITE);
        } else {
            value = INFTY;
            kids = board.legalMoves(BLACK);
        }

        Move whitemove = null;
        Move blackmove = null;

        while (kids.hasNext()) {
            Move move = kids.next();
            board.makeMove(move);
            int scores = findMove(board, depth - 1,
                    saveMove, sense * (-1), alpha, beta);
            board.undo();


            if (sense == 1) {
                if (scores > value) {
                    value = scores;
                    whitemove = move;
                }
                alpha = Math.max(alpha, scores);
                _lastFoundMove = whitemove;
                if (alpha >= beta) {
                    break;
                }


            } else {
                if (scores < value) {
                    value = scores;
                    blackmove = move;
                }
                beta = Math.min(beta, scores);
                _lastFoundMove = blackmove;
                if (beta <= alpha) {
                    break;
                }

            }


        }
        return value;
    }


    /**
     * Return a heuristically determined maximum search depth
     * based on characteristics of BOARD.
     */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        return (N / FORTY) + ONE;
    }


    /**
     * Return a heuristic value for BOARD.
     */
    private int staticScore(Board board) {
        int wcounter = 0;
        int bcounter = 0;
        Iterator<Move> whitemvs = board.legalMoves(WHITE);
        Iterator<Move> blackmvs = board.legalMoves(BLACK);

        while (whitemvs.hasNext()) {
            whitemvs.next();
            wcounter += 1;
        }
        while (blackmvs.hasNext()) {
            blackmvs.next();
            bcounter += 1;
        }
        return Math.abs(bcounter - wcounter);
    }

}







