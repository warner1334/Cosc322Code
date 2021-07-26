package ubc.cosc322;

import java.util.ArrayList;

public class Actions {
	//All up moves
	
	ArrayList<XYCoordinates> actions;
	
public Actions(){
	actions = new ArrayList<XYCoordinates>();
	//Right
	for(int i = 1; i < 10; i++) {
		actions.add(new XYCoordinates(i, 0));
	}
	//Left
	for(int i = 1; i < 10; i++) {
		//(-i,0)
		actions.add(new XYCoordinates(-i, 0));
	}
	//Up
	for(int i = 1; i < 10; i++) {
		//(0,i)
		actions.add(new XYCoordinates( 0,i));
		
	}
	//down
	for(int i = 1; i < 10; i++) {
		//(0,-i)
		actions.add(new XYCoordinates( 0,-i));
	}
	//UpLeft
	for(int i = 1; i < 10; i++) {
		//(-i,i)
		actions.add(new XYCoordinates( -i,i));
	}
	//upRight
	for(int i = 1; i < 10; i++) {
		//(i,i)
		actions.add(new XYCoordinates(i, i));
	}
	//DownRight
	for(int i = 1; i < 10; i++) {
			//(i,-i)
		actions.add(new XYCoordinates(i, -i));
	}
	//DownLeft
	for(int i = 1; i < 10; i++) {
		//(-i,-i)
		actions.add(new XYCoordinates(-i, -i));
	}
}

public ArrayList<XYCoordinates> getActions() {
	return actions;
}
public ArrayList<XYCoordinates> getLevel1(){
	ArrayList<XYCoordinates> a = new ArrayList<XYCoordinates>();
	for(int i = 0; i < 65; i+=9) {
		a.add(actions.get(i));
	}
	return a;
}
}
