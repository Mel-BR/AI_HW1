import java.util.ArrayList;

import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/bigMaze.txt");
		System.out.println(Parser.toString(maze));
	}

}
