package GUI;

import static entities.Parser.FIN;
import static entities.Parser.PATH;
import static entities.Parser.START;
import static entities.Parser.WALL;
import static entities.Parser.PATH2;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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

	private BufferedImage ghost;
	private BufferedImage pacman2;
	private BufferedImage pacman4;

	
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
			
			path = ImageIO.read(this.getClass().getResource("path.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			goal = ImageIO.read(this.getClass().getResource("goal.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			floor = ImageIO.read(this.getClass().getResource("floor.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			start = ImageIO.read(this.getClass().getResource("start.png")).getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);

       } catch (IOException ex) {
            System.out.println("could not find picture/s");
       }
       
         
    }
    
    
    public void tick(){
    	
    	tileTicks++;
    	if (tileTicks>=ticksPerTile){
    		int[] temp = {0,0};
    		temp[0] = this.currPos[0];
	    	temp[1] = this.currPos[1];
	    	this.currMaze[this.currPos[0]][this.currPos[1]] = PATH2;
	    	findNext();
	    	this.lastPos[0] = temp[0];
	    	this.lastPos[1] = temp[1];
	    	tileTicks = 0;
	    	togglePacman = (togglePacman+1)%2;
    	}
    	
    	this.pacmanPosRot[0] = lastPos[0]*imageSize+((-lastPos[0]+currPos[0])*imageSize*tileTicks)/ticksPerTile;
    	this.pacmanPosRot[1] = lastPos[1]*imageSize+((-lastPos[1]+currPos[1])*imageSize*tileTicks)/ticksPerTile;
    	
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
	
private void findNext(){
		
	
	int xold = lastPos[0];
	int yold = lastPos[1];
	
	int x = currPos[0];
	int y = currPos[1];
		
	if		(solMaze[x+1][y] == PATH2 & (x+1)!=xold){
		currPos[0]++;
		pacmanPosRot[2]=0;
	}else if(solMaze[x-1][y] == PATH2 & (x-1)!=xold){
		currPos[0]--;
		pacmanPosRot[2]=180;
	}else if(solMaze[x][y+1] == PATH2 & (y+1)!=yold){
		currPos[1]++;
		pacmanPosRot[2]=90;
	}else if(solMaze[x][y-1] == PATH2 & (y-1)!=yold){
		currPos[1]--;
		pacmanPosRot[2]=270;
	}else{
		this.testWindow.stop();
	}
	
	
	}
    
    
    private void paintPacman(Graphics g){
    	int drawLocationX = pacmanPosRot[0];
    	int drawLocationY = pacmanPosRot[1];

    	// Rotation information

    	double rotationRequired = Math.toRadians (pacmanPosRot[2]);
    	double locationX = pacman2.getWidth(null) / 2;
    	double locationY = pacman2.getHeight(null) / 2;
    	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
    	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
    	
    	Graphics2D g2d = (Graphics2D) g;
    	// Drawing the rotated image at the required drawing locations
    	if (togglePacman == 1){
    	g2d.drawImage(op.filter(pacman2, null), drawLocationX, drawLocationY, null);}
    	else{
        	g2d.drawImage(op.filter(pacman4, null), drawLocationX, drawLocationY, null);}
    	
    }

    
   private void updateMaze(Graphics g){
		for(int i = 0; i < currMaze.length; i++){
			for (int j = 0; j < currMaze[i].length; j++){
				int value = currMaze[i][j];
				if (value==WALL){
			        g.drawImage(wall, i*imageSize, j*imageSize, null);
				}else if(value==PATH){
					g.drawImage(floor, i*imageSize, j*imageSize, null);
				}
				else if(value==FIN){
					g.drawImage(goal, i*imageSize, j*imageSize, null);
				}
				else if(value==START){
					g.drawImage(start, i*imageSize, j*imageSize, null);
				}
				else if(value==PATH2){
					g.drawImage(path, i*imageSize, j*imageSize, null);
				}
				else{
					System.out.printf("Unknown ID number for image tile:%d\n",value);
				}
				
			}
		}
				
	   
   }
    
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        updateMaze(g);
        paintPacman(g);
    }

}