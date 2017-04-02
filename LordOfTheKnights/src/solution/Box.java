package solution;


public class Box {
	public int x, y;
	public char status;    // State of the Box - May be EMPTY, KNIGHT or THREATENED
	int threatQty;  // Amount of threats that the box receives (does not count if it has a horse)

	// Constants with possible states.
	public static char EMPTY = ' ';
	public static char KNIGHT = 'K';
	public static char THREAT = 'T';

	
	/**
	 * Create an empty box with the x and y positions.
	 * @param x
	 * @param y
	 */
	public Box (int x, int y) {
		this.x = x;
		this.y = y;
		this.status = Box.EMPTY;
		this.threatQty = 0;
	}


	@Override
	public String toString() {
		return "Box(" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    
	    final Box other = (Box) obj;
	    
	    if (this.x != other.x) {
	        return false;
	    }
	    
	    if (this.y != other.y) {
	        return false;
	    }
	    
	    return true;
	}
	
}
