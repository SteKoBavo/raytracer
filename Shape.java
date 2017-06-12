import java.util.ArrayList;

abstract public class Shape {
	private double DiffuseCoefficient = 1.0;
	
	public abstract void intersect(Line line, ArrayList<Point> intersectionList);
	public abstract Vector normal(Point intersectionPoint);
	
	public void setDiffuseCoefficient(double newDC) {
		if (newDC > 0.0) {
			this.DiffuseCoefficient = newDC;
		}
		else {
			System.out.println("Invalid DiffuseCoefficient!");
		}
	}
	
	public double getDiffuseCoefficient() {
		return this.DiffuseCoefficient;
	}
}