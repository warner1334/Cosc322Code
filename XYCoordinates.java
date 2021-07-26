package ubc.cosc322;

import java.util.ArrayList;
import java.util.Comparator;

public class XYCoordinates{
	public int x, y;
	// constructor
	public XYCoordinates(int x, int y)  {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public ArrayList<Integer> getXY() {
		ArrayList<Integer> xy = new ArrayList<Integer>();
		xy.add(x);
		xy.add(y);
		return xy;
	}
	
	
	
	@Override
	public String toString() {
		return x + "," + y;
	}

	
}