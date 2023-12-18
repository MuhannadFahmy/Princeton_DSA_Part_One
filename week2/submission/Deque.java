import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T something) {
            this.value = something;
        }
    }

    // intialization
    private Node<Item> first = null;
    private Node<Item> last = null;
    private int dequeSize = 0;

    // private Node lastButOne;    // how would you keep track of this 


    public Deque() { }
    
    public boolean isEmpty() {
        // return (first == null);  I need to se the last delete to make first == null
        return (dequeSize == 0);
    }

    public int size() {
        return dequeSize;
    }

    public void addFirst(Item item) { 
        if (item == null) {
             throw new java.lang.IllegalArgumentException();
        }

        Node<Item> oldFirst = first;
        
        first = new Node<Item>(item);
        if (dequeSize == 0) { // one/first item
            last = first;

            // assign the value
            // first.next = null;
            // first.prev = null;
        } else { // 2+ items
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        dequeSize++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        Node<Item> oldLast = last;
        last = new Node<Item>(item);

        if (dequeSize == 0) { // if its empty
            first = last; 
        } else { // 2+ items
            oldLast.next = last;
            last.prev = oldLast;
        } 
        dequeSize++;
    }

    public Item removeFirst() {
        if (dequeSize == 0) { // zero items
            throw new java.util.NoSuchElementException();
        }
        
        Item returnValue = first.value;
        first = first.next;

        if (dequeSize == 1) {
            // first = null;
            last = null;   
        } else {
            first.prev = null;
        }
        dequeSize--;
        return (returnValue);
    }

    public Item removeLast() {
        if (dequeSize == 0) { // zero items
            throw new java.util.NoSuchElementException();
        } 

        Item returnValue = last.value;
        last = last.prev;

        if (dequeSize == 1) { // one item
            first = null;
        } else { // two items
            last.next = null;
        }
        
        dequeSize--;
        return(returnValue);
    }

    private void print() {
        System.out.print("[");
        for (Item i : this) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
        System.out.println("size: " + size());
        System.out.println(isEmpty());
    }

    @Override // what does that do 
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<>() {
            private Node<Item> indexNode = first;

            @Override
            public boolean hasNext() {
                return (indexNode != null);
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("no more elements");
                }
                Item currentItem = indexNode.value;
                indexNode = indexNode.next;
                return (currentItem);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public static void main(String[] args) {
    }

}
