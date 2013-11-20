package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import ontologies.GridField;


public class GridComponent extends JComponent {
	
	private List<Grid> gridList;
	
	public GridComponent(){
		gridList = new ArrayList<>();
	}
	
	public void addGrid(int x, int y, int w, int h, Color c, String t){
		gridList.add(new Grid(x, y, w, h, c, t));
		repaint();
	}
        
        public void updateGridField(GridField field, int gridSize, int dim){
            int tx = ((field.getX()+1)*gridSize) + 100;
            int ty = ((field.getY()+1)*gridSize) + 20;
            for( Grid g : gridList  ){
                if(g.getX() == tx && g.getY() == ty){
                    if(!field.isSpaceship()){
                        /*
                        if(field.getNumberOfRocks() == 0){
                            g.setColor(Color.GRAY);
                        }
                        g.setText(field.getNumberOfRocks()+"");
                        */
                        
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
