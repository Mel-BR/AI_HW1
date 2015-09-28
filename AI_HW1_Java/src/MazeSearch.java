import java.util.ArrayList;

import searchaglorithms.*;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
            
        /* Parsing input */
		
		//Classic search		
		ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");
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
        /*BFSSearch2 greedySearch = new GreedySearch(matrix);
        greedySearch.search();
        greedySearch.printSolution();*/
      
        
        //A* Search
        /*AStarSearch aStarSearch = new AStarSearch(matrix);
        aStarSearch.Search();
        aStarSearch.printSolution();*/

        
        //PART 1.2
        //A* Search 
        /*AStarSearchPenalizingTurns aStarPenalizingTurns = new AStarSearchPenalizingTurns(matrix);
        aStarPenalizingTurns.Search();
        aStarPenalizingTurns.printSolution();*/
                
        //PART 1.3
        // To run the animation, run TestWindow with the desired maze in TestWindow.java
        /*AStarSearch2 pacMan = new PacMan(pacMat);
        pacMan.search();
        pacMan.printSolution();*/
	}
}
