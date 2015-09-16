package searchaglorithms;

import entities.Node;
import entities.Parser;
import static entities.Parser.FIN;
import static entities.Parser.PATH;
import static entities.Parser.START;
import static entities.Parser.WALL;
import java.util.Iterator;
import java.util.LinkedList;


public class DFSSearch {
    
    private int[][] maze;
    private int[][] solution;
    private LinkedList<Node> lifo;
    private LinkedList<Node> exploredNodes;
    
    
    public DFSSearch(int[][] maze) {
        this.maze = maze;
        this.solution = null;
        this.lifo = new LinkedList<Node>();
        this.exploredNodes = new LinkedList<Node>();
    }
    
    /**
     * search algo for DFS Search
     * @param 
     * @return void
     */
    public void Search(){
        this.lifo.clear();
        this.exploredNodes.clear();
        
        //findind start node
        for(int i = 0 ; i < this.maze.length ; i++)
        {
            for(int j = 0 ; j < this.maze.length ; j++) 
            {
                if (this.maze[i][j]  == START)
                {
                    lifo.add(new Node(null,i,j));
                }
            }
        }

        
        while (lifo.size() > 0) {
            Node currentNode = lifo.removeLast();
            int x = currentNode.getX();
            int y = currentNode.getY();
            if (maze[currentNode.getX()][currentNode.getY()] == FIN) {
                returnSolution(currentNode);
                break;
            }
            else{
                exploredNodes.add(currentNode);
                //Add all adjacent nodes that are not WALLS to the list
                //Left
                checkAndAddNodeToList(currentNode, x-1,y);
                //Up
                checkAndAddNodeToList(currentNode, x,y-1);
                //Right
                checkAndAddNodeToList(currentNode, x+1,y);
                //Down
                checkAndAddNodeToList(currentNode, x,y+1);
            }
            
        }
    }

     /**
     * display the solution
     * @param Node
     * @return void
     */
    public void returnSolution(Node currentNode){
        this.solution = new int[this.maze.length][this.maze.length];
        int cost = 0;
        for(int i=0; i<this.maze.length; i++)
        {
            for(int j=0; j<this.maze.length; j++){
                this.solution[i][j]=this.maze[i][j];
            }      
        }
        
        
        while (currentNode!=null){
            this.solution[currentNode.getX()][currentNode.getY()]=START;
            cost++;
            currentNode = currentNode.getParent();
        }
        
        System.out.println("Solution:");
        Parser.displayBeautifulMatrix(solution);
        
        System.out.println("\n The cost is : "+cost);
        
        System.out.println("\n The number of nodes extended is : "+ this.exploredNodes.size());
        
     
    }

     /**
     * check if a node has already been visited
     * @param node coord
     * @return boolean
     */    
    private boolean alreadyExplored(int x, int y){
        
        for (Node node : this.exploredNodes) {
            if (x == node.getX() && y == node.getY() ){
                return true;
            }
        }
        return false;
    }
    
     /**
     * Add node to lifo if not already visited
     * @param currentNode, x, y
     * @return void
     */
    private void checkAndAddNodeToList(Node currentNode, int x, int y){
        if( this.maze[x][y] != WALL )
        {
            if(!alreadyExplored(x,y))
            {
                lifo.add(new Node(currentNode,x,y));
            }   
        }
    }
}
