import java.util.Objects;

public class Puzzle {
	// solution storage
	private final char[][][] solution = new char[32][7][7];
	// 2-d array for the board
	private final char[][] gameBoard;
	private final int height;
	private final int length;
	private int pegs;
	private final String[] DIRECTION = {"UP", "RIGHT", "BOTTOM", "LEFT"};

	// constructor
	public Puzzle() {
		// init gameBoard with the beginning
		// starting point
		char[][] beginning = {
				{' ', ' ', 'X', 'X', 'X', ' ', ' '},
				{' ', ' ', 'X', 'X', 'X', ' ', ' '},
				{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
				{'X', 'X', 'X', 'O', 'X', 'X', 'X'},
				{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
				{' ', ' ', 'X', 'X', 'X', ' ', ' '},
				{' ', ' ', 'X', 'X', 'X', ' ', ' '}
		};
		this.gameBoard = this.replicate(beginning);
		// store the beginning of the game
		this.solution[0] = this.replicate(this.gameBoard);
		this.height = gameBoard.length;
		this.length = gameBoard[height - 1].length;
		this.pegs = 32;
	}
	// replicate gameBoard
	public char[][] replicate(char[][] gameBoard) {
		// instantiate a 2-d array for the board
		char[][] board = new char[7][7];
		// copy the new board with gameBoard value
//		for (int i = 0; i < 7; i++) {
//			for (int j = 0; j < 7; j++) {
//				board[i][j] = gameBoard[i][j];
//			}
//		}
		for (int i = 0; i < 7; i++) {
			System.arraycopy(gameBoard[i], 0, board[i], 0, 7);
		}
		return board;
	}
	// Backtracking algorithm find solution
	private boolean findSolution(int move) {
		// print the steps
		if (pegs == 1 && gameBoard[3][3] == 'X') {
			for (int i = 0; i < this.solution.length; i++) {
				int step = i + 1;
				System.out.println("Steps " + step + ": ");
				this.displayBoard(this.solution[i]);
			}
			return true;
		} else {
			for (int i = 0; i < length; i++)
				for (int j = 0; j < height; j++)
					for (String s : DIRECTION) {
						int[][] step = setStep(j, i, s);
						if (validStep(step)) {
							move(step);
							this.solution[move] = replicate((this.gameBoard));
							// recursive call
							if (findSolution(move + 1)) {
								return true;
							}
							// backTracking
							backTrack(step);
						}
					}
			return false;
		}
	}
	// setStep method
	private int[][] setStep(int row, int col, String direction) {
		int[][] step = new int[3][2];
		step[0][0] = row;
		step[0][1] = col;
		switch (direction) {
			case "UP" -> {
				step[1][0] = row + 1;
				step[1][1] = col;
				step[2][0] = row + 2;
				step[2][1] = col;
			}
			case "LEFT" -> {
				step[1][0] = row;
				step[1][1] = col - 1;
				step[2][0] = row;
				step[2][1] = col - 2;
			}
			case "RIGHT" -> {
				step[1][0] = row;
				step[1][1] = col + 1;
				step[2][0] = row;
				step[2][1] = col + 2;
			}
			case "BOTTOM" -> {
				step[1][0] = row - 1;
				step[1][1] = col;
				step[2][0] = row - 2;
				step[2][1] = col;
			}
		}
		return step;
//		int[][] step = new int[3][2];
//		step[0][0] = row;
//		step[0][1] = col;
//		if(Objects.equals(direction, "UP")){
//
//			step[1][0] = row + 1;
//			step[1][1] = col;
//			step[2][0] = row + 2;
//			step[2][1] = col;
//		}
//		if(Objects.equals(direction, "LEFT")){
//			step[1][0] = row;
//			step[1][1] = col - 1;
//			step[2][0] = row;
//			step[2][1] = col - 2;
//		}
//		if(Objects.equals(direction, "RIGHT")) {
//			step[1][0] = row;
//			step[1][1] = col + 1;
//			step[2][0] = row;
//			step[2][1] = col + 2;
//		}
//		if(Objects.equals(direction, "BOTTOM")) {
//			step[1][0] = row - 1;
//			step[1][1] = col;
//			step[2][0] = row - 2;
//			step[2][1] = col;
//		}
//		return step;
	}
	// move
	private void move (int[][] move) {
		gameBoard[move[0][0]][move[0][1]] = 'O';
		gameBoard[move[1][0]][move[1][1]] = 'O';
		gameBoard[move[2][0]][move[2][1]] = 'X';
		pegs--;
	}
	// backTracking
	private void backTrack (int[][] move) {
		gameBoard[move[0][0]][move[0][1]] = 'X';
		gameBoard[move[1][0]][move[1][1]] = 'X';
		gameBoard[move[2][0]][move[2][1]] = 'O';
		pegs++;
	}
	// display the board
	private void displayBoard(char[][] gameBoard) {
		//print the board
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(gameBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	// print the current situation of the board
	private void print() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(this.gameBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	// check if the move is valid
	private boolean validStep(int[][] move) {
		// index out of bound
		if (move[2][0] >= 7 || move[2][1] >= 7 || move[2][0] < 0 || move[2][1] < 0)
			return false;
		// if no space, no pegs
		return (gameBoard[move[0][0]][move[0][1]] == 'X')
				&& (gameBoard[move[1][0]][move[1][1]] == 'X')
				&& (gameBoard[move[2][0]][move[2][1]] == 'O');
	}
	public void start() {
		// start the game
		this.print();
		System.out.println();
		if (!findSolution(1)) {
			System.out.println("No Solution Found!");
		}
	}
}
