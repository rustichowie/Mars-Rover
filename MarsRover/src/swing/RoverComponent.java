package swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class RoverComponent extends JComponent {
	
    private int roverX;         // X coordinate to the rover.
    private int roverY;         // Y coordiante to the rover.
    private String roverName;   // Name of the rover.
    private int gridSize;       // Size of the Grid. Used to calculate where to draw the rover in the grid.

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
    
    /**
     * Move the rover 1 north.
     * Used in test face of the implementation of the application.
     * Then repaint.
     */
    public void moveNorth(){
        roverY -= 1;
        repaint();
    }
    
    /**
     * Move the rover 1 east.
     * Used in test face of the implementation of the application.
     * Then repaint.
     */
    public void moveEast(){
        roverX += 1;
        repaint();
    }
    
    /**
     * Move the rover 1 south.
     * Used in test face of the implementation of the application.
     * Then repaint.
     */    public void moveSouth(){
        roverY += 1;
        repaint();
    }
    
    /**
     * Move the rover 1 west.
     * Used in test face of the implementation of the application.
     * Then repaint.
     */     
    public void moveWest(){
        roverX -= 1;
        repaint();
    }
    
    /**
     * Move the rover to the coordinates x and y.
     * Then repaint.
     * @param x
     * @param y 
     */
    public void moveRover(int x, int y){
        roverX = x;
        roverY = y;
        repaint();
    }

    /**
     * Calculates the position of the rover dependent of the grid.
     * Draw the rover on the screen as a oval circle.
     * Draw the name on the rover.
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor( new Color(37,70,115) );
        int tx = ((roverX+1)*gridSize) + 100;
        int ty = ((roverY+1)*gridSize) + 20;
        g.drawOval(tx + adjustMid(), ty+adjustMid(), gridSize/2, gridSize/2);
        g.drawString(roverName, tx + adjustMid(), ty + adjustMid());
            
    }
    
    /**
     * Adjusts the rover to the middle of a grid.
     * @return 
     */
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
