import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleButton extends JButton implements ActionListener {
    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private CircleCommand circleCommand;
    private UndoManager undoManager;

    public CircleButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Circle");
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
        private int pointCount = 0;

        @Override
        public void mouseClicked(MouseEvent event) {
            if (++pointCount == 1) {
                // First click: center of the circle
                circleCommand = new CircleCommand(View.mapPoint(event.getPoint()));
                undoManager.beginCommand(circleCommand);
            } else if (pointCount == 2) {
                // Second click: point on the edge; radius = distance from center
                pointCount = 0;
                circleCommand.setCirclePoint(View.mapPoint(event.getPoint()));
                drawingPanel.removeMouseListener(this);
                drawingPanel.removeMouseMotionListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                undoManager.endCommand(circleCommand);
            }
        }
        @Override
        public void mouseMoved(MouseEvent event) {
            if (pointCount == 1) {
                // Update the radius of the circle as the mouse moves
                circleCommand.setPreview(View.mapPoint(event.getPoint()));
            }
        }
    }
}