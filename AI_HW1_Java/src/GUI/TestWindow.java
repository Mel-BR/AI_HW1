package GUI;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.Parser;
import searchaglorithms.BFSSearch;

public class TestWindow implements Runnable {

	public static final String NAME = "Packman";
	
	public boolean running = false;
	
	public static JFrame frame;
	public pacPanel panel;

	public TestWindow(){
		
		ArrayList<ArrayList<Integer>> maze = Parser.parse("openMaze.txt");
		int[][] matrix = Parser.getMatrix(maze);
		BFSSearch bfsSearch = new BFSSearch(matrix);
		bfsSearch.search();
		
		
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new pacPanel(bfsSearch.getSolution(),matrix,this);
		
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