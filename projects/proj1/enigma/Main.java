package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Arrays;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Jillian Hou
 */
public final class Main {

    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;
    /**
     * Source of input messages.
     */
    private Scanner _input;
    /**
     * Source of machine configuration.
     */
    private Scanner _config;
    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;
    /** Rotor that is created. */
    private Rotor gotcha;
    /** Possible name of the new rotor. */
    private String first;
    /** Number of rotors. */
    private int numrotor;
    /** Number of pawls/moving rotors. */
    private int numpawls;
    /** List of all available rotors. */
    private List<Rotor> allrotors;
    /** String last which is settings. */
    private String last = null;
    /** List of possible rotors names to be added. */


    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine mach = readConfig();
        String prev = null;
        while (_input.hasNextLine()) {
            String currline = _input.nextLine();
            if (currline.isEmpty()) {
                printMessageLine(currline);
            } else if (currline.charAt(0) == '*') {
                setUp(mach, currline);
                prev = currline;
            } else if (currline.charAt(0) != '*' && prev.isEmpty()) {
                throw error("bad input format; needs to start with setting.");
            } else {
                printMessageLine(mach.convert(currline));
            }
        }
    }


    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    private Machine readConfig() {
        try {
            String alphy = _config.nextLine();
            if (alphy.contains("-")) {
                _alphabet = new CharacterRange(alphy.charAt(0),
                        alphy.charAt(alphy.length() - 1));
            } else {
                _alphabet = new GeneralAlphabet(alphy);
            }
            numrotor = _config.nextInt();
            numpawls = _config.nextInt();
            allrotors = new ArrayList<Rotor>();

            while (_config.hasNextLine()) {
                first = _config.next();
                if (first.charAt(0) == '(') {
                    Rotor lastone = allrotors.get(allrotors.size() - 1);
                    Permutation lastperm = lastone.permutation();
                    String help = first + _config.nextLine();
                    String[] eachcycle = help.split("\\s+");
                    for (String cycle : eachcycle) {
                        String stripped = cycle.replaceAll("[()]", "");
                        lastperm.getPerms().replace(stripped.charAt(0),
                                stripped.charAt(1));
                        lastperm.getPerms().replace(stripped.charAt(1),
                                stripped.charAt(0));
                        lastperm.getInverse().replace(stripped.charAt(0),
                                stripped.charAt(1));
                        lastperm.getInverse().replace(stripped.charAt(1),
                                stripped.charAt(0));
                    }
                } else {
                    first = first.toUpperCase();
                    allrotors.add(readRotor());
                }
            }

            return new Machine(_alphabet, numrotor, numpawls, allrotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }


    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        try {
            String check = null;
            String info = _config.next();
            char rotates = info.charAt(0);
            check = _config.nextLine();
            if (check.trim().charAt(0) != '('
                    || check.trim().charAt(check.trim().length() - 1) != ')') {
                throw error("bad configuration format");
            }

            if (rotates == 'M') {
                List<Character> notches = new ArrayList<Character>();
                for (int i = 1; i < info.length(); i += 1) {
                    char notch = info.charAt(i);
                    notches.add(notch);
                }

                Permutation permu =
                        new Permutation(check, _alphabet);
                StringBuilder builder = new StringBuilder();
                for (Character ch : notches) {
                    builder.append(ch);
                }
                String notchies = builder.toString();
                gotcha = new MovingRotor(first, permu, notchies);
            } else if (rotates == 'N') {
                Permutation permu =
                        new Permutation(check, _alphabet);
                gotcha = new FixedRotor(first, permu);
            } else if (rotates == 'R') {
                Permutation permu =
                        new Permutation(check, _alphabet);
                gotcha = new Reflector(first, permu);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
        return gotcha;
    }


    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        Scanner lineScanner = new Scanner(settings);
        List<String> temp = new ArrayList<String>();
        List<String> tempplugs = new ArrayList<String>();
        String[] arguments = settings.split("\\s+");
        String curr = lineScanner.next();
        while (lineScanner.hasNext()) {
            if (curr.charAt(0) == '*') {
                curr = lineScanner.next();
                helper(curr);
            } else if (curr.charAt(0) == '(') {
                tempplugs.add(curr);
                curr = lineScanner.next();
            } else {
                if (arguments.length != numrotor + 2) {
                    List<Boolean> noparen = new ArrayList<Boolean>();
                    for (String str : arguments) {
                        if (str.indexOf('(') == -1) {
                            noparen.add(true);
                        }
                    }
                    if (noparen.size() == arguments.length) {
                        throw error("wrong number of setting args");
                    }
                }
                temp.add(curr);
                curr = lineScanner.next();
            }
        }
        if (curr.charAt(0) == '(') {
            tempplugs.add(curr);
        } else {
            temp.add(curr);
        }
        String[] plugs = tempplugs.toArray(new String[tempplugs.size()]);
        String plugcycles = String.join(" ", tempplugs);
        last = temp.get(temp.size() - 1);
        for (int i = 0; i < last.length(); i += 1) {
            if (!_alphabet.contains(last.charAt(i))) {
                throw error("Initial position chars must be in alphabet. ");
            }
        }
        if (last.length() != numrotor - 1) {
            throw error("Wrong length of initial positions string.");
        }
        temp.remove(temp.size() - 1);
        String[] rotors = temp.toArray(new String[temp.size()]);
        if (tempplugs.size() != 0) {
            M.setPlugboard(new Permutation(plugcycles, _alphabet));
        }
        M.insertRotors(rotors);
        M.setRotors(last);
    }

    /** Helps check if first rotor is a reflector in settings.
     * @param current String for the first rotor which should be a reflector.*/
    private void helper(String current) {
        List<String> ref = new ArrayList<String>();
        for (Rotor rtr : allrotors) {
            if (rtr instanceof Reflector) {
                ref.add(rtr.name());
            }
        }
        String[] asarray = ref.toArray(new String[ref.size()]);
        boolean flag = Arrays.stream(asarray).anyMatch(current::equals);
        if (!flag) {
            throw error("First rotor must be a reflector.");
        }
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        if (msg.isEmpty()) {
            _output.println();
        }
        String[] temporary = msg.split("(?<=\\G.{5})");
        String decoded = String.join(" ", temporary);
        _output.print(decoded);
    }
}
