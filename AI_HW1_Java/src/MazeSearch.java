import java.util.ArrayList;

import searchaglorithms.AStarSearch2;
import searchaglorithms.BFSSearch2;
import searchaglorithms.PacMan;
import entities.Parser;



public class MazeSearch {

	public static void main(String[] args) {
            
                /* Parsing input */
		// TODO Auto-generated method stub
		
				
				ArrayList<ArrayList<Integer>> maze = Parser.parse("src/input/openMaze.txt");
				ArrayList<ArrayList<Integer>> pacMaze = Parser.parse("src/input/smallGhost.txt");     
                //Creating a more simple structure to access and to change data
                int[][] matrix = Parser.getMatrix(maze);
                int[][] pacMat = Parser.getMatrix(pacMaze);
                
                // Displaying matrix
                //Parser.displayBeautifulMatrix(matrix);
                
                
                
                
                /* Search Algorithms */
                
                //DFSSearch
                /*DFSSearch dfsSearch = new DFSSearch(matrix);
                dfsSearch.Search();
                dfsSearch.printSolution();*/
                
                
                //BFSSearch
                /*BFSSearch bfsSearch = new BFSSearch(matrix);
                //Search and displays the solution
                bfsSearch.search();*/           
                
                
                //Greedy Search
                /*BFSSearch2 greedySearch = new AStarSearch2(matrix);
                greedySearch.search();
                greedySearch.printSolution();*/
              
                AStarSearch2 pacman = new PacMan(pacMat);
                pacman.search();
                pacman.printSolution();
                //A* Search
                /*AStarSearch aStarSearch = new AStarSearch(matrix);
                aStarSearch.Search();
                aStarSearch.printSolution();*/
            

	}
}
