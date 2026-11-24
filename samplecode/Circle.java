import java.awt.*;

public class Circle extends Item {
    private Point center;
    private int radius;

    public Circle(Point center)
    {
        this.center = center;
        this.radius = 0; // Initialize radius to 0
    }

    public Circle()
    {
        this.center = null;
        this.radius = 0; // Initialize radius to 0
    }

    // Getters and setters for center and radius
    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    @Override
    public boolean includes(Point point) {
        if (center == null) {
            return false; // If center is not defined, the circle cannot include any point
        }
        return center.distance(point) <= radius;
    }
    @Override
    public void render(UIContext uiContext) {
        if (center != null) {
            uiContext.drawCircle(center, radius);
        }
    }
    @Override
    public void translate(int dx, int dy) {
        if (center != null) {
            center = new Point(center.x + dx, center.y + dy);
        }
    }
    @Override
    public String toString() {
        return "Circle with center at " + center + " and radius " + radius;
    }
}