/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;
    private ArrayList<LineSegment> segments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (hasNull(points) || hasRepeated(points))
            throw new IllegalArgumentException();
        this.points = points;
        segments = new ArrayList<>();
        findSegs();
    }

    private boolean hasRepeated(Point[] points) {

        Arrays.sort(points);

        for (int i = 0, j = 0; i < points.length - 1; i++) {
            j = i + 1;
            if (points[i].compareTo(points[j]) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean hasNull(Point[] points) {
        for (Point p :
                points) {
            if (null == p)
                return true;
        }
        return false;
    }

    private void findSegs() {
        Point[] tempSeg = new Point[4];
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                for (int k = j + 1; k < points.length; k++)
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            tempSeg[0] = points[i];
                            tempSeg[1] = points[j];
                            tempSeg[2] = points[k];
                            tempSeg[3] = points[l];
                            Arrays.sort(tempSeg);
                            segments.add(new LineSegment(tempSeg[0], tempSeg[3]));
                        }
                    }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result=new LineSegment[segments.size()];
        return segments.toArray(result);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
