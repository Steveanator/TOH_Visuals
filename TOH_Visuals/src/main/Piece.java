package main;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Piece extends RoundRectangle2D {
	
	
	private int value;
	private boolean movable;
	private Color color;
	public double x,y;
	private double WIDTH;
	private double HEIGHT = 50;
	private double arcHeight = 30;
	private double arcWidth = 30;
	private Color colorGradient[] = new Color[9];
	
	
	public Piece(int value) {
		
		intializeColorGradient();
		
		this.value = value;
		
		WIDTH = 60 + value*30;
		color = colorGradient[value-1];
		
		x = 0;
		y = 0;
		movable = false;
	}
	
	private void intializeColorGradient() {
		colorGradient[0] = new Color(255,0,0); // Red
		colorGradient[1] = new Color(255,127,0); // Orange
		colorGradient[2] = new Color(255,255,0); // Yellow
		colorGradient[3] = new Color(0,255,0); // Green
		colorGradient[4] = new Color(0,206,209); // Turquoise
		colorGradient[5] = new Color(0,0,255); // Blue
		colorGradient[6] = new Color(75,0,130); // Indigo
		colorGradient[7] = new Color(148,0,211); // Violet
		colorGradient[8] = new Color(255,20,147); // Pink
	}
	
	public void setMovable(boolean bool) {
		movable = bool;
	}
	
	public boolean isMovable() {
		return movable;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}

	@Override
	public Rectangle2D getBounds2D() {
		Rectangle2D  rect = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
		
		return rect;
	}

	@Override
	public double getArcWidth() {
		
		return arcWidth;
	}

	@Override
	public double getArcHeight() {
		return arcHeight;
	}

	@Override
	public void setRoundRect(double x, double y, double w, double h, double arcWidth, double arcHeight) {
		
		this.x = x;
		this.y = y;
		this.WIDTH = w;
		this.HEIGHT = h;
		this.arcWidth = arcWidth;
		this.arcHeight = arcHeight;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getWidth() {
		return WIDTH;
	}

	@Override
	public double getHeight() {
		return HEIGHT;
	}

	@Override
	public boolean isEmpty() {
		if(WIDTH > 0 && HEIGHT > 0) {
			return true;
		}
		return false;
	}

	
	
}