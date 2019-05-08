
public class MinimaxSearch {
	private int depth;
	private Board board;
	
	public MinimaxSearch(Board board, int depth) {
		this.board = board;
		this.depth = depth;
		this.board = this.run();
	}
	
	public Board run() {
		Board next_board;
		this.board.generate_successors();
		next_board = this.board.get_best_board();
		next_board.print();
		
		return next_board;
	}
}
