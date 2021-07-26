package ubc.cosc322;

import java.util.ArrayList;
//Source used https://github.com/cosc-322-main-team/322GameOfAmazons/blob/7d3ff936baec290392193b74cdb7ac10a15643c6/team-01/src/main/java/ubc/cosc322/AmazonsAction.java
public class Move {

	public XYCoordinates queenPos;
	public XYCoordinates queenNext;
	public XYCoordinates arrowPos;

	public Move(XYCoordinates queenPos, XYCoordinates queenNext, XYCoordinates arrowPos) {
		this.queenPos = queenPos;
		this.queenNext = queenNext;
		this.arrowPos = arrowPos;
	}
	
	public ArrayList<Integer> getQueenPos(){
		return queenPos.getXY();
	}
	public ArrayList<Integer> getQueenNext(){
		return queenNext.getXY();
	}
	public ArrayList<Integer> getArrowPos(){
		return arrowPos.getXY();
	}
	
	
	
}
