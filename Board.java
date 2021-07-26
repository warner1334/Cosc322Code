package ubc.cosc322;

import java.util.ArrayList;

import cern.colt.list.BooleanArrayList;
import ygraph.ai.smartfox.games.BoardGameModel;
import ygraph.ai.smartfox.games.GamePlayer;

public  class Board {
	// constants
	public static final int ROWS = 11;
	public static final int COLS = 11;
	
	public static final int AVAILABLE = 0;
	public static final int ARROW = 1;
	public static final int BLACK_QUEEN = 2;
	public static final int WHITE_QUEEN = 3;
	
	public static int arrowCounter = 0; 
	public static int playerNo = 0;
	protected int [][] board;
//	private ArrayList<Integer> gameState = new ArrayList<>();
	ArrayList<XYCoordinates> whitePos = new ArrayList<>();
	ArrayList<XYCoordinates> blackPos = new ArrayList<>();
	Boolean trappedWhitePos = false;
	Boolean trappedBlackPos = false;

	

	

	// constructor
	// creates empty board with queens (Amazons) in starting positions
	public Board() {
		
		
		board = new int[ROWS][COLS];
		board[1][4] = WHITE_QUEEN;
		board[1][7] = WHITE_QUEEN;
		board[4][1] = WHITE_QUEEN;
		board[4][10] = WHITE_QUEEN;
		
		board[7][1] = BLACK_QUEEN;
		board[10][4] = BLACK_QUEEN;
		board[10][7] = BLACK_QUEEN;
		board[7][10] = BLACK_QUEEN;
		whitePos = new ArrayList<>();
		blackPos = new ArrayList<>();
		initPositions();

	}
	public void initPositions() {
		for(int i = 1; i < 11 ; i++) {
			for(int j = 1; j <11; j++) {
		if(board[i][j] == WHITE_QUEEN) {
			whitePos.add(new XYCoordinates(i, j));
		}else if(board[i][j] == BLACK_QUEEN) {
			blackPos.add(new XYCoordinates(i, j));
		}
			}
		}
	}

	
	// clone board constructor
//	public Board(Board cloned) {
//		this();
//	}
	public Board(Board clone) {
//		Boolean trappedWhitePos = false;
//		Boolean trappedBlackPos = false;
		board = new int[ROWS][COLS];
		for(int i = 10; i > 0 ; i--) {
			for(int j = 1; j < 11; j++) {
				if(clone.board[i][j] == WHITE_QUEEN) {
					board[i][j] = WHITE_QUEEN;
					whitePos.add(new XYCoordinates(i, j));
				}else if(clone.board[i][j] == BLACK_QUEEN) {
					board[i][j] = BLACK_QUEEN;
					blackPos.add(new XYCoordinates(i, j));
				}else if(clone.board[i][j] == ARROW){
					board[i][j] = ARROW;
				}else {
					board[i][j] = AVAILABLE;
				}
			}
		}
	}
	
	// clone board method -- do we need this?
	public void cloneBoard(Board cloned) {
		System.arraycopy(cloned.board, 0, board, 0, COLS);
	}
	
		
	//print board
	public void printState() {
		
		for(int i = 10; i > 0 ; i--) {
			System.out.print("| ");
			for(int j = 1; j < 11; j++) {
				if(board[i][j] == WHITE_QUEEN) {
					System.out.print(" W ");
					//whitePos.add(new XYCoordinates(i, j));
				}else if(board[i][j] == BLACK_QUEEN) {
					System.out.print(" B ");
					//blackPos.add(new XYCoordinates(i, j));
				}else if (board[i][j] == ARROW) {
					System.out.print(" A ");
				}else {
					System.out.print(" - ");
				}
			}
			System.out.println(" |");
			
			
		}
		System.out.println(" ");
		System.out.println(" ");
	}


	public void getOppMove(ArrayList<Integer> queenPos, ArrayList<Integer> queenNext, ArrayList<Integer> arrowPos) {
		XYCoordinates oldPos = new XYCoordinates(queenPos.get(0), queenPos.get(1));
		XYCoordinates newPos = new XYCoordinates(queenNext.get(0), queenNext.get(1));
		XYCoordinates newArrow = new XYCoordinates(arrowPos.get(0), arrowPos.get(1));
		updateState(oldPos, newPos, newArrow);
		//isTrappedTest();
		
	}
	
	
	public void updateState(XYCoordinates queenPos, XYCoordinates queenNext, XYCoordinates arrowPos) {
		int queen = board[queenPos.x][queenPos.y];
		board[queenPos.x][queenPos.y] = AVAILABLE;
		board[queenNext.x][queenNext.y] = queen;
		board[arrowPos.x][arrowPos.y] = ARROW;
		whitePos.clear();
		blackPos.clear();
		for(int i = 10; i > 0 ; i--) {
			for(int j = 1; j < 11; j++) {
				if(board[i][j] == WHITE_QUEEN) {
					whitePos.add(new XYCoordinates(i, j));
				}else if(board[i][j] == BLACK_QUEEN) {
					blackPos.add(new XYCoordinates(i, j));
				}
			}
		}
//		AllPossibleActions actions = new AllPossibleActions();
//		if(actions.trappedNextQueen(blackPos, this)) {
//		//	System.out.println("White Wins");
//			setTrappedBlackPos(true);
//		}else if(actions.trappedNextQueen(whitePos, this)) {
////			System.out.println("Black Wins");
//			setTrappedWhitePos(true);
//		}
	
		
	}
	public int getGamePos(int x, int y) {
		return board[x][y];
	}
	
	
	public Boolean isTrapped() {
		AllPossibleActions actions = new AllPossibleActions();
		if(actions.trappedNextQueen(blackPos, this)) {
			setTrappedBlackPos(true);
			return true;
		}else if(actions.trappedNextQueen(whitePos, this)) {
			setTrappedWhitePos(true);
			return true;
		}else {
			return false;
		}
	}

	
	public int checkGame() {
		if(isTrapped()) {
			if(getTrappedBlackPos()) {
				return 2;
			} 
			if(getTrappedWhitePos()) {
				return 1;
			}
		}
		return -1;
	}
	// getter and setter methods
	public ArrayList<XYCoordinates> getWhitePos() {
		return whitePos;
	}
	
	public ArrayList<XYCoordinates> getBlackPos() {
		return blackPos;
	}



	public void setWhitePos(ArrayList<XYCoordinates> whitePos) {
		this.whitePos = whitePos;
	}

	public void setBlackPos(ArrayList<XYCoordinates> blackPos) {
		this.blackPos = blackPos;
	}
	public Boolean getTrappedWhitePos() {
		return trappedWhitePos;
	}
	public void setTrappedWhitePos(Boolean trappedWhitePos) {
		this.trappedWhitePos = trappedWhitePos;
	}
	public Boolean getTrappedBlackPos() {
		return trappedBlackPos;
	}
	public void setTrappedBlackPos(Boolean trappedBlackPos) {
		this.trappedBlackPos = trappedBlackPos;
	}


}