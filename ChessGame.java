import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.LineBorder;

//Konnichiwa! ~Welcome~



public class ChessGame{
	
	private static Player playerRed=new Player("Red");
	private static Player playerBlue=new Player("Blue");
	private ChessBoard theGame;
	
	public ChessGame(){

	}

	public static void main(String[] args){
		ChessBoard theGame=ChessBoard.getInstance();
		theGame.startGame();
		theGame.initializePieces(playerRed);
		theGame.initializePieces(playerBlue);	
	}
	
	
	
}

class ChessBoard implements ActionListener //using Singleton Design pattern
{
	private static ChessBoard theBoard=new ChessBoard();
	protected JFrame theFrame=new JFrame();
	
	protected ArrayList<Piece> playerRedPieces=new ArrayList<Piece>(); //player red's chess pieces
	protected ArrayList<Piece> playerBluePieces=new ArrayList<Piece>();//player blue's chess pieces
	
	protected Piece[][] tilesPiece=new Piece[6][7]; //container for the player pieces
	protected JButton[][] tilesButton=new JButton[6][7];//the buttons
	protected JButton newGame;
	protected JButton saveGame;
	protected JButton loadGame;
	protected boolean playerRedCheck=false;//check status for player Red
	protected boolean playerBlueCheck=false;//check status for player Blue
	
	protected int playerTurn=0;//0 for player Red's turn, 1 for player Blue's turn
	protected int gameRound=0;
	protected boolean firstClick=false;//to determine if a piece has been chosen

	protected ArrayList<PositionNumPair> playerSelection=new ArrayList<PositionNumPair>(); //first selected piece is inserted here
	
	private ChessBoard(){} //to prevent instantiation of ChessBoard class
	
	public static ChessBoard getInstance() //to obtain the instance of the sole ChessBoard object
	{
		return theBoard;
	}
	
	public void startGame()
	{
		
		theFrame.setSize(600,600);	//set starting frame size;
		theFrame.setMinimumSize(theFrame.getSize());
		theFrame.setTitle("Myrmidon Chess");
	
		JMenuBar theMenuBar=new JMenuBar();
		
		JButton newGame=new JButton("New Game");
		JButton saveGame=new JButton("Save Game");
		JButton loadGame=new JButton("Load Game");
		newGame.addActionListener(this);
		saveGame.addActionListener(this);
		loadGame.addActionListener(this);
		newGame.setActionCommand("New Game");
		saveGame.setActionCommand("Save Game");
		loadGame.setActionCommand("Load Game");
		
		theMenuBar.add(newGame);
		theMenuBar.add(saveGame);
		theMenuBar.add(loadGame);
		
		theFrame.setJMenuBar(theMenuBar);
		
		
		JPanel theBoard = new JPanel(new GridLayout(6,7));
		
		for(int count=5;count>=0;count--){//creating buttons with coordinated for the chess board
			for(int count2=0;count2<=6;count2++){
				
				tilesButton[count][count2]=new JButton();
				tilesButton[count][count2].setBackground(new Color(255,255,255));
				tilesButton[count][count2].addActionListener(this);
				tilesButton[count][count2].setActionCommand(count+""+count2);//coordinates added as action command
				tilesButton[count][count2].setBorder(new LineBorder(Color.BLUE));
				theBoard.add(tilesButton[count][count2]);
			}
		}
		
		theFrame.add(theBoard,BorderLayout.CENTER);
		theFrame.setVisible(true);
		theFrame.setDefaultCloseOperation(theFrame.EXIT_ON_CLOSE);
	}
	
	public void initializePieces(Player thePlayer) //to initialize all the chess pieces for both players
	{
		
		if(thePlayer.getColor()=="Red")
		{
			playerRedPieces.clear(); //clearing the arraylist to add pieces inside
			playerRedPieces.add(new ThePlus("Red",5,0,"thePlusRed.PNG",true));
			playerRedPieces.add(new TheTriangle("Red",5,1,"theTriangleRed.PNG",true));
			playerRedPieces.add(new TheChevron("Red",5,2,"theChevronRed.PNG",true));
			playerRedPieces.add(new TheSun("Red",5,3,"theSunRed.PNG",true));
			playerRedPieces.add(new TheChevron("Red",5,4,"theChevronRed.PNG",true));
			playerRedPieces.add(new TheTriangle("Red",5,5,"theTriangleRed.PNG",true));
			playerRedPieces.add(new ThePlus("Red",5,6,"thePlusRed.PNG",true));
			
			tilesPiece[5][0]=playerRedPieces.get(0);//the plus piece
			tilesPiece[5][1]=playerRedPieces.get(1);//the triangle piece
			tilesPiece[5][2]=playerRedPieces.get(2);//the chevron piece
			tilesPiece[5][3]=playerRedPieces.get(3);//the sun piece
			tilesPiece[5][4]=playerRedPieces.get(4);//the chevron piece
			tilesPiece[5][5]=playerRedPieces.get(5);//the triangle piece
			tilesPiece[5][6]=playerRedPieces.get(6);//the plus piece
			
			for(int count=0;count<7;count++)
			{
				tilesPiece[5][count].setValidMoves(); //to determine their valid moves based on their current position
			}
			
			tilesButton[5][0].setIcon(tilesPiece[5][0].getPieceIcon());//to deploy the chess piece icon on the board
			tilesButton[5][1].setIcon(tilesPiece[5][1].getPieceIcon());
			tilesButton[5][2].setIcon(tilesPiece[5][2].getPieceIcon());
			tilesButton[5][3].setIcon(tilesPiece[5][3].getPieceIcon());
			tilesButton[5][4].setIcon(tilesPiece[5][4].getPieceIcon());
			tilesButton[5][5].setIcon(tilesPiece[5][5].getPieceIcon());
			tilesButton[5][6].setIcon(tilesPiece[5][6].getPieceIcon());
		}
		else if(thePlayer.getColor()=="Blue")
		{
			playerBluePieces.clear();//clearing the arraylist to add pieces inside
			playerBluePieces.add(new ThePlus("Blue",0,0,"thePlusBlue.PNG",true));
			playerBluePieces.add(new TheTriangle("Blue",0,1,"theTriangleBlue.PNG",true));
			playerBluePieces.add(new TheChevron("Blue",0,2,"theChevronBlue.PNG",true));			
			playerBluePieces.add(new TheSun("Blue",0,3,"theSunBlue.PNG",true));		
			playerBluePieces.add(new TheChevron("Blue",0,4,"theChevronBlue.PNG",true));
			playerBluePieces.add(new TheTriangle("Blue",0,5,"theTriangleBlue.PNG",true));
			playerBluePieces.add(new ThePlus("Blue",0,6,"thePlusBlue.PNG",true));
			
			
			tilesPiece[0][0]=playerBluePieces.get(0);//the plus piece
			tilesPiece[0][1]=playerBluePieces.get(1);//the triangle piece
			tilesPiece[0][2]=playerBluePieces.get(2);//the chevron piece
			tilesPiece[0][3]=playerBluePieces.get(3);//the sun piece
			tilesPiece[0][4]=playerBluePieces.get(4);//the chevron piece
			tilesPiece[0][5]=playerBluePieces.get(5);//the triangle piece
			tilesPiece[0][6]=playerBluePieces.get(6);//the plus piece
					
			for(int count=0;count<7;count++)
			{
				tilesPiece[0][count].setValidMoves();//to determine their valid moves based on their current position
			}
			tilesButton[0][0].setIcon(tilesPiece[0][0].getPieceIcon());//to deploy the chess piece icon on the board
			tilesButton[0][1].setIcon(tilesPiece[0][1].getPieceIcon());
			tilesButton[0][2].setIcon(tilesPiece[0][2].getPieceIcon());
			tilesButton[0][3].setIcon(tilesPiece[0][3].getPieceIcon());
			tilesButton[0][4].setIcon(tilesPiece[0][4].getPieceIcon());
			tilesButton[0][5].setIcon(tilesPiece[0][5].getPieceIcon());
			tilesButton[0][6].setIcon(tilesPiece[0][6].getPieceIcon());
		}
	}
	
	public void newGame()//to reset all values and start a new game
	{
		
		for(int count=0;count<6;count++)
		{
			for(int count2=0;count2<7;count2++)
			{
				tilesPiece[count][count2]=null;
				tilesButton[count][count2].setIcon(null);
				tilesButton[count][count2].setBorder(new LineBorder(Color.BLUE));
			}
		}
		initializePieces(new Player("Red"));
		initializePieces(new Player("Blue"));
		playerRedCheck=false;
		playerBlueCheck=false;
		playerTurn=0;
		gameRound=0;
		firstClick=false;
		playerSelection.clear();
	}

	public void saveGame()
	{
		JFileChooser theFileChooser=new JFileChooser();
		int fileChooseResult=theFileChooser.showSaveDialog(theFrame);
		if(fileChooseResult==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File saveFile= theFileChooser.getSelectedFile();
				FileOutputStream outToSaveFile=new FileOutputStream(saveFile);
				PrintStream writeToSaveFile = new PrintStream(outToSaveFile);
				for(int count=0;count<playerRedPieces.size();count++)
				{
					if(playerRedPieces.get(count) instanceof ThePlus)
					{
						writeToSaveFile.println("Plus");
					}
					else if(playerRedPieces.get(count) instanceof TheTriangle)
					{
						writeToSaveFile.println("Triangle");
					}
					else if(playerRedPieces.get(count) instanceof TheChevron)
					{
						writeToSaveFile.println("Chevron");
					}
					else if(playerRedPieces.get(count) instanceof TheSun)
					{
						writeToSaveFile.println("Sun");
					}
					
					writeToSaveFile.println(playerRedPieces.get(count).getPieceColor());
					writeToSaveFile.println(playerRedPieces.get(count).getPiecePosRow());
					writeToSaveFile.println(playerRedPieces.get(count).getPiecePosCol());
					writeToSaveFile.println(playerRedPieces.get(count).getAlive());
				}
				
				for(int count=0;count<playerBluePieces.size();count++)
				{
					if(playerBluePieces.get(count) instanceof ThePlus)
					{
						writeToSaveFile.println("Plus");
					}
					else if(playerBluePieces.get(count) instanceof TheTriangle)
					{
						writeToSaveFile.println("Triangle");
					}
					else if(playerBluePieces.get(count) instanceof TheChevron)
					{
						writeToSaveFile.println("Chevron");
					}
					else if(playerBluePieces.get(count) instanceof TheSun)
					{
						writeToSaveFile.println("Sun");
					}
					
					writeToSaveFile.println(playerBluePieces.get(count).getPieceColor());
					writeToSaveFile.println(playerBluePieces.get(count).getPiecePosRow());
					writeToSaveFile.println(playerBluePieces.get(count).getPiecePosCol());
					writeToSaveFile.println(playerBluePieces.get(count).getAlive());
									
				}
				
				writeToSaveFile.println(playerRedCheck);
				writeToSaveFile.println(playerBlueCheck);
				writeToSaveFile.println(playerTurn);
				writeToSaveFile.println(gameRound);	
			}
			catch(FileNotFoundException ex)
			{
				System.out.println("No file man");
			}
			
			
		}
	}
	
	public void loadGame()
	{
		JFileChooser theFileChooser=new JFileChooser();
		int fileChooseResult=theFileChooser.showOpenDialog(theFrame);
		if(fileChooseResult==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File loadFile= theFileChooser.getSelectedFile();
				Scanner readFromLoadFile=new Scanner(loadFile);	
				for(int count=0;count<6;count++)
				{
					for(int count2=0;count2<7;count2++)
					{
						tilesPiece[count][count2]=null;
						tilesButton[count][count2].setIcon(null);
						tilesButton[count][count2].setBorder(new LineBorder(Color.BLUE));
					}
				}
				
				playerRedPieces.clear();
				playerBluePieces.clear();
							
				for(int count=0;count<7;count++)//load red pieces
				{
					
					String thePiece=readFromLoadFile.nextLine();
					String loadedColor=readFromLoadFile.nextLine();
					int loadedPiecePosRow=readFromLoadFile.nextInt();
					int loadedPiecePosCol=readFromLoadFile.nextInt();
					readFromLoadFile.nextLine();	
					boolean loadedAlive=readFromLoadFile.nextBoolean();
					readFromLoadFile.nextLine();
					
					if(thePiece.equals("Plus"))
					{
						System.out.println("Yes");
						playerRedPieces.add(new ThePlus(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"thePlusRed.PNG",loadedAlive));
					}
					else if(thePiece.equals("Triangle"))
					{
						playerRedPieces.add(new TheTriangle(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theTriangleRed.PNG",loadedAlive));
					}			
					else if(thePiece.equals("Chevron"))
					{
						playerRedPieces.add(new TheChevron(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theChevronRed.PNG",loadedAlive));
					}
					else if(thePiece.equals("Sun"))
					{
						playerRedPieces.add(new TheSun(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theSunRed.PNG",loadedAlive));
					}
					
				}
				for(int count=0;count<7;count++)
				{
					String thePiece=readFromLoadFile.nextLine();
					String loadedColor=readFromLoadFile.nextLine();
					int loadedPiecePosRow=readFromLoadFile.nextInt();
					int loadedPiecePosCol=readFromLoadFile.nextInt();
					readFromLoadFile.nextLine();
					boolean loadedAlive=readFromLoadFile.nextBoolean();
					readFromLoadFile.nextLine();
					if(thePiece.equals("Plus"))
					{
						playerBluePieces.add(new ThePlus(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"thePlusBlue.PNG",loadedAlive));
					}
					else if(thePiece.equals("Triangle"))
					{
						playerBluePieces.add(new TheTriangle(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theTriangleBlue.PNG",loadedAlive));
					}			
					else if(thePiece.equals("Chevron"))
					{
						playerBluePieces.add(new TheChevron(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theChevronBlue.PNG",loadedAlive));
					}
					else if(thePiece.equals("Sun"))
					{
						playerBluePieces.add(new TheSun(loadedColor,loadedPiecePosRow,loadedPiecePosCol,"theSunBlue.PNG",loadedAlive));
					}
				}
				
				
				playerRedCheck=readFromLoadFile.nextBoolean();
				readFromLoadFile.nextLine();
				playerBlueCheck=readFromLoadFile.nextBoolean();	
				readFromLoadFile.nextLine();
				playerTurn=readFromLoadFile.nextInt();
				gameRound=readFromLoadFile.nextInt();

				
				for(int count=0;count<playerRedPieces.size();count++)
				{
					if(playerRedPieces.get(count).getAlive())
					{
						playerRedPieces.get(count).setValidMoves();
						tilesPiece[playerRedPieces.get(count).getPiecePosRow()][playerRedPieces.get(count).getPiecePosCol()]=playerRedPieces.get(count);
						tilesButton[playerRedPieces.get(count).getPiecePosRow()][playerRedPieces.get(count).getPiecePosCol()].setIcon(playerRedPieces.get(count).getPieceIcon());
					}
				}
				for(int count=0;count<playerBluePieces.size();count++)
				{
					if(playerBluePieces.get(count).getAlive())
					{
						playerBluePieces.get(count).setValidMoves();
						tilesPiece[playerBluePieces.get(count).getPiecePosRow()][playerBluePieces.get(count).getPiecePosCol()]=playerBluePieces.get(count);
						tilesButton[playerBluePieces.get(count).getPiecePosRow()][playerBluePieces.get(count).getPiecePosCol()].setIcon(playerBluePieces.get(count).getPieceIcon());
					}
				}
				firstClick=false;
				playerSelection.clear();
				
			}
			catch(FileNotFoundException ex)
			{
				System.out.println("File not found~");
			}
		}
	}
			
	public void setSunMove() //to recalculate the sun piece valid moves every turn to determine check and checkmate status
	{
		
		ArrayList<PositionNumPair> playerRedSunMovement=playerRedPieces.get(3).getValidMoves();//get the red sun's valid moves
		
		for(int count=0;count<playerBluePieces.size();count++)//go through all blue pieces
		{
			if(playerBluePieces.get(count).getAlive())//if the piece is still alive
			{
				ArrayList<PositionNumPair> playerBluePieceMovement= playerBluePieces.get(count).getValidMoves();//get the piece's valid moves
				
				for(int count2=playerRedSunMovement.size()-1;count2>=0;count2--)//go through all the red's sun valid movement
				{
					for(int count3=playerBluePieceMovement.size()-1;count3>=0;count3--)//go through all blue pieces valid moves
					{
						if(playerRedSunMovement.get(count2).getPosRow()==playerBluePieceMovement.get(count3).getPosRow()&& //cannot move to place where opponent piece can move to
						playerRedSunMovement.get(count2).getPosCol()==playerBluePieceMovement.get(count3).getPosCol())
						{
							playerRedSunMovement.remove(count2);
							break;
						}
						
					}
				}
			}
		}
		
		for(int count=playerRedSunMovement.size()-1;count>=0;count--) //to remove valid moves where allies piece reside
		{
			if(tilesPiece[playerRedSunMovement.get(count).getPosRow()][playerRedSunMovement.get(count).getPosCol()]!=null)
			{
				if(tilesPiece[playerRedSunMovement.get(count).getPosRow()][playerRedSunMovement.get(count).getPosCol()].getPieceColor()=="Red")
				{
					playerRedSunMovement.remove(count);
				}
			}
		}
		playerRedPieces.get(3).setValidCheckMoves(playerRedSunMovement);
		
	
	
		ArrayList<PositionNumPair> playerBlueSunMovement=playerBluePieces.get(3).getValidMoves();//get the blue sun's valid moves
		
		for(int count=0;count<playerRedPieces.size();count++)//go through all red pieces
		{
			if(playerRedPieces.get(count).getAlive())//if the piece is still alive
			{
				ArrayList<PositionNumPair> playerRedPieceMovement= playerRedPieces.get(count).getValidMoves();//get the piece's valid moves
				
				for(int count2=playerBlueSunMovement.size()-1;count2>=0;count2--)//go through all the blue's sun valid movement
				{
					for(int count3=playerRedPieceMovement.size()-1;count3>=0;count3--)//go through all red pieces valid moves
					{
						if(playerBlueSunMovement.get(count2).getPosRow()==playerRedPieceMovement.get(count3).getPosRow()&&
						playerBlueSunMovement.get(count2).getPosCol()==playerRedPieceMovement.get(count3).getPosCol())
						{
							playerBlueSunMovement.remove(count2);
							break;
						}
						
					}
				}
			}
		}
		
		for(int count=playerBlueSunMovement.size()-1;count>=0;count--)
		{
			if(tilesPiece[playerBlueSunMovement.get(count).getPosRow()][playerBlueSunMovement.get(count).getPosCol()]!=null)
			{
				if(tilesPiece[playerBlueSunMovement.get(count).getPosRow()][playerBlueSunMovement.get(count).getPosCol()].getPieceColor()=="Blue")
				{
					playerBlueSunMovement.remove(count);
				}
			}
		}
		playerBluePieces.get(3).setValidCheckMoves(playerBlueSunMovement);
		
	}
	
	public void checkForCheck()//just to check if one player's piece can move to the opponent's sun
	{
		playerRedCheck=false;
		playerBlueCheck=false;
		
		//Player red sun check
		for(int count=0;count<playerBluePieces.size();count++)
		{
			if(playerBluePieces.get(count).getAlive())
			{
				ArrayList<PositionNumPair> opponentPiece =playerBluePieces.get(count).getValidMoves();
				for(int count2=0;count2<opponentPiece.size();count2++)
				{
					if(opponentPiece.get(count2).getPosRow()==playerRedPieces.get(3).getPiecePosRow()&&//check if any opponent piece overlaps with sun position
					opponentPiece.get(count2).getPosCol()==playerRedPieces.get(3).getPiecePosCol())
					{
						JOptionPane.showMessageDialog(theFrame,"Player Red is checked!");
						playerRedCheck=true; //player red sun is checked
					}
				}
			}
		}
						
		//Player blue sun check
		for(int count=0;count<playerRedPieces.size();count++)
		{
			if(playerRedPieces.get(count).getAlive())
			{
				ArrayList<PositionNumPair> opponentPiece =playerRedPieces.get(count).getValidMoves();
				for(int count2=0;count2<opponentPiece.size();count2++)
				{
					if(opponentPiece.get(count2).getPosRow()==playerBluePieces.get(3).getPiecePosRow()&&//check if any opponent piece overlaps with sun position
					opponentPiece.get(count2).getPosCol()==playerBluePieces.get(3).getPiecePosCol())
					{
						JOptionPane.showMessageDialog(theFrame,"Player Blue is checked!");
						playerBlueCheck=true;//player blue sun is checked
					}
				}
			}
		}
		
		//if the sun is killed, check have to be set to true to trigger checkmate condition
		if(playerRedPieces.get(3).getAlive()==false)
		{
			playerRedCheck=true;
		}

		if(playerBluePieces.get(3).getAlive()==false)
		{
			playerBlueCheck=true;
		}
		
			
		
	}
			
	public void promotePiece()
	{
		gameRound=0;//reset the game round
		
		ArrayList<Piece> promotedRedPlayerPieces=new ArrayList<Piece>(); //create new arraylist for red player chess pieces
		ArrayList<Piece> promotedBluePlayerPieces=new ArrayList<Piece>();//create new arraylist for blue player chess pieces
		
		for(int count=0;count<6;count++)
		{
			for(int count2=0;count2<7;count2++)
			{
				tilesPiece[count][count2]=null;
				tilesButton[count][count2].setIcon(null);
			}
		}
		
		//PROMOTING RED PIECES
		for(int count=0;count<playerRedPieces.size();count++)
		{
			
			if(count==3)//adding the sun to its position in arraylist
			{
				String color=playerRedPieces.get(count).getPieceColor();
				int positionRow=playerRedPieces.get(count).getPiecePosRow();
				int positionCol=playerRedPieces.get(count).getPiecePosCol();
				String theNewPicture="theSunRed.PNG";
				boolean alive=playerRedPieces.get(count).getAlive();
				promotedRedPlayerPieces.add(new TheSun(color,positionRow,positionCol,theNewPicture,alive));
			}
			if(playerRedPieces.get(count) instanceof ThePlus) //check instance of the piece and create a promoted piece according to existing coordinates and values
			{
				String color=playerRedPieces.get(count).getPieceColor();
				int positionRow=playerRedPieces.get(count).getPiecePosRow();
				int positionCol=playerRedPieces.get(count).getPiecePosCol();
				String theNewPicture="theTriangleRed.PNG";
				boolean alive=playerRedPieces.get(count).getAlive();
				promotedRedPlayerPieces.add(new TheTriangle(color,positionRow,positionCol,theNewPicture,alive));//add promoted piece to new array list of chess pieces
			}
			else if (playerRedPieces.get(count) instanceof TheTriangle)
			{
				String color=playerRedPieces.get(count).getPieceColor();
				int positionRow=playerRedPieces.get(count).getPiecePosRow();
				int positionCol=playerRedPieces.get(count).getPiecePosCol();
				String theNewPicture="theChevronRed.PNG";
				boolean alive=playerRedPieces.get(count).getAlive();
				promotedRedPlayerPieces.add(new TheChevron(color,positionRow,positionCol,theNewPicture,alive));
			}
			else if (playerRedPieces.get(count) instanceof TheChevron)
			{
				String color=playerRedPieces.get(count).getPieceColor();
				int positionRow=playerRedPieces.get(count).getPiecePosRow();
				int positionCol=playerRedPieces.get(count).getPiecePosCol();
				String theNewPicture="thePlusRed.PNG";
				boolean alive=playerRedPieces.get(count).getAlive();
				promotedRedPlayerPieces.add(new ThePlus(color,positionRow,positionCol,theNewPicture,alive));
			}
		}
		
		playerRedPieces.clear();//clear existing red player pieces
		playerRedPieces=promotedRedPlayerPieces;//assign new promoted pieces array to actual red player piece array
		
		
		
		for(int count=0;count<playerRedPieces.size();count++)
		{
			playerRedPieces.get(count).setValidMoves();//recalculate valid moves of the new pieces
			if(playerRedPieces.get(count).getAlive())//if the piece is still alive, add them to the chessboard
			{
				tilesPiece[playerRedPieces.get(count).getPiecePosRow()]
				[playerRedPieces.get(count).getPiecePosCol()]=playerRedPieces.get(count);				
				tilesButton[playerRedPieces.get(count).getPiecePosRow()]
				[playerRedPieces.get(count).getPiecePosCol()].setIcon(playerRedPieces.get(count).getPieceIcon());
			}
		}
		
		//PROMOTING RED PIECES ENDS
		
		
		//PROMOTING BLUE PIECES
		for(int count=0;count<playerBluePieces.size();count++)
		{
			
			if(count==3)//adding the sun to its position on arraylist
			{
				String color=playerBluePieces.get(count).getPieceColor();
				int positionRow=playerBluePieces.get(count).getPiecePosRow();
				int positionCol=playerBluePieces.get(count).getPiecePosCol();
				String theNewPicture="theSunBlue.PNG";
				boolean alive=playerBluePieces.get(count).getAlive();
				promotedBluePlayerPieces.add(new TheSun(color,positionRow,positionCol,theNewPicture,alive));
			}
			if(playerBluePieces.get(count) instanceof ThePlus)//check instance of the piece and create a promoted piece according to existing coordinates and values
			{
				String color=playerBluePieces.get(count).getPieceColor();
				int positionRow=playerBluePieces.get(count).getPiecePosRow();
				int positionCol=playerBluePieces.get(count).getPiecePosCol();
				String theNewPicture="theTriangleBlue.PNG";
				boolean alive=playerBluePieces.get(count).getAlive();
				promotedBluePlayerPieces.add(new TheTriangle(color,positionRow,positionCol,theNewPicture,alive));
			}
			else if (playerBluePieces.get(count) instanceof TheTriangle)
			{
				String color=playerBluePieces.get(count).getPieceColor();
				int positionRow=playerBluePieces.get(count).getPiecePosRow();
				int positionCol=playerBluePieces.get(count).getPiecePosCol();
				String theNewPicture="theChevronBlue.PNG";
				boolean alive=playerBluePieces.get(count).getAlive();
				promotedBluePlayerPieces.add(new TheChevron(color,positionRow,positionCol,theNewPicture,alive));
			}
			else if (playerBluePieces.get(count) instanceof TheChevron)
			{
				String color=playerBluePieces.get(count).getPieceColor();
				int positionRow=playerBluePieces.get(count).getPiecePosRow();
				int positionCol=playerBluePieces.get(count).getPiecePosCol();
				String theNewPicture="thePlusBlue.PNG";
				boolean alive=playerBluePieces.get(count).getAlive();
				promotedBluePlayerPieces.add(new ThePlus(color,positionRow,positionCol,theNewPicture,alive));
			}
		}
		
		playerBluePieces.clear();//clear existing blue player pieces
		playerBluePieces=promotedBluePlayerPieces;//assign new promoted pieces array to actual blue player piece array
		
		for(int count=0;count<playerBluePieces.size();count++)
		{
			playerBluePieces.get(count).setValidMoves();//recalculate valid moves of the new pieces
			if(playerBluePieces.get(count).getAlive())//if the piece is still alive, add them to the chessboard
			{
				tilesPiece[playerBluePieces.get(count).getPiecePosRow()]
				[playerBluePieces.get(count).getPiecePosCol()]=playerBluePieces.get(count);				
				tilesButton[playerBluePieces.get(count).getPiecePosRow()]
				[playerBluePieces.get(count).getPiecePosCol()].setIcon(playerBluePieces.get(count).getPieceIcon());
			}
		}
		////PROMOTING BLUE PIECES ENDS
	}
	
	public void verifyMovement(int row,int column)
	{	
	
		
		if(firstClick==false&&tilesPiece[row][column]!=null)// a piece hasn't been selected and the selected tile is not null
		{		
			if(tilesPiece[row][column].getPieceColor().equals("Red")&&playerTurn==0&&playerSelection.size()==0) //select own piece at own turn
			{
				playerSelection.add(new PositionNumPair(row,column));
				firstClick=true;
				tilesButton[row][column].setBorder(new LineBorder(Color.GREEN));
			}
			else if(tilesPiece[row][column].getPieceColor().equals("Blue")&&playerTurn==1&&playerSelection.size()==0)//select own piece at own turn
			{
				playerSelection.add(new PositionNumPair(row,column));
				firstClick=true;
				tilesButton[row][column].setBorder(new LineBorder(Color.GREEN));
			}
			else
			{
				JOptionPane.showMessageDialog(theFrame,"~It is not your turn~");
			}
			
		}
		else if(firstClick==true) //first piece selected
		{
			
			if(playerSelection.get(0).getPosRow()==row&&playerSelection.get(0).getPosCol()==column) //to deselect the selected piece
			{
				tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setBorder(new LineBorder(Color.BLUE));
				playerSelection.clear();
				firstClick=false;
				
			}
			else//to move piece
			{
				
				boolean validity=false;
				//check if movement is valid
				validity=tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].validateMovement(row,column);
			
				if(validity)//the movement is valid!
				{
					gameRound++;
					if(tilesPiece[row][column]==null)
					{
						tilesPiece[row][column]=tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()];
						tilesButton[row][column].setIcon(tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].getPieceIcon());
						//make tile piece be vacant and remove icon from initial position
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setBorder(new LineBorder(Color.BLUE));
						tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()]=null;
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setIcon(null);
						
						//ending turn
						tilesPiece[row][column].setNewPosition(row,column);
						tilesPiece[row][column].setValidMoves();
						
						//completion of a turn
						firstClick=false;
						playerSelection.clear();
						if(playerTurn==0)
						{
							playerTurn=1;
							
						}
						else
						{
							playerTurn=0;
						}
						playerRedPieces.get(3).setValidMoves();
						playerBluePieces.get(3).setValidMoves();
						setSunMove();
						checkForCheck();
						
					}
					else if(tilesPiece[row][column].getPieceColor().equals("Red")&&playerTurn==1)//killing opponent piece
					{
						
						tilesPiece[row][column].setAlive(false);//killing the piece and clearing all its valid moves
											
						tilesPiece[row][column]=tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()];
						tilesButton[row][column].setIcon(tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].getPieceIcon());
						tilesPiece[row][column].setNewPosition(row,column);//set the new position of the piece
						tilesPiece[row][column].setValidMoves();//recalculate the valid moves
						
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setBorder(new LineBorder(Color.BLUE));
						tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()]=null;//previous spot is null to vacant the spot
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setIcon(null);
						firstClick=false;
						playerSelection.clear();					
						playerTurn=0;
						
						if(playerRedPieces.get(3).getAlive())
						{
							playerRedPieces.get(3).setValidMoves();
							setSunMove();
						}
						checkForCheck();
				
					}
					else if(tilesPiece[row][column].getPieceColor().equals("Blue")&&playerTurn==0)//killing opponent piece
					{						
						tilesPiece[row][column].setAlive(false);//killing the piece and clearing all its valid moves	
						tilesPiece[row][column]=tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()];
						tilesButton[row][column].setIcon(tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].getPieceIcon());
						tilesPiece[row][column].setNewPosition(row,column);
						tilesPiece[row][column].setValidMoves();
						
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setBorder(new LineBorder(Color.BLUE));
						tilesPiece[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()]=null;
						tilesButton[playerSelection.get(0).getPosRow()][playerSelection.get(0).getPosCol()].setIcon(null);
						
						firstClick=false;
						playerSelection.clear();						
						playerTurn=1;
						
						if(playerBluePieces.get(3).getAlive())
						{
							playerBluePieces.get(3).setValidMoves();
							setSunMove();
						}
						checkForCheck();
						
					}
					else
					{
						JOptionPane.showMessageDialog(theFrame,"~That spot is occupied by your own piece~");
					}
				}
				else//the movement is not valid
				{
					JOptionPane.showMessageDialog(theFrame,"~This piece is unable to move there~");
				}
			}
		}
		
		if(playerRedCheck)//check for checkmate before promotion
		{
			if(playerRedPieces.get(3).getValidMovesSize()==0)//red sun no longer have any valid move
			{				
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Blue wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
			else if(!playerRedPieces.get(3).getAlive())//red sun is killed
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Blue wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}		
		}
	
		if(playerBlueCheck)//check for checkmate before promotion
		{
			if(playerBluePieces.get(3).getValidMovesSize()==0)// blue sun no longer have any valid move
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Red wins","Continue?",JOptionPane.YES_NO_OPTION);
				
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
			else if(!playerBluePieces.get(3).getAlive())//blue sun is killed
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Red wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
		}
		
		if(gameRound==6)
		{
			promotePiece();
			playerRedPieces.get(3).setValidMoves();
			playerBluePieces.get(3).setValidMoves();
			setSunMove();				
			checkForCheck();
		}
		
		if(playerRedCheck)//check for checkmate after promotion
		{
			if(playerRedPieces.get(3).getValidMovesSize()==0)//red sun no longer have any valid move
			{				
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Blue wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
			else if(!playerRedPieces.get(3).getAlive())//red sun is killed
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Blue wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}		
		}
			
		if(playerBlueCheck)//check for checkmate after promotion
		{
			if(playerBluePieces.get(3).getValidMovesSize()==0)// blue sun no longer have any valid move
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Red wins","Continue?",JOptionPane.YES_NO_OPTION);
				
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
			else if(!playerBluePieces.get(3).getAlive())//blue sun is killed
			{
				JOptionPane.showMessageDialog(theFrame,"CheckMate!");
				int continueDecision=JOptionPane.showConfirmDialog(theFrame,"Player Red wins","Continue?",JOptionPane.YES_NO_OPTION);
				if(continueDecision==JOptionPane.YES_OPTION)
				{
					newGame();
				}
				else if(continueDecision==JOptionPane.NO_OPTION)
				{
					System.exit(0);
				}
				else
				{
					//nothing, cancel option
				}
			}
		}
		
		
		
	}//end of verify movement function
		
	public void actionPerformed(ActionEvent e)
	{
		String command=e.getActionCommand();
		if(command.equals("New Game"))
		{
			newGame();
		}
		else if(command.equals("Save Game"))
		{
			saveGame();
		}
		else if(command.equals("Load Game"))
		{
			loadGame();
		}
		else
		{
			//converting the name of button into row and column
			char rowChar=command.charAt(0);
			char columnChar=command.charAt(1);
			int row=Character.getNumericValue(rowChar);
			int column=Character.getNumericValue(columnChar);
			verifyMovement(row,column);	
		}
		
	}
		
}

class Player
{
	private String color;
	
	public Player(String color)
	{	
		this.color=color;		
	}
	
	public String getColor()
	{
		return color;
	}
	
}

class Piece
{
	protected String color; //the player's colour who owns this piece
	protected int positionRow;//current row position on chessboard
	protected int positionCol;//current column position on chessboard
	protected ArrayList<PositionNumPair> validMoves=new ArrayList<PositionNumPair>();//to store all the valid moves of this piece
	protected ImageIcon thePicture; //ImageIcon of this piece
	protected boolean alive;//boolean value to check if piece is alive
	

	public Piece(String color,int positionRow,int positionCol,String thePictureLink,boolean alive)
	{
		this.color=color;
		this.positionRow=positionRow;
		this.positionCol=positionCol;
		this.thePicture=new ImageIcon(thePictureLink);
		this.alive=alive;
	}
	
	public int getValidMovesSize()//return validmoves size
	{
		return validMoves.size();
	}
	
	public void setNewPosition(int row,int column)//set new position of the piece after a successful movement
	{
		positionRow=row;
		positionCol=column;
	}
	
	public void setAlive(boolean alive)//function to "kill" the piece
	{
		this.alive=alive;
		validMoves.clear();//remove all validmoves to avoid overlapping and conflict
	}
	
	public boolean getAlive()//returns boolean value that tells if the piece is still alive or dead
	{
		return alive;
	}
	
	public int getPiecePosRow()//getting the row position of this piece
	{
		return positionRow;
	}
	
	public int getPiecePosCol()//getting the column position of this piece
	{
		return positionCol;
	}
	
	public boolean validateMovement(int row,int column)//checking movement attempt is valid or not
	{	
		for(int count=0;count<validMoves.size();count++)
		{
			if(validMoves.get(count).getPosRow()==row && validMoves.get(count).getPosCol()==column)
			{
				return true;
			}
		}
		return false;
	
	}
	
	public String getPieceColor()//return the piece's color
	{
		return color;
	}
	
	public ImageIcon getPieceIcon()//return the ImageIcon of this piece
	{
		return thePicture;
	}
	
	public void setValidCheckMoves(ArrayList<PositionNumPair> newSunMovement)//meant for sun piece which calculates is valid moves every round
	{
		validMoves=newSunMovement;//
	}
	
	public void setValidMoves()//meant to be overriden, this string should never appear when this function is called
	{
		System.out.println("Whoops something went wrong@.@");
	}	
		
	public ArrayList<PositionNumPair> getValidMoves()//return valid moves of the piece
	{
		return validMoves;
	}
	
	
	
}

class ThePlus extends Piece
{
	public ThePlus(String color,int positionRow,int positionCol,String thePictureLink,boolean alive)
	{
		super(color,positionRow,positionCol,thePictureLink,alive);
	}
	
	public void setValidMoves()
	{
		//clear previous valid moves and compute new ones based on the latest location
		validMoves.clear();
		
		//vertical movement
		validMoves.add(new PositionNumPair(positionRow + 2,positionCol));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol));
		validMoves.add(new PositionNumPair(positionRow - 2,positionCol));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol));
		//vertical movement ends
		
		//horizontal movement
		validMoves.add(new PositionNumPair(positionRow,positionCol + 2));
		validMoves.add(new PositionNumPair(positionRow,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow,positionCol - 2));
		validMoves.add(new PositionNumPair(positionRow,positionCol - 1));
		//horizontal movement ends	
		
		for(int count=validMoves.size()-1;count>=0;count--)
		{
			if(validMoves.get(count).getPosRow()<0|| validMoves.get(count).getPosRow()>5|| validMoves.get(count).getPosCol()<0||validMoves.get(count).getPosCol()>6)
			{
				validMoves.remove(count);
			}
		}
		
		
	}
		
}

class TheTriangle extends Piece
{	
	public TheTriangle(String color,int positionRow,int positionCol,String thePictureLink,boolean alive)
	{
		super(color,positionRow,positionCol,thePictureLink,alive);
	}
	
	public void setValidMoves()
	{
		//clear previous valid moves and compute new ones based on the latest location
		validMoves.clear();
		
		//forward
		validMoves.add(new PositionNumPair(positionRow + 2,positionCol + 2));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow + 2,positionCol - 2));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol - 1));
		//forward ends
		
		//backward
		validMoves.add(new PositionNumPair(positionRow - 2,positionCol - 2));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol - 1));
		validMoves.add(new PositionNumPair(positionRow - 2,positionCol + 2));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol + 1));
		//backward ends
		
		for(int count=validMoves.size()-1;count>=0;count--)
		{
			if(validMoves.get(count).getPosRow()<0|| validMoves.get(count).getPosRow()>5|| validMoves.get(count).getPosCol()<0||validMoves.get(count).getPosCol()>6)
			{
				validMoves.remove(count);
			}
			
		}
	}
	
	
}

class TheChevron extends Piece
{
	public TheChevron(String color,int positionRow,int positionCol,String thePictureLink,boolean alive)
	{
		super(color,positionRow,positionCol,thePictureLink,alive);
	}
	
	public void setValidMoves()
	{
		//clear previous valid moves and compute new ones based on the latest location
		validMoves.clear();
		
		
		validMoves.add(new PositionNumPair(positionRow + 2,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow + 2,positionCol - 1));
		validMoves.add(new PositionNumPair(positionRow - 2,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow - 2,positionCol - 1));
		
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol + 2));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol + 2));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol - 2));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol - 2));
		
		for(int count=validMoves.size()-1;count>=0;count--)
		{
			if(validMoves.get(count).getPosRow()<0|| validMoves.get(count).getPosRow()>5|| validMoves.get(count).getPosCol()<0||validMoves.get(count).getPosCol()>6)
			{
				validMoves.remove(count);
			}
			
		}
	}
	
}

class TheSun extends Piece
{
	
	public TheSun(String color,int positionRow,int positionCol,String thePictureLink,boolean alive)
	{
		super(color,positionRow,positionCol,thePictureLink,alive);
	}
	
	public void setValidMoves()
	{
		validMoves.clear();
		
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow + 1,positionCol -1 ));
		validMoves.add(new PositionNumPair(positionRow,positionCol - 1));
		validMoves.add(new PositionNumPair(positionRow,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol - 1));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol + 1));
		validMoves.add(new PositionNumPair(positionRow - 1,positionCol));
		
		for(int count=validMoves.size()-1;count>=0;count--)
		{
			if(validMoves.get(count).getPosRow()<0|| validMoves.get(count).getPosRow()>5|| validMoves.get(count).getPosCol()<0||validMoves.get(count).getPosCol()>6)
			{
				validMoves.remove(count);
			}
			
		}
	}
		
}

class PositionNumPair
{
	private int posRow;
	private int posCol;
	
	public PositionNumPair(int posRow,int posCol)
	{
		this.posRow=posRow;
		this.posCol=posCol;
	}
	
	public void setPositionNumPair(int posRow,int posCol)
	{
		this.posRow=posRow;
		this.posCol=posCol;
	}
	
	public int getPosRow()
	{
		return posRow;
	}
	
	public int getPosCol()
	{
		return posCol;
	}
	
	public String toString()
	{
		return "The row:"+posRow+" The column:"+posCol;
	}
}

