package entities;

public class Node {
    
    private Node parent;
    private int x, y;
    
    public Node(Node parent, int x, int y){
        this.parent = parent;
        this.x = x;
        this.y = y;
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
    
}
