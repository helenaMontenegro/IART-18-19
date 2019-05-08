
public class Main {

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 2, 1, "MAX");
		board.print();
		board.generate_successors();
	}
}
