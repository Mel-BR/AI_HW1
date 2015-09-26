package GUI;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

import entities.Node;
import entities.Parser;
import searchaglorithms.AStarSearch2;
import searchaglorithms.PacMan;

public class TestWindow implements Runnable {

	public static final String NAME = "Packman";
	
	public boolean running = false;
	
	public static JFrame frame;
	public pacPanel panel;

	public TestWindow(){
		
		ArrayList<ArrayList<Integer>> maze = Parser.parse("bigGhost.txt");
//		ArrayList<ArrayList<Integer>> maze = Parser.parse("complicatedGhostPathMaze.txt");
		int[][] matrix = Parser.getMatrix(maze);
		int[][] matrixClean = Parser.getMatrix(maze);
        AStarSearch2 pacman = new PacMan(matrix);
        pacman.search();
        
        Node currNode = pacman.getSolNode();
        Stack<int[]> objectPath = new Stack<int[]>();

        while(currNode.getParent()!=null){
        	int[] pos = new int[4];
        	pos[0] = currNode.getX();
        	pos[1] = currNode.getY();
        	pos[2] = currNode.getGhost().getCurrPos().a;
        	pos[3] = currNode.getGhost().getCurrPos().b;
        	objectPath.push(pos);
        	currNode = currNode.getParent();
        	
        }

		
		
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		panel = new pacPanel(objectPath,matrixClean,this);
		
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