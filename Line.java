public class Line {
	private Point a;
	private Point b;
	
	public Line(Point newA, Point newB) {
		this.a = newA;
		this.b = newB;
	}
	
	public Point getPointA() {
		return this.a;
	}
	
	public Point getPointB() {
		return this.b;
	}
	
	public Vector direction() {
		double x = this.b.getX() - this.a.getX();
		double y = this.b.getY() - this.a.getY();
		double z = this.b.getZ() - this.a.getZ();
		return new Vector(x,y,z);
	}
	
	public Point intersect(Line line) {
		//Return intersection point.
		return null;
	}
	
	public double intersectAngle(Line line) {
		//Return intersection angle.
		return 0.0;
	}
	
	public Line normal(Point point) {
		//Loodrechte projectie van punt op lijn. Returns a line.
		return null;
	}
}