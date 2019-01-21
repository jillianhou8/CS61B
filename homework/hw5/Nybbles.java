/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Jillian Hou
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int index = k / 8;
            int innerindex = k % 8;
            int full = _data[index];
            int lefted = (full << (innerindex * 4));
            int result = (lefted >> (32 - 4));
            return result;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            int index = k / 8;
            int innerindex = k % 8;
            int backindex = 28 - (innerindex * 4);


            int skip = (innerindex - 1) * 4;
            int shifted = ~(_data[index] << skip);
            shifted = shifted >> skip;
            _data[index] = (shifted | val);

            /*
            int masked = ~(15 << backindex);
            int full = masked & _data[index];
            val = val << 28;
            val = val >>> 28;
            val = val << backindex;
            _data[index] = full | val;
            */
            /*
            int shifted = (full << (innerindex - 1) * 4);
            int newfull = shifted | val;
            newfull = ~(newfull >> (innerindex - 1) * 4);
            _data[index] = _data[index] | newfull;
            */
        }
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
