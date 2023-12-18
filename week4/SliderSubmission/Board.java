import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private final int[][] tiles;
    private Board[] neighbours;
    private final int n;
    private final int maxInputNum;
    private int hamming, manhattan;
    private boolean hamCalculated = false, manhCalculated = false;

    public Board(int[][] tiles) {
        // Takes 2 < n < 128
        // n-by-n
        // input must be [0, n^2-1]
        
        if (tiles.length != tiles[0].length) {
            throw new IllegalArgumentException("Input board must be n-by-n, Dimention entered is wrong");
        } else {
            
            this.n = tiles.length;
            // System.err.println(Math.pow(this.n,2) - 1);
            this.maxInputNum = ((int) Math.pow(this.n,2) - 1);
        }
        
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] <= maxInputNum && tiles[i][j] >= 0) {
                    this.tiles[i][j] = tiles[i][j];
                } else {
                    throw new IllegalArgumentException("Input tiles value must be between [0," + maxInputNum + " ]");
                }
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        // return n size
        // return n-by-n string representation of the board
        String output = String.valueOf(this.n) + "\n";
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {        
                output = output + String.valueOf((this.tiles[i][j]) + " ");
            }
            output = output + "\n";
        }
        return output;
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    // the number of tiles that is not in the right order
    public int hamming() {
        if(hamCalculated) {
            return this.hamming;
        }

        hamming = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int currentInt = j + (this.n * i) + 1;
                if (this.tiles[i][j] != currentInt && this.tiles[i][j] != 0) {
                    hamming++;
                }
            } 
        }
        hamCalculated = true;
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        //sum how many moves for each tile between its current position and the ideal position
        if(manhCalculated) {
            return this.manhattan;
        }

        manhattan = 0;
        int xGoal,yGoal;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int expectedValue = j + (this.n * i) + 1;
                if (this.tiles[i][j] != expectedValue && this.tiles[i][j] != 0) {
                    int currentValue = tiles[i][j];
                    currentValue--;
                    xGoal = currentValue % this.n;
                    yGoal = currentValue / this.n;
                    manhattan += Math.abs(xGoal - j) + Math.abs(yGoal - i);
                }
            }
        }

        manhCalculated = true;
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // is the board solved
        return (this.hamming() == 0);  
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        // they are equal if and only if 
        // 1. they have the same size
        // 2. if their corresponding tiles are in the same positions
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board compareBoard = (Board) y;
        
        if (compareBoard.dimension() != this.n) return false;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.tiles[i][j] != compareBoard.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighbouring boards
    public Iterable<Board> neighbors() {
        // return and iterable containing all the neighbouring boards
        // depending on the location of the blank squre
        // return 4 boards max;
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {

                if (neighbours == null) {
                    findNeighbours();
                }

                return new Iterator<Board>() {
                    int index = 0;

                    @Override
                    public boolean hasNext() {

                        return (index < neighbours.length);
                    }
                    
                    @Override
                    public Board next() {
                        if (hasNext()) {
                            return neighbours[index++];
                        } else {
                            throw new NoSuchElementException("There is no next neighbour");
                        }
                    }
                
                };
            }
        };
    }

    private void exchangTiles(Board selecteBoard, int zeroX, int zeroY, int exchangeX, int exchangeY) {
        int placeHolder = selecteBoard.tiles[exchangeY][exchangeX];
        selecteBoard.tiles[exchangeY][exchangeX] = 0;  
        selecteBoard.tiles[zeroY][zeroX] = placeHolder;
    }

    private void findNeighbours () {
        List<Board> foundNeighbours = new ArrayList<>();
        int zeroX = 0, zeroY = 0;
        boolean found = false;
        for (int i = 0; i < this.n && !found; i++ ) {
            for (int j = 0; j < this.n && !found; j++) {
                if (this.tiles[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;
                    found = true;
                }
            }
        }

        if (zeroY > 0) {
            Board topNeighbour = new Board(this.tiles);
            exchangTiles(topNeighbour, zeroX, zeroY, zeroX, zeroY-1);
            foundNeighbours.add(topNeighbour);
        }

         if (zeroY < n - 1) {
            Board bottomNeighbour = new Board(this.tiles);
            exchangTiles(bottomNeighbour, zeroX, zeroY, zeroX, zeroY+1);
            
            foundNeighbours.add(bottomNeighbour);
        }

         if (zeroX > 0) {
            Board leftNeighbour = new Board(this.tiles);
            exchangTiles(leftNeighbour, zeroX, zeroY, zeroX-1, zeroY);
            
            foundNeighbours.add(leftNeighbour);
        }

         if (zeroX < n - 1) {
            Board rightNeighbour = new Board(this.tiles);
            exchangTiles(rightNeighbour, zeroX, zeroY, zeroX+1, zeroY);
            
            foundNeighbours.add(rightNeighbour);
        }

        neighbours = foundNeighbours.toArray(new Board[foundNeighbours.size()]);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(tiles);
        
        int i = 0;
        int j = 0;
        while (twinBoard.tiles[i][j] == 0 || twinBoard.tiles[i][j + 1] == 0) {
            j++;
            if (j >= n - 1) {
                i++;
                j = 0;
            }
        }
    
        exchangTiles(twinBoard, j, i, j+1, i);
    
        return twinBoard;
    }
    
    
    
    // unit testing (not graded)
    public static void main(String[] args) {
        // do a unit test for each method
        int[][] tilesExample2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int[][] tilesExample = {{6,3,0},{8,1,4},{7,5,2}};
        // System.out.println("x: "+tilesExample.length+" y: "+tilesExample[0].length);
        Board b = new Board(tilesExample);
        Board b2 = new Board(tilesExample2);
        // System.out.println("here");
        System.out.println("Board 1 :");
        System.out.println(b.toString());

        System.out.println("Hamming Is " + b.hamming());
        System.out.println("Manhattam Is " + b.manhattan());

        System.out.println("Board 2 :");
        System.out.println(b2.toString());
        System.out.println("Hamming Is " + b.hamming());
        System.out.println("Manhattam Is " + b.manhattan());
    
        System.out.println("Is Board 1 == Board 2 " + b.equals(b2));
        

    //     for (Board neig: b.neighbours()) {
    //         System.out.println(neig.toString());
    //     }

    //     System.out.println("ex2: ");
    //     System.out.println(b2.toString());
    //     System.out.println("Hamming Is " + b2.hamming());
    //     System.out.println("Manhattam Is " + b2.manhattan());
    
    //     for (Board neig: b2.neighbours()) {
    //         System.out.println(neig.toString());
    //     }
    }
   
}
