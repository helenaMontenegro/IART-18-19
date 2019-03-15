import java.util.ArrayList;

public class BFS {
	ArrayList<Board> boards_to_expand;
	
	BFS(Board initial_board) {
		boards_to_expand = new ArrayList<>();
		boards_to_expand.add(initial_board);
	}
	
	public Board run() {
		while(!boards_to_expand.isEmpty()) {
			Board parent = boards_to_expand.get(0);
			ArrayList<Board> successors = parent.generate_successors();
			for(int i = 0; i < successors.size(); i++) {
				if(successors.get(i).is_final()) {
					return successors.get(i);
				}
			}
			boards_to_expand.addAll(successors);
			boards_to_expand.remove(0);
		}
		return null;
	}
	
	public ArrayList<Board> generate_sequence(Board final_board) {
		ArrayList<Board> sequence = new ArrayList<>();
		Board board = final_board;
		while(board != null) {
			sequence.add(0, board);
			board = board.get_parent();
		}
		return sequence;
	}
}
