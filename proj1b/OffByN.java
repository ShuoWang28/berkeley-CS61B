/** OffByN public class.
 * @author Shuo Wang
 */
public class OffByN implements CharacterComparator {
    /** differnt */
    private int diff;

    /** Constructor for OffByN */
    public OffByN(int N){
        diff = N;
    }
    @Override
    /** check if x and y are off by one
     * @param x char
     * @param y char
     */
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == diff;
    }
}
