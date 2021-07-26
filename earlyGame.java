package ubc.cosc322;

import java.util.ArrayList;
import java.util.Random;

public class earlyGame {

	static final int WIN_SCORE = 10;
	int level;
	int opponent;
	
	public Move findNextMove(Board board, int playerNo) {
		opponent = 3 - playerNo;
		Tree tree = new Tree();
		Node maxNode = null;
		Node rootNode = tree.getRoot();
		rootNode.getState().setBoard(board);
		rootNode.getState().setPlayerNo(opponent);
		int minMoves = Integer.MAX_VALUE;
		findMostAllChildMoves(rootNode);
		for(Node newNode: rootNode.childArray) {
			ArrayList<State> possibleStates = newNode.getState().getAllPossibleStates();
			if(possibleStates.size() <  minMoves) {
				minMoves = possibleStates.size();
				maxNode = newNode;
			}
		}
		System.out.println("Root node visits");
		System.out.println(rootNode.state.visitCount);
		System.out.println("Possible moves");
		System.out.println(rootNode.childArray.size());
		Node winnerNode = maxNode;
		System.out.println("PlayNo");
		System.out.println(winnerNode.state.playerNo);;
		winnerNode.state.board.printState();
		tree.setRoot(winnerNode);
		return winnerNode.getState().move;
		}

	
	
	private  void findMostAllChildMoves(Node rootNode) {
		ArrayList<State> possibleStates = rootNode.getState().getAllPossibleStates();
		possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(rootNode);
            newNode.getState().setPlayerNo(rootNode.getState().getOpponent());
            rootNode.getChildArray().add(newNode);
        });	
		
	}
	
}

