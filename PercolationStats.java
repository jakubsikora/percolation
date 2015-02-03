public class PercolationStats {
    private Percolation perc;
    private int t;
    
    // Fractions of open sites in computational experiments
    private double[] x;
    
   // perform T independent experiments on an N-by-N grid 
    public PercolationStats(int N, int T) {
        int opened;
        int row;
        int column;
        
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        } 
        
        t = T;
        x = new double[t];
        
        for (int i = 0; i < t; i++) {
            perc = new Percolation(N);
            
            opened = 0;
            while (!perc.percolates()) {
                // Open random sites
                row = StdRandom.uniform(N) + 1;
                column = StdRandom.uniform(N) + 1;
                
                if (perc.isFull(row, column)) {
                    perc.open(row, column);
                    opened++;
                }
            } 
            
            x[i] = (double) opened / (N * N);
        }
    }
   
   // sample mean of percolation threshold 
    public double mean() {
        return StdStats.mean(x);
    }
   
   // sample standard deviation of percolation threshold 
    public double stddev() {
        return StdStats.stddev(x);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(t));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(t));
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        StdOut.println(N + " " + T);
        
        PercolationStats ps = new PercolationStats(N, T);
        
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo()
                     + ", " + ps.confidenceHi());
    }
}