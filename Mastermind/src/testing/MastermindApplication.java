package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MastermindApplication extends JFrame {

	private static JLayeredPane contentPane;
	
	/**
	 * A reference point for the hitbox of the top marble
	 * in the first slot. Vertically, the divets for the player
	 * input marbles are 73px apart. Horizontally, the divets
	 * are 99px apart
	 */
	static Point hitboxRef = new Point(56,57);
	
	/**
	 * A reference point for the top left peg of the first slot.
	 * Horizontally and vertically each peg in a slot is 36px apart
	 */
	static Point pegRef = new Point(32, 353);
	static Rectangle[] hitboxes = new Rectangle[4];
	
	static MastermindSlot secretCode = new MastermindSlot();
	
	static MastermindSlot[] rounds = new MastermindSlot[10];
	static int[] pegs = new int[4];
	
	static int currentRound = 0;
	
	static boolean gameIsActive = true;
	
	static JLabel lblNewLabel;
	
	public static List<MarbleIcon> placedDownMarbles = new ArrayList<MarbleIcon>();
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		secretCode.randomizeColors();
		System.out.println(secretCode.toString());
		updateHitboxPositions();
		
		for (int i = 0; i < rounds.length; i++)
		{
			rounds[i] = new MastermindSlot();
		}
		
		MastermindApplication frame = new MastermindApplication();
		frame.setVisible(true);
		
	}

	/**
	 * Create the frame.
	 */
	private MastermindApplication() {
		setTitle("Mastermind");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MastermindApplication.class.getResource("/resources/redmarble.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JLayeredPane();
		setBounds(100, 100, 1024, 576);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWoodenBoardImg = new JLabel("");
		lblWoodenBoardImg.setIcon(new ImageIcon(MastermindApplication.class.getResource("/resources/MastermindGuiBoardSized.jpg")));
		lblWoodenBoardImg.setBounds(-10, 0, 1025, 576);
		contentPane.add(lblWoodenBoardImg);
		
		DraggableMarble redMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/redmarble.png")));
		redMarble.setBounds(432, 456, 50, 50);
		contentPane.setLayer(redMarble, 5);
		contentPane.add(redMarble);
		
		DraggableMarble greenMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/greenmarble.png")));
		greenMarble.setBounds(523, 456, 50, 50);
		contentPane.setLayer(greenMarble, 5);
		greenMarble.color = Color.GREEN;
		contentPane.add(greenMarble);
		
		DraggableMarble blueMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/bluemarble.png")));
		blueMarble.setBounds(626, 456, 50, 50);
		contentPane.setLayer(blueMarble, 5);
		blueMarble.color = Color.BLUE;
		contentPane.add(blueMarble);
		
		DraggableMarble yellowMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/yellowmarble.png")));
		yellowMarble.setBounds(726, 456, 50, 50);
		contentPane.setLayer(yellowMarble, 5);
		yellowMarble.color = Color.YELLOW;
		contentPane.add(yellowMarble);
		
		DraggableMarble whiteMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/whitemarble.png")));
		whiteMarble.setBounds(824, 456, 50, 50);
		contentPane.setLayer(whiteMarble, 5);
		whiteMarble.color = Color.WHITE;
		contentPane.add(whiteMarble);
		
		DraggableMarble blackMarble = new DraggableMarble(Color.RED, new ImageIcon(MastermindApplication.class.getResource("/resources/blackmarble.png")));
		blackMarble.setBounds(922, 456, 50, 50);
		contentPane.setLayer(blackMarble, 5);
		blackMarble.color = Color.BLACK;
		contentPane.add(blackMarble);
		
		JButton btnSubmit = btnSubmit();
		contentPane.add(btnSubmit);
		
		JButton btnReset = btnReset();
		contentPane.add(btnReset);
		
		lblNewLabel = new JLabel("LOSS or WIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 72));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setOpaque(true);
		contentPane.setLayer(lblNewLabel, 0);
		lblNewLabel.setBounds(0, 0, 1024, 576);
		contentPane.add(lblNewLabel);
		
	}

	/**
	 * Returns the Submit button which contains an actionPerformed method
	 * @return Submit Button
	 */
	private JButton btnSubmit() {
		JButton btnSubmit = new JButton("Submit Guess");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkGuess();
			}
		});
		contentPane.setLayer(btnSubmit, 4);
		btnSubmit.setBounds(15, 456, 129, 50);
		return btnSubmit;
	}

	/** 
	 * Returns the Reset button which contains an actionPerformed method
	 * @return Reset Button
	 */
	private JButton btnReset() {
		JButton btnReset = new JButton("Reset Guess");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSlot();		
			}
		});
		contentPane.setLayer(btnReset, 4);
		btnReset.setBounds(171, 456, 129, 50);
		return btnReset;
	}
	
	private JLabel addBlackPeg()
	{
		MarbleIcon blackPeg = new MarbleIcon(Color.BLACK, new ImageIcon
				(MastermindApplication.class.getResource("/resources/blackpeg.png")));
		blackPeg.setBounds(68, 389, 25, 25);
		contentPane.setLayer(blackPeg, 4);
		return blackPeg;
	}
	
	/**
	 * Returns a 
	 * @return
	 */
	private JLabel addWhitePeg()
	{
		MarbleIcon whitePeg = new MarbleIcon(Color.WHITE, new ImageIcon
				(MastermindApplication.class.getResource("/resources/whitepeg.png")));
		whitePeg.setBounds(0, 0, 25, 25);
		contentPane.setLayer(whitePeg, 4);
		return whitePeg;
	}
	
	private void addPegs()
	{
		List<Integer> slots = new ArrayList<Integer>();
		for (int i = 0; i < pegs.length; i ++)
		{
			slots.add(i);
		}
		
		Collections.shuffle(slots);
		for (int i = 0; i < pegs.length; i++)
		{
			JLabel newPeg = null;
			if (pegs[i] == 1)
			{
				newPeg = addWhitePeg();
			}
			else if (pegs[i] == 2)
			{
				newPeg = addBlackPeg();
			}
			
			if (newPeg != null)
			{
				if (slots.get(i) == 0)
				{
						newPeg.setLocation(pegRef.x + (100 * currentRound), pegRef.y);
				}
				else if (slots.get(i) == 1)
				{
					newPeg.setLocation(pegRef.x + (100 * currentRound) + 36, pegRef.y);
				}
				else if (slots.get(i) == 2)
				{
					newPeg.setLocation(pegRef.x + (100 * currentRound), pegRef.y + 36);
				}
				else if (slots.get(i) == 3)
				{
					newPeg.setLocation(pegRef.x + (100 * currentRound) + 36, pegRef.y + 36);
				}
				
				//System.out.println("hey!");
				addComponent(newPeg);
			}
			
		}
	}
	
	private void checkGuess() {
		if (gameIsActive) 
		{
			placedDownMarbles.clear();
			System.out.println("Checking for solution...");
			Color[] colors = rounds[currentRound].getMarbles();
			Color[] code = secretCode.getMarbles();
			//System.out.println(colors.length + " " + code.length);
			boolean isEqual = true;
			for (int i = 0; i < colors.length; i++)
			{
				pegs[i] = 0;
				for (int a = 0; a < colors.length; a++)
				{
					//System.out.println("Guess: " + colors[i] + "\n" + "Code: " + code[a]);
					if (colors[i] == code[a])
					{
						 //System.out.println(i + " " + a);
						if (pegs[i] != 2)
						{
							if (i == a)
							{
								 pegs[i] = 2;
							}
							else
							{
								 pegs[i] = 1;
							}
						}
						 
					}
				}
				
				if (colors[i] != code[i])
				{
					isEqual = false;
				}
				//System.out.println(pegs[i]);
			}
			
			addPegs();
			
			if (isEqual)
			{
				System.out.println("You did it!");
				contentPane.setLayer(lblNewLabel, 20);
				lblNewLabel.setText("YOU WIN");
				contentPane.updateUI();
				gameIsActive = false;
			}
			else
			{
				System.out.println("Incorrect!");
				if (currentRound < rounds.length - 1)
				{
					currentRound++;
					updateHitboxPositions();
				}
				else
				{
					gameIsActive = false;
					contentPane.setLayer(lblNewLabel, 20);
					lblNewLabel.setText("GAME OVER");
					contentPane.updateUI();
					//System.out.println("Game over!!!");
				}
			}
		}
		else
		{
			System.out.println("Game is complete");
		}
	}

	private static void updateHitboxPositions()
	{
		for (int i = 0; i < hitboxes.length; i++)
		{
			hitboxes[i] = new Rectangle(0,0, 10,10);
			hitboxes[i].setLocation(new Point(hitboxRef.x + (99 * currentRound), hitboxRef.y + (73 * i)));
		}
	}
	
	private static void resetSlot()
	{
		System.out.println("Reset");
		placedDownMarbles.forEach(element -> {contentPane.remove(element);});
		contentPane.validate();
		contentPane.repaint();
		contentPane.updateUI();
		rounds[currentRound] = new MastermindSlot();
	}
	

	/**
	 * If there's a more optimal way to implement this let me know.
	 * Adds a component and updates the UI at runtime.
	 * 
	 * @param Component
	 */
	public static void addComponent(Component comp)
	{
		contentPane.add(comp);
		placedDownMarbles.add((MarbleIcon) comp);
		contentPane.setLayer(comp, 4);
		contentPane.updateUI();
	}
	
	public static Rectangle[] getHitboxes()
	{
		return hitboxes;
	}
	
	public static void changeColor(int index, Color color)
	{
		rounds[currentRound].changeColor(index, color);
		System.out.println(rounds[currentRound].toString());
	}
}
