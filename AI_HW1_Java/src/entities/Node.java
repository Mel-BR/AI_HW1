package entities;

public class Node {
    
    private Node parent;
    private int x, y, currentPathCost;
    
    public Node(Node parent, int x, int y){
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.currentPathCost = currentPathCost;
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

}