package ontologies;

import java.io.Serializable;
public class GridField implements Serializable {

	private boolean left;
	private boolean right;
	private boolean top;
	private boolean bottom;
	private boolean spaceship;
	private int numberOfRocks;
	private int grain;
	private int signalStrength;
        private boolean obstacle;
        private boolean came_from;
        private int x, y;
        
        
	public GridField(){
		this.left = true;
		this.right = true;
		this.top = true;
		this.bottom = true;
		this.spaceship = false;
		this.numberOfRocks = 0;
		this.grain = 0;
                this.signalStrength = 0;
                this.obstacle = false;
                this.came_from = false;
	}
	
	public GridField(boolean left, boolean right, boolean top, boolean bottom, boolean spaceship, int numberOfRocks, int grain, int signalStrength, boolean obstacle, int x, int y){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.spaceship = spaceship;
		this.numberOfRocks = numberOfRocks;
		this.grain = grain;
                this.signalStrength = signalStrength;
                this.obstacle = obstacle;
                this.came_from = false;
                this.x = x;
                this.y = y;
	}
	
	public boolean hasLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean hasRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean hasTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public boolean hasBottom() {
		return bottom;
	}
	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}
	public boolean isSpaceship() {
		return spaceship;
	}
	public void setSpaceship(boolean spaceship) {
		this.spaceship = spaceship;
	}
	public int getNumberOfRocks() {
		return numberOfRocks;
	}
	public void setNumberOfRocks(int numberOfRocks) {
		this.numberOfRocks = numberOfRocks;
	}
	public int getGrain() {
		return grain;
	}
	public void setGrain(int grain) {
		this.grain = grain;
	}

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isCame_from() {
        return came_from;
    }

    public void setCame_from(boolean came_from) {
        this.came_from = came_from;
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
	
}
