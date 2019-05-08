import java.util.ArrayList;

/**
 * Class Board.
 * @param board - the board of the game.
 * @param mancala - the player's respective mancalas.
 * @param value - the value of the board, used for the minimax algorithm.
 * @param num_levels - number of levels to be checked by the algorithm.
 * @param players_turn - the player that performs the next movement.
 * @param minimax_keyword - used to calculate value for minimax algorithm.
 */
public class Board {
	private int[][] board;
	private int[] mancala;
	private int value;
	private int num_levels;
	private int players_turn;
	private String minimax_keyword;
	/**
	 * 
	 * @param board
	 */
	Board(int[][] board, int num_levels, int players_turn, String keyword) {
		this.num_levels = num_levels;
		this.players_turn = players_turn;
		this.board = board;
		this.mancala = new int[]{0,0};
		this.minimax_keyword = keyword;
	}
	public int[][] get_board() {
		return board;
	}
	public void set_players_turn(int player) {
		this.players_turn = player;
	}
	/**
	 * Function that defines a movement.
	 * @param cell - receives cell as input and distributes its pieces between the following cells. cell in 0..5.
	 * @return - resulting board.
	 */
	public Board movement(int cell) {
		int player_area = this.players_turn - 1;
		int[][] new_board = copy(this.board);
		int to_distribute = new_board[player_area][cell];
		int next_player = 1;
		if(this.players_turn == 1)
			next_player = 2;
		String keyword = "MAX";
		if(this.minimax_keyword.equals("MAX"))
			keyword = "MIN";
		Board new_b = new Board(new_board, this.num_levels-1, next_player, keyword);
		new_board[player_area][cell] = 0;
		while(to_distribute > 0) {
			if(player_area == 0) { //in first line - counter clock movement -> cell--
				if(cell != 0) {
					cell--;
					to_distribute--;
					new_board[player_area][cell] = new_board[player_area][cell] + 1;
					//if last piece is put on empty cell on players side, he gets the piece plus the opposing cells pieces
					if(to_distribute == 0 && player_area == this.players_turn-1 && new_board[player_area][cell] == 1) {
						int other_player_area = 0;
						if(player_area == 0)
							other_player_area = 1;
						new_b.increase_mancala(this.players_turn, new_board[other_player_area][cell]+1);
						new_board[player_area][cell] = 0;
						new_board[other_player_area][cell] = 0;
					}
				} else { //last cell of first line - next is mancala
					if(player_area == this.players_turn-1) { //put a piece on players mancala if player is the one making the move
						to_distribute--;
						new_b.increase_mancala(this.players_turn, 1);
						if(to_distribute == 0) {
							new_b.set_players_turn(this.players_turn);
						}
					}
					cell--;
					player_area = 1;
				}
			} else { //in second line - counter clock movement -> cell++
				if(cell != 5) {
					cell++;
					to_distribute--;
					new_board[player_area][cell] = new_board[player_area][cell] + 1;
					//if last piece is put on empty cell on players side, he gets the piece plus the opposing cells pieces
					if(to_distribute == 0 && player_area == this.players_turn-1 && new_board[player_area][cell] == 1) {
						int other_player_area = 0;
						if(player_area == 0)
							other_player_area = 1;
						new_b.increase_mancala(this.players_turn, new_board[other_player_area][cell]+1);
						new_board[player_area][cell] = 0;
						new_board[other_player_area][cell] = 0;
					}
				} else { //last cell of second line - next is mancala
					if(player_area == this.players_turn-1) { //put a piece on players mancala if player is the one making the move
						to_distribute--;
						new_b.increase_mancala(this.players_turn, 1);
						if(to_distribute == 0) {
							new_b.set_players_turn(this.players_turn);
						}
					}
					cell++;
					player_area = 0;
				}
			}
		}
		return new_b;
	}
	
	public boolean is_final() {
		boolean is_final = false;
		int[] sum = new int[] {0,0};
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				sum[i]+=this.board[i][j];
			}
		}
		if(sum[0] == 0) {
			is_final = true;
			this.increase_mancala(2, sum[1]);
		} else if(sum[1] == 0){
			is_final = true;
			this.increase_mancala(1, sum[0]);
		}
		return is_final;
	}
	
	public void increase_mancala(int player, int num) {
		this.mancala[player-1] += num;
	}
	/*********************Possible Minimax Functions************************/
	
	public ArrayList<Board> generate_successors() {
		if(this.num_levels == 0)
			return null;
		ArrayList<Board> successors = new ArrayList<Board>();
		for(int i = 0; i < 5; i++) {
			Board new_board = this.movement(i);
			new_board.print();
			System.out.println("----------");
			new_board.generate_successors();
			successors.add(new_board);
		}
		return successors;
	}
	
	private void calculate_value() {
		//TODO after generating successors -> calculating value of the board using minimax algorithm.
	}
	
	/*****************End of Possible Minimax Functions*********************/
	
	private int[][] copy(int[][] b) {
		int[][] new_board = new int[2][6];
		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; j++) {
				new_board[i][j] = b[i][j];
			}
		}
		return new_board;
	}
	
	public void print() {
		System.out.println("                             -------------------------");
		for(int i = 0; i < this.board.length; i++) {
			System.out.print("Player " + (i+1) + "      ");
			if(i == 0) {
				System.out.print("  Mancala: " + this.mancala[i] + "   | ");
			} else {
				System.out.print("               | ");
			}
			for(int j = 0; j < this.board[i].length; j++) {
				System.out.print(this.board[i][j] + " | ");
			}
			if(i == 1)
				System.out.println("  Mancala: " + this.mancala[i]);
			else System.out.println();
			System.out.println("                             -------------------------");
		}
		System.out.println("Next player: " + this.players_turn + "\n\n");
	}
	
	public void print_result() {
		System.out.println("End of Game\n");
		System.out.println(" Player 1's mancala: " + this.mancala[0]);
		System.out.println(" Player 2's mancala: " + this.mancala[1] + "\n");
		if(this.mancala[1] > this.mancala[0]) {
			System.out.println("Player 2 wins!");
		} else if(this.mancala[0] > this.mancala[1]) {
			System.out.println("Player 1 wins!");
		} else {
			System.out.println("The players tied!");
		}
	}
}
