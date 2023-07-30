
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] points;
    private int numberOfSegment;

    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        numberOfSegment = 0;
        validateInput();
    }

    private void validateInput() {
        if (points == null) {
            throw new IllegalArgumentException("input is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("input contains null");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("input has duplicate");
                }

            }
        }

    }

    public int numberOfSegments() {
        return numberOfSegment;
    }

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[points.length];
        for (int a = 0; a < points.length; a++) {
            for (int b = a + 1; b < points.length; b++) {
                for (int c = b + 1; c < points.length; c++) {

                    if (checkCollinear(points[a], points[b], points[c])) {
                        for (int d = c + 1; d < points.length; d++) {

                            if (checkCollinear(points[b], points[c], points[d])) {
                                Point[] collinearPoints = {points[a], points[b], points[c], points[d]};
                                res[numberOfSegment++] = getSegment(collinearPoints);
                            }

                        }

                    }

                }

            }
        }
        return Arrays.copyOfRange(res, 0, numberOfSegment);

    }

    private boolean checkCollinear(Point p1, Point p2, Point p3) {
        return (Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0);
    }

    private LineSegment getSegment(Point[] collinearPoints) {

        Arrays.sort(collinearPoints);
        Point minPoint = collinearPoints[0];
        Point maxPoint = collinearPoints[3];

        return new LineSegment(minPoint, maxPoint);

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
