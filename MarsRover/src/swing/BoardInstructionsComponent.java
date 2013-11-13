package swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class BoardInstructionsComponent extends JComponent {
        
        
    private String spaceship;
    private String obsticles;
    private String rocks;

    public BoardInstructionsComponent(String spaceship, String obsticles, String rocks) {
        this.spaceship = spaceship;
        this.obsticles = obsticles;
        this.rocks = rocks;
    }
    
    public BoardInstructionsComponent(){
        this.spaceship = "";
        this.obsticles = "";
        this.rocks = "";
    }

    public void addText(String spaceship, String obsticles, String rocks){
        this.spaceship = spaceship;
        this.obsticles = obsticles;
        this.rocks = rocks;
        repaint();
    }
	
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 100, 200 );
        g.drawString(spaceship, 820, 60);
        g.setColor( new Color(229, 24, 24));
        g.drawString(obsticles, 850, 60);
        g.setColor( new Color(52, 181, 95));
        g.drawString(rocks, 880, 60);
    }
        
    public String getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(String spaceship) {
        this.spaceship = spaceship;
    }

    public String getObsticles() {
        return obsticles;
    }

    public void setObsticles(String obsticles) {
        this.obsticles = obsticles;
    }

    public String getRocks() {
        return rocks;
    }

    public void setRocks(String rocks) {
        this.rocks = rocks;
    }
}
