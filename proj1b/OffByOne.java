/** OffByOne public class.
 * @author Shuo Wang
 */
public class OffByOne implements CharacterComparator {
    @Override
    /** check if x and y are off by one
     * @param x char
     * @param y char
     */
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
