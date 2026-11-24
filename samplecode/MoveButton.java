import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MoveButton extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private final MouseHandler mouseHandler;
  private MoveCommand moveCommand;
  private final UndoManager undoManager;
  public MoveButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Move");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    mouseHandler = new MouseHandler();
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    moveCommand = new MoveCommand();
    if (moveCommand.isEmpty()) {
      return;
    }
    view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    drawingPanel.addMouseListener(mouseHandler);
    drawingPanel.addMouseMotionListener(mouseHandler);
  }
  private class MouseHandler extends MouseAdapter {
    private Point lastPoint;
    @Override
    public void mousePressed(MouseEvent event) {
      lastPoint = View.mapPoint(event.getPoint());
      undoManager.beginCommand(moveCommand);
    }
    @Override
    public void mouseDragged(MouseEvent event) {
      if (lastPoint == null) {
        return;  // no press seen yet (e.g. another tool took over the mouse listener)
      }
      Point point = View.mapPoint(event.getPoint());
      moveCommand.moveBy(point.x - lastPoint.x, point.y - lastPoint.y);
      lastPoint = point;
    }
    @Override
    public void mouseReleased(MouseEvent event) {
      if (lastPoint == null) {
        return;
      }
      lastPoint = null;
      drawingPanel.removeMouseListener(this);
      drawingPanel.removeMouseMotionListener(this);
      view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      if (moveCommand.hasMoved()) {
        undoManager.endCommand(moveCommand);
      } else {
        undoManager.cancelCommand();
      }
    }
  }
}
