package logic;

import logic.Board;
import logic.Search;

import java.util.ArrayList;

/**
 * Class for the depth first search algorithm.
 */
public class DFS extends Search {

	/**
	 * Constructor of the class.
	 * @param initial_board
	 */
	public DFS(Board initial_board) {
	super(initial_board);
	}
	/**
	 * Function that runs the algorithm which expands the first element of the boards to expand and, if it doesn't
	 * find the solution, saves the expansions at the beginning of the list of boards to expand. It ignores the first element
	 * of the list if it has alredy been explored.
	 */
	public Board run() {
		long start = System.currentTimeMillis();
		while(!boards_to_expand.isEmpty()) {
			num_boards_visited++;
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

			boards_explored.add(boards_to_expand.get(0));
			boards_to_expand.remove(0);
			
			for(int i = 0; i < successors.size(); i++) {
				if(successors.get(i).is_final()) {
					super.elapsedTime = System.currentTimeMillis() - start;
					return successors.get(i);
				}

				else {
					boards_to_expand.add(0,successors.get(i));
				}
			}
		}
		super.elapsedTime = System.currentTimeMillis() - start;
		return null;
	}

}
