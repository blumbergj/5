package Game;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Game extends JComponent implements MouseListener{


	static BufferedImage blankTile;
	static BufferedImage tileX;
	static BufferedImage tileO;
	static BufferedImage redtileX;
	static BufferedImage redtileO;
	static BufferedImage coveredXTile;

	static BufferedImage coveredOTile;
	private static int size;
	private static JButton[][] jbs;
	private static JButton undo;
	static JFrame screen;
	private static int turn = 0;
	static int[] wins = new int[3];
	private static Stack<ArrayList<Integer>> moves;
	static Board b;

	/**
	 * runs crucial game methods 
	 */
	public static void main(String[] args) 
	{
		Object[] ops1 = {"Let's Play!", "Exit"};

		ImageIcon use = new ImageIcon("Tiles/x.jpg");

		int play = JOptionPane.showOptionDialog(null, "5 in a row! Wanna play?", "5", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				use, ops1, null);

		if(play == 1)
		{
			System.exit(0);
		}

		try
		{


			String sizeStr = JOptionPane.showInputDialog(null, "What size would you like the board? (15-50)", "5", JOptionPane.QUESTION_MESSAGE);
			size = Integer.parseInt(sizeStr);
		}
		catch(Exception e)
		{
			Object[] ops2 = {"15", "30", "45"};

			size = (JOptionPane.showOptionDialog(null, "Size needs to be a number! Pick one?", "5", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					use, ops2, null) + 1) * 15;
		}

		screen = new JFrame("5");
		screen.setLayout(null);
		Game b = new Game(size);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setSize(size*20, size*20 + 40);
		screen.setLocationRelativeTo(null);
		screen.setVisible(true);
		screen.getContentPane().setBackground(Color.WHITE);
		screen.setResizable(false);
		screen.add(b);

	}
	/**
	 * Sets game board and prepares for moves
	 * @param int s size of the board
	 * 
	 */
	public Game(int s)
	{
		size = s;
		b = new Board(s);
		moves = new Stack<ArrayList<Integer>>();
		try
		{
			blankTile = ImageIO.read(new File("Tiles/blankTile.jpg"));
			tileX = ImageIO.read(new File("Tiles/x.jpg"));
			tileO = ImageIO.read(new File("Tiles/o.jpg"));
			redtileX = ImageIO.read(new File("Tiles/redx.jpg"));
			redtileO = ImageIO.read(new File("Tiles/redo.jpg"));
			coveredXTile = ImageIO.read(new File("Tiles/overTileO.jpg"));
			coveredOTile = ImageIO.read(new File("Tiles/overTileX.jpg"));
		}
		catch(Exception e)
		{
			System.out.println("nope");
		}

		undo = new JButton();
		undo.setBounds(0, 0, size * 20, 20);
		undo.setOpaque(false);
		undo.setContentAreaFilled(false);
		undo.addMouseListener(this);
		undo.setText("UNDO");
		screen.add(undo);

		jbs = new JButton[size][size];
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				JButton jb = new JButton();
				jb.setBounds(20*(j), 20*(i + 1), 20, 20);
				jb.setOpaque(false);
				jb.setContentAreaFilled(false);
				jb.setBorderPainted(true);
				jb.setIcon(new ImageIcon(blankTile));
				jb.addMouseListener(this);
				jbs[i][j] = jb;
				screen.add(jb);
			}
		}
	}
	/**
	 * Also known as "Tyler's method" checks each location and extends in a maximum of four directions to verify a win.
	 * return null;
	 */
	public void checkWin()
	{
		Object[] ops1 = {"Play!", "Exit"};
		String[] teams = {"X", "O"};
		ImageIcon[] teamPics = {new ImageIcon("Tiles/x.jpg"), new ImageIcon("Tiles/o.jpg")};

		if(b.gameWon() == 0)
		{
			wins[2]++;

			ImageIcon tie = new ImageIcon("Tiles/blankTile.jpg");
			
			int play = JOptionPane.showOptionDialog(null, "DRAW: No one won!\nX's: " + wins[0] + "\t\t\tO's: " + wins[1] + "\nWould you like to play again?", "5", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					tie, ops1, null);

			if(play == 0)
			{
				reset();
			}
			else
			{
				System.exit(0);
			}
		}
		else if(b.gameWon() != -1)
		{
			wins[b.gameWon() - 1]++;



			int play = JOptionPane.showOptionDialog(null, teams[b.gameWon() - 1] + "'s won!\nX's: " + wins[0] + "\t\t\tO's: " + wins[1] + "\nWould you like to play again?", "5", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					teamPics[b.gameWon() - 1], ops1, null);

			if(play == 0)
			{
				reset();
			}
			else
			{
				System.exit(0);
			}
		}
	}
	/**
	 * Reset board after each game win
	 */
	public void reset()
	{
		b.clear();
		moves.clear();

		int totalGames = 0;
		for(int i : wins)
		{
			totalGames += i;
		}

		turn = totalGames % 2;

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				jbs[i][j].setIcon(new ImageIcon(blankTile)); 
			}
		}
	}

	/**
	 * Needed in the abstract class MouseListener
	 */
	public void mousePressed(MouseEvent e)
	{

		if(e.getSource() == undo)
		{
			if(!moves.isEmpty())
			{
				ArrayList<Integer> last = moves.pop();

				for(int i = 0; i < size; i++)
				{
					for(int j = 0; j < size; j++)
					{

						if(last.get(0) == i && last.get(1) == j)
						{
							jbs[i][j].setIcon(new ImageIcon(blankTile));
							b.unmark(i, j);
							turn--;
						}

					}
				}
			}
		}

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{

				if(e.getSource() == jbs[i][j] && SwingUtilities.isLeftMouseButton(e)
						&& b.get(i, j) == 0)
				{

					ArrayList<Integer> add = new ArrayList<Integer>();
					add.add(i);
					add.add(j);
					moves.add(add);

					//x is 1, o is 2

					if(turn % 2 == 0)
					{
						jbs[i][j].setIcon(new ImageIcon(tileX));
						b.mark(i, j, 1);
						turn++;
						checkWin();
					}
					else
					{
						jbs[i][j].setIcon(new ImageIcon(tileO));
						b.mark(i, j, 2);
						turn++;
						checkWin();
					}
				}
			}
		}
	}

	/**
	 * Needed in the abstract class MouseListener
	 * Also checks for win after each placement
	 */
	public void mouseReleased(MouseEvent e)
	{
		if(!moves.isEmpty())
		{
			ArrayList<Integer> last = moves.peek();

			for(int i = 0; i < size; i++)
			{
				for(int j = 0; j < size; j++)
				{
					if(last.get(0) == i && last.get(1) == j)
					{
						if((turn - 1) % 2 == 0)
						{
							jbs[i][j].setIcon(new ImageIcon(redtileX));
							checkWin();
						}
						else
						{
							jbs[i][j].setIcon(new ImageIcon(redtileO));
							checkWin();
						}
					}
					else if(b.get(i, j) != 0)
					{
						if(b.get(i, j) == 1)
						{
							jbs[i][j].setIcon(new ImageIcon(tileX));
						}
						else if(b.get(i, j) == 2)
						{
							jbs[i][j].setIcon(new ImageIcon(tileO));
						}
					}
				}
			}
		}
	}

	/**
	 * Needed in the abstract class MouseListener
	 */
	public void mouseExited(MouseEvent e)
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(e.getSource() == jbs[i][j] && b.get(i, j) == 0)
				{
					jbs[i][j].setIcon(new ImageIcon(blankTile));
				}
			}
		}
	}

	/**
	 * Needed in the abstract class MouseListener
	 */
	public void mouseEntered(MouseEvent e)
	{
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				if(e.getSource() == jbs[i][j] && b.get(i, j) == 0)
				{
					if(turn % 2 == 0)
					{
						jbs[i][j].setIcon(new ImageIcon(coveredOTile));
					}
					else
					{
						jbs[i][j].setIcon(new ImageIcon(coveredXTile));
					}
				}
				
			}
		}
	}

	/**
	 * Needed in the abstract class MouseListener
	 */
	public void mouseClicked(MouseEvent e)
	{

	}

}
