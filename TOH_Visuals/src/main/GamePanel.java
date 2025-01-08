package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	public static int piecesAmount = 3;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int pole_spacing = (int) (WIDTH / 4);
	public static ArrayList<Piece> pieces;
	private Board board;
	
	
	private int targetPiece = -1;
	private Point previousPoint;
	
	private double dX, dY;
	
	
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
		for(int i = 1; i <= piecesAmount; i++) {
			pieces.add(new Piece(i));
		}
		
		board = new Board();
		board.setLocations();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(92, 47, 3));
		g2.fillRect(0, HEIGHT-50, WIDTH, 50);
		g2.fillRoundRect(pole_spacing, 200, 30, HEIGHT-200, 20, 20);
		g2.fillRoundRect(pole_spacing*2, 200, 30, HEIGHT-200, 20, 20);
		g2.fillRoundRect(pole_spacing*3, 200, 30, HEIGHT-200, 20, 20);
		
		for (int i = 0; i < pieces.size(); i++) {
			if(!(i == targetPiece)) {
				Color color = pieces.get(i).getColor();
            	Shape shape = pieces.get(i);
            	g2.setColor(color);
            	g2.fill(shape);
			}
        }
		
		
		// If a piece is being moved it will appear above all the other pieces
		if(targetPiece != -1) {
			Color color = pieces.get(targetPiece).getColor();
        	Shape shape = pieces.get(targetPiece);
        	g2.setColor(color);
        	g2.fill(shape);
		}
	}
	
	
	private class MouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			for (int i = 0; i < pieces.size(); i++) {
	            if (pieces.get(i).getBounds2D().contains(e.getPoint())) {
	            	if(!(pieces.get(i).isMovable())) {
	            		return;
	            	}
	            	targetPiece = i;
	                previousPoint = e.getPoint();
	                dX = previousPoint.getX() - pieces.get(targetPiece).x;
	                dY = previousPoint.getY()  - pieces.get(targetPiece).y;
	            }
	        }
		}
		
		public void mouseReleased(MouseEvent e) {
			if(targetPiece != -1 && pieces.get(targetPiece).isMovable()) {
				board.movePiece(targetPiece + 1);
			}
			targetPiece = -1;
			board.setLocations();
			repaint();
		}
	}
	
	
	private class DragListener extends MouseMotionAdapter {
		
		public void mouseDragged(MouseEvent e) {
			if(targetPiece == -1) {
				return;
			}
			if(!(pieces.get(targetPiece).isMovable())) {
				return;
			}
			Point currentPoint = e.getPoint();
			
			// Movey parts
			pieces.get(targetPiece).x = (currentPoint.getX() - dX);
			pieces.get(targetPiece).y = (currentPoint.getY() - dY);
			
			
			
			// Adds bounds to the pieces
			if(pieces.get(targetPiece).x + pieces.get(targetPiece).getWidth() > (double) WIDTH) {
				pieces.get(targetPiece).x = (double) WIDTH - pieces.get(targetPiece).getWidth();
			}
			else if(pieces.get(targetPiece).x < 0) {
				pieces.get(targetPiece).x = 0;
			}
			
			if(pieces.get(targetPiece).y + pieces.get(targetPiece).getHeight() > (double) (HEIGHT-50)) {
				pieces.get(targetPiece).y = (double) (HEIGHT - 50) - pieces.get(targetPiece).getHeight();
			}
			else if(pieces.get(targetPiece).y < 0) {
				pieces.get(targetPiece).y = 0;
			}
			
			repaint();
			
		}
	}
}