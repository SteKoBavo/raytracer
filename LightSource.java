public class LightSource {
	private Point location;
	private double brightness;
	
	public LightSource(Point newLocation, double newBrightness) {
		this.location = newLocation;
		this.setBrightness(newBrightness);
	}
	
	public Point getLocation() {
		return this.location;
	}
	
	public void setBrightness(double newBrightness) {
		if ((newBrightness <= 255.0) && (newBrightness >= 0.0)) {
			this.brightness = newBrightness;
		}
		else {
			System.out.println("Incorrect brightness!");
		}
	}
	
	public double getBrightness() {
		return this.brightness;
	}
}