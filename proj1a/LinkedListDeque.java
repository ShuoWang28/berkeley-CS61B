public class LinkedListDeque <T>{

    public class GenNode {
        public T item;
        public GenNode next;
        public GenNode prev;

        public GenNode(T i, GenNode n, GenNode m) {
            item = i;
            next = n;
            prev = m;
        }
    }

    public int size;
    public GenNode sentinel;

    /** Creates an empty linked list deque */
    public LinkedListDeque(){
        sentinel = new GenNode(null, sentinel, sentinel);
        size = 0;
    }

    /** AddFirst method. Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        size = size + 1;
        sentinel.next = new GenNode(item, sentinel, sentinel.next);
    }

    /** Adds an item of type T to the back of the deque.*/
    public void addLast(T item){
        size = size + 1;
        sentinel.prev = new GenNode(item, sentinel, sentinel.prev);
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return sentinel.next == sentinel.prev;
    }

    /** Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        GenNode p = sentinel;
        while(p.next != sentinel){
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst(){
        if(this.isEmpty()){
            return null;
        }
        size = size - 1;
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast(){
        if(this.isEmpty()){
            return null;
        }
        size = size - 1;
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return last;
    }

    //* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    // If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if(this.isEmpty()) {
            return null;
        }
        int i = 0;
        GenNode p = sentinel.next;
        while (i < index) {
            if (p.next == sentinel) {
                return null;
            }
            i++;
            p = p.next;
        }
        return p.item;
    }

    //*Same as get, but uses recursion. */
    public T getRecursive(int index){
        if(this.isEmpty()) {
            return null;
        }
        GenNode p = sentinel;
        return getRecursivehelper(p.next, index);
    }

    //* helper function for getRecursive */
    private T getRecursivehelper(GenNode p, int index){
        if(p == sentinel){
            return null;
        }
        if(index == 0){
            return p.item;
        }
        return getRecursivehelper(p.next, index - 1);
    }

}
