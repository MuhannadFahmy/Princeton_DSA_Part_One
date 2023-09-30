package deque;

import java.util.Deque;

public class deque<item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;

    private class Node
    {
        Item value;
        Node next;
    }

    private int dequeSize = 0;

    public Deque() {}
    
    public boolean isEmpty() 
    {
        return (first == null);
    }

    public int size() 
    {
        return dequeSize;
    }

    public void addFirst(Item item) {

         
        Node oldFirst = first;
        first = new Node();
        first.value = item;
        first.next = oldFirst;

        dequeSize+=1;
    }

    public void addLast(Item item) {
        dequeSize+=1;
    }

    public Item removeFirst() {
        Item returnValue = first;
        first = first.next;
        dequeSize-=1;
        return (returnValue);
    }

    public Item removeLast() {
        dequeSize-=1;
    }

    public Iterator<Item> iterator() {}

}
