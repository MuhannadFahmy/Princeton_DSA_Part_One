package Deque;

// import java.util.Deque;
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

    public void print() {
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
        Deque<Integer> que = new Deque<>();
        que.addFirst(1);
        que.print();
        que.removeFirst();
        que.print();

        que.addLast(2);
        que.print();
        que.removeLast();
        que.print();

        que.addFirst(3);
        que.print();
        que.removeLast();
        que.print();

        que.addLast(4);
        que.print();
        que.removeFirst();
        que.print();


        System.out.println("size: " + que.size());
        System.out.println(que.isEmpty());
        que.addFirst(2);
        que.addFirst(3);
        que.addFirst(4);
        que.addFirst(5);
        que.addLast(6);
        que.addLast(7);
        que.addLast(8);
        que.addLast(9);
        que.addLast(10);
        que.addFirst(0);
        que.print();
        System.out.println("size: " + que.size());

        System.out.println(que.removeFirst());
        System.out.println(que.removeFirst());
        System.out.println(que.removeFirst());
        que.print();
        System.out.println("size: " + que.size());

        System.out.println(que.removeLast());
        System.out.println(que.removeLast());
        System.out.println(que.removeLast());
        que.print();
        System.out.println("size: " + que.size());
    }

}
