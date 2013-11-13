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
import swing.LineComponent;
import jade.core.Agent;

public class SpaceshipAgent extends Agent {

	
	private GridField[][] map;
	private int dimensions;
	private JFrame mapFrame;
	private LineComponent lineComp;
	private int gridSize;
	private int xSpaceship;
	private int ySpaceship;
	
	protected void setup(){
		Object[] args = getArguments();
		
		dimensions = Integer.parseInt((String) args[0]);
		gridSize = 25;
		
		map = new GridField[dimensions][dimensions];
		
		mapFrame = new JFrame();
		mapFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mapFrame.setPreferredSize(new Dimension(800, 680));
		
		lineComp = new LineComponent();
		lineComp.setPreferredSize(new Dimension(200, 200));
		
		JButton button = new JButton("New Line");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x1 = (int) (Math.random()*200);
				int y1 = (int) (Math.random()*200);
				int x2 = (int) (Math.random()*200);
				int y2 = (int) (Math.random()*200);
				Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
				lineComp.addLine(x1, y1, x2, y2, randomColor);
				
			}
		});;
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		
		mapFrame.getContentPane().add(lineComp, BorderLayout.CENTER);
		mapFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		mapFrame.pack();
		mapFrame.setVisible(true);
		
		fillMap();
		
		setSpaceshipLocation();
		
		printMap();
		printSpaceshipLocation();
	}
	
	public void printSpaceshipLocation(){
		lineComp.addLine(xSpaceship, ySpaceship, (xSpaceship+gridSize), (ySpaceship), Color.RED);
		lineComp.addLine((xSpaceship+gridSize), ySpaceship, (xSpaceship+gridSize), (ySpaceship+gridSize), Color.RED);
		lineComp.addLine((xSpaceship+gridSize), (ySpaceship+gridSize), (xSpaceship), (ySpaceship+gridSize), Color.RED);
		lineComp.addLine(xSpaceship, ySpaceship, (xSpaceship), (ySpaceship+gridSize), Color.RED);
	}
			
	public void printMap(){
		for(int tx = 0; tx < dimensions; tx++){
			for(int ty = 0; ty < dimensions; ty++){
				int x = ((tx+1)*gridSize) + 50;
				int y = ((ty+1)*gridSize) + 50;
				
				lineComp.addLine(x, y, (x+gridSize), (y), Color.BLACK);
				lineComp.addLine((x+gridSize), y, (x+gridSize), (y+gridSize), Color.BLACK);
				lineComp.addLine((x+gridSize), (y+gridSize), (x), (y+gridSize), Color.BLACK);
				lineComp.addLine(x, y, (x), (y+gridSize), Color.BLACK);
				
	//			printSingleField(map[tx][ty], x, y);
				
	/*			lineComp.addLine(x, y, (x+10), (y), Color.BLACK);
				lineComp.addLine(x, y, (x), (y+10), Color.BLACK);
				lineComp.addLine((x+10), (y), (x+10), (y+1), Color.BLACK);
				lineComp.addLine((x), (y+10), (x+10), (y+10), Color.BLACK);
		*/	}
		}
	}
	
	public void setSpaceshipLocation(){
		int x = (int) (Math.random() * dimensions);
		int y = (int) (Math.random() * dimensions);
		map[x][y].setSpaceship(true);
		xSpaceship = ((x+1)*gridSize) + 50;
		ySpaceship = ((y+1)*gridSize) + 50;
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
