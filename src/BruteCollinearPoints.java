
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
                        
                        if (checkCollinear(points[b], points[c], points[d])){
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
        Point a = new Point(1, 1);
        Point b = new Point(3, 3);
        Point c = new Point(5, 3);
        Point d = new Point(5, 1);
        Point e = new Point(1, 4);
        Point f = new Point(2, 2);
        Point g = new Point(2, 9);
        Point h = new Point(4, 10);
        Point i = new Point(5, 5);

        Point[] m = {a, b, c, d, e, f, g, h, i};

        BruteCollinearPoints n = new BruteCollinearPoints(m);
        LineSegment[] ll = n.segments();
        for (LineSegment ls : ll) {
            System.out.println(ls);
        }

    }
}
