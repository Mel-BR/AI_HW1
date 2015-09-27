import java.util.ArrayList;
import searchaglorithms.AStarSearch2;
import searchaglorithms.BFSSearch2;
import searchaglorithms.PacMan;
import entities.Parser;
import searchaglorithms.AStarSearch;
import searchaglorithms.BFSSearch;
import searchaglorithms.DFSSearch;
import searchaglorithms.GreedySearch2;
import entities.Parser;
import searchaglorithms.AStarSearchPenalizingTurnsMD;



public class MazeSearch {

	public static void main(String[] args) {
            
        /* Parsing input */

        /* Available files*/
        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/mediumMaze.txt");		
        //ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/bigMaze.txt");		
        //ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");		
        //ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/smallTurns.txt");		
        //ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/bigTurns.txt");
            
        
        //Classic search
        int[][] matrix = Parser.getMatrix(maze);
                
        //Animated search
        ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/complicatedGhostPathMaze.txt"); 
        int[][] pacMat = Parser.getMatrix(pacMaze);

                
        // PART 1.1
        /* Search Algorithms */
        
        //DFSSearch
        /*DFSSearch dfsSearch = new DFSSearch(matrix);
        dfsSearch.Search();
        dfsSearch.printSolution();*/
        
        
        //BFSSearch
        //BFSSearch bfsSearch = new BFSSearch(matrix);
        //Search and displays the solution
        //bfsSearch.search();             

        
        //Greedy Search
        /*BFSSearch2 greedySearch = new AStarSearch2(matrix);
        greedySearch.search();
        greedySearch.printSolution();*/

        
        //A* Search
        /*AStarSearch aStarSearch = new AStarSearch(matrix);
        aStarSearch.Search();
        aStarSearch.printSolution();*/
             
                
        //PART 1.2
        //A* Search penalizing turns
        /*int forwardCost = 2;
        int turnCost = 1;
        String typeHeuristic = "New"; 
        // can be either "MD" or "New"
        AStarSearchPenalizingTurnsMD aStarPenalizingTurnsMD = new AStarSearchPenalizingTurnsMD(matrix, turnCost,forwardCost,typeHeuristic);
        aStarPenalizingTurnsMD.Search();
        aStarPenalizingTurnsMD.printSolution();*/
        
        

	}
}
