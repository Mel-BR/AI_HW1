package GUI;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.Parser;
import searchaglorithms.AStarSearch2;
import searchaglorithms.BFSSearch;
import searchaglorithms.PacMan;

public class TestWindow implements Runnable {

	public static final String NAME = "Packman";
	
	public boolean running = false;
	
	public static JFrame frame;
	public pacPanel panel;

	public TestWindow(){
		
		ArrayList<ArrayList<Integer>> maze = Parser.parse("smallGhost.txt");
		int[][] matrix = Parser.getMatrix(maze);
		int[][] matrixClean = Parser.getMatrix(maze);
        AStarSearch2 pacman = new PacMan(matrix);
        pacman.search();
        pacman.printSolution();
		
		
//		BFSSearch bfsSearch = new BFSSearch(matrix);
//		bfsSearch.search();
		
		
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new pacPanel(pacman.getSolution(),matrixClean,this);
//		panel = new pacPanel(bfsSearch.getSolution(),matrix,this);
		
		frame.setSize(panel.windowSize);
		frame.add(panel);
		frame.setVisible(true);
		
	}
	
	public synchronized void start(){
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop(){
		running = false;
		
	}
	
	
	public void run() {

		
		long lastTimer = System.currentTimeMillis();

		while (running){
			if ((-lastTimer+System.currentTimeMillis())>20){
			lastTimer = System.currentTimeMillis();
				tick();
				render();
			
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void tick(){
		this.panel.tick();
	}

	public void render(){
		this.panel.repaint();
		
	}
	
	
	public static void main(String[] args){
		new TestWindow().start();
	}
}