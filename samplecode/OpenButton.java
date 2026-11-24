import java.awt.event.*;
import javax.swing.*;
public class OpenButton  extends JButton implements ActionListener {
  protected final View view;
  private final UndoManager undoManager;
  public OpenButton(UndoManager undoManager, View jFrame) {
    super("Open");
    this.undoManager = undoManager;
    addActionListener(this);
    this.view = jFrame;
  }
  @Override
  public void actionPerformed(ActionEvent e){
    String string = JOptionPane.showInputDialog(view, "Please type new file name");
    OpenCommand command = new OpenCommand(string);
    view.setFileName(string);
    undoManager.beginCommand(command);
    undoManager.endCommand(command);
  }
}
