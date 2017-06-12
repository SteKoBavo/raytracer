import java.util.concurrent.*;

public class Coordinate implements Callable<Double> {	
	private double xAxis;			// Value in [0,1] that determines where we are at a viewport (relatively).
	private double yAxis;
	private Scene myScene;
	
	public double getXAxis() {
		return this.xAxis;
	}
	
	public double getYAxis() {
		return this.yAxis;
	}
	
	public Coordinate(double x, double y, Scene myScene) {
		if ((x>1.0) || (x<0.0) || (y>1.0) || (y<0.0)) {
			System.out.println("Error! Incorrect coordinate!");
		}
		this.xAxis = x;
		this.yAxis = y;
		this.myScene = myScene;
	}
	
	@Override
    public Double call() throws Exception {
		return this.myScene.trace(this);
    }
}