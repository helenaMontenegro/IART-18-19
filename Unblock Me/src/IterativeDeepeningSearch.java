import java.util.ArrayList;
import java.util.Collections;

public class IterativeDeepeningSearch extends Search{
	IterativeDeepeningSearch(Board initial_board) {
		super(initial_board);
	}
	
	public Board run() {
		long start = System.currentTimeMillis();
		int min_depth = 10, max_depth = 100;
		Board root = boards_to_expand.get(0);
		for(; min_depth <= max_depth; min_depth++) {
			while(!boards_to_expand.isEmpty()) {
				boolean already_explored = false;
				Board parent = boards_to_expand.get(0);
				parent.set_depth();
				int depth = parent.get_depth();
				if(depth > min_depth) {
					boards_to_expand.remove(0);
					continue;
				}
				boolean already_explored2 = false;
				for(int n = 0; n < boards_explored.size(); n++) {
					if(parent.compare_board(boards_explored.get(n))) {
						if(boards_explored.get(n).get_depth() > depth) {
							boards_explored.get(n).set_parent(parent.get_parent());
							boards_explored.get(n).set_depth();
							parent = boards_explored.get(n);
							already_explored = false;
							already_explored2 = true;
						}
						else if(!already_explored2)
							already_explored = true;
					}
				}
				if(already_explored) {
					boards_to_expand.remove(0);
					continue;
				}
				
				ArrayList<Board> successors = parent.generate_successors();
				for(int n = 0; n < boards_explored.size(); n++) {
					boards_explored.get(n).set_depth();
				}
				if(!boards_explored.contains(boards_to_expand.get(0))) {
					boards_explored.add(0, boards_to_expand.get(0));
					Collections.sort(boards_explored);
				}
				boards_to_expand.remove(0);
				for(int i = 0; i < successors.size(); i++) {
					if(successors.get(i).is_final()) {
						super.elapsedTime = System.currentTimeMillis() - start;
						return successors.get(i);
					}
					boards_to_expand.add(0, successors.get(i));
				}
			}
			boards_explored.clear();
			boards_to_expand.clear();
			boards_to_expand.add(root);
		}
		super.elapsedTime = System.currentTimeMillis() - start;
		return null;
	}
	/*
	public Board run() {
		long start = System.currentTimeMillis();
		int min_depth = 10, max_depth = 200;
		Board root = boards_to_expand.get(0);
		for(; min_depth <= max_depth; min_depth++) {
			while(!boards_to_expand.isEmpty()) {
				boolean already_explored = false;
				Board parent = boards_to_expand.get(0);
				int depth = parent.get_depth();
				if(depth >= min_depth) {
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
					already_explored = false;
					for(int n = 0; n < boards_explored.size(); n++) {
						if(successors.get(i).compare_board(boards_explored.get(n)) && boards_explored.get(n).get_depth() <= depth+1)
							already_explored = true;
					}
					if(!already_explored)
						boards_to_expand.add(0, successors.get(i));
				}
			}
			boards_explored.clear();
			boards_to_expand.clear();
			boards_to_expand.add(root);
		}
		super.elapsedTime = System.currentTimeMillis() - start;
		return null;
	}*/
}
