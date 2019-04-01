package logic;

import logic.Search;

import java.util.ArrayList;
import java.util.Collections;

public class GreedySearch extends Search {

    public GreedySearch(Board initial_board) {
       super(initial_board);
    }

    public Board run() {
        long start = System.currentTimeMillis();
        while(!boards_to_expand.isEmpty()) {
        	num_boards_visited++;
            boolean already_explored = false;
            Collections.sort(boards_to_expand);
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
                    this.elapsedTime = System.currentTimeMillis() - start;
                    return successors.get(i);
                }
            }
            boards_to_expand.addAll(successors);
            boards_explored.add(boards_to_expand.get(0));
            boards_to_expand.remove(0);
        }
        super.elapsedTime = System.currentTimeMillis() - start;
        return null;
    }

}
