package ubc.cosc322;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer {
	
	ArrayList<Move> moves;
	
	public Move ranMove(ArrayList<Move> moves) {
		Random rand = new Random();
		this.moves = moves;
		if(moves.size() != 0 ) {
		int randomNum = rand.nextInt(moves.size());
		Move ranMove = moves.get(randomNum);
		
		return ranMove;
		}
		else {
			return null;
		}
		
	}

}
