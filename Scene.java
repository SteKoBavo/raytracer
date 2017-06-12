import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.*;
import javax.imageio.*;
import java.util.concurrent.*;

public class Scene {
	private Point viewPoint = new Point(0.0,0.0,0.0);
	private Viewport viewport = new Viewport(new Point(-10.0,-10.0,10.0), new Point(10.0,-10.0,10.0),new Point(-10.0,10.0,10.0));
	private ArrayList<LightSource> lightSources = new ArrayList<LightSource>();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public static Point findClosestPoint(Point point, ArrayList<Point> pointList) {		
		double x0 = point.getX();
		double y0 = point.getY();
		double z0 = point.getZ();
		
		Point closestPoint = null;
		double closestDistance = -1.0;
		for (Point otherPoint : pointList) {
			double x1 = otherPoint.getX();
			double y1 = otherPoint.getY();
			double z1 = otherPoint.getZ();
			double distance = (x1-x0)*(x1-x0) + (y1-y0)*(y1-y0) + (z1-z0)*(z1-z0);
			if ((distance < closestDistance) || (closestDistance == -1.0)) {
				closestPoint = otherPoint;
				closestDistance = distance;
			}
		}
		return closestPoint;
	}
	
	public double trace(Coordinate coordinate) {
		//Create the line from the viewPoint through a coordinate in the viewport.
		Point viewportPoint = this.viewport.getPoint(coordinate);
		Line viewLine = new Line(this.viewPoint,viewportPoint);
		
		//Determine the intersections with the shapes (at the correct (positive) side of the line: viewpoint = origin, viewportpoint = positive.).
		ArrayList<Point> intersectionList = new ArrayList<Point>();
		for (Shape shape : this.shapes) {
			shape.intersect(viewLine, intersectionList);
		}
		if (intersectionList.isEmpty()) {
			return 0.0;
		}
		
		//Determine the closest intersection to the viewPoint.
		Point closestIntersectionPoint = Scene.findClosestPoint(this.viewPoint,intersectionList);
		Shape closestShape = ((ShapePoint) closestIntersectionPoint).getShape();
		
		//Determine the brightness of the point.
		double brightness = 0.0;
		for (LightSource lightsource : this.lightSources) {
			//Create a line to the light source.
			Line lineToLightSource = new Line(closestIntersectionPoint,lightsource.getLocation());
			
			//Determine the intersections with the shapes.
			ArrayList<Point> intersectionList2 = new ArrayList<Point>();
			for (Shape shape : this.shapes) {
				shape.intersect(lineToLightSource, intersectionList2);
			}
			
			//Determine the closest intersection. If it is the lightsource itself, the ray can make it to the viewpoint.
			intersectionList2.add(lightsource.getLocation());
			
			Point closestIntersectionPoint2 = Scene.findClosestPoint(closestIntersectionPoint,intersectionList2);
			if (closestIntersectionPoint2 == lightsource.getLocation()) {
				
				//Lambertian shading
				Vector vector1 = closestShape.normal(closestIntersectionPoint);
				Vector vector2 = lineToLightSource.direction();
		
				//brightness += lightsource.getBrightness();
				brightness += (Math.max(0.0,vector1.dotProduct(vector2)) * lightsource.getBrightness());
			}
		}
		
		
		return (closestShape.getDiffuseCoefficient() * brightness);
	}
	
	public double[][] computePixels(int width, int height) {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		ArrayList<Future<Double>> pixelsF = new ArrayList<Future<Double>>();
		
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				double x = (i+0.5)/width;
				double y = (j+0.5)/height;
				pixelsF.add(executor.submit(new Coordinate(x,y,this)));
			}
		}
		
		double[][] pixels = new double[width][height];
		int k = 0;
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				double x = (i+0.5)/width;
				double y = (j+0.5)/height;
				try {
					pixels[i][j] = pixelsF.get(k).get();
				}
				catch (Exception e) {
					System.out.println("PROBLEEM PARALLEL");
				}
				k++;
			}
		}
		
		// Write the pixels to a file.
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
			//Determine the maximum pixel value.
		double maxPixel = 0.0;
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				if (pixels[i][j] > maxPixel) {
					maxPixel = pixels[i][j];
				}
			}
		}
		
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				float pixelvalue = (float) (pixels[i][j]/maxPixel);
				image.setRGB(i, j, new Color(pixelvalue,pixelvalue,pixelvalue).getRGB());
			}
		}
		
		try {
			File outputfile = new File("save.png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			System.out.println("PROBLEEM");
		}
		
		
        executor.shutdown();
		return pixels;
	}
	
	
	// Main
	public static void main (String[] args) {
		Scene scene = new Scene();
		scene.shapes.add(new Sphere(new Point(-4.0,-4.0,20.0), 2.0,1.0));
		scene.shapes.add(new Sphere(new Point(10.0,0.0,60.0), 20.0,0.2));
		scene.lightSources.add(new LightSource(new Point(-5000.0,0.0,20.0), 5.0));
		scene.lightSources.add(new LightSource(new Point(0.0,0.0,0.0), 5.0));
		scene.lightSources.add(new LightSource(new Point(-6.0,-5.0,10.0), 100.0));
		scene.lightSources.add(new LightSource(new Point(0.0,20.0,30.0), 100.0));
		
		int width = 1000;
		int height = 1000;
		scene.computePixels(width,height);
		
		
	}		
}
























