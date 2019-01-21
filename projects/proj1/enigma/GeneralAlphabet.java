package enigma;

/** Class that represents a general alphabet.
 * @author Jillian Hou
 */
public class GeneralAlphabet extends Alphabet {
    /** A string representing the alphabet. */
    private String _alphabet;
    /** Character array of letters in string _alph. */
    private char[] letters;

    /** Constructor for the GeneralAlphabet.
     * @param a a string containing the letters of the general alphabet.
     */
    public GeneralAlphabet(String a) {
        _alphabet = a;
        letters = new char[a.length()];
    }

    /** Returns the size of the alphabet. */
    public int size() {
        return _alphabet.length();
    }

    /** Whether the alphabet contains a character.
     * @param c character to check for in alphabet.
     * @return whether the alphabet contains the character c.
     */
    public boolean contains(char c) {
        letters = _alphabet.toCharArray();
        for (Character ch : letters) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    /** Returns the character at index in alphabet.
     * @param index the index at which to get char from alphabet.
     */
    public char toChar(int index) {
        return letters[index];
    }

    /** Returns the index of character ch.
     * @param ch character to get the index of.
     */
    public int toInt(char ch) {
        int index = 0;
        for (int i = 0; i < _alphabet.length(); i += 1) {
            if (_alphabet.charAt(i) == ch) {
                index = i;
            }
        }
        return index;
    }
}
