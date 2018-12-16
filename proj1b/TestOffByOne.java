import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalchars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('a', 'h'));
        assertFalse(offByOne.equalChars('A', 'b'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('@', '%'));
    }
}
