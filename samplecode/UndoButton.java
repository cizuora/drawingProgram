import java.awt.event.*;
import javax.swing.*;
public class UndoButton  extends JButton implements ActionListener {
  private final UndoManager undoManager;
  public UndoButton(UndoManager undoManager) {
    super("Undo");
    setToolTipText("Undo the last action");
    this.undoManager = undoManager;
    addActionListener(this);
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    undoManager.undo();
  }
}