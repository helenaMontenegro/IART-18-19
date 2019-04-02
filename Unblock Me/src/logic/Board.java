package logic;

import java.util.ArrayList;

/**
 * Class Board which defines the state of a board.
 * Contains the board, the board that originates this one (parent), a list of the blocks the board contains, the functions
 * used in the algorithms of informed search (h and g), the type of search (search), the depth of the board and its successors. 
 */
public class Board implements Comparable<Board> {
	int[][] board;
	Board parent;
	ArrayList<Block> blocks;
	int h; //distance to solution
	int g; //distance already achieved
	String search;
	int depth;
	ArrayList<Board> successors;

	/**
	 * Constructor of the board that takes into account its depth.
	 * @param board
	 * @param parent_board
	 * @param h
	 * @param g
	 * @param search
	 * @param depth
	 */
	public Board(int[][] board, Board parent_board, int h, int g, String search, int depth) {
		this.build_board(board, parent_board, h, g, search);
		this.depth = depth;
	}
	
	/**
	 * Constructor of the board that doesn't take into account its depth, setting it as 0.
	 * @param board
	 * @param parent_board
	 * @param h
	 * @param g
	 * @param search
	 * @param depth
	 */
	public Board(int[][] board, Board parent_board, int h, int g, String search) {
		this.build_board(board, parent_board, h, g, search);
		this.depth = 0;
	}
	
	/**
	 * Function that builds the board, which is called by the constructors.
	 * @param board
	 * @param parent_board
	 * @param h
	 * @param g
	 * @param search
	 */
	private void build_board(int[][] board, Board parent_board, int h, int g, String search) {
		this.board = board;
		this.blocks = new ArrayList<>();
		this.parent = parent_board;
		this.h = h;
		this.g = g;
		this.search = search;
		this.build_blocks();
	}

	/**
	 * Function that expands the board if it's not already expanded.
	 * @return list of expansions
	 */
	public ArrayList<Board> generate_successors() {
		if(this.successors != null) {
			return this.successors;
		}
		this.successors = new ArrayList<>();
		for(int i = 0; i < this.blocks.size(); i++) {
			int line_bef = this.blocks.get(i).get_line();
			int column_bef = this.blocks.get(i).get_column();
			int line_aft = this.blocks.get(i).get_line();
			int column_aft = this.blocks.get(i).get_column();
			int line_bef_empty = this.blocks.get(i).get_line();
			int column_bef_empty = this.blocks.get(i).get_column();
			int line_aft_empty = this.blocks.get(i).get_line();
			int column_aft_empty = this.blocks.get(i).get_column();
			if(this.blocks.get(i).get_direction().equals("horizontal")) {
				column_bef--;
				column_bef_empty += this.blocks.get(i).get_length()-1;
				column_aft += this.blocks.get(i).get_length();
			} else {
				line_bef--;
				line_bef_empty += this.blocks.get(i).get_length()-1;
				line_aft += this.blocks.get(i).get_length();
			}
			//generate board with block moved to cell before
			if(this.board[line_bef][column_bef] == 0) {
				int[][] new_board_bef = this.copy_board();
				new_board_bef[line_bef][column_bef] = this.blocks.get(i).get_id();
				new_board_bef[line_bef_empty][column_bef_empty] = 0;
				Board new_bef = new Board(new_board_bef, this, calculate_h(new_board_bef), this.g+1, this.search, this.depth+1);
				if(this.parent == null || (this.parent != null && !this.parent.compare_board(new_bef)))
					this.successors.add(new_bef);
			} 
			//generate board with block moved to cell after
			if(this.board[line_aft][column_aft] == 0) {
				int[][] new_board_aft = this.copy_board();
				new_board_aft[line_aft][column_aft] = this.blocks.get(i).get_id();
				new_board_aft[line_aft_empty][column_aft_empty] = 0;
				Board new_aft = new Board(new_board_aft, this, calculate_h(new_board_aft), this.g+1, this.search, this.depth+1);
				if(this.parent == null || (this.parent != null && !this.parent.compare_board(new_aft)))
					this.successors.add(new_aft);
			}
		}

		return this.successors;
	}
	
	/**
	 * Function that generates the expansion of the board originating from moving the block received as arguments in the 
	 * direction also received as argument.
	 * @param block_id - id of the block to move
	 * @param direction - direction in which the block should move
	 * @return the resulting expansion
	 */
	public Board generate_specific_successor(int block_id, int direction) {
		Board new_board = null;
		System.out.println(block_id);
		for(int i = 0; i < this.blocks.size(); i++) {
			if(this.blocks.get(i).get_id() == block_id) {
				int new_line = this.blocks.get(i).get_line();
				int new_column = this.blocks.get(i).get_column();
				int old_line = this.blocks.get(i).get_line();
				int old_column = this.blocks.get(i).get_column();
				if(direction == 1 && this.blocks.get(i).get_direction().equals("vertical")) {
					new_line--;
					old_line += this.blocks.get(i).get_length()-1;
				} else if(direction == 1) {
					new_column--; 
					old_column += this.blocks.get(i).get_length()-1;
				} else if(direction == 2 && this.blocks.get(i).get_direction().equals("vertical")) {
					new_line += this.blocks.get(i).get_length();
				} else {
					new_column += this.blocks.get(i).get_length();
				}
				int[][] new_board_matrix = this.copy_board();
				new_board_matrix[new_line][new_column] = this.blocks.get(i).get_id();
				new_board_matrix[old_line][old_column] = 0;
				new_board = new Board(new_board_matrix, this, calculate_h(new_board_matrix), this.g+1, this.search, this.depth+1);
			}
		}
		return new_board;
	}

	/**
	 * Function that analysis the board and builds the existing blocks, putting them in the list of blocks.
	 */
	public void build_blocks() {
		boolean found = false;

		for (int i = 0; i < this.board.length; i++) { // percorre linha
			for (int j = 0; j < this.board[i].length; j++) { //percorre a coluna
				//System.out.println(board[i][j]);

				if (this.board[i][j] > 0) // se for um bloco
				{
					found=false;
					for (int x = 0; x < this.blocks.size(); x++) // verificar se o bloco j� existe na lista
					{
						if (this.board[i][j] == this.blocks.get(x).get_id()) {
							// se existir aumenta o length
							this.blocks.get(x).increase_length();
							found = true;

						}
						
					}
					
					if (found == false) {// n�o encontrou o bloco -> cria um novo
						Block newBlock = new Block(board[i][j], j, i);
						blocks.add(newBlock);
						
					}

				}

			}
		}
	}
	
	/**
	 * This function calculates the distance to the final board, taking into consideration the 
	 * distance between block 1 and the exit and the number of blocks blocking its way. The heuristics available
	 * are the distance of the main block to the exit, the number of blocks blocking the exit, and the sum of both of these.
	 * @param board - board to which the h function is being calculated
	 * @return
	 */
	public int calculate_h(int[][] board) {
		int num_blocks = 0, distance_to_exit = 0;
		boolean found_block = false;
		for(int i = 0; i < board[3].length; i++) {
			if(board[3][i]==1)
				found_block = true;
			else if(!found_block)
				continue;
			else if(found_block && board[3][i] > 1) {
				num_blocks++;
				distance_to_exit++;
			}
			else if(found_block && board[3][i] != 1 && board[3][i] != -1)
				distance_to_exit++;
		}
		if(this.search.equals("a_star_1"))
			return distance_to_exit;
		if(this.search.equals("a_star_2"))
			return num_blocks;
		return num_blocks + distance_to_exit;
	}
	
	/**
	 * Function that checks if the objective is met.
	 * @return true if the board is final or false otherwise
	 */
	public Boolean is_final() {
		if(this.board[3][6] == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Function that returns this board's parent.
	 * @return parent
	 */
	public Board get_parent() {
		return this.parent;
	}
	
	/**
	 * Function that sets this board's parent.
	 * @param parent
	 */
	public void set_parent(Board parent) {
		this.parent = parent;
		this.set_depth();
	}
	
	/**
	 * Function that returns function that defines distance to initial board;
	 * @return
	 */
	public int get_g() {
		return this.g;
	}
	
	/**
	 * Function that returns function that defines distance to final board;
	 * @return
	 */
	public int get_h() {
		return this.h;
	}
	
	/**
	 * Function that allows to make a copy of a board, returning it.
	 * @return board
	 */
	public int[][] copy_board() {
		int[][] new_array = new int[8][8];
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				new_array[i][j] = board[i][j];
			}
		}
		return new_array;
	}
	
	/**
	 * Function to get the Board's blocks.
	 * @return
	 */
	public ArrayList<Block> get_blocks() {
		return this.blocks;
	}
	
	/**
	 * Function that prints the board for the human interface.
	 */
	public void print_for_human() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == -2) {
					if (i == 0 || i == this.board.length - 1) {
						System.out.print("--");
					} else if (j == 0) {
						System.out.print("| ");
					} else if (j == this.board[i].length - 1) {
						System.out.print(" |");
					}
				} else if (this.board[i][j] == 0) {
					System.out.print("  ");
				} else if (this.board[i][j] == 1) {
					System.out.print("@@");
				} else if (this.board[i][j] > 1) {
					if (this.board[i][j] % 2 == 0) {
						System.out.print(">>");
					} else {
						System.out.print("\\/");
					}
				} else if (this.board[i][j] == -1) {
					System.out.print("  ");
				}
			}
			System.out.print("     ");
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == -2) {
					if (i == 0 || i == this.board.length - 1) {
						System.out.print("---");
					} else if (j == 0) {
						System.out.print("|  ");
					} else if (j == this.board[i].length - 1) {
						System.out.print("  |");
					}
				} else if (this.board[i][j] == 0) {
					System.out.print("   ");
				} else if (this.board[i][j] > 0) {
					if(this.board[i][j] / 10 >= 1)
						System.out.print(" " + this.board[i][j]);
					else
						System.out.print(" " + this.board[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Function that prints the board.
	 */
	public void print() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if (this.board[i][j] == -2) {
					if (i == 0 || i == this.board.length - 1) {
						System.out.print("--");
					} else if (j == 0) {
						System.out.print("| ");
					} else if (j == this.board[i].length - 1) {
						System.out.print(" |");
					}
				} else if (this.board[i][j] == 0) {
					System.out.print("  ");
				} else if (this.board[i][j] == 1) {
					System.out.print("@@");
				} else if (this.board[i][j] > 1) {
					if (this.board[i][j] % 2 == 0) {
						System.out.print(">>");
					} else {
						System.out.print("\\/");
					}
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Function that returns the board.
	 * @return
	 */
	public int[][] get_board() {
		return this.board;
	}
	
	/**
	 * Function that allows to check if the two boards are equal to one another.
	 * @param b - the board with which this board will be compared.
	 * @return
	 */
	public boolean compare_board(Board b) {
		int[][] board_to_compare = b.get_board();
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				if(board[i][j] != board_to_compare[i][j])
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Function to get the board's depth.
	 * @return
	 */
	public int get_depth() {
		return this.depth;
	}
	
	/**
	 * Function to set the board's depth according to the parent's depth.
	 */
	public void set_depth() {
		if(parent == null)
			this.depth = 0;
		else
			this.depth = this.parent.get_depth()+1;
	}
	
	/**
	 * Function that returns the board's expansions.
	 * @return
	 */
	public ArrayList<Board> get_successors() {
		return successors;
	}
	
	/**
	 * Function that sets the board's expansions.
	 * @param successors
	 */
	public void set_successors(ArrayList<Board> successors) {
		this.successors = successors;
	}
	
	/**
	 * Function that checks if a block can move and where to.
	 * @param block_id
	 * @return array of boolean in which the first element defines if the block can go backwards, the second element defines
	 * whether the clock can move forward and the last element defines if the block moves in an horizontal direction.
	 */
	public boolean[] can_block_move(int block_id) {
		boolean[] can_move = new boolean[3];
		can_move[0] = false; //can move to place before?
		can_move[1] = false; //can move to place after?
		can_move[2] = false; //moves horizontally -> true else false
		for(int i = 0; i < this.blocks.size(); i++) {
			if(this.blocks.get(i).get_id() == block_id) {
				int line_bef = this.blocks.get(i).get_line();
				int column_bef = this.blocks.get(i).get_column();
				int line_aft = this.blocks.get(i).get_line();
				int column_aft = this.blocks.get(i).get_column();
				if(this.blocks.get(i).get_direction().equals("horizontal")) {
					column_bef--;
					column_aft += this.blocks.get(i).get_length();
					can_move[2] = true;
				} else {
					line_bef--;
					line_aft += this.blocks.get(i).get_length();
				}
				if(this.board[line_bef][column_bef] == 0)
					can_move[0] = true;
				if(this.board[line_aft][column_aft] == 0)
					can_move[1] = true;
			}
		}
		return can_move;
	}
	
	/**
	 * Function that compares two Boards to check which block moved from one to another.
	 * @param b - board with which to compare
	 * @return int array that holds in the first position the block that moved and in the second the direction in which it moved
	 */
	public int[] check_moved_block(Board b) {
		boolean move_to_after = false, found = false;
		int[] block_dir = new int[2]; //block_dir[0] -> block number; block_dir[1] -> block direction (0 - left, 1 - right, 2 - up, 3 - down)
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				if(this.board[i][j] != b.get_board()[i][j]) {
					if(this.board[i][j] == 0) {
						block_dir[0] = b.get_board()[i][j];
						move_to_after = false;
					} else {
						block_dir[0] = this.board[i][j];
						move_to_after = true;
					}
					found = true;
					break;
				}
			}
			if(found)
				break;
		}
		for(int i = 0; i < this.blocks.size(); i++) {
			if(this.blocks.get(i).get_id() == block_dir[0]) {
				if (this.blocks.get(i).get_direction().equals("horizontal")) {
					if(move_to_after)
						block_dir[1] = 1;
					else
						block_dir[1] = 0;
				} else {
					if(move_to_after)
						block_dir[1] = 3;
					else
						block_dir[1] = 2;
				}
				break;
			}
		}
		return block_dir;
	}
	
	/**
	 * Function that compares the boards according to the functions g and h used in the A* and Greedy algorithms and
	 * according to their depths in the case of the iterative deepening algorithm.
	 */
	@Override
    public int compareTo(Board b) {
		if(this.search.equals("a_star") || this.search.equals("a_star_1") || this.search.equals("a_star_2"))
			return (this.g+this.h)-(b.get_g()+b.get_h());
		else if(this.search.equals("greedy"))
			return this.h-b.get_h();
		else if(this.search.equals("iter_deep")) {
			if(b.get_depth()-this.depth>0)
				return 1;
			if(b.get_depth()-this.depth<0)
				return -1;
		}
		return 0;
    }
}
