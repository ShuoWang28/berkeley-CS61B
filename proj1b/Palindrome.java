/** Palindrome class.
 * @author Shuo Wang
 */
public class Palindrome {
    /** Given a String, wordToDeque should return a Deque
     * where the characters appear in the same order as in the String.
     * @param word String
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> w = new LinkedListDeque<Character>();
        char t = '\0';
        for (int i = 0; i < word.length(); i++) {
            t = word.charAt(i);
            w.addLast(t);
        }
        return w;
    }

    /** return true if the given word is a palindrome, and false otherwise.
     * @param word String
     */
    public boolean isPalindrome(String word) {
        Deque<Character> T = wordToDeque(word);
        if (T.size() <= 1) {
            return true;
        }
        if (T.removeFirst() != T.removeLast()) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }

    /**The method will return true if the word is a palindrome according to
     * the character comparison test provided by the CharacterComparator
     * passed in as argument cc.
     * @param word String
     * @param cc CharacterComparator
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> T = wordToDeque(word);
        return isPlinddromehelper(T, cc);
    }

    /** private helper function for isPalindrome(word, cc) */
    private boolean isPlinddromehelper(Deque<Character> T, CharacterComparator cc) {
        if(T.size() <= 1){
            return true;
        }
        if (cc.equalChars(T.removeFirst(), T.removeLast()) == false) {
            return false;
        }
        return isPlinddromehelper(T, cc);
    }
}
