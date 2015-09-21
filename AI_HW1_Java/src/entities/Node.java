package entities;

public class Node implements Comparable<Node> {
    
    private Node parent;
    private int x, y, currentPathCost, heuristic, totalCost;
    private Integer prevMove;

    
    public Node(Node parent, int x, int y){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.currentPathCost = -1;
        this.heuristic = 0;
        this.totalCost = this.currentPathCost;
        this.prevMove = null;
    }
    
    public Node(Node parent, int x, int y, int cost, int h){
    	this.parent = parent;
    	this.x = x;
    	this.y = y;
    	this.currentPathCost = cost;
    	this.heuristic = h;
    	this.totalCost = cost + h;
        this.prevMove = null;
    } 
    
    public Node(Node parent, int x, int y, int cost, int h, Integer prevMove){
    	this.parent = parent;
    	this.x = x;
    	this.y = y;
    	this.currentPathCost = cost;
    	this.heuristic = h;
    	this.totalCost = cost + h;
        this.prevMove = prevMove;
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
    
    public int getCurrentPathCost(){
        return currentPathCost;
    }
    
    public int getHeuristic() {
    	return this.heuristic;
    }
    
    public int getTotalCost() {
    	return this.totalCost;
    }
    
    public void setCurrentPastCost(int cost){
        this.currentPathCost = cost;
    }
    
    public void setCurrentTotalCost(int totalCost){
        this.totalCost = totalCost;
    }
    
    public void setPrevMove(Integer move){
        this.prevMove = move;
    }    
    
    public void setParent(Node parent){
        this.parent = parent;
    }  
    
    public Integer getPrevMove(){
        return this.prevMove;
    }

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		if (this.totalCost > o.getTotalCost()) {
			return 1;
		} else if (this.totalCost < o.getTotalCost()) {
			return -1;
		}
		return 0;
	}

}