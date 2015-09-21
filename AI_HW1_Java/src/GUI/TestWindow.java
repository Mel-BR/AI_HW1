package GUI;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Parser;
import searchaglorithms.BFSSearch;

public class TestWindow implements Runnable {


	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Packman";

	public static final int SIZE =1680;
	
	public boolean running = false;
	
	private int tickCount = 0;
	
	public static JFrame frame;
	public pacPanel panel;

	public static int windowW = SIZE;

	public static int windowH = SIZE*10/16;

	public static float scaleW = 1;
	public static float scaleH = 1;
	
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
		tickCount++;
		this.panel.tick();
	}

	public void render(){
		this.panel.repaint();
		//frame.panel.repaint();
		
	}
	
	
	public static void main(String[] args){
		new TestWindow().start();
	}
}