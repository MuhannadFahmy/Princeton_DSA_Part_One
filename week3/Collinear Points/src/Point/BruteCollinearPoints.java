package Point;

import java.util.ArrayList;
import java.util.Arrays;

import Point.Point;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import Point.LineSegment;

public class BruteCollinearPoints {

    // private final Point[] pointArr = null;
    // ArrayList<Point> pointArr = new ArrayList<>(); // what is the differnce between this nd the above one

    final ArrayList<LineSegment> collinearLineSegments = new ArrayList<>();

    /**
     * @param points
     */
    public BruteCollinearPoints (Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException("points can't be null");
        }

        for (Point p : points) {
            if (p==null) {
                throw new IllegalArgumentException("point can't be null");
            }
        }

        Arrays.sort(points);

        checkDuplicates(points);

        double slopePQ, slopePR, slopePS;

        int n = points.length - 1;
        
        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        slopePQ = points[p].slopeTo(points[q]);
                        slopePR = points[p].slopeTo(points[r]);
                        slopePS = points[p].slopeTo(points[s]);

                        if (slopePQ == slopePR && slopePQ == slopePS) {
                            System.out.println("PQ " +slopePQ+ "    PR "+slopePR+"    PS "+slopePS);
                            System.out.println("P" +points[p]+ "    Q "+points[q]+"    R "+points[r] +"    S "+points[s]);

                            collinearLineSegments.add(setLineSegment(points[p],points[q],points[r],points[s]));
                            
                        }

                    }
                }
            }
        }


    }

    private void checkDuplicates (Point[] points) {

        for (int i = 0; i < points.length-2; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                throw new IllegalArgumentException("Duplicate Entries");
            }
        }
    }

    private LineSegment setLineSegment(Point p1, Point p2, Point p3,Point p4) {

        Point[] lineSegmePoints = {p1,p2,p3,p4};
        Point Biggest = p1;
        Point Smallest = p1;
        for (int i = 1; i < 4; i++) {
            if (lineSegmePoints[i].compareTo(Biggest) > 0) { Biggest = lineSegmePoints[i]; }
            else if (lineSegmePoints[i].compareTo(Smallest) < 0) {Smallest = lineSegmePoints[i]; } 
        }

        return (new LineSegment(Biggest, Smallest));
    }

    public int numberOfSegments() {
        return collinearLineSegments.size();
    }
    
    public LineSegment[] segments() {
        LineSegment[] returnLineSegments = collinearLineSegments.toArray(new LineSegment[collinearLineSegments.size()]);
        return returnLineSegments;
    }

      public static void main(String[] args) {
        // read the n points from a file
        // In in = new In(args[0]);
        // In in = new In();
        // int n = in.readInt();
        int n = 10;
        Point[] points = new Point[n];
        points[0] = new Point(0,0);
        points[1] = new Point(1,1);
        points[2] = new Point(2,2);
        points[3] = new Point(3,3);
        points[4] = new Point(45,4);
 
        for (int i = 5; i < n; i++) {
            int x = StdRandom.uniformInt(100);
            int y = StdRandom.uniformInt(100);
            points[i] = new Point(x, y);
        }
        
        for (Point p : points) {
            System.out.println(p);
        }

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
