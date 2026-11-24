import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PolygonButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private PolygonCommand polygonCommand;
    private UndoManager undoManager;

    public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Polygon");
        setToolTipText("Left-click each corner; right-click to finish");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        // Change cursor when button is clicked
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
        // Start listening for mouseclicks on the drawing panel
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON3) {
                // Right click to finish the polygon
                if (polygonCommand != null) {
                    drawingPanel.removeMouseListener(this);
                    drawingPanel.removeMouseMotionListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    undoManager.endCommand(polygonCommand);
                    polygonCommand = null; // Reset for next polygon
                }
                return;
            }
            if (polygonCommand == null) {
                polygonCommand = new PolygonCommand(View.mapPoint(event.getPoint()));
                undoManager.beginCommand(polygonCommand);
            }
            else {
                polygonCommand.addPoint(View.mapPoint(event.getPoint()));
            }
        }
    }
}