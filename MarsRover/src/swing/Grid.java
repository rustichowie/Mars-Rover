package swing;

import java.awt.Color;


public class Grid {
    
        /**
         * This class holds information about what the GridComponent is going to draw.
         */
	private int x;          // Where to draw the grid on the screen(x)
	private int y;          // Where to draw the grid on the screen(y)
	private int width;      // Width of the object to be drawn
	private int heigth;     // Height of the object to be drawn 
	private Color color;    // What color to use
        private String text;    // Text to drawn inside of grid

    public Grid(int x, int y, int width, int heigth, Color color, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.color = color;
        this.text = text;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }   
}
