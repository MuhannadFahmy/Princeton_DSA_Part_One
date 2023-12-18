import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int listSize = 1;
    private int randQueSize = 0;


    /** construct an empty randomized queue */
    public RandomizedQueue() {
        items = (Item[]) new Object[listSize];
     }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (randQueSize == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return randQueSize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (randQueSize == listSize) {
            resizeList( listSize * 2);
        }

        items[randQueSize++] = item;       
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (randQueSize == 0) {
            throw new IllegalArgumentException();
        }

        int randIndex = StdRandom.uniformInt(randQueSize); //starts from 1 to the actual number
        Item randItem = items[randIndex];
        items[randIndex] = items[--randQueSize];
        items[randQueSize] = null;

        if(randQueSize <= (double)(listSize/4))
        {
            resizeList(listSize/2);
        }

        return randItem;
    }
    
    // return a random item (but do not remove it)
    public Item sample() {
        if (randQueSize == 0) {
            throw new IllegalArgumentException();
        }
        return (items[StdRandom.uniformInt(randQueSize)]) ;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new listIterator();
    }

    private void resizeList(int newSize) {
        Item[] newRandList = (Item[]) new Object[newSize];
        for (int i = 0; i < randQueSize; i++) {
            newRandList[i] = items[i];
        }
        items = newRandList;
        listSize = newSize;
    }

    private void print() {
        System.out.print("[");
        for (Item i : this) {
            System.out.print(i + ", ");
        }
        System.out.println("]");
    }

    private class listIterator implements Iterator<Item> {
        private int currentIndex = 0;
        private final Item[] iteratorItems;
        
        public listIterator() {
            iteratorItems = (Item[]) new Object[randQueSize];
            for (int i = 0; i < randQueSize; i++) {
                iteratorItems[i] = items[i];
            }
            StdRandom.shuffle(iteratorItems);
        }

        @Override
        public boolean hasNext() {
            return (currentIndex < randQueSize);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return iteratorItems[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
  
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
    
}
