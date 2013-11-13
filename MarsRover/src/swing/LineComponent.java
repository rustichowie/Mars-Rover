package swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


public class LineComponent extends JComponent {
	
	private List<Line> lineList;
	
	public LineComponent(){
		lineList = new ArrayList<>();
	}
	
	public void addLine(int x1, int y1, int x2, int y2, Color c){
		lineList.add(new Line(x1, y1, x2, y2, c));
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		for( Line line : lineList ){
			g.setColor(line.getColor());
			g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
		}
	}
}
