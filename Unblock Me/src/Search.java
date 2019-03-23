import java.util.ArrayList;

public interface Search {

    public Board run();

    public ArrayList<Board> generate_sequence(Board final_board);

    public long get_time();
}
