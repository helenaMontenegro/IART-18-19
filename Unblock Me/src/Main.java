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
		String type_of_search = "a_star"; //so necessaria na greedy e na a_star, para o compareTo do board
		Board board = new Board(ini_board, null, 0, 0, type_of_search);

		//BFS search = new BFS(board);
		//DFS search = new DFS(board);
		AStarSearch search = new AStarSearch(board);
		Board final_board = search.run();
		if(final_board == null) {
			System.err.println("Final board not found!");
			System.exit(-1);
		}
		ArrayList<Board> sequence = search.generate_sequence(final_board);
		for(int i = 0; i < sequence.size(); i++) {
			sequence.get(i).print();
			System.out.println(sequence.get(i).get_g());
			System.out.println(sequence.get(i).get_h());
		}

		System.out.println("Number of moves: " + sequence.size());
		System.out.println("Time needed: " + search.get_time()/1000F + " seconds");
	}
}
