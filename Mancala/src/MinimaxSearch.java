
public class MinimaxSearch {
	private int depth;
	private Board board;
	
	public MinimaxSearch(int depth, Board board) {
		this.depth = depth;
		this.board = board;
	}
	
	/*public Board run() {
		Board next_board;
		this.board.generate_successors();
		this.board.calculate_value();
		
		return next_board;
	}*/
}
