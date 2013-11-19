package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


public class RoverComponent extends JComponent {
	
    private int roverX;
    private int roverY;
    private String roverName;
    private int gridSize;

    public RoverComponent(int x, int y, String name, int gridSize) {
        this.roverX = x;
        this.roverY = y;
        this.roverName = name;
        this.gridSize = gridSize;
    }
    
    public RoverComponent(){
        this.roverX = 0;
        this.roverY = 0;
        this.roverName = "";
        this.gridSize = 0;
    }
    
    public void moveNorth(){
        roverY -= 1;
        repaint();
    }
    
    public void moveEast(){
        roverX += 1;
        repaint();
    }
    
    public void moveSouth(){
        roverY += 1;
        repaint();
    }
    
    public void moveWest(){
        roverX -= 1;
        repaint();
    }
    
    public void moveRover(int x, int y){
        roverX = x;
        roverY = y;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor( new Color(37,70,115) );
        int tx = ((roverX+1)*gridSize) + 100;
        int ty = ((roverY+1)*gridSize) + 20;
        g.drawOval(tx + adjustMid(), ty+adjustMid(), gridSize/2, gridSize/2);
        g.drawString(roverName, tx + adjustMid(), ty + adjustMid());
            
    }
    
    public int adjustMid(){
        return (gridSize/2)-6;
    }
    
    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
    
    public int getRoverX() {
        return roverX;
    }

    public void setRoverX(int x) {
        this.roverX = x;
    }
    
    public int getRoverY() {
        return roverY;
    }

    public void setRoverY(int y) {
        this.roverY = y;
    }
    
    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String name) {
        this.roverName = name;
    }
}
