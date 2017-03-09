package Menace.construction;
//import construction.Compare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class State
{
	public int gridState[][];
	private int gridSize;
	private int turnCounter;
	private boolean winningState;
	private List<Pair> possibleMoves; //contains integer positions on grid
	
	/*
	 * Grid positions:
	 * [0][1][2]
	 * [3][4][5]
	 * [6][7][8]
	 */
	
	
	
	/*
	 * Constructors
	 */
	public State()
	{
		this(new int[3][3]);
	}
	
	public State(int[][] state)
	{
		this(state, calculateTurn(state));
	}
	
	public State(int[][] state, int turnCount)
	{
		gridState = state;
		gridSize = gridState.length;
		turnCounter = turnCount;
		winningState = checkWinningState();
		
		possibleMoves = new ArrayList<Pair>();
		calculateMoves();
	}
	
	public State(State state)
	{
		gridState = state.gridState;
		gridSize = state.gridSize;
		possibleMoves = state.possibleMoves;
		turnCounter = state.turnCounter;
		winningState = state.winningState;
	}
	
	public State(String stateString)
	{
		possibleMoves = new ArrayList<Pair>();
		
		StringTokenizer tokens = null;
		try
		{
			tokens = new StringTokenizer(stateString, "#");
		}
		catch(NullPointerException e)
		{
			System.out.println(stateString);
			e.printStackTrace();
			System.exit(1);
		}
		String strGridSize = null;
		try
		{
			strGridSize = tokens.nextToken();
		}
		catch(NullPointerException e)
		{
			System.out.println(stateString);
			e.printStackTrace();
			System.exit(1);
		}
		gridSize = Integer.parseInt(strGridSize);
		gridState = new int[gridSize][gridSize];
		
		String strGridState = null;
		try
		{
			strGridState = tokens.nextToken();
		}
		catch(NullPointerException e)
		{
			System.out.println(stateString);
			e.printStackTrace();
			System.exit(1);
		}
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				char c = strGridState.charAt(i*gridSize + j);
				gridState[i][j] = Integer.parseInt("" + c); 
			}
		}
		
		String strTurnCounter = null;
		try
		{
			strTurnCounter = tokens.nextToken();
		}
		catch(NullPointerException e)
		{
			System.out.println(stateString);
			e.printStackTrace();
			System.exit(1);
		}
		turnCounter = Integer.parseInt(strTurnCounter);
		
		String strPairs = tokens.nextToken();
		int numPairs = strPairs.length()/5;	//each pair has length 5, i.e. (0,4)
		for(int i=0; i<numPairs; i++)
		{
			char cPos = strPairs.charAt(i*5 + 1);
			short position = Short.parseShort("" + cPos);
			
			char cBeads = strPairs.charAt(i*5 + 3);
			int beads = Integer.parseInt("" + cBeads);
			
			
			possibleMoves.add(new Pair(position, beads));
		}
	}
	
	
	
	/*
	 * Getter/Setter methods
	 */
	public int getStateSize()
	{
		return gridSize;
	}
	
	public int[][] getGridState()
	{
		int[][] grid = new int[gridSize][gridSize];
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				grid[i][j] = gridState[i][j];
			}
		}
		return grid;
	}
	
//	public boolean getWinningState()
//	{
//		return winningState;
//	}
	
	public int getTurnCounter()
	{
		return turnCounter;
	}
	
	
	public void printMoves()
	{
		System.out.println(possibleMoves.size() + " pairs");
		Iterator<Pair> it = possibleMoves.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
	}
	
	public void printArray(int[][] arr)
	{
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				System.out.print("[" + arr[i][j] + "]");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private boolean checkWinningState()
	{
		boolean winningState = false;
		int NOT_SET = 0;
		
		// general horizontal check
		for(int i = 0; i < gridSize; i++)
		{
			for(int j = 0; j < gridSize; j++)
			{
				if(gridState[i][0] == gridState[i][j]
						&& gridState[i][j] != NOT_SET)
				{
					winningState = true;
				}
				else
				{
					winningState = false;
					break;
				}
			}
			if(winningState == true)
			{
				return winningState;
			}
		}

		// general vertical check
		for(int j = 0; j < gridSize; j++)
		{
			for(int i = 0; i < gridSize; i++)
			{
				if(gridState[0][j] == gridState[i][j]
						&& gridState[i][j] != NOT_SET)
				{
					winningState = true;
				}
				else
				{
					winningState = false;
					break;
				}
			}
			if(winningState == true)
			{
				return winningState;
			}
		}

		// general \ diagonal check
		for(int i = 0, j = 0; i < gridSize && j < gridSize; i++, j++)
		{
			if(gridState[0][0] == gridState[i][j]
					&& gridState[i][j] != NOT_SET)
			{
				winningState = true;
			}
			else
			{
				winningState = false;
				break;
			}
		}
		if(winningState == true)
		{
			return winningState;
		}

		//general / diagonal check
		for(int i = 0, j = gridSize - 1; i < gridSize && j >= 0; i++, j--)
		{
			if(gridState[0][gridSize - 1] == gridState[i][j]
					&& gridState[i][j] != NOT_SET)
			{
				winningState = true;
			}
			else
			{
				winningState = false;
				break;
			}
		}
		return winningState;
	}
	
	private static int calculateTurn(int[][] state)
	{
		int zeroCounter = 0;
		for(int i=0; i<state.length; i++)
		{
			for(int j=0; j<state.length; j++)
			{
				if(state[i][j]==0)
				{
					zeroCounter++;
				}
			}
		}
		int turnCounter = (state.length*state.length + 1) - zeroCounter;
		return turnCounter;
	}
	
	private void calculateMoves()
	{
		List<int[][]> checkList = new ArrayList<int[][]>();
		
		int player;
		if(turnCounter%2==0)
			player=2;
		else
			player=1;

		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				int[][] testState = new int[gridSize][gridSize];
				for(int k=0; k<gridSize; k++)
				{
					for(int l=0; l<gridSize; l++)
					{
						testState[k][l] = gridState[k][l];
					}
				}
				
				if(testState[i][j] == 0)
				{
					testState[i][j] = player;
					boolean match = Compare.CompareStates(checkList, testState);
					if(!match)
					{
						int[][] newState = new int[gridSize][gridSize];
						for(int k=0; k<gridSize; k++)
						{
							for(int l=0; l<gridSize; l++)
							{
								newState[k][l] = testState[k][l];
							}
						}
						checkList.add(newState);
						short position = (short)(gridSize*i + j);
						int beads = 0;
						switch(turnCounter)
						{	
							case 1:
							case 2:
								beads = 4;								
								break;
								
							case 3:
							case 4:
								beads = 3;
								break;
								
							case 5:
							case 6:
								beads = 2;
								break;
								
							case 7:
							case 8:
							case 9:
								beads = 1;
								break;

							default:
								beads = 0;
								break;
						}
						possibleMoves.add(new Pair(position, beads));
					
					}
					testState[i][j]=0;
				}
			}
		}
	}
	
	public int compValue()
	{
		return turnCounter;
	}
	
	public boolean equals(Object obj)
	{
		State compState = (State)obj;
		//int[][] compState = (int[][])obj;
		if(compState.gridSize != gridSize)
		//if(compState.length != gridSize)
		{
			return false;
		}
//		for(int i=0; i<gridSize; i++)
//		{
//			if(compState[i].length != gridSize)
//			{
//				return false;
//			}
//		}
		
		int[][] compGridState = compState.getGridState();
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				if(compGridState[i][j] != gridState[i][j])
				{
					return false;				
				}
			}
		}
		
		return true;
	}
	
	public String storeString()
	{
		String s = new String();
		s += gridSize;
		s += '#';
		for(int i=0; i<gridSize; i++)
		{
			for(int j=0; j<gridSize; j++)
			{
				s += gridState[i][j];
			}
		}
		s += '#';
		s += turnCounter;
		s += '#';
		Iterator<Pair> it = possibleMoves.iterator();
		while(it.hasNext())
		{
			s += it.next().toString();
		}
		return s;
	}


	public String toString()
	{
		String ret = new String();
		for(int i = 0; i<gridSize; i++)
		{
			for(int j = 0; j<gridSize; j++)
			{
				ret += "[" + gridState[i][j] + "]"; 
			}
			ret += '\n';
		}
		return ret;
	}
	
	
	/**
	 * Test function
	 * @param pArgs
	 */
	public static void main(final String pArgs[])
	{
		State s1 = new State();
		System.out.println(s1);
		int[][] a1 = s1.getGridState();
		for(int i=0; i<s1.getStateSize(); i++)
		{
			for(int j=0; j<s1.getStateSize(); j++)
			{
				System.out.print("[" + a1[i][j] + "]");
			}
			System.out.println();
		}
		System.out.println();
				
		int[][] temp = {{0,1,2},{3,4,5},{6,7,8}};
		State s2 = new State(temp);
		System.out.println(s2);
		int[][] a2 = s2.getGridState();
		for(int i=0; i<s2.getStateSize(); i++)
		{
			for(int j=0; j<s2.getStateSize(); j++)
			{
				System.out.print("[" + a2[i][j] + "]");
			}
			System.out.println();
		}
		System.out.println();
	}
}

class Pair
{
	private short gridPosition;
	private int numBeads;
	
	public Pair(short pGridPosition, int pNumBeads)
	{
		gridPosition = pGridPosition;
		numBeads = pNumBeads;
	}
	
	public void updateNumBeads(int pNumBeads)
	{
		numBeads = pNumBeads;
	}
	

	public boolean equals(Object obj)
	{
		Pair other;
		try
		{
			other = (Pair)obj;
		}
		catch(RuntimeException e)
		{
			return false;
		}
		if( gridPosition == other.gridPosition && 
				numBeads == other.numBeads)
		{
			return true;
		}
		
		return false;
	}
	public String toString()
	{
		return "(" + gridPosition + "," + numBeads + ")";
	}
}