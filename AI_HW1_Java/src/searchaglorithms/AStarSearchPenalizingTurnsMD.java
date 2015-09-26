package searchaglorithms;

import entities.Node;
import entities.Parser;
import static entities.Parser.FIN;
import static entities.Parser.PATH;
import static entities.Parser.START;
import static java.lang.Math.abs;
import java.util.PriorityQueue;

//class for situation : forward movement has cost 2 and any turn has cost 1
public class AStarSearchPenalizingTurnsMD {
    
    public static final Integer LEFT = 1; 
    public static final Integer UP = 2;
    public static final Integer RIGHT = 3;
    public static final Integer DOWN = 4;
    
    public Integer TURNCOST; 
    public Integer FORWARDCOST;
    
    private int[][] maze;
    private int[][][] costMaze;
    private int[][] solution;
    private PriorityQueue<Node> pq;
    private int[][][] expandedNodes;
    private int expandedNodesCounts;
    int solCost;
    int xGoal;
    int yGoal;
    
    String typeHeuristic;
    

    public AStarSearchPenalizingTurnsMD(int[][] maze, int turnCost, int forwardCost, String typeHeuristic) {
        this.maze = maze;
        this.solution = null;
        this.pq = new PriorityQueue<Node>();
        this.expandedNodesCounts = 0;
        this.solCost = 0;
        this.TURNCOST = turnCost;
        this.FORWARDCOST = forwardCost;
        this.typeHeuristic = typeHeuristic;
        
        this.solution = new int[this.maze.length][this.maze[0].length];
        this.costMaze = new int[this.maze.length][this.maze[0].length][5];
        this.expandedNodes = new int[this.maze.length][this.maze[0].length][5];
        
        for(int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                for (int k = 0; k < 5; k++){
                    this.costMaze[i][j][k] = this.maze[i][j];
                }
                    
        
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
                    int h = heuristicMD(i, j,RIGHT);
                    int cost = 0;
                    this.pq.add(new Node(null, i, j, cost, h, RIGHT));
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
            int direction = currentNode.getDirection();
            this.expandedNodes[x][y][direction]=1;
            this.expandedNodesCounts++;
            if (this.solution[currentNode.getX()][currentNode.getY()] == FIN) {
                markSolution(currentNode);
                break;
            }
            else{
                //Add all adjacent nodes that are not WALLS to the list or update the existing ones
                //Left
                if(currentNode.getDirection()==LEFT){ // cost of one move forward
                    checkAndAddNodeToList(currentNode, x,y-1,FORWARDCOST, LEFT);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, UP);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, DOWN);
                }
                    
                if(currentNode.getDirection()==RIGHT){ // cost of one move forward
                    checkAndAddNodeToList(currentNode, x,y+1,FORWARDCOST, RIGHT);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, UP);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, DOWN);
                }                
                
                
                if(currentNode.getDirection()==UP){ // cost of one move forward
                    checkAndAddNodeToList(currentNode, x-1,y,FORWARDCOST, UP);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, LEFT);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, RIGHT);
                }
                
                if(currentNode.getDirection()==DOWN){ // cost of one move forward
                    checkAndAddNodeToList(currentNode, x+1,y,FORWARDCOST, DOWN);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, LEFT);
                    checkAndAddNodeToList(currentNode, x, y, TURNCOST, RIGHT);
                }                
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
        Node parent = null;
        while (currentNode!=null){
            this.solution[currentNode.getX()][currentNode.getY()]=START;
            parent = currentNode.getParent();
            if (parent!=null){
                if(parent.getDirection().intValue() == currentNode.getDirection().intValue())
                    this.solCost+=FORWARDCOST;
                else
                    this.solCost+=TURNCOST;
            }
            currentNode = parent;
        }
    }
    
    /**
     * print the maze with solution path, the cost, and the number of nodes expanded 
     */
    public void printSolution() {
        System.out.println("Solution with heuristic : "+ this.typeHeuristic);
        System.out.println("Forward cost = "+FORWARDCOST+" and turn cost = "+TURNCOST);
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
    private boolean alreadyExpanded(int x, int y, int direction){
        
        if(this.expandedNodes[x][y][direction] == 1) {
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
    private void checkAndAddNodeToList(Node currNode, int x, int y, int AdditionalCost, Integer nodeDirection)
    {
        if(!alreadyExpanded(x,y,nodeDirection))
        {
            int cost = currNode.getCurrentPathCost()+AdditionalCost;
            int h = heuristicMD(x,y,nodeDirection);
            if (h+cost < this.costMaze[x][y][nodeDirection.intValue()]) {
                    Node oldNode = getNodeInQueue(x,y,nodeDirection);
                    this.pq.remove(oldNode);
                    this.pq.add(new Node(currNode,x,y,cost,h,nodeDirection));
                    this.costMaze[x][y][nodeDirection.intValue()] = h+cost;
            }
        }
  
    }

    /**
     * find the heuristic value for a given node
     * @param x
     * @param y
     * @return Manhattan distance between the node position x,y and the goal position
     */
    
    private int heuristicMD(int x, int y, Integer direction){
        
        if(typeHeuristic == "MD"){
            return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y);
        }
        
        
        else{
            if(x==xGoal && y==yGoal)
            return 0;
                
            if(y == yGoal && x < xGoal && direction == DOWN )
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*0;
            else if(y == yGoal && x > xGoal && direction == UP )
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*0;
            if(x == xGoal && y < yGoal && direction == RIGHT )
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*0;
            if(y == yGoal && x < xGoal && direction == LEFT )
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*0;

            if(direction==LEFT && y<yGoal)
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*2;        
            if(direction==RIGHT && y>yGoal)
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*2;        
            if(direction==UP && x<xGoal)
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*2;       
            if(direction==DOWN && x>xGoal)
                return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*2;

            return FORWARDCOST*abs(this.xGoal-x)+FORWARDCOST*abs(this.yGoal-y)+TURNCOST*1;
        }
        
    }

    
    
     /**
     * check if a node is already in the priority queue
     * @param x
     * @param y
     * @return true if already exists, false otherwise
     */
    private Node getNodeInQueue(int x, int y, Integer direction){
        for (Node node : this.pq) {
            if (x == node.getX() && y == node.getY() && node.getDirection().intValue() == direction.intValue() ){
                return node;
            }
        }
        return null;
    }

}