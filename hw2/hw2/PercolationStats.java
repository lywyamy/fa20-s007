package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    public int N;
    public int T;
    public PercolationFactory pf;
    public double[] fraction; //fraction of open sites over all sites when the system percolates.
    final double confidenceIntervalValue = 1.96;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("The size of the grid and the number of experiments must be positive numbers!");
        }
        this.N = N;
        this.T = T;
        this.pf = pf;
        this.fraction = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            fraction[i] = (p.numberOfOpenSites() * 1.0)/ (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - confidenceIntervalValue * stddev() / Math.sqrt(T * 1.0);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + confidenceIntervalValue * stddev() / Math.sqrt(T * 1.0);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(20, 10, new PercolationFactory());
        System.out.println("We repeated " + ps.T + " times experiments on a " + ps.N + " by " + ps.N + " system.");
        System.out.println("The average fraction is " + ps.mean());
        System.out.println("The standard deviation is " + ps.stddev());
        System.out.println("The lower bound of 95% confidence interval is " + ps.confidenceLow());
        System.out.println("The upper bound of 95% confidence interval is " + ps.confidenceHigh());
    }

}
