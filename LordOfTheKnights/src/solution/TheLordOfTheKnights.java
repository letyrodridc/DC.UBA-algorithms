package solution;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class TheLordOfTheKnights {

	private Board board;

	private ArrayList<Knight> possibleSolution; // solucion temporal del backtracking
	private ArrayList<Knight> finalSolution;    // solucion final del tablero
	
	/**
	 * Take a board size and a list of knight and build an instance of the Lord of Knights
	 * @param size
	 * @param knights
	 */
	public TheLordOfTheKnights(int size, ArrayList<Knight> knights) {
		this.board = new Board(size, knights);
		
		this.finalSolution = new ArrayList<Knight>(size*size);
		this.possibleSolution = new ArrayList<Knight>(size*size);
		
	}
	
	/**
	 *Build an instance of the Lord of Knights from a test file
	 * @param filename 
	 */
	public TheLordOfTheKnights(String filename) {
		createFromFile(filename);
	}
	
	/**
	 * Start the class variables with the test file information provided in filename
	 * @param filename 
	 */
	private void createFromFile(String filename) {
		ArrayList<Knight> knights = new ArrayList<Knight>();
		int tam = 0;
		
		this.finalSolution = new ArrayList<Knight>();
		this.possibleSolution = new ArrayList<Knight>();
		
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(filename));
			 
			String readline;
			int lineNbr = 0;
			int k = 0;
			
			while ((readline = br.readLine()) != null) {
			    String[] tokens = readline.split(" ");
			     
				if (lineNbr == 0) { 
					// Primer linea
					tam = Integer.parseInt(tokens[0]);
					k = Integer.parseInt(tokens[1]);
					
				} else {
					if (lineNbr <= k) {
						int i = Integer.parseInt(tokens[0])-1;
						int j = Integer.parseInt(tokens[1])-1;
						
						knights.add(new Knight(new Box(i,j)));
					}
				}
				
				lineNbr ++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		
		this.board = new Board(tam,knights);
		
	}

	
	/**
	 * Solve the Lord of the Horses and return the time it took
	 * @return
	 * @throws IOException
	 */
	public long solve() throws IOException {
		long timestart = System.nanoTime();

		// Create a copy of the initial board
		Board tmpBoard = new Board(this.board);
		
		// Pruning: Before entering the backtracking I will only consider possible boxes that do not have horses
		ArrayList<Box> possibleBoxes = possibleBoxes();
		
		// Start backtrack 
		backtrack(tmpBoard, possibleBoxes,0);
		long timeend = System.nanoTime();
		return timeend-timestart;
	}
	
	/**
	 * BackTrack algorithm to solve the Lord of Knights
	 * @param tmpBoard
	 * @param possibleBoxes
	 * @param next
	 * @throws IOException
	 */
	
	private void backtrack(Board tmpBoard, ArrayList<Box> possibleBoxes, int next) throws IOException {
	
		if (tmpBoard.solved()) {	
			// Solution Found
			if ((this.finalSolution.size() == 0) || (this.finalSolution.size() != 0 && this.possibleSolution.size() < this.finalSolution.size())) {
				// If it is the first solution or the solution found is smaller than the one I found previously
				// update the final solution
				this.finalSolution = new ArrayList<Knight>(this.possibleSolution);
			
			}
		} else {
			// It is not solution
			// Pruning: If I already have a solution and I am analyzing possible solutions of greater or equal size, I discard it	
			if (finalSolution.size() != 0 && this.possibleSolution.size() >= this.finalSolution.size()) {
				return; 
			} else {
				if (next == possibleBoxes.size()) {
					return;
				} else {
					Box siguientePosible = possibleBoxes.get(next);
					
						
					// Option 1: I do not add Horse and I will next
					backtrack(tmpBoard, possibleBoxes, next+1);
					
					
					// Pruning - Check if the horse I'm about to put covers at least one free box, but I discard it.
					if (ifWereKnightCoversAFreePosition(siguientePosible,tmpBoard)) {
								
						// Option 2: I add Horse and continuous backtrack

						// I create the knight
						Knight knight = new Knight(siguientePosible);
						
						// I add to the possible solution
						this.possibleSolution.add(knight);
						
						// I add it to the temporary board
						tmpBoard.addKnight(knight);
						
						// Backtrack following from the next possible box
						backtrack(tmpBoard, possibleBoxes, next+1);
						
						// Back to the previous
						next = next -1;
						
						// I remove the knight from the temporary board
						tmpBoard.removeKnight(knight);
						
						// I remove the knight from the solution
						this.possibleSolution.remove(possibleSolution.size()-1);
					} 
				}
			}
		} 
		return;
	}
	
	
	/**
	 * Go through the board and return to the list of boxes that do not have knights
	 * @return
	 * @complexity O(n)
	 */
	private ArrayList<Box> possibleBoxes() {
		ArrayList<Box> freeBoxes = new ArrayList<Box>();
		for (int i = 0; i < this.board.size(); i++) {
			for (int j = 0; j < this.board.size(); j++) {
				if (this.board.isBoxFreeOrThreatened(new Box(i,j))) {
					freeBoxes.add(new Box(i,j));
				}
			}
		}
		return freeBoxes;
	}


	
	private boolean ifWereKnightCoversAFreePosition(Box casCaballo, Board tablero) {
		boolean cubre = false;
		Knight supuestoCaballo = new Knight(casCaballo);
		
		for (Box c : supuestoCaballo.getCoveredPossitions()) { // O(8)
			if (tablero.isIntoBoard(c.x,c.y))
				cubre = tablero.isBoxFree(c)  || cubre;
		}
		
		cubre = cubre || tablero.isBoxFree(supuestoCaballo.getBox());
		
		return cubre;
		
	}
	
		
	public ArrayList<Knight> getSolucion() {
		return this.finalSolution;
	}
	
	public String getSolutionStr() {
		StringBuilder result = new StringBuilder();
		
		result.append(this.finalSolution.size()+"\n");
		
		for (Knight c: this.finalSolution) {
			result.append(c.getBox().x+" "+c.getBox().y+"\n");
		}
		
		return result.toString();
	}
	
	public void printBoard() {
		this.board.print();
	}
	
	public void printSolution() {
		System.out.println(getSolutionStr());
	}

	public void getSolution(ArrayList<Knight> solution) {
		this.finalSolution = solution;
	}
}
