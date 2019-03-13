import java.util.ArrayList;

public class Board {
	int[][] board;
	ArrayList<Block> blocks;

	Board(int[][] board) {
		this.board = board;
		this.blocks = new ArrayList<>();
		this.build_blocks();
	}

	public ArrayList<Board> generate_successors() { // TODO
		ArrayList<Board> successors = new ArrayList<>();
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
				Board new_bef = new Board(new_board_bef);
				successors.add(new_bef);
			} 
			//generate board with block moved to cell after
			if(this.board[line_aft][column_aft] == 0) {
				int[][] new_board_aft = this.copy_board();
				new_board_aft[line_aft][column_aft] = this.blocks.get(i).get_id();
				new_board_aft[line_aft_empty][column_aft_empty] = 0;
				Board new_aft = new Board(new_board_aft);
				successors.add(new_aft);
			}
		}
		return successors;
	}

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
					
					System.out.println(found);
					if (found == false) {// n�o encontrou o bloco -> cria um novo
						Block newBlock = new Block(board[i][j], j, i);
						blocks.add(newBlock);
						
					}

				}

			}
		}

	}
	
	public int[][] copy_board() {
		int[][] new_array = new int[8][8];
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				new_array[i][j] = board[i][j];
			}
		}
		return new_array;
	}
	
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
}
