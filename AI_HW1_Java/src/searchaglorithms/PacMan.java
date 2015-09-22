package searchaglorithms;
import static entities.Parser.FIN;
import static entities.Parser.GHOST;
import static entities.Parser.GPATH;
import static entities.Parser.PATH;
import static entities.Parser.START;
import entities.Ghost;
import entities.Node;
import entities.Pair;

/**
 * Add ghosts to A Star Search
 * @author Brian
 *
 */
public class PacMan extends AStarSearch2 {

	public PacMan(int[][] maze) {
		super(maze);
	}
	
	/**
	 * Find the starting position and add the starting node to priority queue.
	 * Find the location of the goal
	 * FInd the location of the Ghost
	 */
	@Override
	protected void findStartFin() {
		int xStart = 0; 
		int yStart = 0;
		int xGhost = 0;
		int yGhost = 0;
		// find location of start finish and ghost
		for (int i = 0; i < this.maze.length; i++) {
			for (int j = 0; j < this.maze[0].length; j++) {
				if (this.maze[i][j] == START) {
					xStart = i;
					yStart = j;
				}
				if (this.maze[i][j] == FIN) {
					this.finX = j;
					this.finY = j;
				}
				if (this.maze[i][j] == GHOST) {
					xGhost = i;
					yGhost = j;
					this.solution[i][j] = GPATH;
				}
			}
		}
		int h = heuristic(xStart, yStart);
		int cost = 0;
		// create a ghost that will try to move in a direction other than right first
		Ghost ghost = new Ghost(xGhost, yGhost, xGhost, yGhost+1);
		this.pq.add(new Node(null, xStart, yStart, ghost, cost, h));

	}

	
	/**
	 * create a new node and add queue if the new node cost is less than the current cost
	 * @param currNode
	 * @param x
	 * @param y
	 */
	@Override
	protected void checkAndAddNodeToList(Node currNode, int x, int y) {
		Ghost ghost = currNode.getGhost().copy();
		// check if pacman got eaten by the ghost.
		ghost.move(this.solution);
		// pacman dies if it switches position with the ghost of it goes into the same position as the ghost
		Boolean dead = (ghost.getPrevPos().equal(x, y) && 
				ghost.getCurrPos().equal(currNode.getX(), currNode.getY())) ||
				ghost.getCurrPos().equal(x, y);
		if (dead) {
			return;
		}
		
		// add reachable states to queue
		int cost = currNode.getCurrentPathCost()+1;
		int h = heuristic(x,y);
		if (h+cost < this.maze[x][y]) {
			this.pq.add(new Node(currNode, x, y, ghost, cost, h));
			this.maze[x][y] = h+cost;
			this.expands += 1;
		}
	}
	
	@Override
	protected void debugSolution(Node solNode) {
//		Ghost ghost = solNode.getGhost();
//		printSolution();
//		this.solution[ghost.getCurrPos().a][ghost.getCurrPos().b] = GHOST;
//		this.solution[ghost.getPrevPos().a][ghost.getPrevPos().b] = GPATH;
	}

}
