import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author
 */
public class TrReader extends Reader {
    public Reader stringy;
    public String fromy;
    public String toy;

    /**
     * A new TrReader that produces the stream of characters produced
     * by STR, converting all characters that occur in FROM to the
     * corresponding characters in TO.  That is, change occurrences of
     * FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     * in STR unchanged.  FROM and TO must have the same length.
     */
    public TrReader(Reader str, String from, String to) {
        stringy = str;
        fromy = from;
        toy = to;
    }

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!

    /*
    public char[] read(char[] arr) {
        for (int i = 0; i < from_arr.length; i += 1) {
            for (int j = 0; j < arr.length; j += 1) {
                if (arr[j] == from_arr[i]) {
                    arr[j] = to_arr[i];
                }
            }
        }
        return arr;
    }
    */

    public int read(char[] arr, int off, int len) throws IOException {

        int counter = stringy.read(arr, off, len);
        for (int i = off; i < off + len; i += 1) {
            for (int j = 0; j < fromy.length(); j += 1) {
                if (fromy.charAt(j) != -1 && arr[i] == fromy.charAt(j)) {
                    arr[i] = toy.charAt(j);
                    break;
                }
            }
        }
        return counter;
    }



    public void close() {
        try {
            stringy.close();
        }
        catch (IOException e) {
            return;
        }
    }

}



