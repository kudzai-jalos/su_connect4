import java.util.Scanner;
import java.io.File;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.io.FileWriter;

public class SU22655379  {
	// Board size
	static int X = 6;
	static int Y = 7;
	// This variable can be used to turn your debugging output on and off.
	// Preferably use
	static boolean DEBUG = true;
	static int[][] grid;

	public static void main(String[] args) {
		// TODO: Read in a comamnd line argument that states whether the program will be
		// in either terminal #K
		// mode (T) or in GUI mode (G) [Hint: use args and the variable below]
		String mode = args[0];

		// Sets whether the game is in terminal mode or GUI mode
		boolean gui = false;
		if (mode.equals("T"))
			gui = false;
		else {
			if (mode.equals("G"))
				gui = true;
			else {
				StdOut.println("Invalid mode entered");
			}
		}

		int input = 0;
		// The 6-by-7 grid that represents the gameboard, it is initially empty
		grid = new int[Y][X];
		// Shows which player's turn it is, true for player 1 and false for player 2
		boolean player1 = true;
		// Shows the number of special tokens a player has left
		int[] p1powers = { 2, 2, 2 };
		int[] p2powers = { 2, 2, 2 };

		if (!gui) {
			// Terminal mode
			// TODO: Display 10 line title #U
			StdOut.println(	    "****************************************************************************");
			StdOut.println(	    "*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"
					+ "\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"
					+ "\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"
					+ "\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"
					+ "\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"
					+ "\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"
					+ "\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
			StdOut.println(	    "*                                                                          *");
			StdOut.println(	    "****************************************************************************");
			// Array for current player's number of power storage
			int[] curppowers = new int[3];
			int countGames = 0;
			while (true) {
				if (player1) {
					StdOut.println("Player 1's turn (You are Red):");
					curppowers = p1powers;
				} else {
					StdOut.println("Player 2's turn (You are Yellow):");
					curppowers = p2powers;
				}
				StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb (" + curppowers[0]
						+ " left) \n 3. Play Teleportation (" + curppowers[1] + " left) \n 4. Play Colour Changer ("
						+ curppowers[2] + " left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");
				// TODO: Read in chosen move, check that the data is numeric
				input = -1;
					try {
						input = StdIn.readInt();
					} catch (Exception e) {
						
					}
					if ((input < 0) || (input > 7)) {
						StdOut.println("Please choose a valid option");
						player1 = !player1;
					}


				int col = -1;

				switch (input) {
				case 0:
					Exit();
					break;

				case 1:
					StdOut.println("Choose a column to play in:");
					// TODO: Read in chosen column
					// TODO: Check that value is within the given bounds, check that the data is
					// numeric
					// TODO: Play normal disc in chosen column

					col = readColumn();
					if (grid[col][0] != 0){
					  StdOut.println("Column is full.");
					}
					else{
					grid = Play(col, grid, player1);
					}
					break;

				case 2:
					StdOut.println("Choose a column to play in:");
					// TODO: Read in chosen column
					// TODO: Check that value is within the given bounds, check that the data is
					// numeric
					// TODO: Play bomb disc in chosen column and reduce the number of bombs left
					// TODO: Check that player has bomb discs left to play, otherwise print out an
					// error message

					col = readColumn();
					if (curppowers[0]==0){
						StdOut.println("You have no bomb power discs left");
						player1=!player1;
						break;
					}

					if (player1){
						p1powers[0]--;						
					}
					else if (!player1){
						p2powers[0]--;						
					}
					
					if (grid[col][0]==0){
					grid = Bomb(col,grid,player1);
					}
					else{
						StdOut.println("Column is full.");
					}

					break;

				case 3:
					StdOut.println("Choose a column to play in:");
					// TODO: Read in chosen column
					// TODO: Check that value is within the given bounds, check that the data is
					// numeric
					// TODO: Play teleport disc in chosen column and reduce the number of
					// teleporters left
					// TODO: Check that player has teleport discs left to play, otherwise print out
					// an error message
					col = readColumn();
					if (curppowers[1]==0){
						StdOut.println("You have no teleport power discs left");
						player1=!player1;
						break;
					}

					if (player1){
						p1powers[1]--;						
					}
					else if (!player1){
						p2powers[1]--;						
					}

					grid = Teleport(col,grid,player1);

					break;

				case 4:
					StdOut.println("Choose a column to play in:");
					// TODO: Read in chosen column
					// TODO: Check that value is within the given bounds, check that the data is
					// numeric
					// TODO: Play Colour Change disc in chosen column and reduce the number of
					// colour changers left
					// TODO: Check that player has Colour Change discs left to play, otherwise print
					// out an error message
					col = readColumn();
					if (curppowers[2]==0){
						StdOut.println("You have no colour changer power discs left");
						player1=!player1;
						break;
					}

					if (player1){
						p1powers[2]--;						
					}
					else if (!player1){
						p2powers[2]--;						
					}

					grid = Colour_Changer(col,grid,player1);

					break;

				// This part will be used during testing, please DO NOT change it. This will
				// result in loss of marks
				case 5:
					// Displays the current gameboard again, doesn't count as a move, so the player
					// stays the same
					player1 = !player1;
					break;

				// This part will be used during testing, please DO NOT change it. This will
				// result in loss of marks
				case 6:
					grid = Test(StdIn.readString());
					// Reads in a test file and loads the gameboard from it.
					player1 = !player1;
					break;
				// This part will be used during testing, please DO NOT change it. This will
				// result in loss of marks
				case 7:
					Save(StdIn.readString(), grid);
					player1 = !player1;
					break;

				default: // TODO: Invalid choice was made, print out an error message but do not switch
							// player turns
					break;
				}
				
				// Displays the grid after a new move was played
				Display(grid);
				
				// TODO: Checks whether a winning condition was met
				int win = Check_Win(grid);
				if (win == 1) {
					StdOut.println("Player 1 wins!");
				}
				if (win == 2) {
					StdOut.println("Player 2 wins!");
				}
				if (win == -1) {
					StdOut.println("Draw!");
					player1=!player1;
				}
				
				
				if (win != 0 ){
					countGames++;
					if (countGames == 4){
						countGames =0;
						player1 =!player1;
					}
					StdOut.println( "Do you want to play again? \n 1. Yes \n 2. No");
					  input = StdIn.readInt();
					
					if (input ==1){
					   grid = new int[Y][X]; 
					   
					}
					else
					{ 
					   Exit();
					}
				
				}

				// TODO: Switch the players turns
				player1 = !player1;

			}
		} else {
			// Graphics mode
			
			DisplayGUI(grid);
			player1 = true;
			while (true){
				boolean drawn =false;
				while (StdDraw.isMousePressed()){
					if (!(drawn)&& StdDraw.mouseX()<49){
						int col = (int)(StdDraw.mouseX()/7);
						
						if (grid[col][0] != 0){
							StdOut.println("Column is full.");
						}
						else{
							grid = Play(col, grid, player1);
							DisplayGUI(grid);
						}
						
						int win = Check_Win(grid);
						if (win == 1) {
							StdOut.println("Player 1 wins!");
						}
						if (win == 2) {
							StdOut.println("Player 2 wins!");
						}
						if (win == -1) {
							StdOut.println("Draw!");
							player1=!player1;
						}
						
						
						if (win != 0 ){
							return;
						}
						
						
						player1 = !player1;
						drawn= true;
						
					}

				}
				
			}
			
		}

	}
	

	// void mouseClicked(MouseEvent e){
	// 	Play(0,grid,true);
	// 	DisplayGUI(grid);
	// }

	 public static void DisplayGUI(int[][] grid) {
		StdDraw.clear(StdDraw.DARK_GRAY);
			StdDraw.setXscale(0,59);
			StdDraw.setYscale(0,47);
			double[] cx ={49,0.0,0.0,49},cy={0.0,0.0,42,42};
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.filledPolygon(cx,cy);

			double radius = 3.5;
			double x = radius;
			for (int i =0;i<grid.length;i++){
				double y = radius;
				for (int k =grid[i].length-1;k>-1;k--){
					switch (grid[i][k]){
						case 0:StdDraw.setPenColor(StdDraw.GRAY);
								break;
						case 1:StdDraw.setPenColor(StdDraw.RED);
								break;
						case 2:StdDraw.setPenColor(StdDraw.YELLOW);
								break;
					}
					
					StdDraw.filledCircle(x,y,radius); 
					y+=2*radius;
				}
				x+=2*radius;
			}
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(29.5,44.5,"CONNECT4");
 	}
	
	public static int readColumn(){
		int col = -1;
		while ((col < 0) || (col > 6)) {
			try {
				col = StdIn.readInt();
			} catch (Exception e) {							
				}

			if ((col < 0) || (col > 6)) {
				StdOut.println("Illegal move, please input legal move:");
			}
		}
		return col;
	}

	// void mouseClicked(MouseEvent em){
	// 	Play(0,grid,true);
	// 	Display(grid);
	// }
	/**
	 * Displays the grid, empty spots as *, player 1 as R and player 2 as Y. Shows
	 * column and row numbers at the top.
	 * 
	 * @param grid The current board state
	 */
	public static void Display(int[][] grid) {
		// TODO: Display the given board state with empty spots as *, player 1 as R and
		// player 2 as Y. Shows column and row numbers at the top.
		StdOut.print("  ");
		for (int k = 0; k < grid.length; k++) {
			StdOut.print(k + " ");
		}

		StdOut.println();
		for (int k = 0; k < grid[0].length; k++) {
			for (int i = 0; i < grid.length; i++) {
				if (i == 0) {
					StdOut.print(k + " ");
				}

				switch (grid[i][k]) {
				case 0:
					StdOut.print("* ");
					break;
				case 1:
					StdOut.print("R ");
					break;
				case 2:
					StdOut.print("Y ");
					break;
				}
			}
			StdOut.println();
		}
	}

	/**
	 * Exits the current game state
	 */
	public static void Exit() {
		// TODO: Exit the game
		System.exit(0);
	}

	/**
	 * Plays a normal disc in the specified position (i) in the colour of the player
	 * given. Returns the modified grid or prints out an error message if the column
	 * is full.
	 * 
	 * @param i       Column that the disc is going to be played in
	 * @param grid    The current board state
	 * @param player1 The current player
	 * @return grid The modified board state
	 */
	public static int[][] Play(int i, int[][] grid, boolean player1) {
		// TODO: Play a normal disc of the given colour, if the column is full, print
		// out the message: "Column is full."

			for (int k = grid[i].length - 1; k >= 0; k--) {
				if (grid[i][k] == 0) {
					if (player1)
						grid[i][k] = 1;
					else
						grid[i][k] = 2;
					break;
				}
			}
		

		return grid;
	}

	/**
	 * Checks whether a player has 4 tokens in a row or if the game is a draw.
	 * 
	 * @param grid The current board state in a 2D array of integers
	 */
	public static int Check_Win(int[][] grid) {
		int winner = 0;
		// TODO: Check for all the possible win conditions as well as for a possible
		// draw.
		boolean p1win = false,p2win = false;

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[i].length; k++) {
				


				int player = grid[i][k];

				if (player != 0) {
					int count;
					int n, m;

					// Check right :D
					n = k;
					count = 0;
					if (i + 3 < grid.length) {
						for (m = i; m <= i + 3; m++) {
							if (grid[m][n] == player) {
								count++;
							}
						}
					}

					if (count == 4) {
						// Debug("right");
						p1win= player == 1;
						p2win= player==2;
					}

					// Check down :D
					m = i;
					count = 0;
					if (k + 3 < grid[i].length) {
						for (n = k; n <= k + 3; n++) {
							if (grid[m][n] == player) {
								count++;
							}
						}
					}
					if (count == 4) {
						//Debug("down");
						p1win= player == 1;
						p2win= player==2;
					}
					
					//Check down right
					count = 0;
					n = k;
					if (i + 3 < grid.length && k + 3 < grid[i].length) {
					  for (m = i; m <= i + 3; m++){
					      if (grid[m][n] == player) {
						count++;
					      }
					      n++;
					  }
					}
					if (count == 4) {
						//Debug("down right");
						p1win= player == 1;
						p2win= player==2;
					}

					//Check up right
					 n =k;
					if (i + 3 < grid.length && k - 3 > -1) {
					for (m = i; m <= i + 3; m++){
					  if (grid[m][n] == player) {
						count++;
					      }
					      n--;
					
					}
					
					}
					if (count == 4) {
						//Debug("up right");
						p1win= player == 1;
						p2win= player==2;
					}
					
				}

			}
		}
		
		//check draw
		int count = 0;
		if (p1win && p2win){
			return -1;
		}
		if (p1win){
			return 1;
		}
		if (p2win){
			return 2;
		}

		for (int i = 0; i < grid.length; i++) {
			for (int k = 0; k < grid[i].length; k++) {
				if (grid[i][k]!=0) count++;
			}
		}
		
		if (count == grid.length*grid[0].length) {
			return -1;
		}

		return winner;
	}

	/**
	 * Plays a bomb disc that blows up the surrounding tokens and drops down tokens
	 * above it. Should not affect the board state if there's no space in the
	 * column. In that case, print the error message: "Column is full."
	 * 
	 * @param i       Column that the disc is going to be played in
	 * @param grid    The current board state
	 * @param player1 The current player
	 * @return grid The modified board state
	 */
	public static int[][] Bomb(int i, int[][] grid, boolean player1) {
		// TODO: Play a bomb in the given column and make an explosion take place. Discs
		// should drop down afterwards. Should not affect the
		// board state if there's no space in the column. In that case, print the error
		// message: "Column is full."
		// Leaves behind a normal disc of the player's colour
		int k;

		for (k = grid[i].length - 1; k >= 0; k--) {
			if (grid[i][k] == 0) {
				if (player1)
					grid[i][k] = 1;
				else
					grid[i][k] = 2;
				break;
			}
		}
		
		if(k-1 >-1) grid[i][k-1]=0;
		if(k+1 <grid[i].length) grid[i][k+1]=0;
		if(i-1 >-1) grid[i-1][k]=0;
		if(i+1 <grid.length) grid[i+1][k]=0;
		if(k-1 >-1 && i-1>-1) grid[i-1][k-1]=0;
		if(k-1 >-1 && i+1<grid.length) grid[i+1][k-1]=0;
		if(k+1 <grid[i].length && i-1>-1) grid[i-1][k+1]=0;
		if(k+1 <grid[i].length && i+1<grid.length) grid[i+1][k+1]=0;
		
		for (int n=0;n<grid[i].length;n++){
			for (int c = 0;c<grid.length;c++){
				for (int r=0;r<grid[i].length-1;r++){
					if (grid[c][r+1] == 0){
						grid[c][r+1]=grid[c][r];
						grid[c][r]=0;
					}
				}
			}
		}
		return grid;
	}

	/**
	 * Plays a teleporter disc that moves the targeted disc 3 columns to the right.
	 * If this is outside of the board boundaries, it should wrap back around to the
	 * left side. If the column where the targeted disc lands is full, destroy that
	 * disc. If the column where the teleporter disc falls is full, play as normal,
	 * with the teleporter disc replacing the top disc.
	 * 
	 * @param i       Column that the disc is going to be played in
	 * @param grid    The current board state
	 * @param player1 The current player
	 * @return grid The modified board state
	 */
	public static int[][] Teleport(int i, int[][] grid, boolean player1) {
		// TODO: Play a teleporter disc that moves the targeted disc 3 columns to the
		// right. If this is outside of the board
		// boundaries, it should wrap back around to the left side. If the column where
		// the targeted disc lands is full,
		// destroy that disc. If the column where the teleporter disc falls is full,
		// play as normal, with the teleporter
		// disc replacing the top disc.
		// No error message is required.
		// If the colour change disc lands on the bottom row, it must leave a disc of
		// the current player’s colour.
		int k = i;

		for(int m = i;m<4;m++){
			if (k == 6){
				k=-1;
			}
			k++;
		}

		int r;
			for (r = 0;r<grid[i].length;r++){
				if (r-1>-1){
					if (grid[i][r-1]==0&&grid[i][r]!=0){
						if (grid[k][0]==0){
							for (int n = grid[i].length - 1; n >= 0; n--) {
								if (grid[k][n] == 0) {
									grid[k][n] = grid[i][r];
									grid[i][r]=0;
									break;
								}
							}
						}
						else{
							if (grid[i][r]!=0) StdOut.println( "Column is full." );
							grid[i][r]=0;
						}
						break;
					}
				}
			}

		grid = Play(i, grid, player1);

		return grid;
	}

	/**
	 * Plays the colour changer disc that changes the affected disc's colour to the
	 * opposite colour
	 * 
	 * @param i       Column that the disc is going to be played in
	 * @param grid    The current board state
	 * @param player1 The current player
	 * @return grid The modified board state
	 */
	public static int[][] Colour_Changer(int i, int[][] grid, boolean player) {
		// TODO: Colour Change: If the colour change disc lands on top of another disc,
		// it changes the colour of that
		// disc to the opposite colour. The power disc does not remain.
		// If the colour change disc lands on the bottom row, it must leave a disc of
		// the current player’s colour.

		if (grid[i][grid[i].length-1]==0){
			grid = Play(i, grid, player);
		}
		else if (grid[i][0]==0){
			for (int r = 1; r<grid[i].length;r++){
				if (grid[i][r-1]==0&&grid[i][r]!=0){
					if (grid[i][r]==1){
						grid[i][r]=2;
					}
					else grid[i][r]=1;
				}
			}
		}
		else{
			StdOut.println("Column is full.");
		}
		return grid;
	}

	/**
	 * Reads in a board from a text file.
	 * 
	 * @param name The name of the given file
	 * @return
	 */
	// Reads in a game state from a text file, assumes the file is a txt file
	public static int[][] Test(String name) {
		int[][] grid = new int[6][7];
		try {
			File file = new File(name + ".txt");
			Scanner sc = new Scanner(file);

			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					grid[i][j] = sc.nextInt();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	/**
	 * Saves the current game board to a text file.
	 * 
	 * @param name The name of the given file
	 * @param grid The current game board
	 * @return
	 */
	// Used for testing
	public static int[][] Save(String name, int[][] grid) {
		try {
			FileWriter fileWriter = new FileWriter(name + ".txt");
			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					fileWriter.write(Integer.toString(grid[i][j]) + " ");
				}
				fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	/**
	 * Debugging tool, preferably use this since we can then turn off your debugging
	 * output if you forgot to remove it. Only prints out if the DEBUG variable is
	 * set to true.
	 * 
	 * @param line The String you would like to print out.
	 */
	public static void Debug(String line) {
		if (DEBUG)
			System.out.println(line);
	}

}