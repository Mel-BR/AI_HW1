import java.util.ArrayList;

import searchaglorithms.*;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
            
        /* Parsing input */

        /* Available files*/
//        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/mediumMaze.txt");		
//        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/bigMaze.txt");		
        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");		
//        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/smallTurns.txt");		
//        ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/bigTurns.txt");
            
        
        //Classic search
        int[][] matrix = Parser.getMatrix(maze);
                
        //Animated search
//        ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/smallGhost.txt"); 
//        ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/mediumGhost.txt"); 
//        ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/bigGhost.txt"); 
        ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/complicatedGhostPathMaze.txt"); 
        int[][] pacMat = Parser.getMatrix(pacMaze);

                
        // PART 1.1
        /* Search Algorithms */
        
        //DFSSearch
        /*DFSSearch dfsSearch = new DFSSearch(matrix);
        dfsSearch.Search();
        dfsSearch.printSolution();*/
        
        //BFSSearch
        /*BFSSearch2 bfsSearch = new BFSSearch2(matrix);
        bfsSearch.search();
        bfsSearch.printSolution();*/
        
        //BFSSearch (alternate)
        /*BFSSearch bfsSearch = new BFSSearch(matrix);
        //Search and displays the solution
        bfsSearch.search();*/             
        
        //Greedy Search
        /*BFSSearch2 greedySearch = new GreedySearch2(matrix);
        greedySearch.search();
        greedySearch.printSolution();*/

        //A* Search
        AStarSearch aStarSearch = new AStarSearch(matrix);
        aStarSearch.Search();
        aStarSearch.printSolution();
        
        //A* Search (alternative)
        /*BFSSearch2 aStarSearch = new AStarSearch2(matrix);
        aStarSearch.search();
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
                
        //PART 1.3
        // To run the animation, run TestWindow with the desired maze in TestWindow.java
        /*AStarSearch2 pacMan = new PacMan(pacMat);
        pacMan.search();
        pacMan.printSolution();*/

	}
}
