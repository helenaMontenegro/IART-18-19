
public class Main {

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 5, 1, "MAX");
		board.print();
		MinimaxSearch m = new MinimaxSearch(board);
		while(!board.is_final()) {
			board = m.run();
			m.set_board(board);
			board.print();		
		}
	}
}
