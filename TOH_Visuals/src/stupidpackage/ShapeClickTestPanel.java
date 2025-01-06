package stupidpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

class ShapeClickTestPanel extends JPanel implements MouseListener {
    private final List<Shape> shapes;
    private final List<Color> colors;

    public ShapeClickTestPanel() {
        addMouseListener(this);

        shapes = new ArrayList<Shape>();
        colors = new ArrayList<Color>();

        shapes.add(new Arc2D.Double(50, 50, 230, 270, 45, 90, Arc2D.OPEN));
        colors.add(Color.RED);

    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;

        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            Color color = colors.get(i);
            g.setColor(color);
            g.fill(shape);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	System.out.println("Mouse clicked");
        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            if (shape.contains(e.getPoint())) {
                System.out.println("Clicked shape " + i);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}