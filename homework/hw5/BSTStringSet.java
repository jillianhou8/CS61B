import java.util.List;

/**
 * Implementation of a BST based String Set.
 * @author
 */
public class BSTStringSet implements StringSet {
    /** Creates a new empty set. */
    public BSTStringSet() {
        root = null;
    }

    @Override
    public void put(String s) {
        if (root == null) {
            root = new Node(s);
        } else {
            if (s.compareTo(root.s) > 0) {
                root = root.right;
                put(s);
            } else if (s.compareTo(root.s) < 0) {
                root = root.left;
                put(s);
            }
        }
    }

    @Override
    public boolean contains(String s) {
        if (s.compareTo(root.s) < 0) {
            root = root.left;
            contains(s);
        }
        else if (s.compareTo(root.s) > 0) {
            root = root.right;
            contains(s);
        }
        else if (s.compareTo(root.s) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<String> asList() {
        return null;
    }

    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** Root node of the tree. */
    private Node root;
}
