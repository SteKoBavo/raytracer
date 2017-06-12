public class Vector {
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
	
	// Constructor. Get a vector in the direction (x,y,z) of length 1.
	public Vector(double x, double y, double z) {
		double length = Math.sqrt(x*x + y*y + z*z);
		this.x = x/length;
		this.y = y/length;
		this.z = z/length;
	}
	
	public double dotProduct(Vector other) {
		return ((this.getX()*other.getX()) + (this.getY()*other.getY()) + (this.getZ()*other.getZ()));
	}
}