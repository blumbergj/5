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
	 private static int size;
	 private static JButton[][] jbs;
	 private static JButton undo;
	 static JFrame screen;
	 private static int turn = 0;
	 static int[] wins = new int[2];
	 private static Stack<ArrayList<Integer>> moves;
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
        screen.setSize(size*20, size*20 + 40);
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
		moves = new Stack<ArrayList<Integer>>();
		try
		{
			blankTile = ImageIO.read(new File("Tiles/blankTile.jpg"));
			tileX = ImageIO.read(new File("Tiles/x.jpg"));
			tileO = ImageIO.read(new File("Tiles/o.jpg"));
			redtileX = ImageIO.read(new File("Tiles/redx.jpg"));
			redtileO = ImageIO.read(new File("Tiles/redo.jpg"));
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
	
	public void checkWin()
	{
		String[] teams = {"X", "O"};
        
		if(b.gameWon() != -1)
		{
			wins[b.gameWon() - 1]++;
			Object[] ops1 = {"Play!", "Exit"};

		        int play = JOptionPane.showOptionDialog(null, teams[b.gameWon() - 1] + "'s won!\nX's: " + wins[0] + "\t\t\tO's: " + wins[1] + "\nWould you like to play again?", "5", 
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
		moves.clear();
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
		 
		 	if(e.getSource() == undo)
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
	     */
	    public void mouseReleased(MouseEvent e)
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
