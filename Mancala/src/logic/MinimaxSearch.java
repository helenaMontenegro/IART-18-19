package logic;

public class MinimaxSearch {
	private int depth1;
	private int depth2;
	private Board board;
	private long time;
	private boolean alpha_beta;
	
	public MinimaxSearch(Board board, int depth1, int depth2, boolean alpha_beta) {
		this.board = board;
		this.depth1 = depth1;
		this.depth2 = depth2;
		this.alpha_beta = alpha_beta;
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
	
	public long get_time() {
		return this.time;
	}
	
	public Board run() {
		long start_time = System.currentTimeMillis();
		Board next_board = null;
		this.board.generate_successors();
		int value = 0;
		if(alpha_beta)
			value = this.board.get_best_board_alpha_beta(Integer.MIN_VALUE, Integer.MAX_VALUE);
		else
			value = this.board.get_best_board();
		for(int i = 0; i < this.board.get_successors().size(); i++) {
			if(this.board.get_successors().get(i).get_value() == value) {
				next_board = this.board.get_successors().get(i);
				break;
			}
		}
		time = System.currentTimeMillis() - start_time;
		return next_board;
	}
}
