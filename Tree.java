package ubc.cosc322;

import java.util.ArrayList;

public class Tree{
	Node root;
	public Tree(Node root) {
		this.root = root;
	}
	 public Tree() {
	        root = new Node();
	  }
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	public void addChild(Node parent, Node child) {
	    parent.getChildArray().add(child);
	}
}
