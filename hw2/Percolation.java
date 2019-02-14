package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.WildcardType;

public class Percolation {
    private WeightedQuickUnionUF pUF;
    private WeightedQuickUnionUF fUF;
    private boolean[] openArr;
    private int openSite;
    private int WIDTH;
    private final static int TOP = 0;
    private int BOTTOM;


    /** public Percolation(int N). */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Index need to be greater than 1");
        }
        WIDTH = N;
        fUF = new WeightedQuickUnionUF(N * N + 1);
        pUF = new WeightedQuickUnionUF(N * N + 2);
        BOTTOM = N * N + 1;
        openArr = new boolean[N * N + 2];
        openArr[TOP] = true;
        openArr[BOTTOM] = true;
        openSite = 0;
    }

    /** private helper function to change to UF sites */
    private int locTo1D (int row, int col) {
        if (row == -1) {
            return TOP;
        }
        if (row == WIDTH) {
            return BOTTOM;
        }
        return row * WIDTH + col + 1;
    }

    /** open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= WIDTH || col>= WIDTH) {
            throw new IndexOutOfBoundsException("Index out of bound.");
        }
        int loc = locTo1D(row, col);
        if (openArr[loc]) {
            return;
        }
        openArr[loc] = true;
        openSite += 1;
        int locUp = locTo1D(row - 1, col);
        openHelper(loc, locUp);

        int locDown = locTo1D(row + 1, col);
        openHelper(loc, locDown);

        if (col != 0) {
            int locLeft = locTo1D(row, col - 1);
            openHelper(loc, locLeft);
        }
        if (col != WIDTH - 1) {
            int locRight = locTo1D(row, col + 1);
            openHelper(loc, locRight);
        }
    }

    private void openHelper (int loc, int nearbyLoc) {
        if (nearbyLoc == BOTTOM) {
            pUF.union(loc, nearbyLoc);
            return;
        }
        if (openArr[nearbyLoc]) {
            fUF.union(loc, nearbyLoc);
            pUF.union(loc, nearbyLoc);
        }
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= WIDTH || col>= WIDTH) {
            throw new IndexOutOfBoundsException("Index out of bound.");
        }
        return openArr[locTo1D(row, col)];
    }
    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row >= WIDTH || col>= WIDTH) {
            throw new IndexOutOfBoundsException("Index out of bound.");
        }
        int loc = locTo1D(row, col);
        return fUF.connected(loc, TOP);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return openSite;
    }
    /** does the system percolate? */
    public boolean percolates() {
        return pUF.connected(TOP, BOTTOM);
    }

    /** use for unit testing (not required). */
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
    }

}
