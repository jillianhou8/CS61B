package enigma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Jillian Hou
 */
class Machine {
    /** Alphabet of characters. */
    private Alphabet _alphabet;
    /** Final number of rotors. */
    private final int _numRotors;
    /** Final number of pawls/moving rotors. */
    private final int _pawls;
    /** Final list of all available rotors. */
    private final List<Rotor> _allRotors;
    /** List of inserted rotors. */
    private List<Rotor> _rotors;
    /** A permutation of plug board letters. */
    private Permutation _plugs;

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = new ArrayList<Rotor>(allRotors);
        _rotors = new ArrayList<Rotor>();
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        _rotors.clear();
        for (String str : rotors) {
            for (Rotor rtr : _allRotors) {
                if (rtr.name().equals(str)) {
                    _rotors.add(rtr);
                }
            }
        }
        if (_rotors.isEmpty() || _rotors.size() != numRotors()) {
            throw error("Rotors may have been misnamed.");
        }

    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        char[] settings = setting.toCharArray();
        for (int i = 0; i < settings.length; i += 1) {
            _rotors.get(i + 1).set(_alphabet.toInt(settings[i]));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugs = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean[] flags = new boolean[_rotors.size()];

        for (int i = _rotors.size() - 1; i >= 0; i -= 1) {
            if (_rotors.get(i) instanceof MovingRotor) {
                if (_rotors.get(i).atNotch()) {
                    flags[i] = true;
                    flags[i - 1] = true;
                }
            }
        }





        if (!flags[_rotors.size() - 1]) {
            flags[_rotors.size() - 1] = true;
        }

        for (int i = 0; i < _rotors.size(); i += 1) {
            if (flags[i]) {
                _rotors.get(i).advance();
            }
        }

        int x = c;
        if (_plugs != null) {
            x = _plugs.permute(c);
        }


        for (int i = _rotors.size() - 1; i >= 0; i -= 1) {
            x = _rotors.get(i).convertForward(x);
        }

        for (int j = 1; j < _rotors.size(); j += 1) {
            x = _rotors.get(j).convertBackward(x);
        }

        if (_plugs != null) {
            x = _plugs.permute(x);
        }

        return x;

    }


    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String translation = "";
        String upper = msg.toUpperCase();
        String parse = upper.replaceAll("\\s+", "");
        List<Character> all = new ArrayList<Character>();

        for (int i = 0; i < parse.length(); i += 1) {
            if (_alphabet.contains(parse.charAt(i))) {
                int result = convert(_alphabet.toInt(parse.charAt(i)));
                char single = _alphabet.toChar(result);
                all.add(single);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : all) {
            sb.append(c);
        }
        return (sb.toString() + "\n");
    }

}
