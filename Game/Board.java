package Game;

public class Board {
	private int size;
	private int[][] board;
	/**
	 * Constructor
     * @param int s size of the board
     * return void
     */
	public Board(int s)
	{
		size = s;
		board = new int[size][size];
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				board[r][c] = 0;
			}
		}
	}
	/**
     * Constructor
     * @param null
     * Defult size 15 board initialized
     */
	public Board()
	{
		size = 15;
		board = new int[size][size];
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				board[r][c] = 0;
			}
		}
	}
	/**
     * @param int i row of board
     * @param int j col of board
     * @return int s status of this square
     */
	public int get(int i, int j)
	{
		return board[i][j];
	}
	
	public void mark(int row, int col, int id)
	{
		board[row][col] = id;
	}
	
	public void unmark(int row, int col)
	{
		board[row][col] = 0;
	}
	
	public void clear()
	{
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				board[r][c] = 0;
			}
		}
	}
	
	public int gameWon()
	{
		if(done())
		{
			return 0;
		}
		
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				int id = board[r][c];
				if((id != 0) && !(c < 5) && !(r > size - 5) &&
						board[r+1][c-1] == id &&
						board[r+2][c-2] == id &&
						board[r+3][c-3] == id &&
						board[r+4][c-4] == id)
				{
					return id;
				}
				if(id != 0 && !(c > size - 5) && !(r > size - 5) &&
						board[r+1][c+1] == id &&
						board[r+2][c+2] == id &&
						board[r+3][c+3] == id &&
						board[r+4][c+4] == id)
				{
					return id;
				}
				if(id != 0 && !(r > size - 5) &&
						board[r+1][c] == id &&
						board[r+2][c] == id &&
						board[r+3][c] == id &&
						board[r+4][c] == id)
				{
					return id;
				}
				if(id != 0 && !(c > size - 5) &&
						board[r][c+1] == id &&
						board[r][c+2] == id &&
						board[r][c+3] == id &&
						board[r][c+4] == id)
				{
					return id;
				}
			}
		}
		return -1;
	}
	/**
     * Resets board and clears positions
     * @return boolean t if the clear worked or not
     */
	public boolean done()
	{
		for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
            	if(board[i][j] == 0)
            	{
            		return false;
            	}
            }
        }
	
	return true;
	}
	
	
}
