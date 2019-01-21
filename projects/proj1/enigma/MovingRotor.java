package enigma;


/** Class that represents a rotating rotor in the enigma machine.
 *  @author Jillian Hou
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */

    private String _notches;

    /**
     * Constructs a moving rotor with name,
     * permutation, and a string of notches.
     * @param name    String name of rotor.
     * @param perm    Permutation for the rotor.
     * @param notches String of notches.
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    void advance() {
        this.set(this.setting() + 1);
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i += 1) {
            if (alphabet().toInt(_notches.charAt(i)) == setting()) {
                return true;
            }
        }
        return false;
    }
}

