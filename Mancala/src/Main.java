
public class Main {

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{4,0,0,0,0,4},{4,4,1,4,3,4}}, 8, 1, "MAX");
		board.print();
		MinimaxSearch m = new MinimaxSearch(board, 3);
		
	}
}
