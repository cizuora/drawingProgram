import java.util.*;
public class MoveCommand extends Command {
  private Vector items;
  private int totalDx;
  private int totalDy;
  public MoveCommand() {
    items = new Vector();
    Enumeration enumeration = model.getSelectedItems();
    while (enumeration.hasMoreElements()) {
      items.add(enumeration.nextElement());
    }
    totalDx = 0;
    totalDy = 0;
  }
  public boolean isEmpty() {
    return items.isEmpty();
  }
  public void moveBy(int dx, int dy) {
    totalDx += dx;
    totalDy += dy;
    model.moveItems(items, dx, dy);
  }
  public boolean hasMoved() {
    return totalDx != 0 || totalDy != 0;
  }
  public void execute() {
    model.moveItems(items, totalDx, totalDy);
  }
  public boolean undo() {
    model.moveItems(items, -totalDx, -totalDy);
    return true;
  }
  public boolean redo() {
    execute();
    return true;
  }
}
