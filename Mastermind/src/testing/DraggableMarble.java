package testing;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;

/**
 * A class extending MarbleIcon that represents a draggable marble. It extends MouseListener
 * as well as MouseMotionListener
 * @author Ethan King, James Wait
 *
 */
@SuppressWarnings("serial")
public class DraggableMarble extends MarbleIcon implements MouseListener, MouseMotionListener {
	private int screenX = 0;
	private int screenY = 0;
	private int myX = 0;
	private int myY = 0;
	Point restingLocation;
	Rectangle hitbox;
	
	/**
	 * Constructs the DraggableMarble with a Point restingLocation, Rectangle hitbox, 
	 * and adding a mouseListener and a MouseMotionListener in the form of this class
	 * @param color
	 * @param icon
	 */
	public DraggableMarble(Color color, ImageIcon icon) {
		super(color, icon);
		restingLocation = this.getLocation();
		hitbox = new Rectangle(0,0, this.getWidth(), this.getHeight());
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * When the mouse is pressed on this object, it matches the restingLocation, myX, and myY to its
	 * current position.
	 */
	public void mousePressed(MouseEvent e) {
		restingLocation = getLocation();
        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();
        myX = getX();
        myY = getY();	
	}

	/**
	 * Returns the object back to it's original location when the mouse is released
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Rectangle[] hitboxes = MastermindApplication.getHitboxes();
		for(int i = 0; i < hitboxes.length; i++)
		{
			if (hitbox.intersects(hitboxes[i]))
			{
				createMarbleIconAtLocation(new Point(hitboxes[i].x - 20, hitboxes[i].y-20));
				MastermindApplication.changeColor(i, this.color);
				break;
			}
		}
		setLocation(restingLocation);
		hitbox.setLocation(restingLocation);  
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Updates the location of the object and its rectangle hitbox every frame
	 */
	public void mouseDragged(MouseEvent e) {
		int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;

        setLocation(myX + deltaX, myY + deltaY);
        hitbox.setLocation(getLocation());	
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Checks to see if this Objects hitbox is intersecting with the hitboxes in
	 * the divets of the GUI
	 * @param hitboxes
	 * @return index in the array of hitboxes, -1 if they are not intersecting
	 */
	private int checkHitboxCollision(Rectangle[] hitboxes)
	{
		 for (int i = 0; i < hitboxes.length; i++)
   	  	 {
   		  	if (hitbox.intersects(hitboxes[i]))
   		  	{
   		  		return i;
   		  	}
   	  	 }
		 
		 return -1;
	}
	
	
	/**
	 * Creates a new MarbleIcon at the specified location
	 * that is visually identical to this Object
	 */
	private void createMarbleIconAtLocation(Point position)
	{
		MarbleIcon output = new MarbleIcon(color, getIcon());
		output.setLocation(position);
		MastermindApplication.addComponent(output);
	}
	

}
