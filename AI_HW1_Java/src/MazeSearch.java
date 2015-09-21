import java.util.ArrayList;
import searchaglorithms.AStarSearch;
import searchaglorithms.BFSSearch;
import searchaglorithms.GreedySearch;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
            
                /* Parsing input */
		// TODO Auto-generated method stub
		
				
				ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");
		             
                //Creating a more simple structure to access and to change data
                int[][] matrix = Parser.getMatrix(maze);
                
                // Displaying matrix
                //Parser.displayBeautifulMatrix(matrix);
                
                
                
                
                /* Search Algorithms */
                
                //DFSSearch
                /*DFSSearch dfsSearch = new DFSSearch(matrix);
                dfsSearch.Search();
                dfsSearch.printSolution();*/
                
                
                //BFSSearch
                BFSSearch bfsSearch = new BFSSearch(matrix);
                //Search and displays the solution
                bfsSearch.search();           
                
                
                //Greedy Search
                GreedySearch greedySearch = new GreedySearch(matrix);
                greedySearch.search();
                greedySearch.printSolution();
              
                
                //A* Search
                AStarSearch aStarSearch = new AStarSearch(matrix);
                aStarSearch.Search();
                aStarSearch.printSolution();
                 

	}
}
