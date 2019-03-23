import java.util.ArrayList;

public abstract class Search {
    protected ArrayList<Board> boards_to_expand;
    protected ArrayList<Board> boards_explored;
    protected long elapsedTime;

    Search(Board initial_board){
        boards_to_expand = new ArrayList<>();
        boards_explored = new ArrayList<>();
        boards_to_expand.add(initial_board);
    }

    public abstract Board run();

    public ArrayList<Board> generate_sequence(Board final_board){
        ArrayList<Board> sequence = new ArrayList<>();
        Board board = final_board;
        while(board != null) {
            sequence.add(0, board);
            board = board.get_parent();
        }
        return sequence;
    }

    public long get_time(){
        return this.elapsedTime;
    }
}
