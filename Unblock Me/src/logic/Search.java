package logic;

import java.util.ArrayList;

/**
 * Abstract class that holds the elements needed to run the search algorithms, such as the list of board to expand, boards
 * already explored, number of boards visited and the time it took to run the algorithm.
 */
public abstract class Search {
    protected ArrayList<Board> boards_to_expand;
    protected ArrayList<Board> boards_explored;
    protected int num_boards_visited;
    protected long elapsedTime;

    /**
     * Constructor of this class.
     * @param initial_board
     */
    Search(Board initial_board){
        boards_to_expand = new ArrayList<>();
        boards_explored = new ArrayList<>();
        num_boards_visited = 0;
        boards_to_expand.add(initial_board);
    }

    /**
     * Function that runs the algorithm.
     * @return
     */
    public abstract Board run();
    
    /**
     * Function to get list of explored boards.
     * @return
     */
    public ArrayList<Board> get_boards_explored() {
    	return this.boards_explored;
    }
    
    /**
     * Function to get number of boards visited.
     * @return
     */
    public int get_num_boards_visited() {
    	return num_boards_visited;
    }

    /**
     * Function to get the sequence of boards that originated the board received as argument.
     * @param final_board
     * @return
     */
    public ArrayList<Board> generate_sequence(Board final_board){
        ArrayList<Board> sequence = new ArrayList<>();
        Board board = final_board;
        while(board != null) {
            sequence.add(0, board);
            board = board.get_parent();
        }
        return sequence;
    }
    
    /**
     * Function that gets the time it took to run the algorithm.
     * @return
     */
    public long get_time(){
        return this.elapsedTime;
    }
}
