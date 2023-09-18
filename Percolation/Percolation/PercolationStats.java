
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private Percolation pr;
    private double[] thresholds;
    private int n, trials;
    private final static double confidence_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();

        this.n = n;
        this.trials = trials;

        int row, col;
        thresholds = new double[trials];

        for (int i = 0; i < trials; i++)
        {
            pr = new Percolation(n);

            // int printouts = 1;
            while (!pr.percolates())
            {
                
                row = StdRandom.uniformInt(n) + 1;
                col = StdRandom.uniformInt(n) + 1;               
                pr.open(row, col);
            }
           
            thresholds[i] =  ((double) pr.numberOfOpenSites()) / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return (mean() - ((confidence_95*stddev()) / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return (mean() + ((confidence_95*stddev()) / Math.sqrt(trials)));
    }

    public static void main(String[] args)
    {
        int a, b;
        a = Integer.parseInt(args[0]);
        b = Integer.parseInt(args[1]);

        try {
            PercolationStats pp = new PercolationStats(a, b);
            StdOut.println("mean                    = " + pp.mean());
            StdOut.println("stddev                  = " + pp.stddev());
            StdOut.println("95% confidence interval = [" + pp.confidenceLo() + ", " + pp.confidenceHi() + "]");
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

}