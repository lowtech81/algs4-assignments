import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private final WeightedQuickUnionUF sites;
    private boolean[] opened;
    private int numberOfOpenSites = 0;
    private final int size;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n<=0 is not allowed.");
        size = n;
        sites = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size * size + 2];
        for (int i = 1; i < size * size + 1; i++) {
            opened[i] = false;
        }
        opened[0] = true;
        opened[size * size + 1] = true;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("row " + row + " or col " + col + " is not between 1 and " + size + ".");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int pos = (row - 1) * size + col;
        if (!isOpen(row, col)) {
            opened[pos] = true;
            numberOfOpenSites++;
        }
        //try to union neighbor site
        if (row == 1)
            sites.union(pos, 0);
        if (row == size && isFull(row, col))
            sites.union(pos, size * size + 1);
        if (col > 1 && isOpen(row, col - 1))
            sites.union(pos, pos - 1);
        if (col < size && isOpen(row, col + 1))
            sites.union(pos, pos + 1);
        if (row > 1 && isOpen(row - 1, col))
            sites.union(pos, pos - size);
        if (row < size && isOpen(row + 1, col))
            sites.union(pos, pos + size);

        //check the last row for determine percolation
        if (!percolates()) {
            for (int c = 1; c < size + 1; c++) {
                if (isFull(size, c))
                    sites.union((size - 1) * size + c, size * size + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int pos = (row - 1) * size + col;
        return opened[pos];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int pos = (row - 1) * size + col;
        return sites.connected(pos, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return sites.connected(0, size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        StdOut.println(p.percolates()); //should be false
        p.open(1, 2);
        p.open(2, 3);
        p.open(3, 2);
        StdOut.println(p.numberOfOpenSites()); //should be 3
        StdOut.println(p.percolates()); //should be false
        p.open(1, 3);
        p.open(3, 3);
        StdOut.println(p.numberOfOpenSites()); //should be 5
        StdOut.println(p.percolates()); //shoud be true
        p.open(2, 1);
        StdOut.println(p.numberOfOpenSites()); //should be 6
        StdOut.println(p.isFull(2, 1)); //should be false
        StdOut.println(p.isFull(2, 3)); //shoud be true
        StdOut.println(p.percolates()); //shoud be true
    }

}
