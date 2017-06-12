public class Viewport {
	private Point origin;
	private Point xAxis;
	private Point yAxis;
	
	public Viewport(Point newOrigin, Point newXAxis, Point newYAxis) {
		this.origin = newOrigin;
		this.xAxis = newXAxis;
		this.yAxis = newYAxis;
	}
	
	public Point getPoint(Coordinate c) {
		double x0 = this.origin.getX();
		double y0 = this.origin.getY();
		double z0 = this.origin.getZ();
		double x1 = this.xAxis.getX();
		double y1 = this.xAxis.getY();
		double z1 = this.xAxis.getZ();
		double x2 = this.yAxis.getX();
		double y2 = this.yAxis.getY();
		double z2 = this.yAxis.getZ();
		
		double x = x0 + (x1-x0)*c.getXAxis() + (x2-x0)*c.getYAxis();
		double y = y0 + (y1-y0)*c.getXAxis() + (y2-y0)*c.getYAxis();
		double z = z0 + (z1-z0)*c.getXAxis() + (z2-z0)*c.getYAxis();
		
		return new Point(x,y,z);
	}
}