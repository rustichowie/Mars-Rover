package swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class BoardInstructionsComponent extends JComponent {
        
    private String spaceship; 
    private String obstacles; 
    private String rocks;

    public BoardInstructionsComponent(String spaceship, String obstacles, String rocks) {
        this.spaceship = spaceship;
        this.obstacles = obstacles;
        this.rocks = rocks;
    }
    
    public BoardInstructionsComponent(){
        this.spaceship = "";
        this.obstacles = "";
        this.rocks = "";
    }

    /**
     * Update variables and repaint.
     * @param spaceship
     * @param obstacles
     * @param rocks 
     */
    public void addText(String spaceship, String obstacles, String rocks){
        this.spaceship = spaceship;
        this.obstacles = obstacles;
        this.rocks = rocks;
        repaint();
    }

    /**
     * Callback function for repaint.
     * Draws a rectangle with the variables.
     * Holds information of what the GridMap colors represents.
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(10, 10, 80, 80 );
        g.setColor(Color.BLUE);
        g.drawString(spaceship, 20, 30);
        g.setColor( new Color(229, 24, 24));
        g.drawString(obstacles, 20, 50);
        g.setColor( new Color(52, 181, 95));
        g.drawString(rocks, 20, 70);
    }
        
    public String getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(String spaceship) {
        this.spaceship = spaceship;
    }

    public String getObsticles() {
        return obstacles;
    }

    public void setObsticles(String obstacles) {
        this.obstacles = obstacles;
    }

    public String getRocks() {
        return rocks;
    }

    public void setRocks(String rocks) {
        this.rocks = rocks;
    }
}
