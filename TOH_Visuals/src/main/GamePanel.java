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
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	public static int FPS = 30;
	public static int piecesAmount = 9;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 940;
	public static final int pole_spacing = (int) (WIDTH / 4);
	public static ArrayList<Piece> pieces;
	private Board board;
	
	
	
	private int targetPiece = -1;
	private Point previousPoint;
	
	private double dX, dY;
	
	Shape button;
	
	
	public GamePanel() {
		
		MouseListener mouseListener = new MouseListener();
		this.addMouseListener(mouseListener);
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
		// Panel settings
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(new Color(204, 102, 0));
		this.setLayout(null);
		
		button = new Rectangle2D.Double(WIDTH - 50, 0, 50, 50);
		
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
		
		g2.setColor(Color.DARK_GRAY);
		g2.fill(button);
		
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
			if(button.getBounds2D().contains(e.getPoint())) {
				board.solve(piecesAmount, 2);
			}
			
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
			if(targetPiece != -1) {
				if(pieces.get(targetPiece).isMovable()) {
					board.movePiece(targetPiece + 1);
				}
			}
			
			board.setLocations();
			targetPiece = -1;
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
			
			//repaint();
			
		}
	}
	
	public class Board {
		private static int board[][];
		private double spacing = GamePanel.pole_spacing;
		private int BOTTOMROW = GamePanel.piecesAmount -1;
		
		private int SOLUTIONPOLE = 2;
		//private int OPPOSITEPOLE = 1;
		
		public Board() {
			
			board = new int[GamePanel.piecesAmount][3];
			
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
		public void movePiece(int pieceValue) {
			
			double x = GamePanel.pieces.get(pieceValue-1).x;
			int newRow = -1;
			int newCol = -1;
			
			if((x <= spacing || x <= (spacing + spacing*2) / 2 ) && !(x >= (spacing + spacing*2) / 2 && !(x >= (spacing*2 + spacing*3) / 2))) {
				newCol = 0;
			}
			else if((x >= (spacing + spacing*2) / 2 || x <= (spacing*2 + spacing*3) / 2) && !(x >= (spacing*2 + spacing*3) / 2 )) {
				newCol = 1;
			}
			else if(x >= (spacing*2 + spacing*3) / 2 || x <= GamePanel.WIDTH) {
				newCol = 2;
			}
			
			if(newCol == -1) {
				return;
			}
			
			for(int row = board.length - 1; row >= 0; row--) {
				if(row != board.length - 1) {
					if((board[row+1][newCol] > pieceValue) && (board[row][newCol] == 0 || board[row][newCol] == pieceValue)) {
						newRow = row;
						break;
					}
				}
				else {
					if(board[row][newCol] == 0 || board[row][newCol] == pieceValue) {
						newRow = row;
						break;
					}
				}
			}
			
			if(newRow < 0) {
				System.out.println("Couldn't move there!");
				return;
			}
			
			int originalRow = findRow(pieceValue);
			int originalCol = findCol(pieceValue);
			
			if(originalRow < 0 || originalCol < 0) {
				return;
			}
			
			// This actually moves the pieces
			board[originalRow][originalCol] = 0;
			board[newRow][newCol] = pieceValue;
		}
		
		private int findRow(int piece) {
			if(piece > GamePanel.piecesAmount || piece <= 0) {
				return -1;
			}
			
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[row].length; col++) {
					if(board[row][col] == piece) {
						return row;
					}
				}
			}
			
			return -1;
		}
		
		private int findCol(int piece) {
			if(piece > GamePanel.piecesAmount || piece <= 0) {
				return -1;
			}
			
			int row = findRow(piece);
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == piece) {
					return col;
				}
			}
			
			return -1;
		}
		
		public boolean solve(int piece, int col) {
			if(piece > GamePanel.piecesAmount || piece <= 0) {
				System.out.println("Error Solver: Piece Out doesn't exist or unable to move");
				return false;
			}
			if(col < 0 || col > 2) {
				System.out.println("Error Solver: Col Out of bounds");
				return false;
			}
			
			if(isSolved(piece, col)) {
				return true;
			}
			
			if(piece == 1) {
				sudoMovePiece(piece, col);
				return true;
			}
			
			int pieceRow = findRow(piece);
			int pieceCol = findCol(piece);
			
			if(pieceRow == -1 || pieceCol == -1) {
				System.out.println("Error Solve: Could not find pieceRow or pieceCol");
				return false;
			}
			
			// Has piece on top
			if(pieceRow != 0 && board[pieceRow-1][pieceCol] != 0) {
				if(!swapPoles(pieceCol)) {
					System.out.println("Error Solve: Could not swap poles");
					return false;
				}
				solve(board[pieceRow-1][pieceCol], SOLUTIONPOLE);
				if(!swapPoles(pieceCol)) {
					System.out.println("Error Solve: Could not swap poles");
					return false;
				}
			}
			
			
			// If piece is occupying space
			if(!sudoMovePiece(piece, col)) {
				int annoyingPiece = board[BOTTOMROW][SOLUTIONPOLE];
				if(!swapPoles(SOLUTIONPOLE)) {
					System.out.println("Error Solve: Could not swap poles");
					return false;
				}
				
				solve(annoyingPiece, SOLUTIONPOLE);
				if(!swapPoles(pieceCol)) {
					System.out.println("Error Solve: Could not swap poles");
					return false;
				}
				
				System.out.println("Solution er : " + SOLUTIONPOLE);
				solve(piece, SOLUTIONPOLE);
			}
			solve(piece-1, SOLUTIONPOLE);
			
			return true;
		}
		
		private boolean swapPoles(int originPole) {
			int POLETHREE = 2;
			int POLETWO = 1;
			int POLEONE = 0;
			
			switch (originPole) {
			case 0: // POLE ONE
				if(SOLUTIONPOLE == POLETHREE) {
					SOLUTIONPOLE = POLETWO;
					//OPPOSITEPOLE = POLETHREE;
				}
				else {
					SOLUTIONPOLE = POLETHREE;
					//OPPOSITEPOLE = POLETWO;
				}
				break;
			case 1: // POLE TWO
				if(SOLUTIONPOLE == POLETHREE) {
					SOLUTIONPOLE = POLEONE;
					//OPPOSITEPOLE = POLETHREE;
				}
				else {
					SOLUTIONPOLE = POLETHREE;
					//OPPOSITEPOLE = POLEONE;
				}
				break;
			case 2: // POLE THREE
				if(SOLUTIONPOLE == POLEONE) {
					SOLUTIONPOLE = POLETWO;
					//OPPOSITEPOLE = POLEONE;
				}
				else {
					SOLUTIONPOLE = POLEONE;
					//OPPOSITEPOLE = POLETWO;
				}
				break;
			default:
				System.out.println("Error Swapper: Could not find origin pole");
				return false;
			}
			
			return true;
		}
		
		// This is a function that the user cannot interact with as it will be used with the solve function
		private boolean sudoMovePiece(int piece, int col) {
			if(piece > GamePanel.piecesAmount || piece <= 0) {
				System.out.println("Error Moving Piece: Piece cannot be moved or doesn't exist");
				return false;
			}
			if(col < 0 || col > 2) {
				System.out.println("Error Moving Piece: Col Out of bounds");
				return false;
			}
			
			
			
			
			for(int row = BOTTOMROW; row >= 0; row--) {
				if(sudoLegalMove(piece, row, col)) {
					int originalRow = findRow(piece);
					int originalCol = findCol(piece);
					
					board[row][col] = piece;
					board[originalRow][originalCol] = 0;
					
					
					
					
					printBoard();
					System.out.println("-------");
					
					return true;

				}
			}
			
			//System.out.println("Error Move Piece: Cannot move piece");
			return false;
		}
		
		private boolean sudoLegalMove(int piece, int row, int col) {
			if(piece > GamePanel.piecesAmount || GamePanel.piecesAmount <= 0) {
				System.out.println("Error Legal Move: Piece Cannot be moved or does not exist");
				return false;
			}
			if(row > BOTTOMROW || row < 0) {
				System.out.println("Error Legal Move: Row Out of bounds");
				return false;
			}
			if( col < 0 || col > 2) {
				System.out.println("Error Legal Move: Col Out of bounds");
				return false;
			}
			
			// Piece already there
			if(board[row][col] == piece) {
				return true;
			}
			int pieceRow = findRow(piece);
			int pieceCol = findCol(piece);
			
			
			// If top of the row
			if(pieceRow != 0 && board[pieceRow-1][pieceCol] != 0) {
				//System.out.println("Above it");
				return false;
			}
			
			
			// Space is occupied
			if(board[row][col] != 0) {
				//System.out.println("Occupied");
				return false;
			}
			
			// If the piece below it is less than the piece we want to move, then it's an illegal move
			if(row != BOTTOMROW && board[row+1][col] < piece) {
				//System.out.println("Too low");
				return false;
			}
			
			return true;
		}
		
		// 
		public void sudoSolve() {
			int count = 1;
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[row].length; col++) {
					if(col ==2) {
						board[row][col] = count;
						count++;
					}
					else {
						board[row][col] = 0;
					}
				}
			}
		}
		
		public boolean isSolved(int value, int pole) {
			if(value > GamePanel.piecesAmount || value <= 0 || pole < 0 || pole > 2) {
				System.out.println("Error: IsSolved Out of bounds");
				return false;
			}
			if(board[value-1][pole] == value) {
				if(value == 1) {
					return true;
				}
				return isSolved(value-1, pole);
			}
			return false;
		}
		
		public void setLocations() {
			for(int row = 0; row < board.length; row++) {
				for(int col = board[row].length-1; col >= 0; col--) {
					if(board[row][col] > 0) {
						int index = board[row][col] - 1;
						GamePanel.pieces.get(index).y = (GamePanel.HEIGHT - 50) - (GamePanel.pieces.get(index).getHeight() * (board.length - row));
						GamePanel.pieces.get(index).x = spacing*(col+1) - (GamePanel.pieces.get(index).getWidth() - 30) / 2;
						if(row == 0) {
							GamePanel.pieces.get(index).setMovable(true);
						}
						else if(board[row-1][col] == 0) {
							GamePanel.pieces.get(index).setMovable(true);
						}
						else {
							GamePanel.pieces.get(index).setMovable(false);
						}
					}
				}
			}
		}
		
		public void printBoard() {
			for(int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[row].length; col++) {
					System.out.print(board[row][col]);
				}
				System.out.println();
			}
		}
	}

}