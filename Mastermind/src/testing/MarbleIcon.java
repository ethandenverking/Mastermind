package testing;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A class that represents a marble with a color and an image
 * @author Ethan King, James Wait
 *
 */
@SuppressWarnings("serial")
public class MarbleIcon extends JLabel{
	
	Color color;
	ImageIcon icon;
	
	MarbleIcon(Color color, ImageIcon icon)
	{
		this.color = color;
		this.icon = icon;
		this.setIcon(icon);
		setBounds(0, 0, 50, 50);
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @return the ImageIcon
	 */
	public ImageIcon getIcon()
	{
		return icon;
	}
	
}
