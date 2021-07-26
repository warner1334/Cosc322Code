package ubc.cosc322;

import java.util.ArrayList;
import java.util.Random;

public class NewMonteCarlo {
	public int whiteWins = 0;
	public int blackWins = 0;
	static final int WIN_SCORE = 10;
	int level;
	int opponent;
	
	public Move findNextMove(Board board, int playerNo) {
		opponent = 3 - playerNo;
		Tree tree = new Tree();
		Node rootNode = tree.getRoot();
		rootNode.getState().setBoard(board);
		rootNode.getState().setPlayerNo(opponent);
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime) < 1000) {
		
			//System.out.println("Phase 1");
			//Phase 1
			Node promisingNode = selectPromisingNode(rootNode);
			//Phase 2
			if(promisingNode.getState().getBoard().checkGame() == -1) {
				expandNode(promisingNode);
			}
			
			//Phase 3
			//System.out.println("HERE");
			Node nodeToExplore = new Node(promisingNode);
			//nodeToExplore.state.board.printState();
			if(promisingNode.getChildArray().size()>0) {
				nodeToExplore = promisingNode.getRandomChildNode();
			}
			//nodeToExplore.state.board.printState();
			int playoutResult = simulateRandomPlayout(nodeToExplore);
			
			backPropogation(nodeToExplore, playoutResult);
	}
		
		System.out.println("Root node visits");
		System.out.println(rootNode.state.visitCount);
		System.out.println("Possible moves");
		ArrayList<State> possibleStates = rootNode.getState().getAllPossibleStates();
		System.out.println(possibleStates.size());
		System.out.println("Child ArraySize");
		System.out.println(rootNode.childArray.size());
		Node winnerNode = rootNode.getChildWithMaxScore();
		System.out.println("Winner Node Visits");
		System.out.println(winnerNode.state.visitCount);
		System.out.println("Final score");
		System.out.println(winnerNode.state.winScore);
		System.out.println("Black Wins");
		System.out.println(winnerNode.state.winScoreBlack);
		System.out.println("White Wins");
		System.out.println(winnerNode.state.winScoreWhite);
		tree.setRoot(winnerNode);
		return winnerNode.getState().move;
	}
	
	
	
	private Node selectPromisingNode(Node rootNode) {
		Node node = rootNode;
		
		 while (node.getChildArray().size() != 0) {
	            node = UCT.findBestNodeWithUCT(node);
	     }
		return node;
	}
	
	private void expandNode(Node node) {
		ArrayList<State> possibleStates = node.getState().getAllPossibleStates();
		possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayerNo(node.getState().getOpponent());
            node.getChildArray().add(newNode);
        });	
		
	}
	private int simulateRandomPlayout(Node node) {
		Node tempNode = new Node(node);
		State tempState = tempNode.getState();
		int boardStatus = tempState.getBoard().checkGame();

		
//		if(boardStatus == opponent) {
//			tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
//			return boardStatus;
//		}
		
		while(boardStatus == -1) {
			boardStatus = tempState.randomPlay();
			tempState.togglePlayer();
		}
		if(boardStatus == 1) {
			blackWins++;
		}else if(boardStatus == 2) {
			whiteWins++;
		}
		return boardStatus;
	}
	
	private void backPropogation(Node nodeToExplore, int playoutResult) {
		Node tempNode = nodeToExplore;
		while(tempNode != null) {
			tempNode.getState().incrementVisit();
			tempNode.state.addBlackWhiteWins(WIN_SCORE, playoutResult);
			if(tempNode.getState().getPlayerNo() == playoutResult) {
				tempNode.getState().addScore(WIN_SCORE);
			}
			tempNode = tempNode.getParent();
		}
	}
}

