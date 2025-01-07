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
	
	public int piecesAmount = 3;
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	private int s = (int) (GamePanel.WIDTH / 4);
	private ArrayList<Piece> pieces;
	
	private int board[][];
	
	
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
		pieces.add(new Piece(2));
		pieces.add(new Piece(3));
		
		board = new int[piecesAmount][3];
		
		intializeBoard();
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(92, 47, 3));
		g2.fillRect(0, HEIGHT-50, WIDTH, 50);
		g2.fillRoundRect(s, 200, 30, HEIGHT-200, 20, 20);
		g2.fillRoundRect(s*2, 200, 30, HEIGHT-200, 20, 20);
		g2.fillRoundRect(s*3, 200, 30, HEIGHT-200, 20, 20);
		
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
	
	private void intializeBoard() {
		// Works maybe ???
		int count = 1;
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[row].length; col++) {
				if(col == 0) {
					board[row][col] = count;
					count++;
				}
				else {
					board[row][col] = 0;
				}
			}
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
		
		public void mouseReleased(MouseEvent e) {
			targetPiece = -1;
			repaint();
		}
	}
	
	
	private class DragListener extends MouseMotionAdapter {
		
		public void mouseDragged(MouseEvent e) {
			Point currentPoint = e.getPoint();
			System.out.println(dX);
			
			
			pieces.get(targetPiece).x = (currentPoint.getX() - dX);
			//System.out.println(pieces.get(targetPiece).x);
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