package Percolation;

import Percolation.Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private Percolation pr;
    private double[] thresholds;
    private int n, trials;
    private final static double CONFIDENCE_95 = 1.96;

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
            while(!pr.percolates())
            {
                
                row = StdRandom.uniformInt(n) + 1;
                col = StdRandom.uniformInt(n) + 1;               
                pr.open(row, col);
            }
           
            thresholds[i]=  ((double) pr.openSites) / (n*n);
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
        return (mean() - ((CONFIDENCE_95*stddev()) / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return (mean() + ((CONFIDENCE_95*stddev()) / Math.sqrt(trials)));
    }

    public double readThreshold(int i)
    {
        return this.thresholds[i];
    }

}