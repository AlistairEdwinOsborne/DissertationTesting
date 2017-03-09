
package Menace.construction;
import Menace.game.State;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class Generator
{
	List<State>	stateList;

	public Generator()
	{
		stateList = new ArrayList<State>();
	}

	public Generator(List<State> states)
	{
		stateList = states;
	}

	public Generator(Generator generator)
	{
		stateList = generator.stateList;
	}

	public boolean saveStates(String filename)
	{
		boolean success = true;

		File outputFile = new File(filename);
		FileWriter writer = null;
		try
		{
			writer = new FileWriter(outputFile);
		}
		catch(IOException e)
		{
			success = false;
			e.printStackTrace();
			return success;
		}
		Iterator<State> it = stateList.iterator();
		int counter=0;
		while(it.hasNext())
		{
			State s = (State)it.next();
			String stateStr = s.storeString();
			try
			{
				writer.write(stateStr);
				writer.write("\n");
				counter ++;
			}
			catch(IOException e1)
			{
				success = false;
				e1.printStackTrace();
				return success;
			}
		}
		try
		{
			writer.close();
		}
		catch(IOException e1)
		{
			success = false;
			e1.printStackTrace();
			return success;
		}

		return success;
	}

	/**
	 * Compares a state against existing states in the objects list of states
	 * @param newState - state you want to compare to existing states
	 * @return true if state already exists even as a rotation
	 */
	public boolean compare(final State newState)
	{
		State testState = newState;
		//System.out.println("testState:");
		//System.out.println(testState);

		Iterator<State> it = stateList.iterator();
		//int listPosition = 0;
		while(it.hasNext())
		{
			//System.out.println("List size: " + stateList.size());
			//System.out.println("Current position: " + listPosition);
			//listPosition++;

			State next = (State) it.next();
			//System.out.println("next from list:");
			//System.out.println(next);
			//System.out.println("TestState turnCounter: "
			//		+ testState.getTurnCounter());
			//System.out.println("NextState turnCounter: "
			//		+ next.getTurnCounter());
			if(!(testState.getTurnCounter() == next.getTurnCounter()))
			{
				//System.out.println("diff turn \n");
			}
			else
			{

				//				System.out.println("testing against:");
				//				System.out.println(next);

				//				System.out.println("test state:");
				//				System.out.println(testState);
				if(testState.equals(next))
				{
					//					System.out.println("Matches");
					return true;
				}

				//System.out.println("Rotated right 90 degrees");
				Rotate rotater = new Rotate(testState);
				testState = rotater.rotateLeft();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//					System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.rotateRight(); //reset
				}

				//System.out.println("Rotated left 90 degrees");
				testState = rotater.rotateRight();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//					System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.rotateLeft(); //reset
				}

				//System.out.println("Rotated 180 degrees");
				testState = rotater.rotate180();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//					System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.rotate180(); //reset
				}

				//System.out.println("Reflected in vertical axis");
				testState = rotater.mirrorVertical();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//					System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.mirrorVertical();
				}

				//System.out.println("Reflected in horizontal axis");
				testState = rotater.mirrorHorizontal();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.mirrorHorizontal();
				}

				//System.out.println("Rotated left 90 degrees and reflected in vertical axis");
				testState = rotater.rotateLeft();
				testState = rotater.mirrorVertical();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.mirrorVertical();
					testState = rotater.rotateRight();
				}

				//System.out.println("Rotated left 90 degrees and reflected in horizontal axis");
				testState = rotater.rotateLeft();
				testState = rotater.mirrorHorizontal();
				//System.out.println(testState);
				if(testState.equals(next))
				{
					//System.out.println("Matches");
					return true;
				}
				else
				{
					testState = rotater.mirrorVertical();
					testState = rotater.rotateLeft();
				}
			}

		}
		//System.out.println("No match found, returning");
		return false;
		//return true;
	}

	public void generateStates(int gridSize)
	{
		State state = new State(new int[gridSize][gridSize], 0);
		stateList.add(state);

		for(int turnCounter = 1; turnCounter <= 9; turnCounter++)
		{

			for(int i = 0; i < gridSize; i++)
			{
				for(int j = 0; j < gridSize; j++)
				{
					int[][] newState = new int[gridSize][gridSize];
					newState[i][j] = 1; //nought player
					state = new State(newState, turnCounter);
					if(!compare(state)) //checks to see if state already in the
						// list
					{ //taking into consideration rotations and reflections
						stateList.add(state); //state not in list so adding it
					}
					//					if(state.getWinningState())
					//					{
					//					}

				}
			}
			//state = new State();
			//			if(!state.getWinningState())
			//			{
			//
			//			}
		}



	}

	public void readInStates(String filename)
	{
		//		File file = new File("allP1States.txt");
		File file = new File(filename);
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(FileNotFoundException e)
		{
			System.err.println(e);
			//			e.printStackTrace();
		}
		String line = null;
		try
		{
			line = reader.readLine();
		}
		catch(IOException e)
		{
			System.err.println(e);
			//			e.printStackTrace();
		}
		//List states = new ArrayList();
		stateList = new ArrayList<State>();
		int totalStates = 338;
		int stateCounter = 0;
		int progress = -1;
		int percentageComplete = 0;

		while(line != null)
		{
			line.trim();
			int gridSize = line.length() / 3; // 3 characters for each position,
			// i.e. [0]
			int[][] state = new int[gridSize][gridSize];
			int zeroCounter = 0;
			for(int i = 0; i < gridSize; i++)
			{
				for(int j = 0; j < gridSize; j++)
				{
					state[i][j] = Integer.parseInt("" + line.charAt(1));
					if(state[i][j] == 0)
					{
						zeroCounter++;
					}
					if(line.length() > 3)
					{
						line = line.substring(3);
					}
				}
				try
				{
					line = reader.readLine();
				}
				catch(IOException e)
				{
					System.err.println(e);
//					e.printStackTrace();
				}
			}
			int turnCounter = 10 - zeroCounter;
			
			//TODO: Be aware, when generating new State, possible moves are calculated resulting in slower calculations.
			// Therefore, the new State object itself should ideally only be generated once confirmed that it is unique.
			State s = new State(state, turnCounter);
			//			System.out.println(s);


			

			if(turnCounter == 10)
			{
				//System.out.println("end of game");
			}
			else if(turnCounter >= 5)
			{

				if(!WinningState(s))
				{
					//	System.out.println("Not winning state");
					//	System.out.println(s);
					if(!compare(s))
					{
						//System.out.println("adding s");
						stateList.add(s);
						stateCounter++;
//						System.err.println(stateCounter);
					}
				}
			}
			else
			{
				if(!compare(s))
				{
					//System.out.println("adding s");
					stateList.add(s);
					stateCounter++;
//					System.err.println(stateCounter);
				}
			}
			
			if(stateCounter % 1000 == 0)
			{
				System.err.println(stateCounter);
				//System.out.println(stateCounter);
			}
			percentageComplete = (int) ((double) stateCounter / totalStates * 100);
			if(percentageComplete % 10 == 0 && percentageComplete != progress)
			{
				System.err.println(percentageComplete + "%");
				
				//System.out.println(percentageComplete + "%");
				progress = percentageComplete;
			}
			


			try
			{
				line = reader.readLine();
			}
			catch(IOException e)
			{
				System.err.println(e);
//				e.printStackTrace();
			}
		}
		/*
			Iterator<State> i = stateList.iterator();
			while(i.hasNext())
			{
				System.out.println(i.next());
			}
		*/
		System.err.println(stateList.size());
	}

	/**
	 * @return true is state is a winning state
	 */
	public static boolean WinningState(State s)
	{

		int[][] stateTable = s.getGridState();

		boolean winningState = false;
		int gridSize = 3;
		int NOT_SET = 0;
		// general horizontal check
		for(int i = 0; i < gridSize; i++)
		{
			for(int j = 0; j < gridSize; j++)
			{
				if(stateTable[i][0] == stateTable[i][j]
						&& stateTable[i][j] != NOT_SET)
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
				if(stateTable[0][j] == stateTable[i][j]
						&& stateTable[i][j] != NOT_SET)
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
			if(stateTable[0][0] == stateTable[i][j]
					&& stateTable[i][j] != NOT_SET)
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
			if(stateTable[0][gridSize - 1] == stateTable[i][j]
					&& stateTable[i][j] != NOT_SET)
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

	public static void main(final String pArgs[])
	{
		Generator gen = new Generator();

		gen.readInStates("./src/Menace/allP1States.txt");
//		gen.readInStates("allP2States.txt");

		System.err.println("Generating possible moves and writing to file");
		gen.saveStates("./src/Menace/playerStatesReduced.txt");
	}

}