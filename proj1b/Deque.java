/** Deque interface Implementation
 * @author Shuo Wang
 */
public interface Deque<T> {
    /** methods for Deque */
    public void addFirst(T item);
    public void addLast(T item);
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);

}
