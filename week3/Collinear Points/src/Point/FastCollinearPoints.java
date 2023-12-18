package Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class FastCollinearPoints {

    final ArrayList<LineSegment> collinearLineSegments = new ArrayList<>();
    ArrayList<Point[]> collinearPoints = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

        if(points == null) {
            throw new IllegalArgumentException("points can't be null");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("point can't be null");
            }
        }

        int n = points.length;
        Point[] pointsCopy = Arrays.copyOf(points, n);

        for (Point p : points) {

            //Sort the array by the slopeOrder
            Arrays.sort(pointsCopy, p.slopeOrder());

            double prevSlope = Double.NEGATIVE_INFINITY;
            ArrayList<Point> currentSegment = new ArrayList<>();
            // current segment

            for(int i = 1; i < n; i++) {
                if (p.slopeTo(pointsCopy[i]) == prevSlope) {
                    currentSegment.add(pointsCopy[i]);
                } else {
                    if (currentSegment.size() >= 3) {
                        // add the currnet segment  to segments 
                        currentSegment.add(p);
                        // add only if new
                        addSegmentToCollinear(currentSegment);     
                    }
                    currentSegment.clear();
                    currentSegment.add(pointsCopy[i]);
                    prevSlope = p.slopeTo(pointsCopy[i]);
                }
            }
            if (currentSegment.size() >= 3) {
                currentSegment.add(p);
                addSegmentToCollinear(currentSegment);     
            }
        }
   } 
    

   private void addSegmentToCollinear( ArrayList<Point> P) {
        Collections.sort(P);

        LineSegment currentSegment = new LineSegment(P.get(0), P.get(P.size()-1));
        
        Point endPoint = P.get(P.size()-1);
        Point startPoint = P.get(0);

        double pSlope = endPoint.slopeTo(startPoint);
        
         for (Point[] pointArray : collinearPoints) {
            Point endPointArray = pointArray[pointArray.length-1];
            Point startPointArray = pointArray[0];
            double pArraySlope = endPointArray.slopeTo(startPointArray);
            if (endPoint == endPointArray && pSlope == pArraySlope) { return;}
        }
       
        // System.out.println("arranged P Points are");

        // for (Point p: P) {
        //     System.out.println(p);
        // }
        Point[] tempPoints = new Point[P.size()];
        tempPoints = P.toArray(tempPoints);
        collinearPoints.add(tempPoints);
        collinearLineSegments.add( new LineSegment(P.get(0), P.get(P.size()-1)));
   }
   
    public int numberOfSegments()  {
        return collinearLineSegments.size();
    }   // the number of line segments
 
    public LineSegment[] segments()  {
        LineSegment[] returnLineSegments = collinearLineSegments.toArray(new LineSegment[collinearLineSegments.size()]);
        return returnLineSegments;
    }       

    public static void main(String[] args) {
    
        int n = 100;
        Point[] points = new Point[n];
        points[0] = new Point(0,0);
        points[1] = new Point(1,1);
        points[2] = new Point(2,2);
        points[3] = new Point(3,3);
        points[4] = new Point(4,4);
 
        for (int i = 5; i < n; i++) {
            int x = StdRandom.uniformInt(100);
            int y = StdRandom.uniformInt(100);
            points[i] = new Point(x, y);
        }
        
        for (Point p : points) {
            System.out.println(p);
        }

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        System.out.println(collinear.segments().length);
        
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