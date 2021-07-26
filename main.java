package ubc.cosc322;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Random;

import sfs2x.client.requests.PlayerToSpectatorRequest;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board();
		board.printState();
		NewMonteCarlo2 mtcs = new NewMonteCarlo2();
		NewMonteCarlo mtcs2 = new NewMonteCarlo();
		RandomPlayer ran = new RandomPlayer();
		AllPossibleActions all = new AllPossibleActions();
		int moveNo = 0;
		while(!board.isTrapped()) {
			System.out.println("Black Move");
			if(moveNo < 7) {
				
				earlyGame eGame = new earlyGame();
				Move moveM = eGame.findNextMove(board, 1);
				board.updateState(moveM.queenPos, moveM.queenNext, moveM.arrowPos);
				moveNo++;
				
			}else {
				mtcs = new NewMonteCarlo2();
				Move moveM = mtcs.findNextMove(board, 1);
				board.updateState(moveM.queenPos, moveM.queenNext, moveM.arrowPos);
			}
			
			System.out.println("White Move");
			Move moveM2 = mtcs2.findNextMove(board, 2);
			board.updateState(moveM2.queenPos, moveM2.queenNext, moveM2.arrowPos);
			board.printState();

		}
		

		System.out.println("Test Over");
		board.printState();
		
		

		
	}

}

		