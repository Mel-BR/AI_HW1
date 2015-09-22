package searchaglorithms;

import entities.Node;

public class AStarSearch2 extends BFSSearch2 {
	
	public AStarSearch2(int[][] maze) {
		super(maze);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * create a new node and add queue if the new node cost is less than the current cost
	 * @param currNode
	 * @param x
	 * @param y
	 */
	@Override
	protected void checkAndAddNodeToList(Node currNode, int x, int y) {
		int cost = currNode.getCurrentPathCost()+1;
		int h = heuristic(x,y);
		if (h+cost < this.maze[x][y]) {
			this.pq.add(new Node(currNode, x, y, cost, h));
			this.maze[x][y] = h+cost;
			this.expands += 1;
		}
	}
}
