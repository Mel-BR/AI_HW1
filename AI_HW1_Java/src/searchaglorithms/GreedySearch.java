package searchaglorithms;

import static entities.Parser.FIN;
import static entities.Parser.START;
import static entities.Parser.WALL;

import java.util.PriorityQueue;

import entities.Node;
import entities.Parser;

public class GreedySearch {
	private int[][] maze;
	private int[][] solution;
	private PriorityQueue<Node> pq;
	private int finX;
	private int finY;
	private int totalCost;
	private int expands;


	public GreedySearch(int[][] maze) {
		this.maze = maze;
		this.totalCost = 0;
		this.expands = 0;
		this.solution = new int[maze.length][maze[0].length];
		this.pq = new PriorityQueue<Node>();
		
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
	 * execute algorithm and create the solution maze matrix
	 */
	public void search() {
		this.totalCost = 0;
		this.expands = 0;
		Node solNode = mazeSearch();
		// backtrack from fin to start
		while (solNode.getParent() != null) {
			int x = solNode.getX();
			int y = solNode.getY();
			this.solution[x][y] = START; // MARK THE SOLUTION PATH WITH THE START SYMBOL
			solNode = solNode.getParent();
			this.totalCost++;
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
	private Node mazeSearch() {
		Node currNode = this.pq.remove();
		this.expands += 1;
		// found solution if current node is the on FIN
		if (this.solution[currNode.getX()][currNode.getY()] == FIN) {
			return currNode;
		}
		exploreMoves(currNode);
		return mazeSearch();
	}

	
	/**
	 * explore the neighbor nodes and add them to the queue if they have not been explored
	 * @param currNode
	 */
	private void exploreMoves(Node currNode) {
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
	private void checkAndAddNodeToList(Node currNode, int x, int y) {
		int cost = 0; //currNode.getCurrentPathCost()+1;
		int h = heuristic(x,y);
		if (h+cost < this.maze[x][y]) {
			this.pq.add(new Node(currNode, x, y, cost, h));
			this.maze[x][y] = h+cost;
		}
	}

	/**
	 * find an admissible heuristic to the goal
	 * @param x
	 * @param y
	 * @return
	 */
	private int heuristic(int x,int y) {
		// Manhattan distance
		return Math.abs(this.finX - x) + Math.abs(this.finY - y);
	}


}
