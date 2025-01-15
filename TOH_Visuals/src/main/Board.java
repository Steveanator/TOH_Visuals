package main;

import java.awt.Point;

public class Board {
	private boolean board[][];
	private double spacing = GamePanel.pole_spacing;
	private int BOTTOMROW = GamePanel.piecesAmount -1;
	
	public Board() {
		
		board = new boolean[GamePanel.piecesAmount][3];
		
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[row].length; col++) {
				if(col == 0) {
					board[row][col] = true;
				}
				else {
					board[row][col] = false;
				}
			}
		}
		
		
	}
	public void movePiece(int pieceIndex, Point point) {
		
		double x = point.getX();
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
			System.out.println("Error movePiece: Col cannot be found");
			return;
		}
		
		if(!(sudoMovePiece(pieceIndex, newCol))) {
			System.out.println("Error movePiece: Piece cannot be moved");
		}
	}
	
	private int findCol(int pieceIndex) {
		if(pieceIndex >= GamePanel.piecesAmount || pieceIndex < 0) {
			System.out.println("Error findCol: pieceIndex out of bounds");
			return -1;
		}
		
		for(int col = 0; col < board[pieceIndex].length; col++) {
			if(board[pieceIndex][col] == true) {
				return col;
			}
		}
		
		System.out.println("Error findCol: Could not find piece");
		return -1;
	}
	
	public boolean solve(int pieceIndex, int targetCol) {
		if(pieceIndex >= GamePanel.piecesAmount || pieceIndex < 0) {
			System.out.println("Error Solver: Piece doesn't exist or unable to move");
			return false;
		}
		if(targetCol < 0 || targetCol > 2) {
			System.out.println("Error Solver: Col Out of bounds");
			return false;
		}
		
		if(isSolved(pieceIndex, targetCol)) {
			return true;
		}
		
		if(pieceIndex == 0) {
			return sudoMovePiece(pieceIndex, targetCol);
		}
		
		int pieceCol = findCol(pieceIndex);
		int oppositeCol = -1;
		
		switch(pieceCol) {
			case 0:
				if(targetCol == 1) {
					oppositeCol = 2;
					break;
				}
				oppositeCol = 1;
				break;
			case 1:
				if(targetCol == 0) {
					oppositeCol = 2;
					break;
				}
				oppositeCol = 0;
				break;
			case 2:
				if(targetCol == 0) {
					oppositeCol = 1;
					break;
				}
				oppositeCol = 0;
				break;
			default:
				System.out.println("Error Solve: Could not set opposite pole");
				return false;
		}
		if(findCol(pieceIndex) != targetCol) {
			while(!(sudoMovePiece(pieceIndex, targetCol))) {
				
				// Check piece ontop 
				for(int row = pieceIndex -1; row >= 0; row--) {
					if(board[row][pieceCol]) {
						solve(row, oppositeCol);
					}
					
					if(board[row][targetCol]) {
						solve(row, oppositeCol);
					}
				}
			}
		}
		return solve(pieceIndex-1, targetCol);
	}
	
	// This is a function that the user cannot interact with as it will be used with the solve function
	private boolean sudoMovePiece(int pieceIndex, int col) {
		if(pieceIndex >= GamePanel.piecesAmount || pieceIndex < 0) {
			System.out.println("Error Moving Piece: Piece cannot be moved or doesn't exist");
			return false;
		}
		if(col < 0 || col > 2) {
			System.out.println("Error Moving Piece: Col Out of bounds");
			return false;
		}
		
		if(legalMove(pieceIndex, col)) {
			int originalCol = findCol(pieceIndex);
			
			board[pieceIndex][col] = true;
			board[pieceIndex][originalCol] = false;
			
			printBoard();
			System.out.println("------");
			
			return true;

		}
		
		//System.out.println("Error Move Piece: Cannot move piece");
		return false;
	}
	
	private boolean legalMove(int pieceIndex, int col) {
		if(pieceIndex >= GamePanel.piecesAmount || pieceIndex < 0) {
			System.out.println("Error Legal Move: Piece Cannot be moved or does not exist");
			return false;
		}
		if( col < 0 || col > 2) {
			System.out.println("Error Legal Move: Col Out of bounds");
			return false;
		}
		
		// Piece already there
		if(board[pieceIndex][col] == true) {
			//System.out.println("Error legalMove: Piece is already there!");
			return true;
		}
		int pieceCol = findCol(pieceIndex);
		
		
		// If not top of the row
		if(pieceIndex != 0) {
			for(int row = pieceIndex-1; row >=0; row--) {
				// Piece is above the piece we are trying to move
				if(board[row][pieceCol] == true) {
					return false;
				}
			}
			//System.out.println("Above it");
		}
		
		for(int row = pieceIndex - 1; row >=0; row--) {
			// Piece of a lower value is on that pole
			if(board[row][col] == true) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isSolved(int pieceIndex, int col) {
		if(pieceIndex >= GamePanel.piecesAmount || pieceIndex < 0 || col < 0 || col > 2) {
			System.out.println("Error isSolved:  Out of bounds");
			return false;
		}
		if(board[pieceIndex][col] == true) {
			if(pieceIndex == 0) {
				return true;
			}
			return isSolved(pieceIndex-1, col);
		}
		
		return false;
	}
	
	public void setLocations() {
		// Set the X cords for all the pieces
		for(int row = 0; row < GamePanel.piecesAmount; row++) {
			int col = findCol(row);
			GamePanel.pieces.get(row).x = spacing*(col+1) - (GamePanel.pieces.get(row).getWidth() - 30) / 2;
		}
		
		// Sets the Y cords for all the pieces
		for(int col = 0; col <= 2; col++) {
			double y = (GamePanel.HEIGHT - 50) - GamePanel.pieces.get(0).getHeight();
			for(int row = BOTTOMROW; row >= 0; row--) {
				if(board[row][col]) {
					GamePanel.pieces.get(row).y = y;
					y-=GamePanel.pieces.get(0).getHeight();
				}
			}
			y = (GamePanel.HEIGHT - 50) - GamePanel.pieces.get(0).getHeight();
		}
		
		// Set top pieces movable
		for(int col = 0; col <= 2; col++) {
			boolean pieceFound = false;
			for(int row = 0; row < GamePanel.piecesAmount; row++) {
				if(board[row][col]) {
					if(pieceFound) {
						GamePanel.pieces.get(row).setMovable(false);
					}
					else {
						GamePanel.pieces.get(row).setMovable(true);
						pieceFound = true;
					}
				}
			}
			pieceFound = false;
		}
	} 
	
	public void printBoard() {
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[row].length; col++) {
				if(board[row][col]) {
					System.out.print(1);
				}
				else {
					System.out.print(0);
				}
				if(col != 2) {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
}
