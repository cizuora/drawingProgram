import java.awt.event.*;
import javax.swing.*;
public class RedoButton  extends JButton implements ActionListener {
  private final UndoManager undoManager;
  public RedoButton(UndoManager undoManager) {
    super("redo");
    setToolTipText("Redo the last undone action");
    this.undoManager = undoManager;
    addActionListener(this);
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    undoManager.redo();
  }
}
