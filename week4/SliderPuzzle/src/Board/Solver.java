package Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
    private ArrayList<Board> solution = new ArrayList<>();
    
    private MinPQ<Node> treeManhattan;
    private MinPQ<Node> treeManhattanTwin;

    private int moves = 0;
 

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("The board entered is null.");
        }

     solve(initial);   
    }

    private void solve(Board inital) {
        // change manhattan priority com to use node 
        treeManhattan = new MinPQ<>(manhattanPriorityCom);
        treeManhattanTwin = new MinPQ<>(manhattanPriorityCom);

        Node currentNode = new Node(inital, null, moves);
        Node currentNodeTwin = new Node(inital.twin(), null, moves);

        treeManhattan.insert(currentNode);
        treeManhattanTwin.insert(currentNodeTwin);

        while (!currentNode.board.isGoal() && !currentNodeTwin.board.isGoal()) {
            currentNode = treeManhattan.delMin();
            for(Board each : currentNode.board.neighbours()) {
                if(currentNode.previousNode == null || !currentNode.previousNode.board.equals(each)) {
                    treeManhattan.insert(new Node(each, currentNode, currentNode.moves + 1));
                }
            }

            currentNodeTwin = treeManhattanTwin.delMin();
            for(Board each : currentNodeTwin.board.neighbours()) {
                if(currentNodeTwin.previousNode == null || !currentNodeTwin.previousNode.board.equals(each)) {
                    treeManhattanTwin.insert(new Node(each, currentNodeTwin, currentNodeTwin.moves + 1));
                }
            }
        }
        // System.out.println("is twin solvable "+currentNodeTwin.board.isGoal());

        if (currentNode.board.isGoal()) {
            while (currentNode != null) {
                solution.add(currentNode.board);
                currentNode = currentNode.previousNode;
            }
            Collections.reverse(solution);
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !(solution.isEmpty());
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()  {
        // if (!(isSolvable())) { return -1;}
        // return solution.size();
        return solution.size()-1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!(isSolvable())) { return null; }

        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {

                return new Iterator<Board>() {
                    int index = 0;

                    @Override
                    public boolean hasNext() {
                        return (index < solution.size());
                    }

                    @Override
                    public Board next() {
                        if (hasNext()) {
                            return solution.get(index++);
                        } else {
                            throw new NoSuchElementException("There is no next solution");
                        }
                    }
                };
            }
            
        };

    }

    // Second layer
    private class Node {
    
        private final Board board;
        private final Node previousNode;
        private int moves;
        private int manhattan;
        private int priority;

        private Node(Board board, Node previousNode, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = moves;
            this.manhattan = board.manhattan();
            this.priority = this.moves + this.manhattan;
        }
        
    }

    //Hamming Comparator
     private Comparator<Node> hammingPriorityCom = new Comparator<Node>() {
        //compare hamming, I might need to add moves here!
        public int compare(Node first, Node second) {
            if (first.board.hamming()+first.moves > second.board.hamming()+second.moves ) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    //Manhattan Comparator
    // private Comparator<Board> manhattanPriorityCom = (first, second) -> first.manhattan()+first.moves > second.manhattan()+second.moves? 1 : -1;
    
    private Comparator<Node> manhattanPriorityCom = (first, second) -> first.priority > second.priority? 1 : -1;



    // test client (see below) 
    public static void main(String[] args) {
    
        int[][] tilesExample2 = {{1, 8, 7}, {5, 2, 0}, {6, 3, 4}};
        int[][] tilesExample5 = {{1, 7, 0}, {8, 5, 2}, {3, 6, 4}};
        int[][] tilesExample3 = {{4, 5, 3}, {7, 2, 1}, {0, 6, 8}};
        int[][] tilesExample  = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
        int[][] tilesExample6 = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        int[][] tilesExample4 = {{5, 2, 1}, {0, 3, 4}, {8, 7, 6}};

        // System.out.println("x: "+tilesExample.length+" y: "+tilesExample[0].length);
        Board b = new Board(tilesExample);
        Board b2 = new Board(tilesExample6);
        // System.out.println("here");
        System.out.println(b2.toString());
        System.out.println("Hamming Is " + b2.hamming());
        System.out.println("Manhattam Is " + b2.manhattan());
        
        System.out.println("Is goal: "+b2.isGoal());

        Solver solver = new Solver(b2);

        System.out.println("Solution array size:"+solver.solution.size());
        
        int step = 1;
        for (Board solution : solver.solution()) {
            
            System.out.println("Step number " + step++);
            System.out.println(solution);
        }

        //
        //

        //
        //

        //
        //

        //
        //

        //
        //
    }

}
