package enigma;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.ArrayList;


/**
 * Some extra tests for Enigma.
 *
 * @author Jillian Hou
 */
public class MachineTest {

    @Test
    public void testInvertChar() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) (JC)",
                new CharacterRange('A', 'Z'));
        assertEquals(p.invert('B'), 'A');
        assertEquals(p.invert('G'), 'G');
        assertEquals(p.invert('C'), 'J');
    }

    @Test
    public void testPermuteChar() {
        Permutation p = new Permutation("(PNH) (ABDFIKLZYXW) (JC)",
                new CharacterRange('A', 'Z'));
        assertEquals(p.permute('A'), 'B');
        assertEquals(p.permute('F'), 'I');
        assertEquals(p.permute('W'), 'A');
    }

    @Test
    public void testDerangement() {
        Permutation a = new Permutation("(AELTPHQXRU) (BKNWS) (CMOY) "
                + "(DFG) (IV) (JZ)", new CharacterRange('A', 'Z'));
        assertEquals(a.derangement(), true);
        Permutation b = new Permutation("(AELTPHQXRU) (BKNW) "
                + "(CMOY) (DFG) (IV) (JZ) (S)", new CharacterRange('A', 'Z'));
        assertEquals(b.derangement(), false);
    }

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1",
                new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2",
                new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3",
                new Permutation("(ABCD)", ac), "C");
        Rotor four = new MovingRotor("R4",
                new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);

        assertEquals("AAAA", getSetting(ac, machineRotors));
        mach.convert('a');
        assertEquals("AAAB", getSetting(ac, machineRotors));
    }

    /**
     * Helper method to get the String
     * representation of the current Rotor settings
     */
    private String getSetting(Alphabet alph, Rotor[] machineRotors) {
        String currSetting = "";
        for (Rotor r : machineRotors) {
            currSetting += alph.toChar(r.setting());
        }
        return currSetting;
    }

    @Test
    public void mySteppingTest1() {
        Alphabet ac = new CharacterRange('A', 'Y');
        Rotor one = new Reflector("Reflector",
                new Permutation("(KF) (AJ)", ac));
        Rotor two = new MovingRotor("Rotor 2",
                new Permutation("(EILK)", ac), "W");
        Rotor three = new MovingRotor("Rotor 3",
                new Permutation("(ABCD)", ac), "B");
        Rotor four = new MovingRotor("Fast Rotor",
                new Permutation("(QRST)", ac), "B");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"Reflector", "Rotor 2", "Rotor 3", "Fast Rotor"};
        Machine mach = new Machine(ac, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);

        mach.convert('q');
        assertEquals("AAAB", getSetting(ac, machineRotors));
        mach.convert('R');
        assertEquals("AABC", getSetting(ac, machineRotors));
        mach.convert('s');
        assertEquals("ABCD", getSetting(ac, machineRotors));
    }


    @Test
    public void mySteppingTest2() {
        Alphabet bp = new CharacterRange('B', 'P');
        Rotor one = new Reflector("Reflector",
                new Permutation("(CDEFG)", bp));
        Rotor two = new MovingRotor("Rotor 2",
                new Permutation("(BCP) (HIJKLMNO)", bp), "H");
        Rotor three = new MovingRotor("Rotor 3",
                new Permutation("(BCD)", bp), "B");
        Rotor four = new MovingRotor("Fast Rotor",
                new Permutation("(LKJH)", bp), "C");
        String setting = "BBB";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"Reflector", "Rotor 2", "Rotor 3", "Fast Rotor"};
        Machine mach = new Machine(bp, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);


        mach.convert('L');
        assertEquals("BCCC", getSetting(bp, machineRotors));
        mach.convert('J');
        assertEquals("BCDD", getSetting(bp, machineRotors));
        mach.convert('k');
        assertEquals("BCDE", getSetting(bp, machineRotors));
    }
}








