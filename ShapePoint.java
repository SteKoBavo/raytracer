public class ShapePoint extends Point {
	Shape shapeItBelongsTo;
	
	// Constructor. Construct a point at (x,y,z).
	public ShapePoint(double newX, double newY, double newZ) {
		super(newX,newY,newZ);
	}
	
	public void setShape(Shape newShape) {
		this.shapeItBelongsTo = newShape;
	}
	
	public Shape getShape() {
		return this.shapeItBelongsTo;
	}
}