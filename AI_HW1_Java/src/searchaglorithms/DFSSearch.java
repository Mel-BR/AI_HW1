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
                    addNodeToExpandedSet(null,i,j);
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
            if (maze[currentNode.getX()][currentNode.getY()] == FIN) {
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
        System.out.println("Failure");
    }

    
    /**
     * update the solution matrix to mark the found path
     * @param Node
     */
    public void markSolution(Node currentNode){
        while (currentNode!=null){
            this.solution[currentNode.getX()][currentNode.getY()]=START;
            this.solCost++;
            currentNode = currentNode.getParent();
        }
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
    private boolean alreadyExplored(int x, int y){
        
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
        if( this.maze[x][y] != WALL )
        {
            if(!alreadyExplored(x,y))
            {
               addNodeToExpandedSet(currentNode,x,y);
            }   
        }
    }
    
    
     /**
     * add node to the lifo
     * @param currentNode
     * @param x
     * @param y
     */
    private void addNodeToExpandedSet(Node currentNode,int x,int y){
        lifo.add(new Node(currentNode,x,y));
        this.expandedNodes[x][y]=1;
        this.expandedNodesCounts++;
    }
}
