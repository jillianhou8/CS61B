// REPLACE THIS STUB WITH THE CORRECT SOLUTION.
// The current contents of this file are merely to allow things to compile
// out of the box.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
/** A set of String values.
 *  @author Jillian Hou
 */
public class ECHashStringSet implements StringSet {
    //List<LinkedList> bins;
    LinkedList[] binny;
    int size;
    float loadFactor = 5;
    List<String> strings;


    public ECHashStringSet() {
        binny = new LinkedList[100];
        strings = new ArrayList<String>();
        size = 0;
        this.loadFactor = loadFactor;
    }

    public int hashmaybe(String s) {
        int h;
        h = 0;
        for (int p = 0; p < s.length (); p += 1) {
            h = (h << 4) + s.charAt(p);
            h = (h ^ ((h & 0xf0000000) >> 24)) & 0x0fffffff;
        }
        return h;
    }

    public int hash(String s) {
        return s.hashCode();
    }

    public void put(String s) {
        strings.add(s);
        size += 1;
        int h = hash(s);
        int index = h % binny.length;
        if (binny[index] == null) {
            binny[index] = new LinkedList();
        }
        if (binny[index].size() > loadFactor) {
            resize();
        }
        binny[index].add(h);
    }


    public void resize() {
        LinkedList[] saved = binny;
        binny = new LinkedList[binny.length * 2];
        for (String str : strings) {
            put(str);
        }
    }


    public boolean contains(String s) {
        int h = hash(s);
        int index = h % binny.length;
        if (binny[index].contains(h)) {
            return true;
        }
        return false;
    }

    public List<String> asList() {
        return strings;
    }

}
