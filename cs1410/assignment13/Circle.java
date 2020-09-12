package assignment13;

import java.awt.*;

/**
 * Circle objects represent a circle shape drawn to the screen at a particular
 * position with some size and color.
 *
 * @author Peter Jensen
 * @version Fall 2018
 */
public class Circle extends Shape {
	// Instance variables.

	private int radius; // The radius in our circle, measured in pixels.

	/**
	 * Constructor - initializes the position, diameter, and color of this circle
	 * object. The position is a point that describes a box that contains this
	 * circle. The point is the upper-left corner of the box that would contain this
	 * circle.
	 *
	 * @param x        the x coordinate of this object's position
	 *
	 * @param y        the x coordinate of this object's position
	 *
	 * @param diameter the diameter of this circle
	 *
	 * @param color    the color of this circle
	 */
	public Circle(int x, int y, int diameter, Color color) {
		// Call the superclass constructor. This must be the first statement
		// in this constructor.

		super(x, y, color);

		// Save the size.

		this.radius = diameter / 2;
	}

	/**
	 * Draws the circle at it's current position and color to the specified graphics
	 * object.
	 *
	 * @param g the graphics object (where to draw to)
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, radius * 2, radius * 2);
	}

	/**
	 * Returns true if the coordinates are within the circle.
	 *
	 * @param targetX an x coordinate
	 *
	 * @param targetY a y coordinate
	 *
	 * @return true if the coordinates are within the shape
	 */
	public boolean isInside(int targetX, int targetY) {
		int cx = x + radius; // Calculate the center point
		int cy = y + radius;

		int deltaX = cx - targetX; // Calculate the deltas to the click
		int deltaY = cy - targetY;

		// Make sure the distance from the click to the center is less
		// than the radius. (Normally you'd use the Pythagorean
		// formula to calculate the distance between two points. I
		// rearranged the math to avoid a sqaure root.)

		return (deltaX * deltaX + deltaY * deltaY) <= radius * radius;
	}
}
