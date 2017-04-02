package solution;


import java.util.HashSet;
import java.util.Set;

public class Knight {
	private Box box;
	
	public Knight(Box box) {
		this.box = box;
	}
	
	@Override
	public String toString() {
		return "Knight [" + box + "]";
	}

	public Box getBox() { 
		return box;
	}
	
	/**
	 * Returns a set of the positions covered by the knight on a board
	 * @return
	 */
	public Set<Box> getCoveredPossitions() {
		HashSet<Box> result = new HashSet<Box>();
		
		int x = this.box.x;
		int y = this.box.y;
		
		result.add(new Box(x+1,y+2));
		result.add(new Box(x+1,y-2));
		result.add(new Box(x-1,y+2));
		result.add(new Box(x-1,y-2));
		result.add(new Box(x+2,y+1));
		result.add(new Box(x+2,y-1));
		result.add(new Box(x-2,y+1));
		result.add(new Box(x-2,y-1));
		
		return result;
	}

}
