import java.util.ArrayList;

public class DFS {
	ArrayList<Board> boards_to_expand;
	ArrayList<Board> boards_explored;
	
	DFS(Board initial_board) {
		boards_to_expand = new ArrayList<>();
		boards_explored = new ArrayList<>();
		boards_to_expand.add(initial_board);
	}
	
	public Board run() {
		while(!boards_to_expand.isEmpty()) {
			boolean already_explored = false;
			Board parent = boards_to_expand.get(0);

			for(int n = 0; n < boards_explored.size(); n++) {
				if(parent.compare_board(boards_explored.get(n))) {
					already_explored = true;
					continue;
				}
			}
				if(already_explored) {
					boards_to_expand.remove(0);
					continue;
				}
				
			ArrayList<Board> successors = parent.generate_successors();
			
			for(int i = 0; i < successors.size(); i++) {
				if(successors.get(i).is_final()) {
					return successors.get(i);
				}

				else {
					boards_to_expand.add(0,successors.get(i));
					System.out.println("ola");
				}
			}
				boards_explored.add(boards_to_expand.get(0));
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
