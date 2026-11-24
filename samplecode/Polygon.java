import java.awt.*;

public class Polygon extends Item {
    private Point[] points;
    boolean isClosed;

    public Polygon(Point p1)
    {
        this.points = new Point[]{p1};
    }

    public void addPoint(Point p) {
        Point[] newPoints = new Point[points.length + 1];
        System.arraycopy(points, 0, newPoints, 0, points.length);
        newPoints[points.length] = p;
        points = newPoints;
    }

    public void close()
    {
        isClosed = true;
    }

    @Override
    public boolean includes(Point point) {
        if (points.length < 3 || !isClosed) {
            return false; // A polygon must have at least 3 points and be closed to include a point
        }
        // Implement point-in-polygon algorithm (e.g., ray-casting algorithm)
        int intersectCount = 0;
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            Point p2 = points[(i + 1) % points.length];
            if (rayIntersectsSegment(point, p1, p2)) {
                intersectCount++;
            }
        }
        return (intersectCount % 2) == 1; // Odd intersections mean the point is inside
    }

    @Override
    public void render(UIContext uiContext) {
        if (points.length < 2) {
            return; // Not enough points to draw a polygon
        }
        for (int i = 0; i < points.length; i++) {
            Point p1 = points[i];
            Point p2 = points[(i + 1) % points.length];
            uiContext.drawLine(p1, p2);
        }
    }

    @Override
    public void translate(int dx, int dy) {
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(points[i].x + dx, points[i].y + dy);
        }
    }

    private boolean rayIntersectsSegment(Point p, Point p1, Point p2) {
        if (p1.y > p2.y) {
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
        if (p.y == p1.y || p.y == p2.y) {
            p = new Point(p.x, p.y + 1); // Move the point slightly to avoid edge cases
        }
        if (p.y < p1.y || p.y > p2.y || p.x >= Math.max(p1.x, p2.x)) {
            return false; // The point is outside the vertical bounds of the segment
        }
        if (p.x < Math.min(p1.x, p2.x)) {
            return true; // The point is to the left of the segment
        }
        double m_edge = (double)(p2.y - p1.y) / (p2.x - p1.x);
        double m_point = (double)(p.y - p1.y) / (p.x - p1.x);
        return m_point >= m_edge; // Check if the point is above the line segment
    }
}