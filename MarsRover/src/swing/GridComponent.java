package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


public class GridComponent extends JComponent {
	
	private List<Grid> gridList;
	
	public GridComponent(){
		gridList = new ArrayList<>();
	}
	
	public void addGrid(int x, int y, int w, int h, Color c, String t){
		gridList.add(new Grid(x, y, w, h, c, t));
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
