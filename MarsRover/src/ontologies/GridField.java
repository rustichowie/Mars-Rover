package ontologies;

import java.io.Serializable;
public class GridField implements Serializable {

        //Checks if there are fields next to it in all four directions
	private boolean left;
	private boolean right;
	private boolean top;
	private boolean bottom;
        
        
	private boolean spaceship;      //is this the spaceship
	private int numberOfRocks;      //number of rocks on the field
	private int grain;              //grain on the field
        private boolean cluster_found;  //if this is a cluster, is it discovered
	private int signalStrength;     //Signalstrength, it is 0 at spaceship and gets bigger the further away(so that rover can go home)
        private boolean obstacle;       //is this an obstacle
        private boolean came_from;      //Did the rover just visit this field?
        private int x, y;               //posistion on map
        
        /**
         * empty constructor
         */
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
                this.cluster_found = false;
	}
	
        /**
         * full contructor
         * @param left
         * @param right
         * @param top
         * @param bottom
         * @param spaceship
         * @param numberOfRocks
         * @param grain
         * @param signalStrength
         * @param obstacle
         * @param x
         * @param y
         * @param cluster_found 
         */
	public GridField(boolean left, boolean right, boolean top, boolean bottom, boolean spaceship,
                            int numberOfRocks, int grain, int signalStrength, boolean obstacle, int x, int y, boolean cluster_found){
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
                this.cluster_found = cluster_found;
	}
	
        /**
         * Getter and Setter methods.
         */
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

    public boolean isCluster_found() {
        return cluster_found;
    }

    public void setCluster_found(boolean cluster_found) {
        this.cluster_found = cluster_found;
    }
	
}
