package entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
	
	public static final Integer WALL = 100000000;
	public static final Integer PATH = 0;
	public static final Integer START = -2;
	public static final Integer FIN = -1;
	
	/**
	 * parses the maze txt file into an arraylist matrix of integers
	 * @param filename
	 * @return maze
	 */
	public static ArrayList<ArrayList<Integer>> parse(String filename) {
		
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
				else
					ch = '%';
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
			default:
				label = WALL;
				break;
			}
			row.add(label);
		}
		
		return row;
	}
}
