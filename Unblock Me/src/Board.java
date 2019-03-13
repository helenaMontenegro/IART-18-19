import java.util.ArrayList;

public class Board {
	int[][] board;
	ArrayList<Block> blocks;

	Board(int[][] board) {
		this.board = board;
		blocks = new ArrayList<>();
	}

	public ArrayList<Board> generate_successors() { // TODO
		ArrayList<Board> successors = new ArrayList<>();
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
					for (int x = 0; x < this.blocks.size(); x++) // verificar se o bloco já existe na lista
					{
						if (this.board[i][j] == this.blocks.get(x).get_id()) {
							// se existir aumenta o length
							this.blocks.get(x).increase_length();
							found = true;

						}
						
					}
					
					System.out.println(found);
					if (found == false) {// não encontrou o bloco -> cria um novo
						Block newBlock = new Block(board[i][j], j, i);
						blocks.add(newBlock);
						
					}

				}

			}
		}

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
