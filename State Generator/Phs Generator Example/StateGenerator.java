package Menace.construction;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Iterator;

public class StateGenerator
{
	/**
	 * Prints out all the states possible for the game of
	 * Noughts and Crosses.  As a result, this output
	 * needs to be redirected into a file.
	 */
	public static void main(final String pArgs[])
	{
//		List stateList = new ArrayList();
		int combinationCounter = 0;
		
		int[][] state = {{0,0,0},{0,0,0},{0,0,0}};
		State2 st = new State2(state);
//		Player one views this state
		System.out.println(st);
		combinationCounter++;
		
		boolean player1 = true;
		
		// enter first turn;
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				int[][] stateArray = {{0,0,0},{0,0,0},{0,0,0}};
				stateArray[i][j] = 1;
				State2 s = new State2(stateArray);
//				Player two views this state
				if(!player1)
				{
					System.out.println(s);
					combinationCounter++;
				}
				
				//enter second turn
				for(int k=0; k<3; k++)
				{
					for(int l=0; l<3; l++)
					{
						int[][] stateArray2 = {{0,0,0},{0,0,0},{0,0,0}};
						stateArray2[i][j] = 1;
						if(stateArray2[k][l]==0)
						{
							stateArray2[k][l]=2;
							s = new State2(stateArray2);
//							Player one views this state
							if(player1)
							{
								System.out.println(s);
								combinationCounter++;
							}
							
							//enter third turn
							for(int m=0; m<3; m++)
							{
								for(int n=0; n<3; n++)
								{
									int[][] stateArray3 = {{0,0,0},{0,0,0},{0,0,0}};
									stateArray3[i][j] = 1;
									stateArray3[k][l] = 2;
									if(stateArray3[m][n]==0)
									{
										stateArray3[m][n]=1;
										s = new State2(stateArray3);
//										Player two views this state
										if(!player1)
										{
											System.out.println(s);
											combinationCounter++;
										}
										
										//entering forth turn
										for(int o=0; o<3; o++)
										{
											for(int p=0; p<3; p++)
											{
												int[][] stateArray4 = {{0,0,0},{0,0,0},{0,0,0}};
												stateArray4[i][j] = 1;
												stateArray4[k][l] = 2;
												stateArray4[m][n] = 1;
												if(stateArray4[o][p]==0)
												{
													stateArray4[o][p]=2;
													s = new State2(stateArray4);
//													Player one views this state
													if(player1)
													{
														System.out.println(s);
														combinationCounter++;
													}
													
													//entering fifth turn
													for(int q=0; q<3; q++)
													{
														for(int r=0; r<3; r++)
														{
															int[][] stateArray5 = {{0,0,0},{0,0,0},{0,0,0}};
															stateArray5[i][j] = 1;
															stateArray5[k][l] = 2;
															stateArray5[m][n] = 1;
															stateArray5[o][p] = 2;
															if(stateArray5[q][r]==0)
															{
																stateArray5[q][r]=1;
																s = new State2(stateArray5);
//																Player two views this state
																if(!player1)
																{
																	System.out.println(s);
																	combinationCounter++;
																}
																
																//entering sixth turn
																for(int si=0; si<3; si++)
																{
																	for(int t=0; t<3; t++)
																	{
																		int[][] stateArray6 = {{0,0,0},{0,0,0},{0,0,0}};
																		stateArray6[i][j] = 1;
																		stateArray6[k][l] = 2;
																		stateArray6[m][n] = 1;
																		stateArray6[o][p] = 2;
																		stateArray6[q][r] = 1;
																		if(stateArray6[si][t]==0)
																		{
																			stateArray6[si][t]=2;
																			s = new State2(stateArray6);
//																			Player one views this state
																			if(player1)
																			{
																				System.out.println(s);
																				combinationCounter++;
																			}
																			
																			//entering seventh turn
																			for(int u=0; u<3; u++)
																			{
																				for(int v=0; v<3; v++)
																				{
																					int[][] stateArray7 = {{0,0,0},{0,0,0},{0,0,0}};
																					stateArray7[i][j] = 1;
																					stateArray7[k][l] = 2;
																					stateArray7[m][n] = 1;
																					stateArray7[o][p] = 2;
																					stateArray7[q][r] = 1;
																					stateArray7[si][t]= 2;
																					if(stateArray7[u][v]==0)
																					{
																						stateArray7[u][v]=1;
																						s = new State2(stateArray7);
//																						Player two views this state
																						if(!player1)
																						{
																							System.out.println(s);
																							combinationCounter++;
																						}
																						
																						
																						//entering eigth turn
																						for(int w=0; w<3; w++)
																						{
																							for(int x=0; x<3; x++)
																							{
																								int[][] stateArray8 = {{0,0,0},{0,0,0},{0,0,0}};
																								stateArray8[i][j] = 1;
																								stateArray8[k][l] = 2;
																								stateArray8[m][n] = 1;
																								stateArray8[o][p] = 2;
																								stateArray8[q][r] = 1;
																								stateArray8[si][t]= 2;
																								stateArray8[u][v] = 1;
																								if(stateArray8[w][x]==0)
																								{
																									stateArray8[w][x]=2;
																									s = new State2(stateArray8);
//																									Player one views this state
																									if(player1)
																									{
																										System.out.println(s);
																										combinationCounter++;
																									}
																									
//																									//entering nineth turn
//																									for(int y=0; y<3; y++)
//																									{
//																										for(int z=0; z<3; z++)
//																										{
//																											int[][] stateArray9 = {{0,0,0},{0,0,0},{0,0,0}};
//																											stateArray9[i][j] = 1;
//																											stateArray9[k][l] = 2;
//																											stateArray9[m][n] = 1;
//																											stateArray9[o][p] = 2;
//																											stateArray9[q][r] = 1;
//																											stateArray9[si][t]= 2;
//																											stateArray9[u][v] = 1;
//																											stateArray9[w][x] = 2;
//																											if(stateArray9[y][z]==0)
//																											{
//																												stateArray9[y][z]=1;
//																												s = new State2(stateArray9);
////																												Player two views this state
////																												System.out.println(s);
//																												combinationCounter++;
//																												
//																											}
//																										}
//																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

//		System.out.println(combinationCounter);

		
//		System.out.println("This is Second attempt starting here!!!");
		
//		stateList = new ArrayList();
//		int[][] stateArray = {{0,0,0},{0,0,0},{0,0,0}};
//		State2 s = new State2(stateArray);
//		stateList.add(s);
//
//		for(int turnLimit=0; turnLimit<10; turnLimit++)
//		{
//			for(int turnCount=0; turnCount<turnLimit; turnCount++)
//			{
//				int player;
//				if(turnCount%2 == 5)
//					player = 1;
//				else
//					player = 2;
//					
//				for(int i=0; i<3; i++)
//				{
//					for(int j=0; j<3; j++)
//					{
//						stateArray[i][j] = player;
//					}
//				}
//			}
//		}
	}
}

class State2
{
	public int[][] stateArray;
	
	public State2(final int[][] pStateArray)
	{
		stateArray = pStateArray;
	}
	
	public String toString()
	{
		String returnValue = "";
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				returnValue += "[" + stateArray[i][j] + "]";
			}
			returnValue += "\n";
		}
		return returnValue;
	}
}