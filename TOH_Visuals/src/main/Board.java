package main;

public class Board {
	private int board[][];
	private double spacing = GamePanel.pole_spacing;
	private int BOTTOMROW = GamePanel.piecesAmount -1;
	
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
		if(piece > GamePanel.piecesAmount || piece < 0) {
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
	
	// This is a function that the user cannot interact with as it will be used with the solve function
	private void sudoMovePiece(int piece, int col) {
		if(piece > GamePanel.piecesAmount || piece <= 0) {
			System.out.println("Error Moving Piece: Piece cannot be moved or doesn't exist");
			return;
		}
		if(col < 0 || col > 2) {
			System.out.println("Error Moving Piece: Col Out of bounds");
			return;
		}
		
		for(int row = BOTTOMROW; row > 0; row--) {
			if(sudoLegalMove(piece, row, col)) {
				int originalRow = findRow(piece);
				int originalCol = findCol(piece);
				
				board[row][col] = piece;
				board[originalRow][originalCol] = 0;
				return;
			}
		}
		
		System.out.println("Error Move Piece: Cannot move piece");
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
