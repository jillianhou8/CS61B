
public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     *
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     *
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     *
     * @return The number of elements in this list.
     */
    public int size() {
        int count = 0;
        if (_front == null) {
            return 0;
        } else {
            DNode curr = _front;
            while (curr != null) {
                count++;
                curr = curr._next;
            }
        }
        return count;
    }



    /**
     *
     * @param i index of element to return, where i = 0 returns the first element,
     *          i = 1 returns the second element, i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *          for positive indices and -size <= i < 0 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        if (i >= 0) {
            DNode curr = _front;
            int count = 0;
            while (curr != null) {
                if (count == i) {
                    return curr._val;
                }
                count++;
                curr = curr._next;
            }
        }
        DNode curr = _back;
        int count = -1;
        while (curr != null) {
            if (count == i) {
                return curr._val;
            }
            count--;
            curr = curr._prev;
        }
        return 987;
    }

    /**
     *
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        DNode newie = new DNode(null, d, null);
        if (_back == null) {
            _front = _back = newie;
        } else {
            _front._prev = newie;
            newie._next = _front;
            _front = newie;
        }
    }


    /**
     *
      * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        DNode newie = new DNode(null, d, null);
        if (_front == null) {
            _front = _back = newie;
        } else {
            _back._next = newie;
            newie._prev = _back;
            _back = newie;
        }
    }


    /**
     * Removes the last item in the IntDList and returns it
     * @return the item that was deleted
     */
    public int deleteBack() {
        if (_front == _back) {
            int lastval = _back._val;
            _front = _back = null;
            return lastval;
        } else {
            DNode curr = _front;
            while (curr != _back) {
                curr = curr._next;
            }
            int lastval = curr._val;
            _back = curr._prev;
            _back._next = null;
            return lastval;
        }
    }


    /**
     *
     * @return a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc.
     *  Hint:
     *  String a = "a";
     *  a += "b";
     *  System.out.println(a); //prints ab
     */
    public String toString() {
        DNode curr = _front;
        String rep = "[";
        while (curr != null) {
            if (curr == _front) {
                rep += curr._val;
                curr = curr._next;
            }
            rep += ", " + curr._val;
            curr = curr._next;
        }
        rep += "]";
        return rep;
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        protected DNode(int val) {
            this(null, val, null);
        }

        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
