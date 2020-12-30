package testing;

import java.awt.Color;
import java.util.Random;

/**
 * A class that represents a round in the game Mastermind. It holds an array of marbles
 * that represents the player's guess a well as an array of pegs for the AI to output 
 * as feedback
 * @author Ethan King, James Wait
 *
 */
public class MastermindSlot {
	private Color[] marbles;
	private Color[] pegs;
	
	/**
	 * Constructs a MastermindSlot with Color arrays of Gray, which represents an empty slot;
	 */
	MastermindSlot() {
		this.marbles = new Color[]{Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY};
		this.pegs = new Color[]{Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY};
	}
	
	/**
	 * Randomizes the contents of the marbles with a constant from Color.
	 */
	public void randomizeColors()
	{
		Random random = new Random();
		for (int i = 0; i < marbles.length; i++)
		{
			int colorIndex = random.nextInt(6);
			if (colorIndex == 0)
			{
				marbles[i] = Color.RED;
			}
			else if (colorIndex == 1)
			{
				marbles[i] = Color.YELLOW;
			}
			else if (colorIndex == 2)
			{
				marbles[i] = Color.BLUE;
			}
			else if (colorIndex == 3)
			{
				marbles[i] = Color.GREEN;
			}
			else if (colorIndex == 4)
			{
				marbles[i] = Color.BLACK;
			}
			else if (colorIndex == 5)
			{
				marbles[i] = Color.WHITE;
			}
		}
	}

	public void changeColor(int index, Color color)
	{
		marbles[index] = color;
	}
	
	
	
	/**
	 * @return the marbles
	 */
	public Color[] getMarbles() {
		return marbles;
	}

	/**
	 * @return the pegs
	 */
	public Color[] getPegs() {
		return pegs;
	}

	/**
	 * Returns a String in the format
	 * Marbles: <color0, color1, color2, color3>
	 * Pegs: <color0, color1, color2, color3>
	 */
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder("Marbles (From top to bottom): ");
		for (Color i : marbles)
		{
			toString.append(i + " ");
		}
		toString.append("\nPegs (From row by row, left to right): ");
		//for (Color e : pegs)
		//{
		//	toString.append(e + " ");
		//}
		return toString.toString();
	}
	
	
}
