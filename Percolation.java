public class Percolation {
    // Array of all sites
    private int[][] grid;
    private int n;
    private WeightedQuickUnionUF uf;
    private int vTop;
    private int vBottom;
    private int open = 1;
    
    // create N-by-N grid, with all sites blocked 
    public Percolation(int N) {
        if (N < 0) {
             throw new java.lang.IllegalArgumentException();
        }
        
        // Number of sites
        n = N; 
        
        // Create the NxN grid
        grid = new int[n][n];
        
        // Add virtual top and bottom sites
        // Create N*N + 2 components
        uf = new WeightedQuickUnionUF((n * n) + 2);
        
        // Index of virtual sites
        vTop = 0;
        vBottom = (n * n) + 1;
    }
    
    // row i, column j
    private int xyTo1D(int i, int j) {
        return (i-1) * n + j;
    }
       
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        // Check corner cases
        if (i <= 0 || i > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "row index i out of bounds");
        }
        
        if (j <= 0 || j > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "column index j out of bounds");
        }
        
        if (!isOpen(i, j)) {
            grid[i-1][j-1] = open;
            
            // If first row connect with top virtual
            if (i == 1) {
                uf.union(xyTo1D(i, j), vTop);
            }
            
            // If last row connect with bottom virtual
            if (i == n) {
                uf.union(xyTo1D(i, j), vBottom);
            }
            
            // Top
            if (i-1 >= 1) { 
                if (grid[i-2][j-1] == 1) {
                    uf.union(xyTo1D(i, j), xyTo1D(i-1, j)); 
                }
            }           
            
            // Right
            if (j+1 <= n) {
                if (grid[i-1][j] == 1) {
                    uf.union(xyTo1D(i, j), xyTo1D(i, j+1)); 
                }
            }
            
            // Bottom
            if (i+1 <= n) {
                if (grid[i][j-1] == 1) {
                    uf.union(xyTo1D(i, j), xyTo1D(i+1, j)); 
                }
            }
            
            // Left
            if (j-1 >= 1) {
                if (grid[i-1][j-2] == 1) {
                    uf.union(xyTo1D(i, j), xyTo1D(i, j-1)); 
                }
            }
        }
    }
       
    // is site (row i, column j) open?    
    public boolean isOpen(int i, int j) {
        // Check corner cases
        if (i <= 0 || i > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "row index i out of bounds");
        }
        
        if (j <= 0 || j > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "column index j out of bounds");
        }
        
        return grid[i-1][j-1] == open;
    }
       
    // is site (row i, column j) full?    
    public boolean isFull(int i, int j) {
        // Check corner cases
        if (i <= 0 || i > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "row index i out of bounds");
        }
        
        if (j <= 0 || j > n) {
            throw new java.lang.IndexOutOfBoundsException(
              "column index j out of bounds");
        }
        
        return uf.connected(vTop, xyTo1D(i, j));
    }
       
    // does the system percolate?    
    public boolean percolates() {
        return uf.connected(vTop, vBottom);
    }

    // test client (optional)    
    public static void main(String[] args) {
        int N = 5;
        int row;
        int column;
        
        Percolation perc = new Percolation(N);
        
        while (!perc.percolates()) {
          // Open random sites
            row = StdRandom.uniform(N) + 1;
            column = StdRandom.uniform(N) + 1;
            
            if (perc.isFull(row, column)) {
                perc.open(row, column);
            }
        }
    }
}