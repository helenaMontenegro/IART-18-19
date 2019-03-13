import java.util.ArrayList;

public class Board {
	int[][] board;
	ArrayList<Block> blocks;
	Board(int[][] board) {
		this.board = board;
		blocks = new ArrayList<>();
	}
	
	public ArrayList<Board> generate_successors() { //TODO
		ArrayList<Board> successors = new ArrayList<>();
		return successors;
	}
	
	public void build_blocks() { //TODO
		
	}
	
	public void print() {
		for(int i = 0; i < this.board.length; i++) {
			for(int j = 0; j < this.board[i].length; j++) {
				if(this.board[i][j] == -2) {
					if(i == 0 || i == this.board.length-1) {
						System.out.print("--");
					}
					else if(j == 0) {
						System.out.print("| ");
					}
					else if(j == this.board[i].length-1) {
						System.out.print(" |");
					}
				}
				else if(this.board[i][j] == 0) {
					System.out.print("  ");
				}
				else if(this.board[i][j] == 1) {
					System.out.print("@@");
				}
				else if(this.board[i][j] > 1) {
					if(this.board[i][j] % 2 == 0) {
						System.out.print(">>");
					}
					else {
						System.out.print("\\/");
					}
				}
			}
			System.out.println();
		}
	}
}
