import java.awt.geom.Rectangle2D;

/* Class to denote a brick */
public class Brick
{
    public double x, y; // 2d coordinates denoting postion of the brick
    public static final double width = 100, height = 30; //width and heights
    public boolean isRendered; // tells whether to render the brick on the screen or not
    public Rectangle2D br;  // The rectangle object that will be drawn on the JComponent
    public Brick(double x, double y) {
	this.x = x;
	this.y = y;
	isRendered = true;
	br = new Rectangle2D.Double(x, y, width, height);
    }
}
