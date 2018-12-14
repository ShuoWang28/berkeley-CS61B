/** Double Ended Array list Implementation.
 * @author Shuo Wang */
public class ArrayDeque<T> {
    /**ArrayDeque Implementation. */
    private int size;
    /** size. */
    private int nextFirst;
    /** nextFirst. */
    private int nextLast;
    /** nextLast. */
    private T[] items;
    /** Array items[]. */

    /** Creates an empty array deque. */
    public ArrayDeque() {
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        items = (T[]) new Object[8];
    }

    /** Adds an item of type T to the front of the deque.
     * @param item T
     * */
    public void addFirst(T item) {
        size = size + 1;
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        if (nextFirst - 1 >= 0) {
            nextFirst--;
        } else {
            nextFirst = items.length - 1;
        }
    }

    /** Adds an item of type T to the back of the deque.
     * @param item T
     * */
    public void addLast(T item) {
        size++;
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        if (nextLast + 1 < items.length) {
            nextLast++;
        } else {
            nextLast = 0;
        }
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by
     * a space. */
    public void printDeque() {
        if (nextLast > nextFirst) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
            System.out.println();
            return;
        }
        for (int i = nextFirst + 1; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        for (int i = 0; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists,
     * returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T val = get(0);
        size--;
        int first = nextFirst + 1;
        if (first >= items.length) {
            first = 0;
        }
        items[first] = null;
        nextFirst = first;
        if ((float) size / items.length < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        return val;
    }

    /** Removes and returns the item at the last of the deque.
     * If no such item exists,
     * returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T val = get(size - 1);
        size--;
        int last = nextLast - 1;
        if (last < 0) {
            last = items.length - 1;
        }
        items[last] = null;
        nextLast = last;
        if ((float) size / items.length < 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        return val;
    }
    /** Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * @param index integer
    /** If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        int i = nextFirst + 1 + index;
        if (i > items.length - 1) {
            i = i - items.length;
        }
        return items[i];
    }

    /** Resize the underlying array to the target capacity.
     * @param capacity int
     * */
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        if (capacity > items.length) {
            System.arraycopy(items, nextFirst + 1, temp, 1, size - 1 - nextFirst);
            System.arraycopy(items, 0, temp, size - nextFirst, nextLast);
            nextLast = size;
        } else {
            if (nextLast > nextFirst) {
                 System.arraycopy(items, nextFirst + 1, temp, 1, size);
            } else {
                 System.arraycopy(items, nextFirst + 1, temp, 1, items.length - 1 - nextFirst);
                 System.arraycopy(items, 0, temp, items.length - nextFirst, nextLast );
            }
            nextLast = size + 1;
        }
        nextFirst = 0;
        items = temp;
    }
}
