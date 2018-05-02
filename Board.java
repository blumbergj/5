public class Board {
	private int size;
	private int[][] board;
	
	public static void main(String[] args)
	{
		System.out.println("hi");
	}
	
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
	
	public void mark(int row, int col, int id)
	{
		board[row][col] = id;
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
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				int id = board[r][c];
				if((id != 0) && !(c < 4) && !(r > size - 4) &&
						board[r+1][c-1] == id &&
						board[r+2][c-2] == id &&
						board[r+3][c-3] == id &&
						board[r+4][c-4] == id)
				{
					return id;
				}
				if(id != 0 && !(c > size - 4) && !(r > size - 4) &&
						board[r+1][c+1] == id &&
						board[r+2][c+2] == id &&
						board[r+3][c+3] == id &&
						board[r+4][c+4] == id)
				{
					return id;
				}
				if(id != 0 && !(r > size - 4) &&
						board[r+1][c] == id &&
						board[r+2][c] == id &&
						board[r+3][c] == id &&
						board[r+4][c] == id)
				{
					return id;
				}
				if(id != 0 && !(c > size - 4) &&
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
}
