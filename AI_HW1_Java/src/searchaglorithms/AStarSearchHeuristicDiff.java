package searchaglorithms;

import entities.Node;
import entities.Parser;
import static entities.Parser.FIN;
import static entities.Parser.START;
import static java.lang.Math.abs;
import java.util.PriorityQueue;

//class for situation : forward movement has cost 2 and any turn has cost 1
public class AStarSearchHeuristicDiff {
    
    public static final Integer LEFT = 1; 
    public static final Integer UP = 2;
    public static final Integer RIGHT = 3;
    public static final Integer DOWN = 4;
    
    
    private int[][] maze;
    private int[][] solution;
    private PriorityQueue<Node> pq;
    private int[][] expandedNodes;
    private int expandedNodesCounts;
    int solCost;
    int xGoal;
    int yGoal;
    

    public AStarSearchHeuristicDiff(int[][] maze) {
        this.maze = maze;
        this.solution = null;
        this.pq = new PriorityQueue<Node>();
        this.expandedNodesCounts = 0;
        this.solCost = 0;
        
        this.solution = new int[this.maze.length][this.maze[0].length];
        
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
        this.expandedNodes = new int[maze.length][maze[0].length];

        //findind start and goal node
        for(int i = 0 ; i < this.maze.length ; i++)
        {
            for(int j = 0 ; j < this.maze[0].length ; j++) 
            {
                if (this.maze[i][j]  == START)
                {
                    int h = heuristic(i, j);
                    int cost = 0;
                    this.pq.add(new Node(null, i, j, cost, h, RIGHT));
                    this.expandedNodesCounts++;
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
            if (this.solution[currentNode.getX()][currentNode.getY()] == FIN) {
                markSolution(currentNode);
                break;
            }
            else{
                //Add all adjacent nodes that are not WALLS to the list or update the existing ones
                //Left
                if(currentNode.getPrevMove()==LEFT) // cost of one move forward
                    checkAndAddNodeToList(currentNode, x-1,y,2);
                else // cost of one turn plus one move forward
                    checkAndAddNodeToList(currentNode, x-1,y,3);
                
                
                //Up
                if(currentNode.getPrevMove()==UP) // cost of one move forward
                    checkAndAddNodeToList(currentNode, x,y-1,2);
                else // cost of one turn plus one move forward
                    checkAndAddNodeToList(currentNode, x,y-1,3);
                       
                
                //Right
                if(currentNode.getPrevMove()==RIGHT) // cost of one move forward
                    checkAndAddNodeToList(currentNode, x+1,y,2);
                else // cost of one turn plus one move forward
                    checkAndAddNodeToList(currentNode, x+1,y,3);
                
              
                //Down
                if(currentNode.getPrevMove()==DOWN) // cost of one move forward
                    checkAndAddNodeToList(currentNode, x,y+1,2);
                else // cost of one turn plus one move forward
                    checkAndAddNodeToList(currentNode, x,y+1,3);
                

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
     * Add node to list if not already visited, update them if necessary and if already visited
     * @param currentNode
     * @param x
     * @param y
     */
    private void checkAndAddNodeToList(Node currNode, int x, int y, int AdditionalCost)
    {
        int cost = currNode.getCurrentPathCost()+AdditionalCost;
        int h = heuristic(x,y);
        if (h+cost < this.maze[x][y]) {
                Node n = existNode(x,y);
                //node already expanded, need to change totalCost value
                if (n!=null){ 
                    n.setCurrentTotalCost(h+cost);
                }
                else{ // node not already expanded
                    this.expandedNodesCounts++;
                }
                this.pq.add(new Node(currNode, x, y, cost, h));

                this.maze[x][y] = h+cost;
        }
  
    }

    /**
     * find the heuristic value for a given node
     * @param x
     * @param y
     * @return manhattan distance between the node position x,y and the goal position
     */
    private int heuristic(int x, int y){
        return 2*abs(this.xGoal-x)+2*abs(this.yGoal-y)+1;
    }

    
    
     /**
     * check if a node is already in the priority queue
     * @param x
     * @param y
     * @return true if already exists, false otherwise
     */
    private Node existNode(int x, int y){
        for (Node node : this.pq) {
            if (x == node.getX() && y == node.getY() ){
                return node;
            }
        }
        return null;
    }
}