
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class App {

    private WeightedQuickUnionUF uf;

    private boolean[][] site; 

    int openSites;

    int n;

    int bottomInd;

    private int tdToOd(int row, int col)
    {
        return((row-1) * n + col); 
    }

    private void validSite(int row, int col)
    {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new java.lang.IllegalArgumentException();
    }

    private boolean connected(int p, int q) 
    {
        return uf.find(p) == uf.find(q);
    }

    public void Percolation(int n)
    {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();

        uf = new WeightedQuickUnionUF((n*n) + 2);
        site = new boolean[n][n];
        openSites = 0;
        bottomInd = (n * n) + 1;

        for(int i = 1; i <= n; i++)
        {
            uf.union(0, i);
            uf.union(bottomInd, bottomInd - i);
        }

        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        validSite(row,col);

        int currentPos = tdToOd(row,col);

        if (!site[row-1][col-1])
        {
            site[row-1][col-1] = true;
            openSites++;

            // checks the right site
            if (col < n && isOpen(row, col+1))
                uf.union(currentPos, currentPos+1);
            
            // checks the left site
            if (col > 1 && isOpen(row, col-1))
                uf.union(currentPos, currentPos-1);

            // checks the top site
            if (row < n && isOpen(row+1, col))
                uf.union(currentPos, currentPos+n);

            // checks the bottom site
            if (row > 1 && isOpen(row-1, col))
                uf.union(currentPos, currentPos-n);
        }
    }
 
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        validSite(row, col);
        return site[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {  
        validSite(row, col);
        return connected(0, tdToOd(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return connected(0, bottomInd);
    }

}
