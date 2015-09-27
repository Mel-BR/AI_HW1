package GUI;

import static entities.Parser.FIN;
import static entities.Parser.GHOST;
import static entities.Parser.GPATH;
import static entities.Parser.START;
import static entities.Parser.WALL;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Stack;

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
	
	//-------------
	private Stack<int[]> objectPath;

	
	private int[] startPos;
	private int imageSize = 18;
	private int[][] currMaze;
	public Dimension windowSize;

	private TestWindow testWindow;
	private int tileTicks = 0;
	private int ticksPerTile = 10;
	private int togglePacman = 0;
	
	private int[] newObjectPos = {10,10,10,10};
	private int[] oldObjectPos = {20,20,20,20};
	private int[] pacmanPosRot = {300,12,45}; //{x,y,rotation{deg}}
	private int[] ghostPosRot = {100,12,0};
	
	private AudioInputStream audioInPacman;
	private AudioInputStream audioInEatFruit;
	private Clip pacmanSound;
	private Clip eatFruitSound;
	private int[] startPosGhost;
	
	private int[][] ghostMaze;
	
    public pacPanel(Stack<int[]> objectPath, int[][] currMaze, TestWindow testWindow) {
    	
    	this.testWindow = testWindow;
    	this.objectPath = objectPath;
    	this.currMaze = currMaze;
    	int width = (this.currMaze[0].length+1)*imageSize;
    	int height = (this.currMaze.length+2)*imageSize;
    	windowSize = new Dimension();
    	this.windowSize.setSize(width+5,height+20);
    	
    	this.startPos = findInMaze(START);
    	this.newObjectPos[0] = this.startPos[0];
    	this.newObjectPos[1] = this.startPos[1];
    	this.startPosGhost = findInMaze(GHOST);
    	this.newObjectPos[2] = this.startPosGhost[0];
    	this.newObjectPos[3] = this.startPosGhost[1];

    	this.currMaze[this.startPosGhost[1]][this.startPosGhost[0]] = GPATH;
    	
    	//Parser.displayBeautifulMatrix(currMaze);
    	//Parser.displayRawMatrix(currMaze);
    	
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
	    	this.currMaze[this.newObjectPos[1]][this.newObjectPos[0]] = START;
	    	oldObjectPos[0] = newObjectPos[0];
	    	oldObjectPos[1] = newObjectPos[1];
	    	oldObjectPos[2] = newObjectPos[2];
	    	oldObjectPos[3] = newObjectPos[3];
	    	if (!objectPath.isEmpty()){
	    		newObjectPos = objectPath.pop();
	    	}else{
	    		endAnimation();
	    	}
	    	
	    	if ((newObjectPos[0]-oldObjectPos[0])>0){
	    		pacmanPosRot[2] = 0;
	    	}else if ((newObjectPos[0]-oldObjectPos[0])<0){
	    		pacmanPosRot[2] = 180;
	    	}else if ((newObjectPos[1]-oldObjectPos[1])<0){
	    		pacmanPosRot[2] = -90;
	    	}else if ((newObjectPos[1]-oldObjectPos[1])>0){
	    		pacmanPosRot[2] = 90;
	    	}
	    	
	    	
	    	togglePacman = (togglePacman+1)%2;
	    		    	
	    	

	    	
    	}
    	
    	this.pacmanPosRot[0] = oldObjectPos[0]*imageSize+((-oldObjectPos[0]+newObjectPos[0])*imageSize*tileTicks)/ticksPerTile;
    	this.pacmanPosRot[1] = oldObjectPos[1]*imageSize+((-oldObjectPos[1]+newObjectPos[1])*imageSize*tileTicks)/ticksPerTile;
    	
    	this.ghostPosRot[0] = oldObjectPos[2]*imageSize+((-oldObjectPos[2]+newObjectPos[2])*imageSize*tileTicks)/ticksPerTile;
    	this.ghostPosRot[1] = oldObjectPos[3]*imageSize+((-oldObjectPos[3]+newObjectPos[3])*imageSize*tileTicks)/ticksPerTile;

    			
    }
    
    
    
    private void endAnimation(){
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
    }
    
    
    
	//Finds target in this.maze and returns the coordiantes in [row][col]
	private int[] findInMaze(int target){
		
		int rows = currMaze.length;
		int cols = currMaze[0].length;
		int[] targetCoords = {-1,-1};
		for (int i = 0; i < rows; i++) {
			for (int ii = 0; ii < cols; ii++) {
				
				if (currMaze[i][ii] == target){
					targetCoords[1]=i;
					targetCoords[0]=ii;
					return targetCoords;
				}
				
			}
		}
		return targetCoords;	
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
			        g.drawImage(wall, j*imageSize, i*imageSize, null);
				}else if(value==FIN){
					g.drawImage(goal, j*imageSize, i*imageSize, null);
				}
				else if(value==START){
					g.drawImage(start, j*imageSize, i*imageSize, null);
				}
				else if(value==GHOST){
					g.drawImage(path, j*imageSize, i*imageSize, null);
				}
				else{
					g.drawImage(floor, j*imageSize, i*imageSize, null);
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