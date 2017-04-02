package solution;


import java.util.ArrayList;
import java.util.Set;

public class Board {

	private int size;                        // board size
	private ArrayList<Knight> knight;    // list of knight on the board
	private Box board[][];            // matrix size x size of boxes
	private int emptyBoxes;             // Available boxes counter 
	
	/**
	 * Creates an instance of size board and places the knights
	 * @param size
	 * @param knight
	 */
	public Board(int size, ArrayList<Knight> knight) {
		this.knight = knight;
		this.size = size;
		this.board = new Box[this.size][this.size];
		this.emptyBoxes = this.size*this.size;
		initialize();
	}
	
	/**
	 * 
	 * @param t
	 */
	public Board(Board t) {
		this.knight = t.knight;
		this.size = t.size;
		this.board = createCopy(t.size, t.board);
		this.emptyBoxes = t.emptyBoxes;
	}
	
	/**
	 * Creates a copy of the board matrix
	 * @param size
	 * @param tab
	 * @return
	 */
	private Box[][] createCopy(int size, Box[][] tab) {
		Box newBoard[][] = new Box[size][size];
		
		for (int i=0; i < size; i ++) {
			for (int j=0; j < size; j ++) {
				Box c = new Box(i,j);
				c.status = tab[i][j].status;
				c.threatQty = tab[i][j].threatQty;
				
				newBoard[i][j] = c;
			}
			
		}
		return newBoard;
		
	}
	
	/**
	 * Initializes the positions on the board and places the starting knights
	 */
	private void initialize() {
		for (int i=0; i < this.size; i++) {
			for (int j=0; j < this.size; j++) {
				this.board[i][j] = new Box(i,j);
			}
		}
		
		placeKnightsOnBoard(this.knight);
	}
	
	/**
	 * Receives a list of knights and places them on board
	 * @param knight
	 */
	private void placeKnightsOnBoard(ArrayList<Knight> knight) {
		for (Knight c: knight) {
			placeKnightsOnBoard(c);
		}
	}

	/**
	 * Place the Knight c on the board
	 * @param c
	 */
	private void placeKnightsOnBoard(Knight c) {
		
		Box position = c.getBox();
		occupyBoxWith(position.x, position.y, Box.KNIGHT);
		
		for (Box threat : c.getCoveredPossitions()) {
			int x = threat.x;
			int y = threat.y;

			if (isIntoBoard(x,y)) {
				if (board[x][y].status == Box.EMPTY) {
					occupyBoxWith(x,y,Box.THREAT);
				}
				
				board[x][y].threatQty = board[x][y].threatQty+1;
				
				}
			}
		}
	
	/**
	 * Add the knight and place it on the board
	 * @param c
	 */
	public void addKnight(Knight c) {
		this.knight.add(c);
		placeKnightsOnBoard(c);
	}
	
	public int emptyBoxes() {
		return emptyBoxes;
	}
	
	public int size() {
		return this.size;
	}
	
	
	public boolean solved() {
		return (this.emptyBoxes == 0);
	}

	private void occupyBoxWith(int x, int y, char c) {
		if (board[x][y].status == Box.EMPTY) {
			this.emptyBoxes = this.emptyBoxes-1;
		}
		board[x][y].status = c;
	}
	
	private void liberateBox(int x, int y) {
		board[x][y].status = ' ';
		this.emptyBoxes = this.emptyBoxes+1;
	}
	
	
	public boolean isIntoBoard(int x, int y) {
		return x >= 0 && x < this.size && y >= 0 && this.size > y;
	}
	
	public void removeKnight(Knight knight) {
		this.knight.remove(knight);
		
		Set<Box> boxesToCleanUp = knight.getCoveredPossitions();
		
		for (Box c: boxesToCleanUp) {
			
			if (isIntoBoard(c.x, c.y)) {
				Box cboard = this.board[c.x][c.y];
				
				if (cboard.threatQty > 0) {
					cboard.threatQty = cboard.threatQty - 1;
				} 
				
				if (cboard.status == Box.THREAT && cboard.threatQty == 0) {
					liberateBox(cboard.x, cboard.y);
				}
			}
		}
		
		Box actual = knight.getBox();
		Box cboard = this.board[actual.x][actual.y];
		
		if (cboard.status == Box.KNIGHT) {
			if (cboard.threatQty == 0) {
				liberateBox(cboard.x, cboard.y);
			} else {
				cboard.status = Box.THREAT;
			}
		} 
		
	}
	
	/**
	 * Test if the box c is empty.
	 * @param c
	 * @return
	 */
	public boolean isBoxFree(Box c) {
		return this.board[c.x][c.y].status == Box.EMPTY; 
	}
	
	/**
	 * Returns true if the box is free or threatened by a knight
	 * @param c
	 * @return
	 * @complexity O(1)
	 */
	public boolean isBoxFreeOrThreatened(Box c) {
		return this.isBoxFree(c) || this.board[c.x][c.y].status == Box.THREAT;
	}
	
	 
	private void printBoard(Box[][] board) {
		for (int i=0; i< this.size; i++) {
			System.out.print("|");
			for (int j=0; j<this.size; j++) {
				System.out.print(board[i][j].status+"|");
			}
			System.out.println("\n----------");
		}
	}
	
	public void print() {
		printBoard(this.board);
	}
	
	
}
