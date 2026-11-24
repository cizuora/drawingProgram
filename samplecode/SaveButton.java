import java.awt.event.*;
import javax.swing.*;
public class SaveButton  extends JButton implements ActionListener {
  protected View view;
  private final UndoManager undoManager;
  public SaveButton(UndoManager undoManager, View jFrame) {
    super("Save");
    setToolTipText("Save the drawing to a file");
    this.undoManager = undoManager;
    addActionListener(this);
    view = jFrame;
  }
  @Override
  public void actionPerformed(ActionEvent event) {
    String string = view.getFileName();
    if (string == null) {
      string = JOptionPane.showInputDialog(view, "Please specify file name");
      view.setFileName(string);
    }
    SaveCommand command = new SaveCommand(string);
    undoManager.beginCommand(command);
    undoManager.endCommand(command);
  }
}
