import java.awt.event.*;
import javax.swing.*;
public class SelectButton  extends JButton implements ActionListener {
  protected JPanel drawingPanel;
  protected View view;
  private final MouseHandler mouseHandler;
  private SelectCommand selectCommand;
  private final UndoManager undoManager;
  public SelectButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
    super("Select");
    setToolTipText("Click a shape to select it");
    addActionListener(this);
    view = jFrame;
    drawingPanel = jPanel;
    this.undoManager = undoManager;
    mouseHandler = new MouseHandler();
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    selectCommand = new SelectCommand();
    drawingPanel.addMouseListener(mouseHandler);
    undoManager.beginCommand(selectCommand);
  }
  private class MouseHandler extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent event) {
       if (selectCommand.setPoint(View.mapPoint(event.getPoint())))
	   {
		drawingPanel.removeMouseListener(this);
		undoManager.endCommand(selectCommand);
	   }
	   else {
		   undoManager.cancelCommand();
	   }
    }
  }
}
