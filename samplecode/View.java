import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
class View extends JFrame {
  private UIContext uiContext;
  private final JPanel drawingPanel;
  private final JPanel buttonPanel;
  private final JButton lineButton;
  private final JButton deleteButton;
  private final JButton labelButton;
  private final JButton selectButton;
  private final JButton saveButton;
  private final JButton openButton;
  private final JButton undoButton;
  private final JButton redoButton;
  private final JButton circleButton;
  private final JButton polygonButton;
  private final JButton moveButton;
  private static UndoManager undoManager;
    private String fileName;
  // other buttons to be added as needed;
  private static Model model;
  public UIContext getUI() {
    return uiContext;
  }
  private void setUI(UIContext uiContext) {
    this.uiContext = uiContext;
  }
  public static void setModel(Model model) {
    View.model = model;
  }
  public static void setUndoManager(UndoManager undoManager) {
    View.undoManager = undoManager;
  }
  private class DrawingPanel extends JPanel {
    private MouseListener currentMouseListener;
    private KeyListener currentKeyListener;
    private FocusListener currentFocusListener;
    public DrawingPanel() {
      setLayout(null);
    }
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      (NewSwingUI.getInstance()).setGraphics(g);
      g.setColor(Color.BLUE);
      Enumeration enumeration = model.getItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render(uiContext);
      }
      g.setColor(Color.RED);
      enumeration = model.getSelectedItems();
      while (enumeration.hasMoreElements()) {
        ((Item) enumeration.nextElement()).render(uiContext);
      }
    }
    @Override
    public void addMouseListener(MouseListener newListener) {
      removeMouseListener(currentMouseListener);
      currentMouseListener =  newListener;
      super.addMouseListener(newListener);
    }
    @Override
    public void addKeyListener(KeyListener newListener) {
      removeKeyListener(currentKeyListener);
      currentKeyListener =  newListener;
      super.addKeyListener(newListener);
    }
    @Override
    public void addFocusListener(FocusListener newListener) {
      removeFocusListener(currentFocusListener);
      currentFocusListener =  newListener;
      super.addFocusListener(newListener);
    }
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
    setTitle("Drawing Program 1.1  " + fileName);
  }
  public String getFileName() {
    return fileName;
  }

  public View() {
    super("Drawing Program 1.1  Untitled");
    fileName = null;
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });
    this.setUI(NewSwingUI.getInstance());
    drawingPanel = new DrawingPanel();
    buttonPanel = new JPanel();
    Container contentpane = getContentPane();
    contentpane.add(buttonPanel, "North");
    contentpane.add(drawingPanel);
    lineButton= new LineButton(undoManager, this, drawingPanel);
    labelButton = new LabelButton(undoManager, this, drawingPanel);
    circleButton = new CircleButton(undoManager, this, drawingPanel);
    polygonButton = new PolygonButton(undoManager, this, drawingPanel);
    selectButton= new SelectButton(undoManager, this, drawingPanel);
    moveButton = new MoveButton(undoManager, this, drawingPanel);
    deleteButton= new DeleteButton(undoManager);
    saveButton= new SaveButton(undoManager, this);
    openButton= new OpenButton(undoManager, this);
    undoButton = new UndoButton(undoManager);
    redoButton = new RedoButton(undoManager);
    buttonPanel.add(lineButton);
    buttonPanel.add(labelButton);
    buttonPanel.add(circleButton);
    buttonPanel.add(polygonButton);
    buttonPanel.add(selectButton);
    buttonPanel.add(moveButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(openButton);
    buttonPanel.add(undoButton);
    buttonPanel.add(redoButton);
    this.setSize(600, 400);
  }
  public void refresh() {
    // code to access the Model update the contents of the drawing panel.
    drawingPanel.repaint();
  }
  public static Point mapPoint(Point point){
    // maps a point on the drawing panel to a point
    // on the figure being created. Perhaps this
    // should be in drawing panel
    return point;
  }
}
