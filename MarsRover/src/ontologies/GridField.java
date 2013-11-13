package ontologies;

public class GridField {

	private boolean left;
	private boolean right;
	private boolean top;
	private boolean bottom;
	private boolean spaceship;
	private boolean rocks;
	private int numberOfRocks;
	private boolean grain;
	
	public GridField(){
		this.left = true;
		this.right = true;
		this.top = true;
		this.bottom = true;
		this.spaceship = false;
		this.rocks = false;
		this.numberOfRocks = 0;
		this.grain = false;
	}
	
	public GridField(boolean left, boolean right, boolean top, boolean bottom, boolean spaceship, boolean rocks, int numberOfRocks, boolean grain){
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.spaceship = spaceship;
		this.rocks = rocks;
		this.numberOfRocks = numberOfRocks;
		this.grain = grain;
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
	public boolean isRocks() {
		return rocks;
	}
	public void setRocks(boolean rocks) {
		this.rocks = rocks;
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
	
}
