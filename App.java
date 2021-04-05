
public class App {
	public int[][] board = new int[4][4];
	public int empty = 16;
	public int moves = 0;
	public boolean valid = false;
	public int max;
	
	
	/**
	 * Adds a 2 or 4 to a random position in the array
	 */
	public void addNumber() {
		if (empty > 0) {
			int pos = (int) (Math.random() * 16); // Random int for position
			
			while (pos == 16 || board[pos / 4][pos % 4] != 0) {
				pos = (int) (Math.random() * 16);
			}
			
			int value = (int) (Math.random() * 5); // Random int for either 2 or 4
				if (value == 1) { // 0.2 probability
					value = 4;
				} else { // 0.8 probability
					value = 2;
				}
			
			board[pos / 4][pos % 4] = value;
			empty --; // deincrement number of empty cells
		}
	}
	
	/**
	 * Responds to command 'w' key or up arrow key
	 * @return true if a valid up move is made
	 */
	public boolean moveUp() {
		boolean valid = false;
		
		for(int j = 0; j < 4; j++) { 		
			int last = 0; // represents the last number not changed
			
			for(int i = 1; i < 4; i++) { 
				
				if(board[i][j] != 0) { // if cell is filled
					int k = i - 1; // represents cell one row above
					
					while(k >= 0) { // while cell is at a valid index
						
						if(board[k][j] == 0) { // if cell is blank
							if(k == 0) { // if cell is in row 0
								// upper cell takes value of lower cell
								board[k][j] = board[i][j];
								board[i][j] = 0;  // lower cell becomes 0
								last = k; // the last number not changed is the top
								valid = true; // a valid move has been made
								break;
							} else {
								k--; // else, move to the upper cell
								continue;
							}
						// if the lower cell value is the same as the upper cell 
						} else if(board[k][j] == board[i][j] && k >= last) { 
							board[i][j] = 0; // lower cell becomes 0
							board[k][j] *= 2; // upper cell value is doubled
							last = k; // the last number not changed is row 'k'
							empty++; // increment number of empty cells
							valid = true; // a valid move has been made
							break;
						} else if((k + 1) != i){ // if values are not the same
							int value = board[i][j]; // temporary variable
							board[i][j] = 0; // original cell becomes 0
							board[k + 1][j] = value; // new cell gets original value
							valid = true; // a valid move has been made
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		return valid;
	}
	
	/**
	 * Responds to command 's' key or down arrow key
	 * @return true if a valid down move is made
	 */
	public boolean moveDown() {
		boolean valid = false;
		
		for(int j = 0; j < 4; j++) {
			int last = 3; // represents the last number not changed
			
			for(int i = 2; i >= 0; i--) {				
				
				if(board[i][j] != 0) { // if cell is filled
					int k = i + 1; // the cell one row below
					
					while(k < 4) { // while cell index is valid
						
						if(board[k][j] == 0) { // if cell is blank
							if(k == 3) { // if cell is in row 3
								// lower cell takes value of upper cell
								board[k][j] = board[i][j]; 
								board[i][j] = 0; // upper cell becomes 0
								last = k; // last number not changed is the bottom
								valid = true; // a valid move has been made
								break;
							} else {
								k++; // else, move to cell below
								continue;
							}
						// if the upper cell value is the same as the lower cell
						} else if(board[k][j] == board[i][j] && k <= last) {
							board[i][j] = 0; // upper cell becomes 0
							board[k][j] *= 2; // lower cell is doubled
							last = k; // last number not changed is row 'k'
							empty++; // increment empty cells
							valid = true; // a valid move has been made
							break;
						} else if((k - 1) != i) { // if values are not the same
							int value = board[i][j]; // temporary variable
							board[i][j] = 0; // original cell becomes 0
							board[k - 1][j] = value; // new cell gets original value
							valid = true; // a valid move has been made
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		
		return valid;
	}
	
	/**
	 * Responds to command 'd' or right arrow key
	 * @return true if a valid right move has been made
	 */
	public boolean moveRight() {
		boolean valid = false;
		
		for(int i = 0; i < 4; i++) {
			int last = 3; // represents the last number not changed
			
			for(int j = 2; j >= 0; j--) {				
				
				if(board[i][j] != 0) { // if the cell is filled
					int k = j + 1; // represents the column to the right
					
					while(k < 4) { // while the cell index is valid
						
						if(board[i][k] == 0) { //if the cell is blank
							if(k == 3) { // if the cell is in right most column
								// right cell gets value of original cell
								board[i][k] = board[i][j]; 
								board[i][j] = 0; // original cell becomes 0
								last = k; // last number not changed is right column
								valid = true; // a valid move has been made
								break;
							} else { 
								k++; // else, move to the right
								continue;
							}
						// if left cell value is the same as the right cell
						} else if(board[i][k] == board[i][j] && k <= last){
							board[i][j] = 0; // original cell becomes 0
							board[i][k] *= 2; // right cell is doubled
							last = k; // last number not merged is column 'k'
							empty++; // increment number of empty cells
							valid = true; // a valid move has been made
							break;
						} else if((k - 1) != j) { // if values are not the same
							int value = board[i][j]; // a temporary variable
							board[i][j] = 0; // original cell becomes 0
							board[i][k - 1] = value; // new cell gets original value
							valid = true; // a valid move has been made
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		
		return valid;
	}
	
	/**
	 * Responds to the command 'a' key and the left arrow key
	 * @return true if a valid left move is made
	 */
	public boolean moveLeft() {
		boolean valid = false;
	
		for(int i = 0; i < 4; i++) {
			int last = 0; // represents the last number not changed
			
			for(int j = 1; j < 4; j++) {				
				
				if(board[i][j] != 0) { // if the cell is filled
					int k = j - 1; // represents the column to the left
					
					while(k >= 0) { // while the cell index is valid
						
						if(board[i][k] == 0) { // if the cell is blank
							if(k == 0) { // if the cell is the left most column
								// the left cell gets the value of the right cell
								board[i][k] = board[i][j]; 
								board[i][j] = 0; // the original cell becomes 0
								last = k; // last number not changed is left column
								valid = true; // a valid move has been made
								break;
							} else {
								k--; // else, move the left
								continue;
							}
						// if the right cell value is the same as the left cell
						} else if(board[i][k] == board[i][j] && k >= last){
							board[i][j] = 0; // original cell becomes 0
							board[i][k] *= 2; // left cell is doubled
							last = k; // last number not changed is column 'k'
							empty++; // increment empty cells
							valid = true; // a valid move has been made
							break;
						} else if((k + 1) != j) { // if the values are not the same
							int value = board[i][j]; // temporary variable
							board[i][j] = 0; // original cell becomes 0
							board[i][k + 1] = value; // new cell gets original value
							valid = true; // a valid move has been made
							break;
						} else {
							break;
						}
					}
				}
			}
		}
		
		return valid;
	}
	
	/** 
	 * 	Ensures move is valid
	 * 	If true, adds a number and increments moves
	 */
	public void move(String string) {
		switch(string) {
		case "up": valid = moveUp();
			break;
		case "down": valid = moveDown();
			break;
		case "right": valid = moveRight();
			break;
		case "left": valid = moveLeft();
		}
		
		if (valid) {
			addNumber();
			moves++;
			System.out.println(" VALID");
			System.out.println("Moves: " + getMoves());
			System.out.println("Max: " + getMax());
		}
		if (!valid) {
			System.out.println(" INVALID");
		}
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public int getMax() {
		for (int i = 0; i < 16; i ++) {
			if (board[i / 4][i % 4] > max) {
				max = board[i / 4][i % 4];
			}
		}
		return max;
					
	}
	
	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	/**
	 * Checks if the game has been lost
	 * @return true if there are no empty positions and a move is invalid
	 */
	public boolean gameOver() {
		if (empty <= 0 && valid == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the game has been won
	 * @return true if the max value in the array is 2048
	 */
	public boolean won() {
		if (getMax() == 2048) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		App app = new App();
		int [][] board = new int[4][4];
		app.setBoard(board);
	}
}

