package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stdVar;
    private final static double CONS = 1.96;
    private double T1;

    /** perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException();
        }
        T1 = T;
        double[] x = new double[T];
        int i = 0;
        while (i < T) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                p.open(row, col);
            }
            x[i] = (double) p.numberOfOpenSites() / (N * N);
            i++;
        }
        mean = StdStats.mean(x);
        stdVar = StdStats.stddev(x);
    }
    /** sample mean of percolation threshold. */
    public double mean() {
        return mean;
    }

    /** sample standard deviation of percolation threshold. */
    public double stddev() {
        return stdVar;
    }
    /** low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return mean - (CONS * stdVar)/Math.sqrt(T1);
    }
    /** high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return mean + (CONS * stdVar)/Math.sqrt(T1);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(20, 30, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stdVar);
        System.out.println(ps.confidenceHigh());
        System.out.println(ps.confidenceLow());
    }
}
