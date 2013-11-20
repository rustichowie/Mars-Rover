package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import ontologies.GridField;


public class GridComponent extends JComponent {
	
	private List<Grid> gridList;    // List of Grids. Holds the whole map
	
	public GridComponent(){
		gridList = new ArrayList<>();
	}
	
        /**
         * When a grid is added to the list. Repaint.
         * @param x coordinate
         * @param y coordinate
         * @param w width
         * @param h height
         * @param c color
         * @param t text
         */
	public void addGrid(int x, int y, int w, int h, Color c, String t){
		gridList.add(new Grid(x, y, w, h, c, t));
		repaint();
	}
        
        /**
         * Update elements where the fields x and y coordinates match each other.
         * Changes the color and updates number of rocks if changed.
         * Then repaint component.
         * @param field
         * @param gridSize
         * @param dim 
         */
        public void updateGridField(GridField field, int gridSize, int dim){
            int tx = ((field.getX()+1)*gridSize) + 100;
            int ty = ((field.getY()+1)*gridSize) + 20;
            for( Grid g : gridList  ){
                if(g.getX() == tx && g.getY() == ty){
                    if(!field.isSpaceship()){
                        
                        if(field.getNumberOfRocks() > 0){
                            g.setText(field.getNumberOfRocks()+"");
                            g.setColor(new Color(52, 181, 95));
                        } else {
                            g.setColor(Color.GRAY);
                            g.setText("");
                        }
                        
                        
                    }
                }
            }
            repaint();
            
        }
	
        /**
         * Draw the grid for every element oin the gridList.
         * Based on the information in the list.
         * @param g 
         */
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for( Grid grid : gridList ){
			g.setColor(grid.getColor());
                        g.drawRect(grid.getX(), grid.getY(), grid.getWidth(), grid.getHeigth());
			g.drawString(grid.getText(), grid.getX()+5, grid.getY()+(grid.getHeigth()-5));
		}
	}
}
