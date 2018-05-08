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
	 private static int size;
	 private static JButton[][] jbs;
	 static JFrame screen;
	 private static int turn = 0;
	 static int[] wins = new int[2];
	 static Board b;
	
	public static void main(String[] args) 
	{
		Object[] ops1 = {"Let's Play!", "Exit"};

        int play = JOptionPane.showOptionDialog(null, "5 in a row! Wanna play?", "5", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, ops1, null);
        
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
	                null, ops2, null) + 1) * 15;
		}
		
		screen = new JFrame("5");
        screen.setLayout(null);
        Game b = new Game(size);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setSize(size*20, size*20);
        screen.setLocationRelativeTo(null);
        screen.setVisible(true);
        screen.getContentPane().setBackground(Color.WHITE);
        screen.setResizable(false);
        screen.add(b);
	}
	
	public Game(int s)
	{
		size = s;
		b = new Board(s);
		try
		{
			blankTile = ImageIO.read(new File("Tiles/blankTile.jpg"));
			tileX = ImageIO.read(new File("Tiles/x.jpg"));
			tileO = ImageIO.read(new File("Tiles/o.jpg"));
		}
		catch(Exception e)
		{
			System.out.println("nope");
		}
		
		jbs = new JButton[size][size];
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                JButton jb = new JButton();
                jb.setBounds(20*j, 20*i, 20, 20);
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
	
	public void checkWin()
	{
		turn++;
		
		if(b.gameWon() != -1)
		{
			wins[b.gameWon() - 1]++;
			Object[] ops1 = {"Play!", "Exit"};
			String[] teams = {"X", "O"};

		        int play = JOptionPane.showOptionDialog(null, teams[b.gameWon() - 1] + "'s won!\nX's: " + wins[0] + "\tO's: " + wins[1] + "\nWould you like to play again?", "5", 
		        			JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
		                null, ops1, null);
		        
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
	
	public void reset()
	{
		b.clear();
		turn = 0;
		for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                jbs[i][j].setIcon(new ImageIcon(blankTile)); 
            }
        }
	}
	
	 @Override
	    public void mousePressed(MouseEvent e)
	    {
	        for(int i = 0; i < size; i++)
	        {
	            for(int j = 0; j < size; j++)
	            {

	                if(e.getSource() == jbs[i][j] && SwingUtilities.isLeftMouseButton(e)
	                		&& b.get(i, j) == 0)
	                {

	                	//x is 1, o is 2
	                	
	                    if(turn % 2 == 0)
	                    {
	                    	jbs[i][j].setIcon(new ImageIcon(tileX));
	                    	b.mark(i, j, 1);
	                    	checkWin();
	                    }
	                    else
	                    {
	                    	jbs[i][j].setIcon(new ImageIcon(tileO));
	                    	b.mark(i, j, 2);
	                    	checkWin();
	                    }
	                }
	            }
	        }
	    }

	    /**
	     * Needed in the abstract class MouseListener
	     */
	    public void mouseReleased(MouseEvent e)
	    {

	    }

	    /**
	     * Needed in the abstract class MouseListener
	     */
	    public void mouseExited(MouseEvent e)
	    {

	    }

	    /**
	     * Needed in the abstract class MouseListener
	     */
	    public void mouseEntered(MouseEvent e)
	    {

	    }

	    /**
	     * Needed in the abstract class MouseListener
	     */
	    public void mouseClicked(MouseEvent e)
	    {

	    }
	
}
