public class ArrayDeque <T> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public T[] items;

    //* Creates an empty array deque. */
    public ArrayDeque(){
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        items = (T[]) new Object[8];
    }

    //* Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        size = size + 1;
        items[nextFirst] = item;
        if(nextFirst - 1 >= 0){
            nextFirst--;
            return;
        }
        nextFirst = items.length - 1;
        if (size == items.length) {
           resize(size * 2);
        }
    }

    //* Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        size++;
        items[nextLast] = item;
        if(nextLast + 1 < items.length){
            nextLast++;
            return;
        }
        nextLast = 0;
        if (size == items.length) {
            resize(size * 2);
        }
    }

    //* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    //* Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    //* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        if(nextLast > nextFirst){
            for(int i = nextFirst + 1; i < nextLast; i++){
                System.out.print(items[i] + " ");
            }
            return;
        }
        for(int i = nextFirst + 1; i < items.length; i++){
            System.out.print(items[i] + " ");
        }
        for(int i = 0; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
    }

    //* Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst(){
        size--;
        if(nextFirst == items.length){
            T val = items[0];
            items[0] = null;
            nextFirst = 0;
            return val;
        }
        T val = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst = nextFirst + 1;
        if(size/items.length < 0.25) {
            resize(items.length/2);
        }
        return val;
    }

    //* Removes and returns the item at the last of the deque. If no such item exists, returns null. */
    public T removeLast(){
        size--;
        if(nextLast == 0){
            T val = items[items.length];
            items[items.length] = null;
            nextLast = items.length;
            return val;
        }
        T val = items[nextLast - 1];
        items[nextLast - 1] = null;
        nextLast = nextLast - 1;
        if(size/items.length < 0.25) {
            resize(items.length/2);
        }
        return val;
    }
    //* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null. Must not alter the deque! */
    public T get(int index){
        if (nextLast > nextFirst) {
            return items[nextFirst + index - 1];
        }
        if (nextFirst + index < items.length) {
            return items[nextFirst + index];
        }
        return items[nextFirst + index - 1 - items.length];
    }

    /** Resize the underlying array to the target capacity */
    private void resize(int capacity){
        T[] a = (T[]) new Object[capacity];
        if (capacity > items.length) {
            System.arraycopy(items, nextFirst + 1, a, capacity/2,items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, capacity/2 + items.length - nextFirst - 1 , items.length - nextFirst);
            nextFirst = capacity/2 - 1;
            nextLast = 0;
            items = a;
            return;
        }
        else {
            if(nextLast > nextFirst || nextLast == 0){
                System.arraycopy(items, nextFirst + 1, a, capacity/2, size);
                nextFirst = capacity/2 - 1;
                nextLast = capacity;
                items = a;
                return;
            }
            else {
                System.arraycopy(items, 0, a, 0, nextLast);
                System.arraycopy(items, nextFirst + 1, a, capacity - size + nextLast, size - nextLast);
                nextFirst = nextFirst - capacity / 2;
                items = a;
                return;
            }
        }
    }
}
