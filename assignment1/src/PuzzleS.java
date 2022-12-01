public class PuzzleS {
	private char[][][] solution = new char[32][7][7];//the array to store solution

	public static void main(String[] args) {
		new PuzzleS().run();
	}
	private char[][] board;
	private char[][] start =
			{
					{' ', ' ', 'X', 'X', 'X', ' ', ' '},
					{' ', ' ', 'X', 'X', 'X', ' ', ' '},
					{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
					{'X', 'X', 'X', 'O', 'X', 'X', 'X'},
					{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
					{' ', ' ', 'X', 'X', 'X', ' ', ' '},
					{' ', ' ', 'X', 'X', 'X', ' ', ' '}
			};
	private int height;
	private int length;
	private int pegs;
	private final String[] DIRECTIONS = {"UP", "RIGHT", "DOWN", "LEFT"};


	public PuzzleS() {
		this.board = this.copy(this.start);//initialize the board with a start board
		this.solution[0] = this.copy(this.board);//copy and store the start board
		this.height = board.length;
		this.length = board[height-1].length;
		this.pegs = 32;//pegs = 32
	}
	private void run() {
		System.out.println("The game board:");
		this.print();//print the start board
		System.out.println();
		System.out.println("Calculating...... \n");

		if (!findSolution(1))
			System.out.println("\nSolution was not found!");

	}

	public char[][] copy(char[][] board){
		char[][] newBoard = new char[7][7];
		for (int j = 0; j < 7; j ++) {
			for (int k = 0; k < 7; k ++){
				newBoard[j][k] = board[j][k];
			}
		}
		return newBoard;
	}
	private boolean findSolution(int move) {

		if (pegs == 1 && board[3][3] == 'X') {
			//if find the solution
			//output the steps
			System.out.println("The solution:");
			for (int i = 0; i < this.solution.length; i ++) {
				System.out.printf("Step%d: \n", i);
				this.print(this.solution[i]);
			}
			return true;
		} else {
			for (int i = 0; i < length; i++)
				for (int j = 0; j < height; j++)
					for (int k = 0; k < DIRECTIONS.length; k++) {
						int[][] mov = setMove(j, i, DIRECTIONS[k]);
						if (legalMove(mov)) {
							makeMove(mov);


							this.solution[move] = copy(this.board);
							if (findSolution(move + 1)) {//recursion
								return true;
							}

							moveBack(mov);//backTrack
						}
					}
			return false;
		}
	}

	private int[][] setMove(int row, int col, String direction) {
		//make move
		int[][] move = new int[3][2];
		move[0][0] = row;
		move[0][1] = col;


		if(direction == "UP"){

			move[1][0] = row+1;
			move[1][1] = col;
			move[2][0] = row+2;
			move[2][1] = col;
		}

		if(direction == "LEFT"){
			move[1][0] = row;
			move[1][1] = col-1;
			move[2][0] = row;
			move[2][1] = col-2;
		}

		if(direction == "RIGHT") {
			move[1][0] = row;
			move[1][1] = col+1;
			move[2][0] = row;
			move[2][1] = col+2;
		}

		if(direction == "DOWN") {
			move[1][0] = row-1;
			move[1][1] = col;
			move[2][0] = row-2;
			move[2][1] = col;
		}

		return move;
	}

	private void makeMove(int[][] move) {
		board[move[0][0]][move[0][1]] = 'O';
		board[move[1][0]][move[1][1]] = 'O';
		board[move[2][0]][move[2][1]] = 'X';

		pegs--;
	}

	private void moveBack (int[][] move) {
		board[move[0][0]][move[0][1]] = 'X';
		board[move[1][0]][move[1][1]] = 'X';
		board[move[2][0]][move[2][1]] = 'O';

		pegs++;
	}

	private boolean legalMove(int[][] move) {

		if (move[2][0] >= 7 || move[2][1] >= 7 || move[2][0] < 0 || move[2][1] < 0)//if out of bound
			return false;

		return 	(board[move[0][0]][move[0][1]] == 'X') &&
				(board[move[1][0]][move[1][1]] == 'X') &&//if there is a peg between the source and target
				(board[move[2][0]][move[2][1]] == 'O') ;//if there is a space
	}
	private void print() {
		//print the current situation of board
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(this.board[i][j] + " ");
			}

			System.out.println();
		}

		System.out.println();

	}

	private void print(char[][] board) {
		//print the board
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(board[i][j] + " ");
			}

			System.out.println();
		}

		System.out.println();

	}

}