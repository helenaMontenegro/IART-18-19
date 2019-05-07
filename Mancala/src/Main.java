
public class Main {

	public static void main(String[] args) {
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 1, 1, "MAX");
		board.print();
		Board b2 = board.movement(0);
		b2.print();
		Board b_final = new Board(new int[][] {{0,0,0,0,0,0},{0,0,1,2,0,0}}, 1, 1, "MAX");
		if(b_final.is_final()) {
			b_final.print_result();
		}
	}
}
