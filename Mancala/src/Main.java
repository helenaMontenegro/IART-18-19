
public class Main {

	public static void main(String[] args) {
		Main m = new Main();
		m.pcVSpc(2, 1);
	}
	
	public int get_depth(int difficulty) {
		if(difficulty == 1)
			return 3;
		if(difficulty == 2)
			return 5;
		return 7;
	}
	
	public void pcVSpc(int pc1_difficulty, int pc2_difficulty) {
		int depth1 = this.get_depth(pc1_difficulty);
		int depth2 = this.get_depth(pc2_difficulty);
		
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, depth1, 1, "MAX", 1);
		board.print();
		MinimaxSearch minimax = new MinimaxSearch(board, depth1, depth2);
		while(!board.is_final()) {
			board = minimax.run();
			minimax.set_board(board);
			board.print();
		}
		board.print_result();				
	}
}
