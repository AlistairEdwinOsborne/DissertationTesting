package Menace.construction;
import java.util.Iterator;
import java.util.List;

/*
 * Created on 05-Nov-2004
 * Created by Tricia Shaw
 * 
 * File: Compare.java
 * Package: 
 */

/**
 * @author Tricia Shaw
 */
public class Compare
{
	
	public static boolean CompareStates(final List<int[][]> stateList, final int[][] newState)
	{
		int[][] testState = newState;
		
		Iterator<int[][]> it = stateList.iterator();
		while(it.hasNext())
		{
			int[][] next = (int[][]) it.next();
			
			if(compareStates(testState, next))
			{
				return true;
			}

			//System.out.println("Rotated right 90 degrees");
			testState = rotateLeft(newState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Rotated left 90 degrees");
			testState = rotateRight(newState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Rotated 180 degrees");
			testState = rotate180(newState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Reflected in vertical axis");
			testState = mirrorVertical(newState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Reflected in horizontal axis");
			testState = mirrorHorizontal(newState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Rotated left 90 degrees and reflected in vertical axis");
			testState = rotateLeft(newState);
			testState = mirrorVertical(testState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

			//System.out.println("Rotated left 90 degrees and reflected in horizontal axis");
			testState = rotateLeft(newState);
			testState = mirrorHorizontal(testState);
			//System.out.println(testState);
			if(compareStates(testState, next))
			{
				//System.out.println("Matches");
				return true;
			}

		}
		//System.out.println("No match found, returning");
		return false;
	}
	
	private static void printArray(int[][] arr)
	{
		for(int i=0; i<arr.length; i++)
		{
			for(int j=0; j<arr.length; j++)
			{
				System.out.print("[" + arr[i][j] + "]");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private static boolean compareStates(int[][] s1, int[][] s2)
	{
		int gridSize = 0;
		if(s1.length != s2.length)
		{
			return false;
		}
		else
		{
			gridSize = s1.length;
		}
	
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				if(s1[i][j] != s2[i][j])
				{
					return false;				
				}
			}
		}
		return true;
	}
	
		
	private static int[][] rotateRight(int[][] gridState)
	{
//		System.out.println("Rotate right 90");
		int gridSize = gridState.length;
		int[][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-j-1][i];
			}
		}
		gridState = newGrid;
		return gridState;
	}
	
	private static int[][] rotateLeft(int[][] gridState)
	{
//		System.out.println("Rotate left 90");
		int gridSize = gridState.length;
		int [][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[j][gridSize-i-1];
			}
		}
		gridState = newGrid;
		return gridState;
	}
	
	private static int[][] rotate180(int[][] gridState)
	{
//		System.out.println("Rotate 180");
		int gridSize = gridState.length;
		int[][] newGrid = new int[gridSize][gridSize];
		
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-i-1][gridSize-j-1];
			}
		}
		
		gridState = newGrid;
		return gridState;
	}
	
	private static int[][] mirrorHorizontal(int[][] gridState)
	{
//		System.out.println("mirror horizontal");
		int gridSize = gridState.length;
		int[][] newGrid = new int[gridSize][gridSize];
		
		for(int i=0; i<gridSize/2; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				newGrid[i][j] = gridState[gridSize-i-1][j];
				newGrid[gridSize-i-1][j] = gridState[i][j];
			}
		}
		int i = gridSize/2;
		for(int j=0; j<gridSize; j++)
		{
			newGrid[i][j] = gridState[i][j];
		}
		
		gridState = newGrid;
		return gridState;
	}
		
	private static int[][] mirrorVertical(int[][] gridState)
	{
//		System.out.println("mirror vertical");
		int gridSize = gridState.length;
		int[][] newGrid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize/2; j++)
			{
				newGrid[i][j] = gridState[i][gridSize-j-1];
				newGrid[i][gridSize-j-1] = gridState[i][j];
			}
		}
		
		for(int i=0; i<gridSize; i++)
		{
			int j = gridSize/2;
			newGrid[i][j] = gridState[i][j];
		}
		
		gridState = newGrid;
		return gridState;
	}
}
