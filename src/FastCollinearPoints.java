
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int numberOfSegment;
    private Point toCompare;

    public FastCollinearPoints(Point[] points) {
        this.points = points;
        validateInput();
        numberOfSegment = 0;
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

        Point[] copiedArr = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copiedArr[i] = points[i];
        }
        for (Point p : copiedArr) {
            toCompare = p;
            Arrays.sort(points, p.slopeOrder());

            int i = 2;
            int index = 0;
            Point[] collinearPoints = new Point[points.length];
            collinearPoints[index++] = points[1];

            double slope = toCompare.slopeTo(collinearPoints[0]);

            while (i < points.length) {
                double currentSlope = toCompare.slopeTo(points[i]);
                if (currentSlope != slope) {
                    if (index >= 3) {
                        res[numberOfSegment++] = getSegment(collinearPoints);
                        collinearPoints = new Point[points.length];
                    }
                    index = 0;
                    slope = currentSlope;
                }
                collinearPoints[index++] = points[i];
                i++;
            }

        }

        return clearRepetition(res);
    }

    private LineSegment[] clearRepetition(LineSegment[] res) {
//        double loop to indetify repetition == null
        LineSegment[] result = new LineSegment[numberOfSegment];
        for (int i = 0; i < numberOfSegment; i++) {
            for (int j = i + 1; j < numberOfSegment; j++) {
                if (res[i] == null || res[j] == null) {
                    continue;
                }
                if (res[i].toString().compareTo(res[j].toString()) == 0) {
                    res[j] = null;
                }
            }
        }
//        if any null in res, do not at it in result
        int index = 0;
//        this is index: the true length

        for (int i = 0; i < numberOfSegment; i++) {
            if (res[i] != null) {
                result[index++] = res[i];
            }
        }
        numberOfSegment = index;
        return Arrays.copyOfRange(result, 0, numberOfSegment);
    }

    private LineSegment getSegment(Point[] collinearPoints) {

        Point minPoint = toCompare;
        Point maxPoint = toCompare;
        for (Point p : collinearPoints) {
            if (p == null){
                continue;
            }
            if (p.compareTo(minPoint) < 0) {
                minPoint = p;
            }
            if (p.compareTo(maxPoint) > 0) {
                maxPoint = p;
            }
        }
        return new LineSegment(minPoint, maxPoint);

    }

    

    public static void main(String[] args) {
        Point a = new Point(2, 3);
        Point b = new Point(3, 3);
        Point c = new Point(5, 3);
        Point d = new Point(1, 3);
        Point e = new Point(1, 4);
        Point f = new Point(2, 2);
        Point g = new Point(2, 5);
        Point h = new Point(4, 4);
        Point i = new Point(5, 5);
        Point[] points = {a, b, c, d, e, f, g, h, i};

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
