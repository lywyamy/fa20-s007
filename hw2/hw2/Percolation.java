package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public int N;
    private boolean[][] grid;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF uf;

    // Create N-by-N grid, with all sites initially isOpen? false
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be a positive number!");
        }
        this.N = N;
        this.grid = new boolean[N][N];
        this.numberOfOpenSites = 0;
        this.uf = new WeightedQuickUnionUF(N * N);
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                grid[row][col] = false;
            }
        }
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N) { throw new IndexOutOfBoundsException("The row is out of range!"); }
        if (col < 0 || col >=N) { throw new IndexOutOfBoundsException("The column is out of range!"); }
    }

    // Open the site (row, col) if it is not open already.
    // Then union it with all open neighbors.
    // Its upNeighbor is at (row -1, col), downNeighbor (row + 1), leftNeighbor (row, col - 1), rightNeighbor (row, col + 1)
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            int siteNumber = siteToNumber(row, col);
            numberOfOpenSites++;

            if (hasUpNeighbor(row, col) && isOpen(row - 1, col)) { uf.union(siteNumber,siteToNumber(row - 1, col) ); }
            if (hasDownNeighbor(row, col) && isOpen(row + 1, col)) { uf.union(siteNumber, siteToNumber(row + 1, col)); }
            if (hasLeftNeighbor(row, col) && isOpen(row, col - 1)) { uf.union(siteNumber, siteToNumber(row, col - 1)); }
            if (hasRightNeighbor(row, col) && isOpen(row, col + 1)) { uf.union(siteNumber, siteToNumber(row, col + 1)); }
        }
    }

    // Transfer the 2D grid to an array so that we can use the WeightedQuickUnionUF class.
    private int siteToNumber(int row, int col) {
        return row * N + col;
    }

    // Check if the site has a neighbor before connection operation to avoid IndexOutOfBoundException
    private boolean hasUpNeighbor(int row, int col) {
        return row > 0;
    }

    private boolean hasDownNeighbor(int row, int col) {
        return row < N - 1;
    }

    private boolean hasLeftNeighbor(int row, int col) {
        return col > 0;
    }

    private boolean hasRightNeighbor(int row, int col) {
        return col < N - 1;
    }

    // Is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }

    // The site (row, col) is full when connected to any site in the top row.
    public boolean isFull(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            return false;
        } else {
            if (row == 0) {
                return true;
            } else {
                int siteNumber = siteToNumber(row, col);
                for (int i = 0; i < N; i++) {
                    if (uf.connected(siteNumber, siteToNumber(0, i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // The system percolates when at least one bottom site is full.
    public boolean percolates() {
        for (int i = 0; i < N; i++) {
            if (isFull(N - 1, i)) { return true; }
        }
        return false;
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }
}
