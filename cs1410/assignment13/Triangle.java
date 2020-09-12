package assignment13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Represents a triangle shape drawn to the screen at a particular position with
 * some size and color.
 * 
 * @author Qianlang Chen
 * @version 1.0.0
 */
public class Triangle extends Shape {

	////// CONSTRUCTOR /////////////////////////////////////////////////////////////

	/**
	 * Creates a new Triangle instance.
	 * 
	 * @param p0    The coordinates of the top point.
	 * @param p1    The coordinates of the bottom-left point.
	 * @param p2    The coordinates of the bottom-right point.
	 * @param color The color of this triangle shape.
	 */
	public Triangle(Point p0, Point p1, Point p2, Color color) {
		// Use the super constructor to initialize its location.

		super(p0.x, p0.y, color);

		// Store the values.
		
		this.polygon = new Polygon();

		this.polygon.addPoint(p0.x, p0.y); // store the relative locations only
		this.polygon.addPoint(p1.x, p1.y);
		this.polygon.addPoint(p1.x, p2.y);
	}

	////// FIELDS ///////////////////////////////////////////////////////////////////

	/** The polygon object which stores the coordinates of the points. **/
	private Polygon polygon;

	////// METHODS //////////////////////////////////////////////////////////////////

	/**
	 * [Override]
	 * 
	 * Changes the position of this shape by the specified amount. Note that this
	 * does not set the position absolutely, the deltas specify how far to move the
	 * shape from its current position.
	 *
	 * @param deltaX how far to move the shape horizontally
	 *
	 * @param deltaY how far to move the shape vertically
	 */
	public void move(int deltaX, int deltaY) {
		polygon.translate(deltaX, deltaY);
	}

	/**
	 * Draws the triangle at it's current position and color to the specified
	 * graphics object.
	 *
	 * @param g the graphics object (where to draw to)
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillPolygon(polygon);
	}

	/**
	 * Returns true if the coordinates are within the triangle.
	 *
	 * @param targetX an x coordinate
	 *
	 * @param targetY a y coordinate
	 *
	 * @return true if the coordinates are within the shape
	 */
	public boolean isInside(int targetX, int targetY) {
		return polygon.contains(targetX, targetY);
	}

}
