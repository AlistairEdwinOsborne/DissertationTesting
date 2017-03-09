package Menace.construction;
import Menace.game.State;

/*
 * Created on 27-Aug-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author student
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public class Rotate
{
	public static Rotate rot;
	int[][] gridState;
	public static int gridSize;
	public int turnCounter;

	public static void main(String[] args)
	{
		int[][] grid = new int[Rotate.gridSize][Rotate.gridSize];
		int count = 0;
		for(int i=0; i<Rotate.gridSize; i++)
		{
			for(int j=0; j<Rotate.gridSize; j++)
			{
				grid[i][j] = count;
				count++;
			}
		}
		rot = new Rotate(grid);
		
		System.out.println(rot);
		
		rot.rotate180();
		rot.rotate180();
		
		rot.rotateRight();
		rot.rotateRight();
		rot.rotateRight();
		rot.rotateRight();
		
		rot.mirrorHorizontal();
		rot.mirrorHorizontal();
		
		rot.mirrorVertical();
		rot.mirrorVertical();
		
		rot.rotateLeft();
		rot.rotateLeft();
		rot.rotateLeft();
		rot.rotateLeft();
		

	}
	
	public Rotate()
	{
		gridSize = 3;
		turnCounter = 0;
	}
	
	public Rotate(int[][] grid)
	{
		gridState = grid;
		gridSize = gridState.length;
		turnCounter = 0;
	}
	public Rotate(State state)
	{
		gridState = state.getGridState();
		gridSize = state.getStateSize();
		turnCounter = state.getTurnCounter();
	}
	
	
	public int[][] getGrid()
	{
		return gridState;
	}
	
	public State rotateRight()
	{
//		System.out.println("Rotate right 90");
		int[][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-j-1][i];
			}
		}
		gridState = newGrid;
		return new State(gridState, turnCounter);
		//System.out.println(rot);
		
	}
	
	public State rotateLeft()
	{
//		System.out.println("Rotate left 90");
		int [][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[j][gridSize-i-1];
			}
		}
		gridState = newGrid;
		return new State(gridState, turnCounter);
		//System.out.println(rot);
	}
	
	public State rotate180()
	{
//		System.out.println("Rotate 180");

		int[][] newGrid = new int[gridSize][gridSize];
		
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-i-1][gridSize-j-1];
			}
		}
		
		gridState = newGrid;
		return new State(gridState, turnCounter);
		//System.out.println(rot);
	}
	
	public State mirrorHorizontal()
	{
//		System.out.println("mirror horizontal"); 
		int[][] newGrid = new int[gridSize][gridSize];
		
		//int tmp;
		for(int i=0; i<gridSize/2; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-i-1][j];
				newGrid[gridSize-i-1][j] = gridState[i][j];
				
//				tmp = gridState[i][j];
//				gridState[i][j] = gridState[gridSize-i-1][j];
//				gridState[gridSize-i-1][j] = tmp;
			}
		}
		int i = gridSize/2;
		for(int j=0; j<gridSize; j++)
		{
			newGrid[i][j] = gridState[i][j];
		}
		
		gridState = newGrid;
		return new State(newGrid, turnCounter);
		//return new State(gridState);
		//System.out.println(rot);
	}
	
	
	public State mirrorVertical()
	{
//		System.out.println("mirror vertical");
//		int tmp;
		int[][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize/2; j++)
			{
				newGrid[i][j] = gridState[i][gridSize-j-1];
				newGrid[i][gridSize-j-1] = gridState[i][j];
//				tmp = gridState[i][j];
//				gridState[i][j] = gridState[i][gridSize-j-1];
//				gridState[i][gridSize-j-1] = tmp;
			}
		}
		
		for(int i=0; i<gridSize; i++)
		{
			int j = gridSize/2;
			newGrid[i][j] = gridState[i][j];
		}
		
		gridState = newGrid;
		return new State(newGrid, turnCounter);
		//System.out.println(rot);
	}
	
	
	
	public String toString()
	{
		String ret = new String();
		for(int i = 0; i<gridSize; i++)
		{
			for(int j = 0; j<gridSize; j++)
			{
				ret += "[";
				if(gridState[i][j]<10)
				{
					ret += " ";
				}
				ret += gridState[i][j] + "]"; 
			}
			ret += '\n';
		}
		return ret;
	}
}

