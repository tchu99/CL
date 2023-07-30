
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] points;
    private int numberOfSegment;
    private Point toCompare;
    private LineSegment[] res;
    int[] allValidPair;

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
        res = new LineSegment[points.length];
        allValidPair = new int[2];
        allValidPair[0] = 0;
        allValidPair[1] = 0;
        Point[] copiedArr = Arrays.copyOfRange(points, 0, points.length);
        for (Point p : copiedArr) {
            toCompare = p;
            Arrays.sort(points, p.slopeOrder());

            int i = 0, j = 0;

            while (j < points.length) {

                double comparision = toCompare.slopeOrder().compare(points[i], points[j]);
                if (comparision == 0) {
                    if (j - i + 1 >= 3) {
                        addSegment(i, j);
                    }
                    j++;

                } else {
                    i = j;
                }
            }

        }

        return clearRepetition(res);
    }

    private void addSegment(int i, int j) {
        
        Point[] collinearPoints = Arrays.copyOfRange(points, i, j + 1);
        res[numberOfSegment++] = getSegment(collinearPoints);
        if (allValidPair[0] == 0 && allValidPair[1] == 0){
            allValidPair[0] = i;
            allValidPair[1] = j;
            return;
        }
        if (allValidPair[0] == i && allValidPair[1]+1 == j){
            int deletedId = numberOfSegment-2;
            res[deletedId] = null;
            allValidPair[1] = j;
        }
        
    }

    private LineSegment getSegment(Point[] collinearPoints) {

        Point minPoint = toCompare;
        Point maxPoint = toCompare;
        for (Point p : collinearPoints) {
            
            if (p.compareTo(minPoint) < 0) {
                minPoint = p;
            }
            if (p.compareTo(maxPoint) > 0) {
                maxPoint = p;
            }
        }
        return new LineSegment(minPoint, maxPoint);

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

    public static void main(String[] args) {
        Point a = new Point(10, 3);
        Point b = new Point(10, 4);
        Point c = new Point(0, 1);
        Point d = new Point(2, 7);
        Point e = new Point(3, 3);
        Point f = new Point(2, 3);
        Point g = new Point(10, 0);
        Point h = new Point(10, 9);
        Point i = new Point(3, 7);
        Point k = new Point(0, 3);
        Point[] points = {a, b, c, d, e, f, g, h, i, k};

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
