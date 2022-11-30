public class Solitaer {
	/**
	 * the game board with values 1 (a peg), 2 (empty field), 0 (unused field)
	 */
	private GameBoard board = new GameBoard();

	/**
	 * the solution given as a sequence of board situations
	 */
	private GameBoard[] solution = new GameBoard[32];

	/**
	 * all four possible directions for a move (jump of a peg over another peg)
	 */
	private int[] directions = board.getDirections();

	/**
	 * creates a new solitare instance with empty solution
	 * and initial start position of all pegs
	 */
	public Solitaer() {
		for (int i = 0; i < solution.length; i++) {
			solution[i] = new GameBoard();
		}
	}

	/**
	 * Starts the backtracking algorithm and prints out the solution
	 * as the sequence of all resulting intermediate board situation
	 */
	public static void main(String[] args) {
		Solitaer solitaer = new Solitaer();

		long t1 = System.currentTimeMillis();
		if (solitaer.findSolution(1)) {
			System.out.println("Solution found in " + (System.currentTimeMillis() - t1) + " [ms]");

			solitaer.printSolution();
		} else {
			System.out.println("No solution found!?");
		}
	}

	/**
	 * Backtracking algorithm to solve the solitare puzzle
	 *
	 * @param move current number of move, first move must be 1
	 */
	public boolean findSolution(int move) {
		for (int x = 0; move <= 31 && x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				for (int direction : directions) {
					if (board.jump(x, y, direction)) {
						board.copyBoard(board, solution[move]);
						if (!(move >= 31 && board.isOccupied(3, 3))) {
							if (findSolution(move + 1)) {
								return true;
							} else {
								board.jumpBack(x, y, direction);
							}
						} else {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	private void printSolution() {
		for (int zug = 0; zug < solution.length; zug++) {
			solution[zug].print();
		}
	}

}
