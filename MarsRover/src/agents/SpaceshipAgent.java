package agents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ontologies.GridField;
import swing.GridComponent;
import swing.BoardInstructionsComponent;
import jade.core.Agent;
import java.util.Random;

public class SpaceshipAgent extends Agent {

	
	private GridField[][] map;
	private int dimensions;
	private JFrame mapFrame;
	private GridComponent gridComp;
        private BoardInstructionsComponent boardComp;
	private int gridSize;
	private int xSpaceship;
	private int ySpaceship;
        private int xSpaceshipGridPos;
        private int ySpaceshipGridPos;
	
	protected void setup(){
		Object[] args = getArguments();
		
		dimensions = 15;
		gridSize = 25;
		
		map = new GridField[dimensions][dimensions];
		
		mapFrame = new JFrame();
		mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mapFrame.setPreferredSize(new Dimension(800, 680));
		
		gridComp = new GridComponent();
		gridComp.setPreferredSize(new Dimension(200, 200));
                
                boardComp = new BoardInstructionsComponent();
                
		JButton button = new JButton("New Line");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x1 = (int) (Math.random()*200);
				int y1 = (int) (Math.random()*200);
				int x2 = (int) (Math.random()*200);
				int y2 = (int) (Math.random()*200);
				Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
				gridComp.addGrid(x1, y1, x2, y2, randomColor, "");
				
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		
		mapFrame.getContentPane().add(gridComp, BorderLayout.CENTER);
                mapFrame.getContentPane().add(boardComp, BorderLayout.WEST);
		mapFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		mapFrame.pack();
		mapFrame.setVisible(true);
		
		fillMap();
		
		setSpaceshipLocation();
		
		printMap();
		printSpaceshipLocation();
                
                initObstacles();
                initRocks();
                
                printObsticlesAndRocks();
                
                printBoard();
	}
	
	public void printSpaceshipLocation(){
		gridComp.addGrid(xSpaceship, ySpaceship, gridSize, gridSize, Color.BLUE, "S");
	}
        
        public void printBoard(){
            boardComp.addText("Spaceship = S", "Obsticles = O", "Number of Rocks in gridfield = Number");
        }
        
        public void printObsticlesAndRocks(){
            for(int tx = 0; tx < dimensions; tx++){
                for(int ty = 0; ty < dimensions; ty++){
                    int i = ((tx+1)*gridSize) + 50;
                    int j = ((ty+1)*gridSize) + 50;
                    if(map[tx][ty].isObstacle()){
                        gridComp.addGrid(i, j, gridSize, gridSize, new Color(229, 24, 24), "O");
                    }
                    if(map[tx][ty].getNumberOfRocks() != 0){
                        gridComp.addGrid(i, j, gridSize, gridSize, new Color(52, 181, 95), "" + map[tx][ty].getNumberOfRocks());
                    }
                    
                }
            }
        }
			
	public void printMap(){

		for(int tx = 0; tx < dimensions; tx++){
			for(int ty = 0; ty < dimensions; ty++){
				int x = ((tx+1)*gridSize) + 50;
				int y = ((ty+1)*gridSize) + 50;
                                int ss = (Math.abs(xSpaceshipGridPos - tx)+Math.abs(ySpaceshipGridPos - ty));
				
				gridComp.addGrid(x, y, gridSize, gridSize, Color.BLACK, "");
				
                                map[ty][ty].setSignalStrength(ss);
			}
                        
		}
	}
	
        public void initObstacles(){
            Random rand = new Random();
            int noOfObstacles = (rand.nextInt(4) + 3);
       
            int count = 0;
            while(count < noOfObstacles){
                int i = rand.nextInt(dimensions);
                int j = rand.nextInt(dimensions);
                
                if(!map[i][j].isObstacle() && !map[i][j].isSpaceship()){
                    map[i][j].setObstacle(true);
                    count++;
                }
            }
            
        }
        
        public void initRocks(){
            Random rand = new Random();
            int count = 0;
            int noOfRocks = (rand.nextInt(6) + 10);
            
            while(count < noOfRocks){
                int i = rand.nextInt(dimensions);
                int j = rand.nextInt(dimensions);
                
                if(!map[i][j].isObstacle() && !map[i][j].isSpaceship() && map[i][j].getNumberOfRocks() == 0){
                    int cluster = rand.nextInt(10)+1;
                    if(cluster < 4){
                        map[i][j].setNumberOfRocks(1);
                    }
                    else {
                        map[i][j].setNumberOfRocks(cluster);
                    }
                    count++;
                }
            }
        }
        
	public void setSpaceshipLocation(){
		xSpaceshipGridPos = (int) (Math.random() * dimensions);
		ySpaceshipGridPos = (int) (Math.random() * dimensions);
		map[xSpaceshipGridPos][ySpaceshipGridPos].setSpaceship(true);
		xSpaceship = ((xSpaceshipGridPos+1)*gridSize) + 50;
		ySpaceship = ((ySpaceshipGridPos+1)*gridSize) + 50;
	}
	
	public void printSingleField(GridField gf, int x, int y){
		
		/*
		if(gf.isLeft()){
			lineComp.addLine(x, y, (x-gridSize), (y), Color.BLACK);
			lineComp.addLine((x-gridSize), y, (x-gridSize), (y+gridSize), Color.BLACK);
			lineComp.addLine((x-gridSize), (y+gridSize), (x), (y+gridSize), Color.BLACK);
		}
		if(gf.isTop()){
			lineComp.addLine(x, y, (x), (y-gridSize), Color.BLACK);
			lineComp.addLine(x, (y-gridSize), (x+gridSize), (y-gridSize), Color.BLACK);
			lineComp.addLine((x+gridSize), (y-gridSize), (x+gridSize), (y), Color.BLACK);
		}
		if(gf.isRight()){
			lineComp.addLine((x+gridSize), y, (x+gridSize), (y), Color.BLACK);
			lineComp.addLine((x+gridSize), y, (x+gridSize), (y+gridSize), Color.BLACK);
			lineComp.addLine((x+gridSize), (y+gridSize), (x+gridSize), (y+gridSize), Color.BLACK);
		}
		if(gf.isBottom()){
			lineComp.addLine(x, (y+gridSize), (x), (y+gridSize), Color.BLACK);
			lineComp.addLine(x, (y+gridSize), (x+gridSize), (y+gridSize), Color.BLACK);
			lineComp.addLine((x+gridSize), (y+gridSize), (x+gridSize), (y+gridSize), Color.BLACK);
		}
		*/
	}
	
	public void fillMap(){
		for(int i = 0; i < dimensions; i++){
			for(int j = 0; j < dimensions; j++){
				map[i][j] = new GridField();
				
				if(i == 0){
					map[i][j].setTop(false);
				} 
				if(i == dimensions){
					map[i][j].setBottom(false);
				}
				if(j == 0){
					map[i][j].setLeft(false);
				}
				if(j == dimensions){
					map[i][j].setRight(false);
				}	
			}
		}
	}
}
