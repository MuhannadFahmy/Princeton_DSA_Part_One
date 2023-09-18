
import Percolation.Percolation;
import Percolation.PercolationStats;
import Percolation.PercolationVisualizer;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;



public class App {

    private static final int DELAY = 700;
    
    public static void main(String[] args) {
        System.out.println("Hello, World!");


        // PercolationStats prStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        // PercolationStats prStats = new PercolationStats(2, 100);
        // // 3for (int i = 0; i < 3; i++)
        // //     System.out.println( "threshold "+ i + "is" + prStats.readThreshold(i));    
        
        // System.out.println("mean\t= " + prStats.mean());
        // System.out.println("stddev\t= " + prStats.stddev());
        // System.out.println("95% confidence interval\t= [" + prStats.confidenceLo() + ", " + prStats.confidenceHi() + "]");
        
        int N = StdIn.readInt();         // N-by-N percolation system

        int row, col;
        // turn on animation mode
        StdDraw.show(0);

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        PercolationVisualizer percVis = new PercolationVisualizer();
        percVis.draw(perc, N);
        StdDraw.show(DELAY);
        while (!perc.percolates()) {
            // int i = StdIn.readInt();
            // int j = StdIn.readInt();
            row = StdRandom.uniformInt(N) + 1;
            col = StdRandom.uniformInt(N) + 1;
            System.out.println("Row: " + row + ", Col: "+ col);
            perc.open(row, col);
            percVis.draw(perc, N);
            StdDraw.show(DELAY);
        }
    }
}
