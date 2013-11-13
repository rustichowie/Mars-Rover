package ontologies;

public class GridField {

	private boolean left;
	private boolean right;
	private boolean top;
	private boolean bottom;
	private boolean spaceship;
	private int numberOfRocks;
	private boolean grain;
	private int signalStrength;
        
	public GridField(){
		this.left = true;
		this.right = true;
		this.top = true;
		this.bottom = true;
		this.spaceship = false;
		this.numberOfRocks = 0;
		this.grain = false;
                this.signalStrength = 0;
	}
	
	public GridField(boolean left, boolean right, boolean top, boolean bottom, boolean spaceship, 
                            boolean rocks, int numberOfRocks, boolean grain, int signalStrength){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.spaceship = spaceship;
		this.numberOfRocks = numberOfRocks;
		this.grain = grain;
                this.signalStrength = signalStrength;
	}
	
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isTop() {
		return top;
	}
	public void setTop(boolean top) {
		this.top = top;
	}
	public boolean isBottom() {
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
	public boolean isGrain() {
		return grain;
	}
	public void setGrain(boolean grain) {
		this.grain = grain;
	}

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }
	
}
