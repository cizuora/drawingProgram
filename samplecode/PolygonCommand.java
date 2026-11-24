import java.awt.*;

public class PolygonCommand extends Command {
    private Polygon polygon;
    private int pointCount;

    public PolygonCommand(Point point) {
        polygon = new Polygon(point); // Polygon's constructor already stores the first point
        if (point != null) {
            pointCount = 1;
        } else {
            pointCount = 0;
        }
    }

    public void addPoint(Point point) {
        polygon.addPoint(point);
        pointCount++;
        model.setChanged();
    }

    @Override
    public void execute() {
        model.addItem(polygon);
    }
    @Override
    public boolean undo() {
        model.removeItem(polygon);
        return true;
    }
    @Override
    public boolean redo() {
        execute();
        return true;
    }
    @Override
    public boolean end() {
        if (pointCount < 3) { // A polygon must have at least 3 points
            undo();
            return false;
        }
        polygon.close(); // Finished: draw the closing edge and allow selection by clicking inside
        model.setChanged();
        return true;
    }
}