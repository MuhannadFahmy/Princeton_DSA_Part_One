package deque;

// import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<item> implements Iterable<Item> {

    private class Node {
        Item value;
        Node next;
    }

    private Node first;
    private Node last;
    private int dequeSize;
    private Node lastButOne;    // how would you keep track of this 


    public Deque() {
        first = null;
        last = null;
        lastButOne = null;
        dequeSize = 0;
    }
    
    public boolean isEmpty() {
        return (first == null);
    }

    public int size() {
        return dequeSize;
    }
    
    public void addFirst(Item item) { 
        if (item == null) {
             throw new java.lang.IllegalArgumentException();
        }

        Node oldFirst = first;
        
        first = new Node();

        if (oldFirst == null) { // one item
            last = first;
        } else if (oldFirst == last) { // two item
            lastButOne = first;
        } else {    // more than 2
            first.value = item;
            first.next = oldFirst;
        }
        dequeSize++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        
        if (first == null) { // if its empty
            addFirst(item);
        } else if (first == last) { // if it has one element
            last= new Node();
            last.value = item;
            last.next = null;
            first.next = last;
            lastButOne = first;
            dequeSize++;
        } else {
            // if it has more than one
            Node oldLast = last;
            last= new Node();
            last.value = item;
            last.next = null;
            oldLast.next = last;
            lastButOne = oldLast;
            dequeSize++;
        }
    }

    public Item removeFirst() {
        if (first == null) { // zero items
            throw new java.util.NoSuchElementException();
        }
        Item returnValue = first.value;
        if (first == last) {
            first = first.next;
            last = last.next;   
        } else if (lastButOne == first) {
            lastButOne = null;
            first = last;
        } else {
            first = first.next;
        }
        dequeSize--;
        return (returnValue);
    }

    public Item removeLast() {
        if (first == null) { // zero items
            throw new java.util.NoSuchElementException();
        } 
        Item returnValue = last.value;
        if (first == last) { // one item
            first = first.next;
            last = last.next;
        } else if (lastButOne == first) { // two items
            lastButOne = null;
            last = first;
            first.next = null;
        } else if (first.next.next.next == null) {
            last = lastButOne;
            lastButOne = first;
            last.next = null;
        } else {
            Node indexNode = first;
            while(indexNode.next.next.next != null)
            {
                indexNode = indexNode.next;
            }
            lastButOne = indexNode;
            last = lastButOne.next;
            last.next = null; 
        }
        dequeSize--;
        return(returnValue);
    }

    @Override // what does that do 
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<>() {
            private Node indexNode = first;

            @Override
            public boolean hasNext() {
                return (indexNode.next != null);
            }

            @Override
            public Item next() {
                indexNode = indexNode.next;
                return (indexNode.value);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

}
