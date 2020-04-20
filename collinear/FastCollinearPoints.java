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

public class FastCollinearPoints {

    private Point[] points;
    private ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
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

        // 对每个点(这里的点已经按照位置从小到大进行过排序），将改点作为原点，将其他相对于改点斜率相同的点按斜率进行排序
        Point[] slopeOrderedPoints;
        for (Point origin :
                points) {

            slopeOrderedPoints = Arrays.copyOf(points, points.length);

            Arrays.sort(slopeOrderedPoints, origin.slopeOrder());

            // 在排好序的序列里，找斜率相同的点，如果有连续相同的3个及以上的点，则把这些点及原点放入一个共线序列
            int i = 1, j = 1;
            while (i < slopeOrderedPoints.length) {
                while (j < slopeOrderedPoints.length) {
                    if (slopeOrderedPoints[i].slopeTo(origin) == slopeOrderedPoints[j].slopeTo(origin))
                        j++;
                    else
                        break;
                }
                if ((j - i) >= 3) {
                    Point[] collinearSegPoints = new Point[j - i + 1];
                    collinearSegPoints[0] = origin;
                    for (int k = i, l = 1; k < j; k++, l++) {
                        collinearSegPoints[l] = slopeOrderedPoints[k];
                    }
                    // 将共线序列里的点按大小排序，如果最小的点小于原点，则说明已经被找到并放入的线段数组，抛弃之
                    // 否则，将序列的第一个点和最后一个点作为参数，构造线段，并加入线段列表，
                    Arrays.sort(collinearSegPoints);
                    if (collinearSegPoints[0].compareTo(origin) >= 0) {
                        LineSegment seg = new LineSegment(collinearSegPoints[0],
                                                          collinearSegPoints[collinearSegPoints.length
                                                                  - 1]);
                        segments.add(seg);
                    }
                }
                i = j;
            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.size()];
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
