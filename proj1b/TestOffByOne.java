import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalchars() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('a', 'b'));
        assertFalse(obo.equalChars('a', 'h'));
        assertFalse(obo.equalChars('A', 'h'));
        assertTrue(obo.equalChars('&', '%'));
    }

    @Test
    public void testNequalchars() {
        OffByN ob2 = new OffByN(2);
        assertTrue(ob2.equalChars('a', 'c'));
        assertFalse(ob2.equalChars('a', 'd'));
        assertFalse(ob2.equalChars('A', 'd'));
        assertTrue(ob2.equalChars('&', '$'));
    }
}
