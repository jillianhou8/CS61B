/**
 * Created by joshuazeitsoff on 9/2/17.
 */

public class BuggyIntDListSolution extends IntDList {

    public BuggyIntDListSolution(Integer... values) {
        super(values);
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

    public String getException() {
        //hint : this is what comes after the "java.lang" at the top of the stack trace
        return "NullPointerException";
    }

    public String getErrorFunction() {
        // hint: this is the first function name that you see
        // when reading the stack trace from top to bottom
        return "BuggyIntDList.insertback";
    }

    public int getErrorLineNumber() {
        // PUT ERROR LINE NUMBER HERE
        // hint: this is the number that comes after whichever function the
        // error is occurring in.
        return 17;
    }
}
