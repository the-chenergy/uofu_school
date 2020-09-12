package assignment13;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class represents and application/JPanel that
 * draws a few shapes for the user and allows the
 * user to drag the shapes around with the mouse.
 *
 * @author Peter Jensen
 * @version Fall 2018
 */
public class ShapeMover extends JPanel implements MouseListener,
                                                  MouseMotionListener,
                                                  Runnable
{
    // Instance variables.
    
    Shape[] shapes;

    int lastX, lastY;
    Shape current;

    /**
     * Constructor
     *
     * Initialize this (our JPanel).  Create the shapes, and
     * register this object as a listener to mouse events
     * produced by this object.
     *
     * Our ShapeMover class 'extends' the JPanel class.  Our
     * objects will be ShapeMover objects, but they'll also
     * inherit (be) JPanel objects. 
     */
    public ShapeMover ()
    {
        // Initialize the JPanel portion of this object.  In Java
        //   whenever you extend a class to build another class,
        //   both classes' constructors need to execute to initialize
        //   both portions of the object.  It's simple to do -- just
    	//   make the first line of your constructor call/execute
        //   the constructor of your superclass (the class you extend).

        super();  // The JPanel constructor does not take any parameters.

    	// Make the shapes.
    	
    	shapes = new Shape[]
    	    {
    	       new Rect    (130,  40,  60,  60, Color.RED),
               new Circle  (230,  40,  20, Color.BLUE),
               new Rect    (330,  40,  80,  50, Color.GREEN.darker()),
               new Circle  (130, 140, 100, new Color (0.8f, 0.6f, 0.2f)),
    	       new Rect    ( 30, 140,  40,  30, Color.YELLOW),
    	       
    	       new Triangle(
    	    		   new Point(350, 350),
    	    		   new Point(310, 380),
    	    		   new Point(80, 80),
    	    		   Color.PINK
    	       ),
    	       
    	       new Triangle(
    	    		   new Point(360, 140),
    	    		   new Point(320, 220),
    	    		   new Point(250, 270),
    	    		   Color.GRAY
    	       )
    	       
    	    };
    
    	// Set the size of this panel.
    	
    	Dimension d = new Dimension (600, 600);
    	this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
    	
    	// Register this object as a listener to its own events.
    	
    	this.addMouseListener (this);
    	this.addMouseMotionListener (this);
    }

    /**
     * Draws the shapes at their current locations
     * and colors.  Note that this function is automatically
     * called by the GUI system whenever our object needs to
     * be drawn/painted.
     *
     * @param g
     *         the Graphics object to use for drawing
     */
    public void paint (Graphics g)
    {
    	// Clear the background to a nice light blue color.
    	
    	g.setColor(new Color (0.8f, 0.85f, 1.0f));
    	g.fillRect(0, 0, this.getWidth(), this.getHeight());
    	
    	// Draw all of the shapes.  Remember, this 'for' loop type
        //   loops through all of the elements that are stored in
        //   the array 'shapes'.  Each object reference is retrieved
        //   from the array (one at a time) and placed in 's'.  Then,
        //   the loop body is executed (once for each element in the
	//   array.)
    	
    	for (Shape s : shapes)
    	    s.draw(g);

        // An alternate way of doing the above code.  You may delete this.
        // It's just to show you what the above loop does.
        //
        // for (int pos = 0; pos < shapes.length; pos++)
        // { 
        //     Rect s = shapes[pos];
        //     s.draw();
        // }
    }

    /**
     * Advanced code -- you will adjust the type of the variable used
     * within the loop below.  Leave the rest of it alone (unless you'd
     * like to explore the code further.)
     *
     * This method is part of the MouseListener interface.
     * Because we registered this application object as a listener
     * to its own mouse events, this method will be automatically
     * called whenever the mouse button is pressed down.
     *
     * In this method, we determine if the mouse click occurred
     * in any of our shapes.  If so, we record that shape object
     * as the current shape.  This has the effect of selecting
     * a shape to drag around.
     *
     * @param e
     *         the mouse event
     */
    public void mousePressed  (MouseEvent e) 
    {
    	// Get the location of the mouse click within this window.
    	
    	int x = e.getX (); 
    	int y = e.getY ();
    
    	// Save it for later use.
    	
    	lastX = x;
    	lastY = y;
    
    	// Determine if the mouse click is within any shape.
    	//   If so, save the shape as the current shape.
    
    	for (Shape s : shapes)
    	    if (s.isInside (x, y))
    	        current = s;
    }
    
    /**
     * This method is part of the MouseListener interface.
     * Because we registered this applet object as a listener
     * to its own mouse events, this method will be automatically
     * called whenever the mouse button is let down.
     *
     * In this method, we mark the current shape as null.  This
     * has the effect of dropping whatever shape we are dragging
     * around.
     *
     * @param e
     *         the mouse event
     */
    public void mouseReleased (MouseEvent e) 
    {
        current = null;
    }
    
    /**
     * This method is part of the MouseMotionListener interface.
     * Because we registered this applet object as a listener
     * to its own mouse events, this method will be automatically
     * called whenever the mouse is moved with the button pressed down.
     *
     * In this method, we adjust the position of the shape the user
     * is dragging around.
     *
     * @param e
     *         the mouse event
     */
    public void mouseDragged  (MouseEvent e) 
    {
    	// Compute how far the mouse moved since the last event.
    	
    	int x = e.getX (); 
    	int y = e.getY ();
    	
    	int deltaX = x - lastX;
    	int deltaY = y - lastY;
    
    	// Save the current mouse position.
    	
    	lastX = x;
    	lastY = y;
    
    	// If the user is dragging around a shape, move it by
    	//   the same amount that the mouse moved.
    	
    	if (current != null)
    	{
    		current.move (deltaX, deltaY); 
    		repaint ();
    	}
    }

    // Unused event methods (required by the interfaces).

    public void mouseClicked  (MouseEvent e) { }
    public void mouseEntered  (MouseEvent e) { }
    public void mouseExited   (MouseEvent e) { }
    public void mouseMoved    (MouseEvent e) { }
    
    /* Above this point are the methods and variables that we use in the JPanel */
    /* Below this point are the methods and variables that launch the application */
    
    /* I violated separation of concerns.  The JPanel and Application classes
     * are merged, and 'main' is below.  This works for simple code (like this
     * lab), but is not a good idea for larger projects.
     *
     * In a larger project, I'd put the 'main' in it's own class.
     */
    
    /**
     * The application entry point.
     * 
     * @param args unused
     */
    public static void main (String[] args)
    {
        // Main runs in the 'main' execution thread, and the GUI
        //   needs to be built by the GUI execution thread.
        //   Ask the GUI thread to run our 'run' method (at some
        //   later time).
        
        SwingUtilities.invokeLater(new ShapeMover());

        // Done.  Let the main thread of execution finish.  All the
        //   remaining work will be done by the GUI thread.
    }
    
    /**
     * Builds the GUI for this application.  This method must
     * only be called/executed by the GUI thread. 
     */
    public void run ()
    {
        // Create a new application window.  Make sure that our application
        //   exits when this window closes.

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 'this' object is our ShapeMover object.  It is a Runnable object, 
        //    we can also treat it as a JPanel.  Here, we'll use it
        //    as a JPanel and add it to the window.  Our object will become
        //    a 'visible' drawing surface when the window becomes visible.
        
        f.setContentPane(this); 
        
        f.pack();
        f.setLocationRelativeTo(null);  // Centers window
        f.setVisible(true);
    }
}
