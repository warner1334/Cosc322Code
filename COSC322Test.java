package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    private int color = 0;
    public int getColor() {
		return color;
	}
    private int numMoves = 0;

	public void setColor(int color) {
		this.color = color;
	}
	Board board = new Board();
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 
   

    

    @Override
    public void onLogin() {
    	System.out.println("Congratualations!!! "
    			+ "I am called because the server indicated that the login is successfully");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!"); 
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	if(messageType.equalsIgnoreCase(GameMessage.GAME_ACTION_START)) {
    		String blackPlayer = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
    		String whitePlayer = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
    		//test if username == to blackPlayer
    		//if black make first move 
    		if(blackPlayer.equals(userName)) {
    			System.out.println("User: Black");
    			board.playerNo = 1;
    			pickAmove();
    		}else if(whitePlayer.equals(userName)){
    			System.out.println("User: White");
    			board.playerNo = 2;
    		}else {
    			System.out.println(userName);
    			System.out.println(whitePlayer);
    			System.out.println(blackPlayer);
    		}
    	
    	}
    	if(messageType.equalsIgnoreCase(GameMessage.GAME_ACTION_MOVE)) {
    		//get the queens last pos, next pos, and arrow pos from opp turn
    		ArrayList <Integer> queenpos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
    		ArrayList <Integer> queennext = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
    		ArrayList <Integer> arrowpos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
    		//update our board with board.getOppMove(queenpos, queennext, arrowpos);
    		board.getOppMove(queenpos, queennext, arrowpos);
    		//print board state
    		board.printState();
    		//update gamegui gamestate
    		gamegui.updateGameState(queenpos,queennext,arrowpos);
    		pickAmove();
    		
    		
    	}
    	if(messageType.equalsIgnoreCase(GameMessage.GAME_STATE_BOARD)) {
    		ArrayList <Integer> gameState = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    	//	board.setState(gameState);
    		//board.printStateTest(gameState);
    	
    		board.printState();
    		gamegui.setGameState(gameState);
    		//edit setState, updateState, and printState methods
    	}
    	return true;
    }
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return  this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}
	
	protected void pickAmove() {
		int moveNo = getNumMoves();
		if(board.checkGame() == -1) {
			if(moveNo < 6) {
				earlyGame eGame = new earlyGame();
				Move eGameMove = eGame.findNextMove(board, board.playerNo);
				sendMove(eGameMove);
				moveNo++;
				setNumMoves(moveNo);
				
			}else {
				NewMonteCarlo2 mtcs = new NewMonteCarlo2();
				Move mtcsMove = mtcs.findNextMove(board, board.playerNo);
				sendMove(mtcsMove);
			}
		}else {
			if(board.trappedBlackPos) {
				System.out.println("White Wins");
			}else {
				System.out.println("Black Wins");
				
			}
		}
    	
    }
	
	
	 public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	protected void sendMove(Move move) {
	    	if(move != null) {
	    		board.updateState(move.queenPos, move.queenNext, move.arrowPos);
	    		board.printState();
	    		gamegui.updateGameState(new ArrayList<Integer>(move.getQueenPos()), new ArrayList<Integer>(move.getQueenNext()), new ArrayList<Integer>(move.getArrowPos()));
	    		gameClient.sendMoveMessage(new ArrayList<Integer>(move.getQueenPos()), new ArrayList<Integer>(move.getQueenNext()), new ArrayList<Integer>(move.getArrowPos()));
	    	}else {
	    		System.out.println("GAME OVER!");
	    	}
	    	
	    	}

 
}//end of class