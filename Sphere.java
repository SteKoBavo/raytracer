import java.util.ArrayList;

public class Sphere extends Shape {
	private Point centre;
	private double radius;
	
	public Sphere(Point newCentre, double newRadius) {
		this.centre = newCentre;
		this.radius = newRadius;
	}
	
	public Sphere(Point newCentre, double newRadius, double newDiffuseCoefficient) {
		this.centre = newCentre;
		this.radius = newRadius;
		this.setDiffuseCoefficient(newDiffuseCoefficient);
	}
	
	public Vector normal(Point point) {
		//Does not check if the point is really an intersection point.
		double x0 = this.centre.getX();
		double y0 = this.centre.getY();
		double z0 =	this.centre.getZ();
		double x1 = point.getX();
		double y1 = point.getY();
		double z1 = point.getZ();
		return new Vector(x1-x0,y1-y0,z1-z0);
	}
	
	// Only adds intersections that are at the positive side of the line (where point A of the line is the origin and point B determines the positive side of the line.).
	public void intersect(Line line, ArrayList<Point> intersectionList) {
		Point a = line.getPointA();
		Point b = line.getPointB();
		
		//Unpack the Points.
		double x0 = a.getX();
		double y0 = a.getY();
		double z0 = a.getZ();
		double x1 = b.getX();
		double y1 = b.getY();
		double z1 = b.getZ();
		double xc = this.centre.getX();
		double yc = this.centre.getY();
		double zc = this.centre.getZ();
		
		//Calculate intersection.
		double aa = (x1-x0)*(x1-x0) + (y1-y0)*(y1-y0) + (z1-z0)*(z1-z0);
		double bb = 2.0 * ((x1-x0)*(x0-xc)+(y1-y0)*(y0-yc)+(z1-z0)*(z0-zc));
		double cc = ((x0-xc)*(x0-xc)+(y0-yc)*(y0-yc)+(z0-zc)*(z0-zc))-(this.radius*this.radius);
		double discriminant = bb*bb-4.0*aa*cc;
		
		if (discriminant > 0.0) {
			double d1 = (-bb - Math.sqrt(discriminant))/(2.0*aa);
			double d2 = (-bb + Math.sqrt(discriminant))/(2.0*aa);
			if (d1>=0.00001) {
				ShapePoint point1 = new ShapePoint(x0+d1*(x1-x0),y0+d1*(y1-y0),z0+d1*(z1-z0));
				point1.setShape(this);
				intersectionList.add(point1);
			}
			if (d2>=0.00001) {
				ShapePoint point2 = new ShapePoint(x0+d2*(x1-x0),y0+d2*(y1-y0),z0+d2*(z1-z0));
				point2.setShape(this);
				intersectionList.add(point2);
			}
		}
		else if (discriminant == 0.0) {
			double d = -bb/(2.0*aa);
			if (d>=0.00001) {
				ShapePoint point0 = new ShapePoint(x0+d*(x1-x0),y0+d*(y1-y0),z0+d*(z1-z0));
				point0.setShape(this);
				intersectionList.add(point0);
			}
		}
	}
}