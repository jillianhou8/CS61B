package amazons;

import ucb.gui2.Pad;

import java.io.IOException;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static amazons.Piece.*;
import static amazons.Square.sq;


/** A widget that displays an Amazons game.
 *  @author Jillian Hou
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /** Colors of empty squares and grid lines. */
    static final Color
        SPEAR_COLOR = new Color(64, 64, 64),
        LIGHT_SQUARE_COLOR = new Color(238, 207, 161),
        DARK_SQUARE_COLOR = new Color(205, 133, 63);

    /** Locations of images of white and black queens. */
    private static final String
        WHITE_QUEEN_IMAGE = "timmyhoe.png",
        BLACK_QUEEN_IMAGE = "yghoe.png",
        SPEAR_IMAGE = "peachy.png";

    /** Size parameters. */
    private static final int
        SQUARE_SIDE = 30,
        BOARD_SIDE = SQUARE_SIDE * 10;

    /** Queue on which to post move commands (from mouse clicks). */
    private ArrayBlockingQueue<String> _commands;
    /** Board being displayed. */
    private final Board _board = new Board();

    /** Image of white queen. */
    private BufferedImage _whiteQueen;
    /** Image of black queen. */
    private BufferedImage _blackQueen;
    /** Image of spear. */
    private BufferedImage _spear;

    /** Count of clicks. */
    private int _counter;

    /** Squares for FROM, TWO, SPEAR. */
    private Square from, to, spear;

    /** True iff accepting moves from user. */
    private boolean _acceptingMoves;

    /** A graphical representation of an Amazons board that sends commands
     *  derived from mouse clicks to COMMANDS.  */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_QUEEN_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_QUEEN_IMAGE));
            _spear = ImageIO.read(Utils.getResource(SPEAR_IMAGE));
        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
    }

    /** Draw the bare board G.  */
    private void drawGrid(Graphics2D g) { // FIXME
        g.setColor(LIGHT_SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);
        int x, y;

        for (int i = 0; i < Board.SIZE; i++) {
            y = i * SQUARE_SIDE;


            for (int j = 0; j < Board.SIZE; j++) {
                x = j * SQUARE_SIDE;
                if (i % 2 == 0 && j % 2 == 0) {
                    g.setColor(DARK_SQUARE_COLOR);
                } else if (j % 2 == 0) {
                    g.setColor(LIGHT_SQUARE_COLOR);
                } else if (i % 2 == 0) {
                    g.setColor(LIGHT_SQUARE_COLOR);
                } else {
                    g.setColor(DARK_SQUARE_COLOR);
                }
                g.fillRect(y, x, SQUARE_SIDE, SQUARE_SIDE);
            }
        }

    }

    @Override
    public synchronized void paintComponent(Graphics2D g) { // FIXME
        drawGrid(g);
        Iterator<Square> iter = Square.iterator();
        while (iter.hasNext()) {
            Square next = iter.next();
            if (_board.get(next) == WHITE) {
                drawQueen(g, next, WHITE);
            } else if (_board.get(next) == BLACK) {
                drawQueen(g, next, BLACK);
            } else if (_board.get(next) == SPEAR) {
                drawSpear(g, next, SPEAR);
            }
        }
    }



    /** Draw a queen for side PIECE at square S on G.  */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        g.drawImage(piece == WHITE ? _whiteQueen : _blackQueen,
                    cx(s.col()) + 2, cy(s.row()) + 4, null);
    }

    /** Draw a queen for side PIECE at square S on G.  */
    private void drawSpear(Graphics2D g, Square s, Piece piece) {
        g.drawImage(_spear, cx(s.col()) + 2, cy(s.row()) + 4, null);
    }

    /** Handle a click on S. */
    private void click(Square s) { //FIXME
        _counter += 1;

        if (_counter > 3) {
            _counter = 0;
        }
        if (_counter == 1) {
            from = s;
        }
        if (_counter == 2) {
            to = s;
        }
        if (_counter == 3) {
            spear = s;
            String b = from.toString() + "-"
                    + to.toString() + "(" + spear.toString() + ")";
            _commands.add(b);
            if (_board.isLegal(from, to, spear)) {
                _board.makeMove(from, to, spear);
            }
            _counter = 0;
        }
        repaint(); //before or after??
    }

    /** Handle mouse click event E. */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = xpos / SQUARE_SIDE,
            y = (BOARD_SIDE - ypos) / SQUARE_SIDE;
        if (_acceptingMoves
            && x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE) {
            click(sq(x, y));
        }
    }

    /** Revise the displayed board according to BOARD. */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /** Turn on move collection iff COLLECTING, and clear any current
     *  partial selection.   When move collection is off, ignore clicks on
     *  the board. */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /** Return x-pixel coordinate of the left corners of column X
     *  relative to the upper-left corner of the board. */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /** Return y-pixel coordinate of the upper corners of row Y
     *  relative to the upper-left corner of the board. */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /** Return x-pixel coordinate of the left corner of S
     *  relative to the upper-left corner of the board. */
    private int cx(Square s) {
        return cx(s.col());
    }

    /** Return y-pixel coordinate of the upper corner of S
     *  relative to the upper-left corner of the board. */
    private int cy(Square s) {
        return cy(s.row());
    }


}
