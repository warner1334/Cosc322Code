package ubc.cosc322;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
//heavly copied need to fix and write our own
public class UCT {
	
	public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
		if(nodeVisit  == 0) {
			return Integer.MAX_VALUE;
		}
		return ((double)nodeWinScore/(double)nodeVisit)+ 2*Math.sqrt(Math.log(totalVisit)/(double)nodeVisit);
		//return   nodeWinScore / (nodeVisit + epsilon) + Math.sqrt(Math.log(totalVisit+1) / (totalVisit + epsilon)) + r.nextDouble() * epsilon;
	}
	public static Node findBestNodeWithUCT(Node node) {
		int parentVisit = node.getState().getVisitCount();
		return Collections.max(node.getChildArray(), Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
	}
}
