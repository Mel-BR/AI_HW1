package entities;
import entities.Pair;
import static entities.Parser.GPATH;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Brian
 *
 */
public class Ghost {
	private Pair<Integer, Integer> currPos;
	private Pair<Integer, Integer> prevPos;
	
	public Ghost(Pair<Integer, Integer> currPos, Pair<Integer, Integer> prevPos) {
		this.currPos = currPos;
		this.prevPos = prevPos;
	}
	
	public Ghost(int x, int y, int xPrev, int yPrev) {
		this.currPos = new Pair<Integer, Integer>(x, y);
		this.prevPos = new Pair<Integer, Integer>(xPrev, yPrev);
	}
	
	public Ghost copy() {
		return new Ghost(currPos.a, currPos.b, prevPos.a, prevPos.b); 
	}
	
	/**
	 * move the ghost based on its current possible moves and its current and previous position
	 * @param maze
	 */
	public void move(int[][] maze) {
		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
		int x = currPos.a;
		int y = currPos.b;
		// add possible moves to a list
		if (maze[x-1][y] == GPATH) moves.add(new Pair<Integer, Integer> (x-1, y));
		if (maze[x+1][y] == GPATH) moves.add(new Pair<Integer, Integer> (x+1, y));
		if (maze[x][y-1] == GPATH) moves.add(new Pair<Integer, Integer> (x, y-1));
		if (maze[x][y+1] == GPATH) moves.add(new Pair<Integer, Integer> (x, y+1));
		
		// move the ghost a location other than its previous position.
		ArrayList<Pair<Integer, Integer>> frontMoves = new ArrayList<Pair<Integer,Integer>>();
		for (Pair<Integer, Integer> m : moves) {
			if (!m.equal(this.prevPos)) {
				frontMoves.add(m);
			}
		}
		
		// if no moves found, ghost needs to turn back, otherwise move the last available position in the list
		if (frontMoves.size() == 0) {
			Pair<Integer, Integer> temp = currPos;
			this.currPos = this.prevPos;
			this.prevPos = temp;
		} else {
			this.prevPos = this.currPos;
			this.currPos = frontMoves.get(new Random().nextInt(frontMoves.size()));
		}
		
	}
	
	public Pair<Integer, Integer> getCurrPos() {
		return this.currPos;
	}
	
	public Pair<Integer, Integer> getPrevPos() {
		return this.prevPos;
	}
}
