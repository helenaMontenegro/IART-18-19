import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int[][] ini_board= {
				{-2,-2,-2,-2,-2,-2,-2,-2},
				{-2, 6, 6, 6, 0, 7, 0,-2},
				{-2, 0, 0, 0, 0, 7, 0,-2},
				{-2, 1, 1, 3, 0, 0, 0,-1},
				{-2, 4, 4, 3, 0, 0, 9,-2},
				{-2, 5, 0, 3, 0, 0, 9,-2},
    			{-2, 5, 2, 2, 2, 0, 9,-2},
				{-2,-2,-2,-2,-2,-2,-2,-2}};
		Board board = new Board(ini_board);

		
		board.print();
		System.out.println("SUCCESSORS:");
		ArrayList<Board> successors = board.generate_successors();
		for(int i = 0; i < successors.size(); i++) {
			successors.get(i).print();
			System.out.println();
		}
	}
}
