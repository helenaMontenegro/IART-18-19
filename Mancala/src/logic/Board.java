package logic;
import java.util.ArrayList;

/**
 * Class Board
 *
 * @param board - the board of the game.
 * @param mancala - the player's respective mancalas.
 * @param value - the value of the board, used for the minimax algorithm.
 * @param num_levels - number of levels to be checked by the algorithm.
 * @param players_turn - the player that performs the next movement.
 * @param minimax_keyword - used to calculate value for minimax algorithm.
 * @param playing - player currently playing, used to calculate value in regards to the player of the original board
 * and not to the player's turn during the minimax algorithm.
 */
public class Board {
    private int[][] board;
    public int[] mancala;
    private int value;
    private int num_levels;
    private int players_turn;
    private int playing;
    private int heuristic;
    private String minimax_keyword;
    public ArrayList<Board> successors;

    public Board(int[][] board, int num_levels, int players_turn, String keyword, int playing, int heuristic) {
        this.num_levels = num_levels;
        this.players_turn = players_turn;
        this.board = board;
        this.mancala = new int[]{0, 0};
        this.playing = playing;
        this.minimax_keyword = keyword;
        this.value = -1;
        this.heuristic = heuristic;
    }

    public void set_num_levels(int num_levels) {
    	this.num_levels = num_levels;
    }
    
    public void set_mancala(int m0, int m1) {
    	this.mancala[0] = m0;
    	this.mancala[1] = m1;
    }
    
    public void set_successors(ArrayList<Board> s) {
    	this.successors = s;
    }
    
    public void set_value(int v) {
    	this.value = v;
    }
    
    public void set_playing(int playing) {
    	this.playing = playing;
    }
    
    public void set_keyword(String key) {
    	this.minimax_keyword = key;
    }
    
    public ArrayList<Board> get_successors() {
    	return this.successors;
    }
    
    public int get_players_turn() {
    	return this.players_turn;
    }
    
    public int get_num_levels() {
    	return this.num_levels;
    }
    
    public int[][] get_board() {

        return board;
    }

    public int get_value(){
        return value;
    }

    public void set_players_turn(int player){
        this.players_turn = player;
    }

    /**
     * Function that defines a movement.
     *
     * @param cell - receives cell as input and distributes its pieces between the following cells. cell in 0..5.
     * @return - resulting board.
     */
    public Board movement(int cell) {
        int player_area = this.players_turn - 1;
        int[][] new_board = copy(this.board);
        int to_distribute = new_board[player_area][cell];
        int next_player = 1;
        String keyword = "MAX";

        if (this.players_turn == 1)
            next_player = 2;


        if (this.minimax_keyword.equals("MAX"))
            keyword = "MIN";

        Board new_b = new Board(new_board, this.num_levels - 1, next_player, keyword, this.playing, this.heuristic);
        new_b.set_mancala(this.mancala[0], this.mancala[1]);
        new_board[player_area][cell] = 0;

        while (to_distribute > 0) {
            if (player_area == 0) { //in first line - counter clock movement -> cell--
                if (cell != 0) {
                    cell--;
                    to_distribute--;
                    new_board[player_area][cell] = new_board[player_area][cell] + 1;

                    //if last piece is put on empty cell on players side, he gets the piece plus the opposing cells pieces
                    if (to_distribute == 0 && player_area == this.players_turn - 1 && new_board[player_area][cell] == 1) {
                        int other_player_area = 0;
                        if (player_area == 0)
                            other_player_area = 1;

                        new_b.increase_mancala(this.players_turn, new_board[other_player_area][cell] + 1);
                        new_board[player_area][cell] = 0;
                        new_board[other_player_area][cell] = 0;
                    }

                } else { //last cell of first line - next is mancala
                    if (player_area == this.players_turn - 1) { //put a piece on players mancala if player is the one making the move
                        to_distribute--;
                        new_b.increase_mancala(this.players_turn, 1);

                        if (to_distribute == 0) {
                            new_b.set_players_turn(this.players_turn);
                            new_b.set_keyword(this.minimax_keyword);
                        }
                    }
                    cell--;
                    player_area = 1;
                }

            } else { //in second line - counter clock movement -> cell++

                if (cell != 5) {
                    cell++;
                    to_distribute--;
                    new_board[player_area][cell] = new_board[player_area][cell] + 1;

                    //if last piece is put on empty cell on players side, he gets the piece plus the opposing cells pieces
                    if (to_distribute == 0 && player_area == this.players_turn - 1 && new_board[player_area][cell] == 1) {
                        int other_player_area = 0;

                        if (player_area == 0)
                            other_player_area = 1;

                        new_b.increase_mancala(this.players_turn, new_board[other_player_area][cell] + 1);
                        new_board[player_area][cell] = 0;
                        new_board[other_player_area][cell] = 0;
                    }

                } else { //last cell of second line - next is mancala

                    if (player_area == this.players_turn - 1) { //put a piece on players mancala if player is the one making the move
                        to_distribute--;
                        new_b.increase_mancala(this.players_turn, 1);

                        if (to_distribute == 0) {
                            new_b.set_players_turn(this.players_turn);
                            new_b.set_keyword(this.minimax_keyword);
                        }
                    }

                    cell++;
                    player_area = 0;
                }
            }
        }
        return new_b;
    }

    public boolean check_empty(int cell) {
        if (this.board[this.players_turn-1][cell] == 0)
            return true;
        return false;
    }

    public boolean is_final() {
        boolean is_final = false;
        int[] sum = new int[]{0, 0};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                sum[i] += this.board[i][j];
            }
        }
        if (sum[0] == 0 || sum[1] == 0)
            is_final = true;
        return is_final;
    }
    
    public void set_final() {
        this.increase_mancala(1, sum_board(0));
        this.increase_mancala(2, sum_board(1));
    }

    public void increase_mancala(int player, int num) {
        this.mancala[player - 1] += num;
    }

    /*********************Minimax Functions************************/

    public void generate_successors() {
		if(this.num_levels == 0) {
			this.calculate_value();
			return;
		}
		this.successors = new ArrayList<Board>();
		for(int i = 0; i < 6; i++) {
			if(this.board[this.players_turn-1][i] == 0)
				continue;
			Board new_board = this.movement(i);
			new_board.generate_successors();
			this.successors.add(new_board);
		}
	}
    
    public int get_best_board() {
    	if(this.num_levels == 0 || this.is_final()) {
    		this.calculate_value();
    		return this.value;
    	}
    	//MAX
    	if(this.minimax_keyword.equals("MAX")) {
        	this.value = Integer.MIN_VALUE;
    		for(int i = 0; i < this.successors.size(); i++) {
    			int v = this.successors.get(i).get_best_board();
    			if(v > this.value)
    				this.value = v;
    		}
    	} else { //MIN
	    	this.value = Integer.MAX_VALUE;
	    	for(int i = 0; i < this.successors.size(); i++) {
	    		int v = this.successors.get(i).get_best_board();
	    		if(v < this.value)
	    			this.value = v;
	    	}
    	}
		return this.value;
    }
    
    public int get_best_board_alpha_beta(int alpha, int beta) {
    	if(this.num_levels == 0 || this.is_final()) {
    		this.calculate_value();
    		return this.value;
    	}
    	//alpha-beta pruning in MAX
    	if(this.minimax_keyword.equals("MAX")) {
    		for(int i = 0; i < this.successors.size(); i++) {
    			int v = this.successors.get(i).get_best_board_alpha_beta(alpha, beta);
    			if(v > alpha)
    				alpha = v;
    			if(alpha >= beta)
    				break;
    		}
    		this.value = alpha;
			return alpha;
    	}
    	//alpha-beta pruning in MIN
    	for(int i = 0; i < this.successors.size(); i++) {
    		int v = this.successors.get(i).get_best_board_alpha_beta(alpha, beta);
    		if(v < beta)
    			beta = v;
    		if(alpha >= beta)
    			break;
    	}
    	this.value = beta;
		return beta;
    }

    // evaluation function: number of game pieces in player area
    public void calculate_value() {
    	this.value = (mancala[this.playing-1] + sum_board(this.playing-1)) * 100 / 48; //para ficar uniformemente entre 0 e 100
    	if(this.is_final() && value > 50)
    		this.value=100;
    	else if(this.is_final() && value < 50)
    		this.value=0;
    	else if(this.heuristic == 2) {
    		int other = 1;
    		if(this.playing == 1) {
    			other = 2;
    		}
    		this.value = mancala[this.playing-1] - mancala[other-1];
    	}
    }

    // sum of player pieces
    private int sum_board(int player){
        int player_area[] = board[player];
        int sum=0;

        for( int i=0; i<player_area.length; i++){
            sum += player_area[i];
        }
        return sum;
    }

    /*****************End of Minimax Functions*********************/

    private int[][] copy(int[][] b) {
        int[][] new_board = new int[2][6];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                new_board[i][j] = b[i][j];
            }
        }
        return new_board;
    }

    public void print() {
        String ident = "";
        if(this.mancala[0] / 10 >= 1) {
            ident = " ";
        }
        System.out.println(ident + "                             -------------------------");
        for (int i = 0; i < this.board.length; i++) {
            System.out.print("Player " + (i + 1) + "      ");
            if (i == 0) {
                System.out.print("  Mancala: " + this.mancala[i] + "   | ");
            } else {
                System.out.print(ident + "               | ");
            }
            for (int j = 0; j < this.board[i].length; j++) {
                System.out.print(this.board[i][j] + " | ");
            }
            if (i == 1)
                System.out.println("  Mancala: " + this.mancala[i]);
            else System.out.println();
            System.out.println(ident + "                             -------------------------");
        }
        System.out.println("Next player: " + this.players_turn + "\n\n");
    }

    public void print_result() {
        System.out.println("End of Game\n");
        System.out.println(" Player 1's mancala: " + this.mancala[0]);
        System.out.println(" Player 2's mancala: " + this.mancala[1] + "\n");
        if (this.mancala[1] > this.mancala[0]) {
            System.out.println("Player 2 wins!");
        } else if (this.mancala[0] > this.mancala[1]) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("The players tied!");
        }
    }
}
