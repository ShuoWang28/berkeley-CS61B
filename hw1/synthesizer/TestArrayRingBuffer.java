package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        int a = (int) arb.dequeue();
        assertEquals(a, 1);
        assertEquals(arb.dequeue(), 2);
        arb.enqueue(5);
        assertEquals(arb.peek(), 3);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
