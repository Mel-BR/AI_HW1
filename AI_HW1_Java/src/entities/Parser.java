package entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
	
	public static final Integer WALL = -2; 
	public static final Integer PATH = 2147483647;
	public static final Integer START = PATH-1;
	public static final Integer FIN = PATH-2;
	public static final Integer GPATH = PATH-3;
	public static final Integer GHOST = PATH-4;
	public static final Integer PATH2 = -3;
//	public static final Integer PATH = -16256518;
//	public static final Integer START = -1;
//	public static final Integer FIN = PATH-1;
	
	/**
	 * parses the maze txt file into an arraylist matrix of integers
	 * @param filename
	 * @return maze
	 */
	public static ArrayList<ArrayList<Integer>> parse(String filename) {
		
		URL url = Parser.class.getClassLoader().getResource(filename);
		
		ArrayList<ArrayList<Integer>> maze = new ArrayList<ArrayList<Integer>>();
		
		Scanner s = null;
		
		try {
			s = new Scanner(new BufferedReader(new FileReader(filename)));
			while (s.hasNextLine()) {
				maze.add(parseString(s.nextLine()));
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} finally {
			if (s != null) {
				s.close();
			}
		}
		 
		return maze;
	}
	
	/**
	 * convert the maze matrix back into a string
	 * @param maze
	 * @return string format maze
	 */
	public static String toString(ArrayList<ArrayList<Integer>> maze) {
		String mazeString = "";
		for (ArrayList<Integer> row: maze) {
			for (Integer label: row) {
				char ch;
				if (label == WALL)
					ch = '%';
				else if (label == PATH)
					ch = ' ';
				else if (label == START)
					ch = '.';
				else if (label == FIN)
					ch = 'P';
				else if (label == GHOST)
					ch = 'G';
				else
					ch = ' ';
				mazeString += ch;
			}
			mazeString += "\n";
			
		}
		return mazeString;
	}
	
	/**
	 * parses string into a row of the maze
	 * @param line
	 * @return row
	 */
	private static ArrayList<Integer> parseString(String line) {
		ArrayList<Integer> row = new ArrayList<Integer>();
		for (char ch: line.toCharArray()) {
			Integer label = null;
			switch(ch) { 
			case '%':
				label = WALL;
				break;
			case ' ':
				label = PATH;
				break;
			case '.':
				label = START;
				break;
			case 'P':
				label = FIN;
				break;
			case 'G':
				label = GHOST;
				break;
			case 'g':
				label = GPATH;
				break;
			default:
				label = PATH;
				break;
			}
			row.add(label);
		}
		
		return row;
	}
        
        /**
	 * create a simple two-dimensional array from the double list
	 * @param maze
	 * @return two-dimensional array of integers
	 */
        public static int[][] getMatrix(ArrayList<ArrayList<Integer>> integers)
        {
            int[][] ret = new int[integers.size()][integers.get(0).size()];
            for (int i=0; i < ret.length; i++)
            {            
                for (int j=0; j < ret[0].length; j++)
                {
                    ret[i][j] = integers.get(i).get(j).intValue();
                }
            }
            return ret;
        }
        
        /**
	 * display the matrix with numbers
	 * @param maze
	 * @return string format maze
	 */
        public static void displayRawMatrix(int[][] m)
        {
                for(int i=0;i<m.length;i++)
                {
                        for(int j=0 ;j<m[0].length ;j++) {
                            System.out.print(m[i][j]+" ");
                        }
                        System.out.print("\n");
                }
        }        
        
        /**
	 * display the matrix with characters
	 * @param maze
	 * @return nothing
	 */
        public static void displayBeautifulMatrix(int[][] m)
        {
            String mazeString = "";
            for(int i=0;i<m.length;i++)
            {
                for(int j=0 ;j<m[0].length ;j++) {
                    char ch;
                    if (m[i][j] == WALL)
                        ch = '%';
                    else if (m[i][j]  == PATH)
                        ch = ' ';
                    else if (m[i][j]  == START)
                        ch = '.';
                    else if (m[i][j]  == FIN)
                        ch = 'P';
    				else if (m[i][j] == GHOST)
    					ch = 'G';
                    else
                        ch = ' ';
                    mazeString += ch;
                }
                mazeString += "\n";
            }
            System.out.println(mazeString);
            
        }
}
