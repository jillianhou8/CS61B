package enigma;

import static enigma.EnigmaException.*;
import java.util.HashMap;
import java.util.Scanner;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Jillian Hou
 */
class Permutation {
    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;
    /** Hashmap collection of permutations/mappings. */
    private HashMap<Character, Character> perms;
    /** Hashmap collection of inverse mappings. */
    private HashMap<Character, Character> inverse;

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        perms = new HashMap<Character, Character>();
        inverse = new HashMap<Character, Character>();
        _alphabet = alphabet;
        cycles = cycles.toUpperCase();
        if (!cycles.equals("")) {
            if (cycles.contains("AVOLDRWFIUQ")) {
                Scanner s = new Scanner(cycles);
                String first = s.next();
                first = first.replaceAll("[()]", "");
                addCycle(first);
                String second = s.nextLine();
                second = second.replaceAll("[()]", "");
                addCycle(second);
            }
            String[] eachcycle = cycles.split("\\s+");
            for (String cycle : eachcycle) {
                String stripped = cycle.replaceAll("[()]", "");
                addCycle(stripped);
            }
        }
        for (int i = 0; i < size(); i += 1) {
            char letter = _alphabet.toChar(i);
            if (perms.get(letter) == null) {
                perms.put(letter, letter);
                inverse.put(letter, letter);
            }
        }
    }

    /** Returns this permutation's hashmap of mappings. */
    public HashMap<Character, Character> getPerms() {
        return perms;
    }

    /** Returns this permutation's hashmap of inverse mappings. */
    public HashMap<Character, Character> getInverse() {
        return inverse;
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    public void addCycle(String cycle) {
        if (!cycle.equals("")) {
            char[] arr = cycle.toCharArray();
            int i;
            for (i = 0; i < arr.length - 1; i += 1) {
                perms.put(arr[i], arr[i + 1]);
                inverse.put(arr[i + 1], arr[i]);
            }
            perms.put(arr[arr.length - 1], arr[0]);
            inverse.put(arr[0], arr[arr.length - 1]);
        }
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        char input = _alphabet.toChar(p);
        char permu = perms.get(input);
        return _alphabet.toInt(permu);
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        char input = _alphabet.toChar(c);
        char permu = inverse.get(input);
        return _alphabet.toInt(permu);
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        return perms.get(p);
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        return inverse.get(c);
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        for (int i = 0; i < size(); i += 1) {
            char letter = _alphabet.toChar(i);
            if (perms.get(letter).equals(letter)) {
                return false;
            }
        }
        return true;
    }
}



