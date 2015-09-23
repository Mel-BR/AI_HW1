package GUI;

import static entities.Parser.FIN;
import static entities.Parser.PATH;
import static entities.Parser.PATH2;
import static entities.Parser.START;
import static entities.Parser.WALL;
import static entities.Parser.GHOST;
import static entities.Parser.GPATH;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import entities.Parser;

public class pacPanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image wall;
	private Image pacman;
	private Image pacman3;
	private Image path;
	private Image goal;
	private Image floor;
	private Image start;
	private Image ghost1;

	//private BufferedImage ghost;
	private BufferedImage pacman2;
	private BufferedImage pacman4;
	private BufferedImage ghost2;

	
	private int[] startPos;
	private int imageSize = 25;
	private int[][] solMaze;
	private int[][] currMaze;
	public Dimension windowSize;
	private int[] pacmanPosRot = {300,12,45}; //{x,y,rotation{deg}}
	private int[] currPos;
	private int[] lastPos = {9999,9999};
	private TestWindow testWindow;
	private int tileTicks = 0;
	private int ticksPerTile = 10;
	private int togglePacman = 0;
	
	AudioInputStream audioInPacman;
	AudioInputStream audioInEatFruit;
	private Clip pacmanSound;
	private Clip eatFruitSound;
	private int[] startPosGhost;
	private int[] lastPosGhost= {9999,9999};
	private int[] currPosGhost;
	private int[] ghostPosRot = {100,12,0};
	private int[][] ghostMaze;
	
    public pacPanel(int[][] solMaze, int[][] currMaze, TestWindow testWindow) {
    	
    	this.testWindow = testWindow;
    	this.solMaze = solMaze;
    	this.currMaze = currMaze;
    	int width = (this.currMaze.length+1)*imageSize;
    	int height = (this.currMaze[0].length+2)*imageSize;
    	windowSize = new Dimension();
    	this.windowSize.setSize(width,height);
    	
    	this.startPos = findInMaze(START);
    	this.currPos = this.startPos;
    	this.startPosGhost = findInMaze(GHOST);
    	this.currPosGhost = this.startPosGhost;
    	
		this.ghostMaze = new int[currMaze.length][currMaze[0].length];
		for(int i = 0; i < currMaze.length; i++)
			for (int j = 0; j < currMaze[i].length; j++)
				this.ghostMaze[i][j] = this.currMaze[i][j];
		
    	this.ghostMaze[this.startPosGhost[0]][this.startPosGhost[1]] = GPATH;
    	this.currMaze[this.startPosGhost[0]][this.startPosGhost[1]] = GPATH;
    	
    	
    	System.out.println(startPos[0]);
    	System.out.println(startPos[1]);
    	Parser.displayRawMatrix(currMaze);
    	
       try {          
			wall = ImageIO.read(this.getClass().getResource("wall.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			pacman = ImageIO.read(this.getClass().getResource("pacman.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			
			pacman2 = new BufferedImage(pacman.getWidth(null), pacman.getHeight(null), BufferedImage.TYPE_INT_ARGB);   
			// Draw the image on to the buffered image
			Graphics2D bGr = pacman2.createGraphics();
			bGr.drawImage(pacman, 0, 0, null);
			bGr.dispose();
			
			pacman3 = ImageIO.read(this.getClass().getResource("pacman2.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			
			pacman4 = new BufferedImage(pacman3.getWidth(null), pacman3.getHeight(null), BufferedImage.TYPE_INT_ARGB);   
			// Draw the image on to the buffered image
			Graphics2D bGr2 = pacman4.createGraphics();
			bGr2.drawImage(pacman3, 0, 0, null);
			bGr2.dispose();
			
			ghost1 = ImageIO.read(this.getClass().getResource("ghost.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			
			ghost2 = new BufferedImage(pacman3.getWidth(null), pacman3.getHeight(null), BufferedImage.TYPE_INT_ARGB);   
			// Draw the image on to the buffered image
			Graphics2D bGr3 = ghost2.createGraphics();
			bGr3.drawImage(ghost1, 0, 0, null);
			bGr3.dispose();
			
			path = ImageIO.read(this.getClass().getResource("path.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			goal = ImageIO.read(this.getClass().getResource("goal.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			floor = ImageIO.read(this.getClass().getResource("floor.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			start = ImageIO.read(this.getClass().getResource("start.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);

       } catch (IOException ex) {
            System.out.println("could not find picture/s");
       }
       
       try {
	         // Open an audio input stream.
	         URL url = this.getClass().getResource("pacman_chomp.wav");
	         audioInPacman = AudioSystem.getAudioInputStream(url);
	         
	         url = this.getClass().getResource("pacman_eatfruit.wav");
	         audioInEatFruit = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         pacmanSound = AudioSystem.getClip();
	         eatFruitSound = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         pacmanSound.open(audioInPacman);
	         pacmanSound.loop(Clip.LOOP_CONTINUOUSLY);
	      } catch (UnsupportedAudioFileException er) {
	         er.printStackTrace();
	      } catch (IOException ee) {
	         ee.printStackTrace();
	      } catch (LineUnavailableException eee) {
	         eee.printStackTrace();
	      }
       
         
    }
    
    
    public void tick(){
    	
    	tileTicks++;
    	if (tileTicks>=ticksPerTile){
    		
			tileTicks = 0;
	    	this.currMaze[this.currPos[0]][this.currPos[1]] = START;
	    	findNext(this.solMaze,this.lastPos,this.currPos,START,1);
	    	
	    	togglePacman = (togglePacman+1)%2;
	    	
	    	findNext(this.ghostMaze,this.lastPosGhost,this.currPosGhost,GPATH,0);
	    	
	    	

	    	
    	}
    	
    	this.pacmanPosRot[0] = lastPos[0]*imageSize+((-lastPos[0]+currPos[0])*imageSize*tileTicks)/ticksPerTile;
    	this.pacmanPosRot[1] = lastPos[1]*imageSize+((-lastPos[1]+currPos[1])*imageSize*tileTicks)/ticksPerTile;
    	
    	this.ghostPosRot[0] = lastPosGhost[0]*imageSize+((-lastPosGhost[0]+currPosGhost[0])*imageSize*tileTicks)/ticksPerTile;
    	this.ghostPosRot[1] = lastPosGhost[1]*imageSize+((-lastPosGhost[1]+currPosGhost[1])*imageSize*tileTicks)/ticksPerTile;

    			
    }
    
	//Finds target in this.maze and returns the coordiantes in [row][col]
	private int[] findInMaze(int target){
		
		int rows = currMaze.length;
		int cols = currMaze[0].length;
		int[] targetCoords = {-1,-1};
		for (int i = 0; i < rows; i++) {
			for (int ii = 0; ii < cols; ii++) {
				
				if (currMaze[i][ii] == target){
					targetCoords[0]=i;
					targetCoords[1]=ii;
					return targetCoords;
				}
				
			}
		}
		return targetCoords;	
	}
	
private void findNext(int[][] maze, int[] lastPos, int[] currPos, int pathValue, int isPacman){
		
	
	int xold = lastPos[0];
	int yold = lastPos[1];
	
	lastPos[0] = currPos[0];
	lastPos[1] = currPos[1];
	
	int x = currPos[0];
	int y = currPos[1];
		
	if		(maze[x+1][y] == pathValue & (x+1)!=xold){
		currPos[0]++;
		if (isPacman==1){pacmanPosRot[2] = 0;}
	}else if(maze[x-1][y] == pathValue & (x-1)!=xold){
		currPos[0]--;
		if (isPacman==1){pacmanPosRot[2] = 180;}
	}else if(maze[x][y+1] == pathValue & (y+1)!=yold){
		currPos[1]++;
		if (isPacman==1){pacmanPosRot[2] = 90;}
	}else if(maze[x][y-1] == pathValue & (y-1)!=yold){
		currPos[1]--;
		if (isPacman==1){pacmanPosRot[2] = 270;}
	}else{
		
		if (isPacman==1){
			this.testWindow.stop();
			this.pacmanSound.stop();
	        try {
				eatFruitSound.open(audioInEatFruit);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        eatFruitSound.start();
		}else{
			findNext(this.ghostMaze,this.lastPosGhost,this.currPosGhost,GPATH,0);
		}
	}

	
	
	
	
	}
    
    
    private void paintObject(Graphics g,int isPacman, BufferedImage icon1,BufferedImage icon2,int[] posRot){
    	int drawLocationX = posRot[0];
    	int drawLocationY = posRot[1];
    	int rot = posRot[2];

    	// Rotation information
    	double rotationRequired = Math.toRadians (rot);
    	double locationX = icon1.getWidth(null) / 2;
    	double locationY = icon1.getHeight(null) / 2;
    	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
    	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	// Drawing the rotated image at the required drawing locations
    	if (togglePacman == 1){
    	g2d.drawImage(op.filter(icon1, null), drawLocationX, drawLocationY, null);}
    	else{
        	g2d.drawImage(op.filter(icon2, null), drawLocationX, drawLocationY, null);}
    	
    }

    
   private void updateMaze(Graphics g){
		for(int i = 0; i < currMaze.length; i++){
			for (int j = 0; j < currMaze[i].length; j++){
				int value = currMaze[i][j];
				if (value==WALL){
			        g.drawImage(wall, i*imageSize, j*imageSize, null);
				}else if(value==FIN){
					g.drawImage(goal, i*imageSize, j*imageSize, null);
				}
				else if(value==START){
					g.drawImage(start, i*imageSize, j*imageSize, null);
				}
				else if(value==GHOST){
					g.drawImage(path, i*imageSize, j*imageSize, null);
				}
				else{
					g.drawImage(floor, i*imageSize, j*imageSize, null);
					//System.out.printf("Unknown ID number for image tile:%d\n",value);
				}
				
			}
		}
				
	   
   }
    
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        updateMaze(g);
        paintObject(g,1,pacman2,pacman4,pacmanPosRot);
        paintObject(g,0,ghost2,ghost2,ghostPosRot);
    }

}