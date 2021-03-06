package searchaglorithms;

import static entities.Parser.FIN;
import static entities.Parser.START;
import static entities.Parser.WALL;
import static entities.Parser.PATH2;

import static java.lang.Math.abs;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import entities.Node;
import entities.Parser;

public class BFSSearch {
	private int[][] maze;
	private int[][] solution;
	private int[] start;
	private Queue<Node> activeQueue = new LinkedList<Node>();
	private Node goalNode;
	private int[][] mazeVisited;
	IOException reachedGoal;
	private int nrOfNodesExpanded=0;
	private int visited=PATH2;
	private int pathcost = 0;


	public BFSSearch(int[][] maze){
		this.maze = maze;

		//Finding the start coordinates to start from
		this.start = findInMaze(START);
		this.mazeVisited = new int[this.maze.length][this.maze[0].length];
		this.reachedGoal = new IOException();
		
		//Deep copying the maze to the solution
		this.solution = new int[maze.length][maze[0].length];
		for(int i = 0; i < maze.length; i++)
			for (int j = 0; j < maze[i].length; j++)
				this.solution[i][j] = this.maze[i][j];
		
	}
	
	public void search(){
		this.activeQueue.add(new Node(null,start[0],start[1]));
		
		
		while(!this.activeQueue.isEmpty()){
			try{
			addNeighbours();
			}catch(IOException reachedGoal)
			 {
			      System.out.println("GOAL!!!");
			      
			  }
		}
		
		if(!(this.goalNode==null)){
			Node currNode = this.goalNode;
			while(!(currNode.getParent()==null)){
				this.solution[currNode.getX()][currNode.getY()] = this.visited;
				currNode = currNode.getParent();
				this.pathcost++;
			}
			
		}
		
		
		DispSolution();
	}
	

	private void addNeighbours() throws IOException {
		Node currNode = this.activeQueue.poll();
		int x=currNode.getX();
		int y=currNode.getY();
		
		checkIfWallOrVisited(currNode,x+1,y);
		checkIfWallOrVisited(currNode,x-1,y);
		checkIfWallOrVisited(currNode,x,y+1);
		checkIfWallOrVisited(currNode,x,y-1);
		
	}
	
	private void checkIfWallOrVisited(Node currNode, int x, int y) throws IOException{
		
		if(this.maze[x][y]!=WALL && this.mazeVisited[x][y]!=this.visited){
			Node newNode = new Node(currNode,x,y);
			this.activeQueue.add(newNode);	
			this.mazeVisited[x][y]=this.visited;
			this.nrOfNodesExpanded++;
			
			if(this.maze[x][y]==FIN){
				   this.goalNode = newNode;
				   throw reachedGoal;
			}
			
		}
	}

	//Finds target in this.maze and returns the coordiantes in [row][col]
	private int[] findInMaze(int target){
		
		int rows = maze.length;
		int cols = maze[0].length;
		int[] targetCoords = {-1,-1};
		for (int i = 0; i < rows; i++) {
			for (int ii = 0; ii < cols; ii++) {
				
				if (maze[i][ii] == target){
					targetCoords[0]=i;
					targetCoords[1]=ii;
					return targetCoords;
				}
				
			}
		}
		return targetCoords;	
	}

	public void DispSolution() {
		// TODO Auto-generated method stub
		Parser.displayBeautifulMatrix(this.solution);
		System.out.printf("Number of nodes expanded=%d\tpathcost=%d\n",this.nrOfNodesExpanded,this.pathcost);
		return;
	}
	
	public int[][] getSolution(){
		return solution;
	}
	
	
	

}


