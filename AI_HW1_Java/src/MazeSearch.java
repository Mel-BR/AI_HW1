import entities.Node;
import java.util.ArrayList;


import entities.Parser;
import java.util.LinkedList;
import searchaglorithms.AStarSearch;
import searchaglorithms.DFSSearch;
import searchaglorithms.GreedySearch;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
            
                /* Parsing input */
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");
		//System.out.println(Parser.toString(maze));
                
                //Creating a more simple structure to access and to change data
                int[][] matrix = Parser.getMatrix(maze);
                
                // Displaying matrix
                //Parser.displayBeautifulMatrix(matrix);
                
                
                /* Search Algorithms */
                
                //DFSSearch
                //DFSSearch dfsSearch = new DFSSearch(matrix);
                //Search and displays the solution
                //dfsSearch.Search();
                
                                
                //BFSSearch
                //BFSSearch bfsSearch = new BFSSearch(matrix);
                //Search and displays the solution
                //bfsSearch.Search();                
                //DFSSearch
                
                
                //GreedySearch greedySearch = new GreedySearch(matrix);
                //greedySearch.search();
                //greedySearch.printSolution();
              
                
                //AStarSearch
                //AStarSearch greedySearch = new AStarSearch(matrix);
                //Search and displays the solution
                //AStarSearch.Search();
                 

	}
}
