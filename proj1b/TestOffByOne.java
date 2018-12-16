import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalchars() {
        OffByOne obo = new OffByOne();
        assertTrue(obo.equalChars('a', 'b'));
        assertTrue(obo.equalChars('b', 'a'));
        assertFalse(obo.equalChars('a', 'h'));
        assertFalse(obo.equalChars('A', 'b'));
        assertTrue(obo.equalChars('&', '%'));
        assertFalse(obo.equalChars('@', '%'));
    }
}
