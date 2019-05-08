
public class MinimaxSearch {
	private int depth;
	private Board board;
	
	public MinimaxSearch(Board board) {
		this.board = board;
		this.depth = this.board.get_num_levels();
	}
	
	public void set_board(Board board) {
		this.board = board;
		this.board.set_num_levels(this.depth);
		this.board.set_successors(null);
		this.board.set_value(-1);
		this.board.set_keyword("MAX");
	}
	
	public Board run() {
		Board next_board;
		this.board.generate_successors();
		next_board = this.board.get_best_board();
		
		return next_board;
	}
}
