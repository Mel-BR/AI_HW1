package searchaglorithms;

import static entities.Parser.FIN;
import static entities.Parser.START;

import java.util.LinkedList;
import java.util.Queue;

import entities.Node;
import entities.Parser;

/**
 * BFS maze search using priority queue and uniform move costs.
 * @author Brian
 *
 */
public class BFSSearch2 {
	protected int[][] maze;
	protected int[][] solution;
	protected Queue<Node> pq;
	protected int finX;
	protected int finY;
	protected int totalCost;
	protected int expands;
	protected Node solNode;


	public BFSSearch2(int[][] maze) {
		this.maze = maze;
		this.totalCost = 0;
		this.expands = 0;
		this.solution = new int[maze.length][maze[0].length];
		this.pq = new LinkedList<Node>();
		
		for(int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				this.solution[i][j] = this.maze[i][j];
	}

	/**
	 * Find the starting position and add the starting node to priority queue.
	 * Find the location of the goal
	 */
	protected void findStartFin() {
		int x = this.maze.length;
		int y = this.maze[0].length;
		// look for the start location a
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (this.maze[i][j] == START) {
					int h = heuristic(i, j);
					int cost = 0;
					this.pq.add(new Node(null, i, j, cost, h));
				}
				if (this.maze[i][j] == FIN) {
					this.finX = j;
					this.finY = j;
				}
			}
		}
	}

	/**
	 * Get the final node of the search
	 * @return
	 */
	public Node getSolNode() {
		return this.solNode;
	}
	
	/**
	 * execute algorithm and create the solution maze matrix
	 */
	public void search() {
		findStartFin();
		this.totalCost = 0;
		this.expands = 0;
		Node solNode = mazeSearch();
		this.solNode = solNode;
		// backtrack from fin to start
		while (solNode.getParent() != null) {
			int x = solNode.getX();
			int y = solNode.getY();
			this.solution[x][y] = START; // MARK THE SOLUTION PATH WITH THE START SYMBOL			
			solNode = solNode.getParent();
			this.totalCost++;
			debugSolution(solNode);
		}
	}
	
	
	/**
	 * print the maze with solution path, the cost, and the number of nodes expanded 
	 */
	public void printSolution() {
        System.out.println("Solution:");
		Parser.displayBeautifulMatrix(solution);
 
        System.out.println("\n Cost : "+this.totalCost);       
        System.out.println("\n Nodes expanded : "+ this.expands);
    }
	
	/**
	 * 
	 * @return node that contains the path traveled from start to finish
	 */
	protected Node mazeSearch() {
		Node currNode = this.pq.remove();
		// found solution if current node is the on FIN
		if (this.solution[currNode.getX()][currNode.getY()] == FIN) {
			this.expands -= this.pq.size();
			return currNode;
		}
		exploreMoves(currNode);
		return mazeSearch();
	}

	
	/**
	 * explore the neighbor nodes and add them to the queue if they have not been explored
	 * @param currNode
	 */
	protected void exploreMoves(Node currNode) {
		int x = currNode.getX();
		int y = currNode.getY();
        //Add all adjacent nodes that are not WALLS to the list
        //Left
        checkAndAddNodeToList(currNode, x-1,y);
        //Up
        checkAndAddNodeToList(currNode, x,y-1);
        //Right
        checkAndAddNodeToList(currNode, x+1,y);
        //Down
        checkAndAddNodeToList(currNode, x,y+1);
	}

	
	/**
	 * create a new node and add queue if the new node cost is less than the current cost
	 * @param currNode
	 * @param x
	 * @param y
	 */
	protected void checkAndAddNodeToList(Node currNode, int x, int y) {
		int cost = currNode.getCurrentPathCost()+1;
		int h = 0; //heuristic(x,y);
		if (h+cost < this.maze[x][y]) {
			this.pq.add(new Node(currNode, x, y, cost, h));
			this.maze[x][y] = h+cost;
			this.expands += 1;
		}
	}

	/**
	 * find an admissible heuristic to the goal
	 * @param x
	 * @param y
	 * @return
	 */
	protected int heuristic(int x,int y) {
		// Manhattan distance
		return Math.abs(this.finX - x) + Math.abs(this.finY - y);
	}
	
	protected void debugSolution(Node solNode) {}


}
