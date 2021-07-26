package ubc.cosc322;
//source of idea https://github.com/cosc-322-main-team/322GameOfAmazons/blob/7d3ff936baec290392193b74cdb7ac10a15643c6/team-01/src/main/java/ubc/cosc322/AmazonsActionFactory.java
import java.util.ArrayList;

public class AllPossibleActions {

	
	
	public ArrayList<Move> getAllBlackQueens(Board board) {
		//if(user is black)
		ArrayList<Move> allMoves = new ArrayList<Move>();
		ArrayList<XYCoordinates> queenPosBlack = board.getBlackPos();
		
		int queenCount = 0;
		
		for(int i = 0; i < queenPosBlack.size(); i++) {
			XYCoordinates queenPos = queenPosBlack.get(i);
			ArrayList<XYCoordinates> allQueenMoves = getTargets(queenPos.getX(), queenPos.getY(), board,queenPos);
			
			while(!allQueenMoves.isEmpty()) {
				XYCoordinates queenNext = allQueenMoves.remove(0);
				ArrayList<XYCoordinates> allTargets = getTargets(queenNext.getX(), queenNext.getY(), board,queenPos);

				while(!allTargets.isEmpty()) {
					XYCoordinates arrowPos = allTargets.remove(0);
					
					allMoves.add(new Move(queenPos, queenNext, arrowPos));
				}
			
			}
			queenCount++;
			
		}
		if(allMoves.isEmpty()) {
			board.setTrappedBlackPos(true);
		}
		return allMoves;
	
		
	}
	public ArrayList<Move> getAllWhiteQueens(Board board) {
		//if(user is white)
		ArrayList<Move> allMoves = new ArrayList<Move>();
		ArrayList<XYCoordinates> queenPosWhite = board.whitePos;
	
		int queenCount = 0;
		for(int i = 0; i < queenPosWhite.size(); i++) {
			XYCoordinates queenPos = queenPosWhite.get(i);
		
			ArrayList<XYCoordinates> allQueenMoves = getTargets(queenPos.getX(), queenPos.getY(), board, queenPos);
			
			while(!allQueenMoves.isEmpty()) {
				XYCoordinates queenNext = allQueenMoves.remove(0);
				ArrayList<XYCoordinates> allTargets = getTargets(queenNext.getX(), queenNext.getY(), board, queenPos);
				
				while(!allTargets.isEmpty()) {
					XYCoordinates arrowPos = allTargets.remove(0);
					
					allMoves.add(new Move(queenPos, queenNext, arrowPos));
				}
			}
			queenCount++;
			
		}
		if(allMoves.isEmpty()) {
			board.setTrappedWhitePos(true);
		}
		return allMoves;
		
		
		
	}
	
	
	public ArrayList<XYCoordinates> getTargets(int X, int Y, Board board, XYCoordinates queenPos) {
		ArrayList<XYCoordinates> target = new ArrayList<XYCoordinates>();
		Actions action = new Actions();
		ArrayList<XYCoordinates> actionList = action.getActions();
		int count = 8;
		
		//ArrayList<XYCoordinates> actionList = action.getActions();
		int i = 0;
		while(i < actionList.size()) {	
			int x = X + actionList.get(i).getX();
			int y = Y + actionList.get(i).getY();
			
			if(x > 10 || x < 1 || y > 10 || y < 1 ) { // is it in the bounds of the board
				i += count;
				count = 8;
				
			}else if(board.getGamePos(x,y) != 0 && (x != queenPos.x || y != queenPos.y)) { //If returns 0 it is a free space if not it can't move
				i += count;
				count = 8;
			}else {
				target.add(new XYCoordinates(x, y));
				if(count!= 0) {
					count--;
				}else {
					count = 8;
				}
			}
			i++;
		}
		
		
		return target;
	}
	
	public Boolean trappedNextQueen(ArrayList<XYCoordinates> queenPos, Board board){
		 Actions action = new Actions();
		// ArrayList<XYCoordinates> actionList = action.getActions();
		ArrayList<XYCoordinates> actionList = action.getLevel1();
		ArrayList<XYCoordinates> queenNext = new ArrayList<XYCoordinates>();
		Boolean isTrapped = true;
		for(XYCoordinates queen: queenPos) {
			int i =0;
			int X = queen.x;
			int Y = queen.y;
			while(i < actionList.size()) {	
				int x = X + actionList.get(i).getX();
				int y = Y + actionList.get(i).getY();
				if(x > 10 || x < 1 || y > 10 || y < 1 ) { 
					
				}else if(board.getGamePos(x,y) != 0){
					
				}else {
					//System.out.println("Here");
					queenNext.add(new XYCoordinates(x, y));
				}
				isTrapped = trappedArrow(queenNext, board, queen);
				if(isTrapped == false) {
					return false;
				}
			i++;
		}
			
		}
		return true;
	}
	public Boolean trappedArrow(ArrayList<XYCoordinates> queenNext, Board board, XYCoordinates queenPos) {
		Actions action = new Actions();
		//private ArrayList<XYCoordinates> actionList = action.getActions();
		ArrayList<XYCoordinates> actionList = action.getLevel1();
		ArrayList<XYCoordinates> arrowPos = new ArrayList<XYCoordinates>();
		for(XYCoordinates queen: queenNext) {
			int i =0;
			int X = queen.x;
			int Y = queen.y;
			while(i < actionList.size()) {	
				int x = X + actionList.get(i).getX();
				int y = Y + actionList.get(i).getY();
				//System.out.println(x + ", " + y);
				if(x > 10 || x < 1 || y > 10 || y < 1 ) { 
					//System.out.println("Out of bounds");
				}else if(board.getGamePos(x,y) != 0  && (x != queenPos.x || y != queenPos.y)){
					//System.out.println("game piece");
				}else {
					return false;
				}
			i++;
		}
		}
		return true;
	}
	
	
}
