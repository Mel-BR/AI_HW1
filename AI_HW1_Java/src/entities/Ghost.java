package entities;
import entities.Pair;
import static entities.Parser.GPATH;

import java.util.ArrayList;

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
	
	public void move(int[][] maze) {
		ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
		int x = currPos.a;
		int y = currPos.b;
		if (maze[x-1][y] == GPATH) moves.add(new Pair<Integer, Integer> (x-1, y));
		if (maze[x+1][y] == GPATH) moves.add(new Pair<Integer, Integer> (x+1, y));
		if (maze[x][y-1] == GPATH) moves.add(new Pair<Integer, Integer> (x, y-1));
		if (maze[x][y+1] == GPATH) moves.add(new Pair<Integer, Integer> (x, y+1));
		
		
		Pair<Integer, Integer> move = null;
		for (Pair<Integer, Integer> m : moves) {
			if (!m.equal(this.prevPos)) {
				move = m;
			}
		}
		
		if (move == null) {
			Pair<Integer, Integer> temp = currPos;
			this.currPos = this.prevPos;
			this.prevPos = temp;
		} else {
			this.prevPos = this.currPos;
			this.currPos = move;
		}
		
	}
	
	public Pair<Integer, Integer> getCurrPos() {
		return this.currPos;
	}
	
	public Pair<Integer, Integer> getPrevPos() {
		return this.prevPos;
	}
}
