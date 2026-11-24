import java.awt.*;

public class CircleCommand extends Command {
  private Circle circle;
  private boolean radiusProvided;

    public CircleCommand(){
        this(null, null);
        radiusProvided = false;
    }

    public CircleCommand(Point point)
    {
        this(point, null);
        radiusProvided = false;
    }

    public CircleCommand(Point center, Integer radius)
    {
        circle = new Circle(center);
        if (radius != null) {
        circle.setRadius(radius);
        radiusProvided = true;
        } else {
        radiusProvided = false;
        }
    }

    public void setCirclePoint(Point point) {
        if (circle.getCenter() == null) {
        circle.setCenter(point);
        } else if (!radiusProvided) {
        int radius = (int) circle.getCenter().distance(point);
        circle.setRadius(radius);
        radiusProvided = true;
        }
    }

    public void setRadius(int radius) {
        circle.setRadius(radius);
        radiusProvided = true;
    }

    public void execute() {
        model.addItem(circle);
    }
  
    public boolean undo() {
        model.removeItem(circle);
        return true;
    }

    public boolean redo() {
        execute();
        return true;
    }

    public boolean end() {
        if (circle.getCenter() == null) {
        undo();
        return false;
        }
        if (!radiusProvided) {
        circle.setRadius(0);
        }
        return true;
    }

    public void setPreview(Point point){
        if (circle.getCenter() != null) {
            circle.setRadius((int) circle.getCenter().distance(point));
            model.setChanged();
        }
    }

    
}