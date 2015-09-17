package entities;

public class Node implements Comparable<Node> {
    
    private Node parent;
    private int x, y, currentPathCost;
    
    public Node(Node parent, int x, int y){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.currentPathCost = -1;
    }
    
    public Node(Node parent, int x, int y, int cost){
    	this.parent = parent;
    	this.x = x;
    	this.y = y;
    	this.currentPathCost = cost;
    }
    
    public Node getParent(){
        return this.parent;
    }
    
    public int getX(){
        return this.x;
    }    
    
    public int getY(){
        return this.y;
    }
    
    public int getCurrentPastCost(){
        return currentPathCost;
    }
    
    public void setCurrentPastCost(int cost){
        this.currentPathCost = cost;
    }

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		if (this.currentPathCost > o.getCurrentPastCost()) {
			return 1;
		} else if (this.currentPathCost < o.getCurrentPastCost()) {
			return -1;
		}
		return 0;
	}

}