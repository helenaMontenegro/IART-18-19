import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int[][] ini_board= {
				{-2,-2,-2,-2,-2,-2,-2,-2},
				{-2, 6, 6, 6, 0, 0, 9,-2},
				{-2, 0, 0, 3, 0, 0, 9,-2},
				{-2, 1, 1, 3, 0, 0, 9,-1},
				{-2, 5, 0, 3, 0, 4, 4,-2},
				{-2, 5, 0, 0, 0, 7, 0,-2},
    			{-2, 2, 2, 2, 0, 7, 0,-2},
				{-2,-2,-2,-2,-2,-2,-2,-2}};
		Board board = new Board(ini_board, null);

		DFS dfs = new DFS(board);
		System.out.println("Number of moves: ");
		Board final_board = dfs.run();
		System.out.println("Number of moves: ");
		ArrayList<Board> sequence = dfs.generate_sequence(final_board);
		for(int i = 0; i < sequence.size(); i++) {
			sequence.get(i).print();
		}

		System.out.println("Number of moves: " + sequence.size());
	}
}
