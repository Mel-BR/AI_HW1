import java.util.ArrayList;

import searchaglorithms.DFSSearch;
import searchaglorithms.GreedySearch;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/mediumMaze.txt");
		//System.out.println(Parser.toString(maze));
                
                //Creating a more simple structure to access and to change data
                int[][] matrix = Parser.getMatrix(maze);
                
                // Displaying matrix
                //Parser.displayBeautifulMatrix(matrix);
                
                
                //DFSSearch
                DFSSearch dfsSearch = new DFSSearch(matrix);
                //Search and displays the solution
                dfsSearch.Search();
                
                GreedySearch greedySearch = new GreedySearch(matrix);
                greedySearch.search();
                greedySearch.printSolution();

	}
}
