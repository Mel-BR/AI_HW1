package searchaglorithms;

import entities.Node;
import entities.Parser;
import static entities.Parser.FIN;
import static entities.Parser.START;
import static java.lang.Math.abs;
import java.util.PriorityQueue;


public class AStarSearch {
    
    private int[][] maze;
    private int[][] solution;
    private PriorityQueue<Node> pq;
    private int[][] expandedNodes;
    private int expandedNodesCounts;
    int solCost;
    int xGoal;
    int yGoal;
    

    public AStarSearch(int[][] maze) {
        this.maze = maze;
        this.solution = null;
        this.pq = new PriorityQueue<Node>();
        this.expandedNodesCounts = 0;
        this.solCost = 0;
        
        this.solution = new int[this.maze.length][this.maze[0].length];
        this.expandedNodes = new int[maze.length][maze[0].length];
        
        for(int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                this.solution[i][j] = this.maze[i][j];
        
        findStartFin();
		
    }
    
    
    /**
     * Find the starting position and add the starting node to priority queue.
     * Find the location of the goal
     */    
    private void findStartFin() {
        this.pq.clear();
        

        //findind start and goal node
        for(int i = 0 ; i < this.maze.length ; i++)
        {
            for(int j = 0 ; j < this.maze[0].length ; j++) 
            {
                if (this.maze[i][j]  == START)
                {
                    int h = heuristic(i, j);
                    int cost = 0;
                    this.pq.add(new Node(null, i, j, cost, h));
                }
                else if (this.maze[i][j]  == FIN)
                {
                    this.xGoal = i;
                    this.yGoal = j;
                }
            }
        }
    }
    
    /**
     * search algo for A* Search 
     */
    public void Search(){
        
        while (pq.size() > 0) {
            Node currentNode = pq.remove();
            int x = currentNode.getX();
            int y = currentNode.getY();
            this.expandedNodes[x][y]=1;
            this.expandedNodesCounts++;
            if (this.solution[currentNode.getX()][currentNode.getY()] == FIN) {
                markSolution(currentNode);
                break;
            }
            else{
                //Add all adjacent nodes that are not WALLS to the list or update the existing ones
                //Left
                checkAndAddNodeToList(currentNode, x,y-1);
                //Up
                checkAndAddNodeToList(currentNode, x-1,y);
                //Right
                checkAndAddNodeToList(currentNode, x,y+1);
                //Down
                checkAndAddNodeToList(currentNode, x+1,y);
            }
            
        }
        if((pq.size() == 0))
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
     * Add node to list if not already visited, update them if necessary and if already visited
     * @param currentNode
     * @param x
     * @param y
     */
    private void checkAndAddNodeToList(Node currNode, int x, int y)
    {
        if(!alreadyExpanded(x,y))
        {
            int cost = currNode.getCurrentPathCost()+1;
            int h = heuristic(x,y);
            if (h+cost < this.maze[x][y]) {
                    Node oldNode = getNodeInQueue(x,y);
                    this.pq.remove(oldNode);
                    this.pq.add(new Node(currNode,x,y,cost,h));
                    this.maze[x][y] = h+cost;
            }
        }
        
  
    }

    /**
     * find the heuristic value for a given node
     * @param x
     * @param y
     * @return manhattan distance between the node position x,y and the goal position
     */
    private int heuristic(int x, int y){
        return abs(this.xGoal-x)+abs(this.yGoal-y);
    }

    
    
     /**
     * get node in priority queue
     * @param x
     * @param y
     * @return node if exists, null otherwise
     */
    private Node getNodeInQueue(int x, int y){
        for (Node node : this.pq) {
            if (x == node.getX() && y == node.getY()){
                return node;
            }
        }
        return null;
    }
}