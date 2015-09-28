package searchaglorithms;

import entities.Node;
import entities.Parser;
import static entities.Parser.FIN;
import static entities.Parser.START;
import static entities.Parser.WALL;
import java.util.LinkedList;


public class DFSSearch {
    
    private int[][] maze;
    private int[][] solution;
    private LinkedList<Node> lifo;
    private int[][] expandedNodes;
    private int expandedNodesCounts;
    int solCost;
    
    
    public DFSSearch(int[][] maze) {
        this.maze = maze;
        this.solution = null;
        this.lifo = new LinkedList<Node>();
        this.expandedNodesCounts = 0;
        this.solCost = 0;
        
        this.solution = new int[this.maze.length][this.maze[0].length];
        
        for(int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                this.solution[i][j] = this.maze[i][j];
        
        findStart();
        
    }
    
    /**
    * Find the starting position and add the starting node to priority queue.
    */  
    private void findStart() 
    {
        this.lifo.clear();
        this.expandedNodes = new int[maze.length][maze[0].length];


        //findind start node
        for(int i = 0 ; i < this.maze.length ; i++)
        {
            for(int j = 0 ; j < this.maze[0].length ; j++) 
            {
                if (this.maze[i][j]  == START)
                {
                    this.lifo.add(new Node(null,i,j,0,0));
                }
            }
        }
    }
    
    /**
     * search algo for DFS Search
     */
    public void Search(){
  
        while (lifo.size() > 0) {
            Node currentNode = lifo.removeLast();
            int x = currentNode.getX();
            int y = currentNode.getY();
            this.expandedNodes[x][y]=1;
            this.expandedNodesCounts++;
            if (solution[currentNode.getX()][currentNode.getY()] == FIN) {
                markSolution(currentNode);
                break;
            }
            else{
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
        if((lifo.size() == 0))
            System.out.println("Failure");
    }

    
    /**
     * update the solution matrix to mark the found path
     * @param Node
     */
    public void markSolution(Node currentNode){
        while (currentNode.getParent()!=null){
            this.solution[currentNode.getX()][currentNode.getY()]=FIN;
            this.solCost++;
            currentNode = currentNode.getParent();
        }
        this.solCost--; // n nodes -> n-1 moves
    }
    
    
    
    /**
     * print the maze with solution path, the cost, and the number of nodes expanded 
     */
    public void printSolution() {
        System.out.println("Solution:");
        Parser.displayBeautifulMatrix(solution);
        System.out.println("\n Cost : "+this.solCost);       
        System.out.println("\n Nodes expanded : "+ this.expandedNodesCounts);
    }
    

     /**
     * check if a node has already been visited
     * @param node 
     * @param coord
     * @return boolean
     */    
    private boolean alreadyExpanded(int x, int y){
        
        if(this.expandedNodes[x][y] == 1) {
            return true;
        }
        return false;
    }
    
     /**
     * Add node to lifo if not already visited
     * @param currentNode, x, y
     * @return void
     */
    private void checkAndAddNodeToList(Node currentNode, int x, int y){
        if(!alreadyExpanded(x,y))
        {
            int cost = currentNode.getCurrentPathCost()+1;
            int h = 0;
            if (cost < this.maze[x][y]) {
                Node oldNode = getNodeInQueue(x, y);
                this.lifo.remove(oldNode);
                this.lifo.add(new Node(currentNode,x,y,cost,h));
                this.maze[x][y] = cost;
            }
        }
        
    }
    
    private Node getNodeInQueue(int x, int y){
        for (Node node : this.lifo) {
            if (x == node.getX() && y == node.getY()){
                return node;
            }
        }
        return null;
    }
    
}
