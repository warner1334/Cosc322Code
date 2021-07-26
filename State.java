package ubc.cosc322;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;
// 
public class State {
	
	    Board board;
	    int playerNo;
	    int visitCount;
	    double winScore;
	    double winScoreWhite;
	    double winScoreBlack;
	  //  private AllPossibleActions actions = new AllPossibleActions();
	    Move move;
//	    AllPossibleActions actions;
	    // copy constructor, getters, and setters
	    public State() {
	    	board = new Board();
	    }
	    
	    
	    public State(Board board, int playerNo) {
	    	this.board = board;
	    	this.playerNo = playerNo;
	    }
	 
	    public State(State state) {
	    	this.board = new Board(state.getBoard());
	    	this.playerNo = state.getPlayerNo();
	    	this.visitCount = state.getVisitCount();
	    	this.winScore = state.getWinScore();
	    	this.move = state.move;
	    }
	    
	    public State(Board board) {
	    	this.board = new Board(board);
	    }
	    
	    public ArrayList<State> getAllPossibleStates() {
	        // constructs a list of all possible states from current state
	    	ArrayList<Move> allActions = new ArrayList<Move>();
	    	AllPossibleActions actions = new AllPossibleActions();
	    	//board.printState();
	    	if(playerNo == 2) {
	    		allActions = actions.getAllBlackQueens(board);
	    		
	    	}else {
	    		 allActions = actions.getAllWhiteQueens(board);
	    		
	    	}
	    	ArrayList<State> allStates = new ArrayList<State>();
	    	for(Move moves: allActions) {
	    		State newState = new State(this.board);
	    		newState.setPlayerNo(3 - this.playerNo);
	    		newState.setMove(moves);
	    		newState.getBoard().updateState(moves.queenPos, moves.queenNext, moves.arrowPos);
	    		allStates.add(newState);
	    	}
	    	
	    	return allStates;
	    	
	    }
	    public Move getMove() {
			return move;
		}


		public void setMove(Move move) {
			this.move = move;
		}


		public ArrayList<Move> getAllPossibleMoves(){
			AllPossibleActions actions = new AllPossibleActions();
	      	ArrayList<Move> allActions = new ArrayList<Move>();
	    	if(playerNo == 2) {
	    		allActions = actions.getAllBlackQueens(board);
	    	
	    		
	    	}else {
	    		 allActions = actions.getAllWhiteQueens(board);
	    		
	    	}
	    	return allActions;
	    }
	    public int randomPlay() {
	        /* get a list of all possible positions on the board and 
	           play a random move */
	    	//try to add a random move pick and test if valid 
	    	ArrayList<Move> allPos = getAllPossibleMoves();
	    	if(allPos.size() > 0) {
	    	//System.out.println(allPos.size());
	    	Random rand = new Random();
			int randomNum = rand.nextInt(allPos.size());
			Move ranMove = allPos.get(randomNum);
	        board.updateState(ranMove.queenPos, ranMove.queenNext, ranMove.arrowPos);
	    	return -1;
	    	}else {
	    		if(board.trappedBlackPos) {
	    			return 2;
	    		}else if(board.trappedWhitePos) {
	    			return 1;
	    		}else {
	    			System.out.println("Error");
	    			return -1;
	    		}
	    	}
	    
	    }
	    
	    
	    
		public Board getBoard() {
			return board;
		}
		public void setBoard(Board board) {
			this.board = board;
		}
		public int getPlayerNo() {
			return playerNo;
		}
		public void setPlayerNo(int playerNo) {
			this.playerNo = playerNo;
		}
		public int getVisitCount() {
			return visitCount;
		}
		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}
		public double getWinScore() {
			return winScore;
		}
		public void setWinScore(double winScore) {
			this.winScore = winScore;
		}
		public int getOpponent() {
			if (playerNo == 1) {
				return 2;
			} else {
				return 1;
			}
		}
		public void togglePlayer() {
			playerNo = playerNo == 2? 1 :2;
		}
		public void incrementVisit() {
			visitCount++;
		}
		void addScore(double score) {
		     // if (this.winScore != Integer.MIN_VALUE)
		      this.winScore += score;
		
		}
		void addBlackWhiteWins(double score, int winner) {
			if(winner == 1) {
				winScoreBlack++;
			}else {
				winScoreWhite++;
			}
		}
		
		
	    
	}

