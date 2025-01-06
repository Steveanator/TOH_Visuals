package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class Piece extends RoundRectangle2D {
	
	
	private int value;
	private Color color;
	public double x,y;
	private double WIDTH,HEIGHT;
	private double arcHeight, arcWidth;
	
	
	public Piece(int value) {
		this.value = value;
		
		switch (value) {
			case 1:
				color = Color.red;
				WIDTH = 150;
				HEIGHT = 100;
				break;
			case 2:
				color = Color.green;
				WIDTH = 300;
				HEIGHT = 100;
				break;
			case 3:
				color = Color.blue;
				WIDTH = 300;
				HEIGHT = 100;
				break;
			default:
				color = Color.yellow;
				WIDTH = 300;
				HEIGHT = 100;
		}
		
		x = 50;
		y = 50;
		
		arcHeight = 30;
		arcWidth = 30;
	}

	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
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