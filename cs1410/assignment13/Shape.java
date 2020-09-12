package assignment13;

import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents a shape object which stores its bounding and coloring information
 * and can be drawn onto the screen.
 * 
 * @author Qianlang Chen
 * @version 1.0.0
 */
abstract public class Shape {
	// Instance variables (none, but you'll add some later)

	protected int x, y; // The upper-left corner of a box containing our SHAPE.
	protected Color color; // A reference to a color object, our color.

	/**
	 * Creates a new Shape instance.
	 * 
	 * @param x
	 * 		The x coordinate of this shape.
	 * @param y
	 * 		The y coordinate of this shape.
	 * @param color
	 * 		The color of this shape.
	 */
	public Shape(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	/**
	 * Changes the position of this shape by the specified amount. Note that this
	 * does not set the position absolutely, the deltas specify how far to move the
	 * shape from its current position.
	 *
	 * @param deltaX how far to move the shape horizontally
	 *
	 * @param deltaY how far to move the shape vertically
	 */
	public void move(int deltaX, int deltaY) {
		x = x + deltaX;
		y = y + deltaY;
	}

	abstract public boolean isInside(int targetX, int targetY);

	abstract public void draw(Graphics g);
}