public class Point {
	private double x;
	private double y;
	private double z;
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getZ() {
		return this.z;
	}
	
	// Constructor. Construct a point at (x,y,z).
	public Point(double newX, double newY, double newZ) {
		this.x = newX;
		this.y = newY;
		this.z = newZ;
	}
}