
public class MinimaxSearch {
	private int depth1;
	private int depth2;
	private Board board;
	
	public MinimaxSearch(Board board, int depth1, int depth2) {
		this.board = board;
		this.depth1 = depth1;
		this.depth2 = depth2;
	}
	
	public void set_board(Board board) {
		this.board = board;
		if(this.board.get_players_turn() == 1)
			this.board.set_num_levels(this.depth1);
		else
			this.board.set_num_levels(this.depth2);
		this.board.set_successors(null);
		this.board.set_keyword("MAX");
		this.board.set_value(-1);
		this.board.set_playing(this.board.get_players_turn());
	}
	
	public Board run() {
		Board next_board;
		this.board.generate_successors();
		next_board = this.board.get_best_board();
		return next_board;
	}
}
