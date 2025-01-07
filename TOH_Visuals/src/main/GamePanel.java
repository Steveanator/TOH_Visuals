package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePanel extends JPanel {
	
	public int piecesAmount = 1;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	private int s = (int) (GamePanel.WIDTH / 4);
	private ArrayList<Piece> pieces;
	
	public int targetPiece = -1;
	public Point previousPoint;
	
	public double dX, dY;
	
	
	public GamePanel() {
		MouseListener mouseListener = new MouseListener();
		this.addMouseListener(mouseListener);
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
		// Panel settings
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(new Color(204, 102, 0));
		this.setLayout(null);
		
		pieces = new ArrayList<Piece>();
		pieces.add(new Piece(1));
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(92, 47, 3));
		g2.fillRect(0, GamePanel.HEIGHT-50, GamePanel.WIDTH, 50);
		g2.fillRoundRect(s, 200, 30, 500, 20, 20);
		g2.fillRoundRect(s*2, 200, 30, 500, 20, 20);
		g2.fillRoundRect(s*3, 200, 30, 500, 20, 20);
		
		for (int i = 0; i < pieces.size(); i++) {
            g2.setColor(Color.red);
            Shape shape = pieces.get(i);
            
            g2.fill(shape);
        }
		
		
	}
	
	
	private class MouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			for (int i = 0; i < pieces.size(); i++) {
	            if (pieces.get(i).getBounds2D().contains(e.getPoint())) {
	                targetPiece = i;
	                previousPoint = e.getPoint();
	                dX = previousPoint.getX() - pieces.get(targetPiece).x;
	                dY = previousPoint.getY()  - pieces.get(targetPiece).y;
	                System.out.println("Mouse clicked: " + previousPoint.getX());
	                System.out.println("Clicked shape " + (i+1));
	            }
	        }
	        
		}
	}
	
	
	private class DragListener extends MouseMotionAdapter {
		
		public void mouseDragged(MouseEvent e) {
			Point currentPoint = e.getPoint();
			System.out.println(dX);
			
			
			pieces.get(targetPiece).x = (currentPoint.getX() - dX);
			//System.out.println(pieces.get(targetPiece).x);
			pieces.get(targetPiece).y = (currentPoint.getY() - dY);
			
			//previousPoint = currentPoint;
			
			repaint();
		}
	}
}