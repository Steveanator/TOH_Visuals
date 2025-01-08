package main;

public class Board {
	private int board[][];
	private double spacing = GamePanel.pole_spacing;
	
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
		
		int originalRow = -1;
		int originalCol = -1;
		
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[row].length; col++) {
				if(board[row][col] == pieceValue) {
					originalRow = row;
					originalCol = col;
					break;
				}
			}
		}
		
		if(originalRow < 0 || originalCol < 0) {
			return;
		}
		
		// This actually moves the pieces
		board[originalRow][originalCol] = 0;
		board[newRow][newCol] = pieceValue;
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
