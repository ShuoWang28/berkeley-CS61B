import static org.junit.Assert.*;

import jh61b.grader.GradedTest;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/** Test for ArrayDeqye.
 * @auther Shuo Wang
 */
public class TestArrayDequeGold {
    /** randomly call StudentArrayDeque and ArrayDequeSolution methods
     * until they disagree on an output.
     * @source StudentArrayDequeLauncher
     */
    ArrayDequeSolution<String> message = new ArrayDequeSolution<String>();

    /** Private method for message.
     * Only three most recent message being recorded */
    private String toStringMessage(ArrayDequeSolution<String> T){
        String errorMessage = "";
;        for (int i = 0; i < 3; i++) {
            errorMessage = T.removeLast() + "\n" + errorMessage;
        }
        return "\n" + errorMessage;
    }

    @Test
    public void testArrayDeque(){
        ArrayDequeSolution<Integer> a = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> b = new StudentArrayDeque<Integer>();

        for (int i = 0; i < 100; i++)  {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.3) {
                message.addLast("addLast(" + i + ")");
                a.addLast(i);
                b.addLast(i);
            } else if (0.3 <= numberBetweenZeroAndOne && numberBetweenZeroAndOne < 0.6) {
                message.addLast("addFirst(" + i + ")");
                a.addFirst(i);
                b.addFirst(i);
            } else if (0.6 <= numberBetweenZeroAndOne && numberBetweenZeroAndOne < 0.8) {
                message.addLast("removeFirst()");
                assertEquals(toStringMessage(message), a.removeFirst(), b.removeFirst());
            } else {
                message.addLast("removeLast()");
                assertEquals(toStringMessage(message), a.removeLast(), b.removeLast());
            }
        }
    }
}
