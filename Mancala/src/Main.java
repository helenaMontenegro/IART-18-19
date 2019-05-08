
public class Main {

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 7, 1, "MAX", 1);
		board.print();
		MinimaxSearch m = new MinimaxSearch(board);
		while(!board.is_final()) {
			board = m.run();
			m.set_board(board);
			board.print();
		}
		board.print_result();
	}
}
