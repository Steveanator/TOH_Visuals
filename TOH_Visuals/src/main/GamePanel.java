package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements MouseListener {
	
	public int piecesAmount = 1;
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	private int s = (int) (GamePanel.WIDTH / 4);
	private ArrayList<Piece> pieces;
	private ArrayList<Shape> shapes;
	
	
	public GamePanel() {
		addMouseListener(this);
		// Panel settings
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(new Color(204, 102, 0));
		this.setLayout(null);
		/*
		pieces = new Piece[piecesAmount];
		for(int i = 0; i < piecesAmount; i++) {
			pieces[i] = new Piece(i+1);
			pieces[i].x = s-60;
			pieces[i].y = 570;
		}
		*/
		
		pieces = new ArrayList<Piece>();
		
		
		pieces.add(new Piece(1));
		
		shapes = new ArrayList<Shape>();

        shapes.add(new RoundRectangle2D.Double(pieces.get(0).x, pieces.get(0).y, pieces.get(0).getWidth(), pieces.get(0).getHeight(), pieces.get(0).getArcWidth(), pieces.get(0).getArcHeight()));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(92, 47, 3));
		g2.fillRect(0, GamePanel.HEIGHT-50, GamePanel.WIDTH, 50);
		g2.fillRoundRect(s, 200, 30, 500, 20, 20);
		g2.fillRoundRect(s*2, 200, 30, 500, 20, 20);
		g2.fillRoundRect(s*3, 200, 30, 500, 20, 20);
		//pieces[0].draw(g2);
		
		for (int i = 0; i < pieces.size(); i++) {
            g2.setColor(Color.red);
            Shape shape = shapes.get(i);
            
            g2.fill(shape);
        }
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < shapes.size(); i++) {
            if (shapes.get(i).contains(e.getPoint())) {
                System.out.println("Clicked shape " + (i+1));
            }
            else {
            	System.out.println("Mouse clicked");
            }
        }
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}