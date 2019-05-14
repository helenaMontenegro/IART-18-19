import java.util.Scanner;

public class Main {
	public int[] number_moves = new int[]{0,0};
	public long[] time_needed = new long[]{0,0};

	public static void main(String[] args) {
		mainMenu();
	}
	
	public int get_depth(int difficulty) {
		if(difficulty == 1)
			return 1;
		if(difficulty == 2)
			return 4;
		return 8;
	}
	
	public void pcVSpc(int pc1_difficulty, int pc2_difficulty, int heuristic, boolean alpha_beta) {
		int depth1 = this.get_depth(pc1_difficulty);
		int depth2 = this.get_depth(pc2_difficulty);
		
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, depth1, 1, "MAX", 1, heuristic);
		board.print();
		MinimaxSearch minimax = new MinimaxSearch(board, depth1, depth2, alpha_beta);

		while(!board.is_final()) {
			int prev_player = board.get_players_turn();
			board = minimax.run();
			minimax.set_board(board);
			board.print();
			System.out.println("Time needed: " + minimax.get_time()/1000F + " seconds.\n");
			this.time_needed[prev_player-1] += minimax.get_time();
			this.number_moves[prev_player-1]++;
		}
		board.set_final(); //updates mancala with the end of game
		board.print_result();
		System.out.println("\nNumber of movements needed:");
		System.out.println("Player 1: " + this.number_moves[0]);
		System.out.println("Player 2: " + this.number_moves[1]);
		System.out.println("\nAverage time needed for each play:");
		System.out.println("Player 1: " + this.time_needed[0] / (long) this.number_moves[0] /1000F + " seconds");
		System.out.println("Player 2: " + this.time_needed[1] / (long) this.number_moves[1] /1000F + " seconds");
	}

	public static void humanVShuman() {
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 1, 1, "MAX", 1, 1);
		board.print();
		while(!board.is_final()) {
			System.out.println("Choose a number between 1 and 6 representing the cell that you want to choose.");
			int option = getOption(1, 6);
			if(board.check_empty(option-1)) {
				System.out.println("There are no pieces at the cell to move. Choose another one.");
				continue;
			}
			board = board.movement(option-1);
			board.print();
		}
		board.set_final(); //updates mancala with the end of game
		board.print_result();
	}

	public void humanVSpc(int pc1_difficulty, int heuristic, boolean alpha_beta) {
		int depth1 = this.get_depth(pc1_difficulty);
		
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, depth1, 1, "MAX", 1, heuristic);
		board.print();
		MinimaxSearch minimax = new MinimaxSearch(board, depth1, depth1, alpha_beta);
		while(!board.is_final()) {
			if(board.get_players_turn() == 1)
			{
				System.out.println("Choose a number between 1 and 6 representing the cell that you want to choose.");
				int option = getOption(1, 6);
				if(board.check_empty(option-1)) {
					System.out.println("There are no pieces at the cell to move. Choose another one.");
					continue;
				}
				board = board.movement(option-1);
				board.print();
				minimax.set_board(board);
			} else {
				int prev_player = board.get_players_turn();
				board = minimax.run();
				minimax.set_board(board);
				board.print();
				System.out.println("Time needed: " + minimax.get_time()/1000F + " seconds.\n");
				this.time_needed[prev_player-1] += minimax.get_time();
				this.number_moves[prev_player-1]++;
			}
		}
		board.set_final(); //updates mancala with the end of game
		board.print_result();
		System.out.println("\nAverage time needed for the computer's play: " + this.time_needed[1] / (long) this.number_moves[1] /1000F + " seconds");
	}

	/**
	 * Function that shows the initial menu of the program
	 * @param board - initial board.
	 */
	public static void mainMenu() {
		System.out.println("---------------------------------------");
		System.out.println("|   __  __                   _         |");
		System.out.println("|  |  \\/  |__ _ _ _  __ __ _| |__ _    |");
		System.out.println("|  | |\\/| / _` | ' \\/ _/ _` | / _` |   |");
		System.out.println("|  |_|  |_\\__,_|_||_\\__\\__,_|_\\__,_|   |");
		System.out.println("|                                      |");
		System.out.println("|           1-Human Vs Human           |");
		System.out.println("|           2-PC Vs PC                 |");
		System.out.println("|           3-Human Vs Pc              |");
		System.out.println("|                                      |");
		System.out.println("---------------------------------------");


		int option = getOption(1, 3);

		switch (option) {
			case 1:
				humanVShuman();
				break;
			case 2:
				pcMenu();
				break;
			case 3:
				humanPcMenu();
			default:
				break;
		}


	}

public static void pcMenu(){

	Main m = new Main();

	System.out.println("-------------------------------------------------------");
	System.out.println("|                                                      |");
	System.out.println("|    Please, choose the difficulty of the first PC:    |");
	System.out.println("|      1-Beginner                                      |");
	System.out.println("|      2-Medium                                        |");
	System.out.println("|      3-Professional                                  |");
	System.out.println("|                                                      |");
	System.out.println("-------------------------------------------------------");

	int option = getOption(1, 3);

	System.out.println("-------------------------------------------------------");
	System.out.println("|                                                      |");
	System.out.println("|    Please, choose the difficulty of the second PC:   |");
	System.out.println("|      1-Beginner                                      |");
	System.out.println("|      2-Medium                                        |");
	System.out.println("|      3-Professional                                  |");
	System.out.println("|                                                      |");
	System.out.println("-------------------------------------------------------");

	int option2 = getOption(1, 3);

	m.pcVSpc(option, option2, 2, true); //ask heuristic and alfa beta
}

public static void humanPcMenu(){

	Main m = new Main();

	System.out.println("-------------------------------------------------------");
	System.out.println("|                                                      |");
	System.out.println("|    Please, choose the difficulty of the PC:          |");
	System.out.println("|      1-Beginner                                      |");
	System.out.println("|      2-Medium                                        |");
	System.out.println("|      3-Professional                                  |");
	System.out.println("|                                                      |");
	System.out.println("-------------------------------------------------------");

	int option = getOption(1, 3);

	m.humanVSpc(option, 2, true);
}
	/**
	 * Function that receives an input from the user between min and max and returns it.
	 * @param min - minimum value
	 * @param max - maximum value
	 * @return user input
	 */
	private static int getOption(int min, int max) {
		int option = 0;
		do {
			try {
				System.out.println("Option:");
				Scanner sc = new Scanner  (System.in);
				option = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid Input");
				continue;
			}
			if(option < min || option > max){
				System.out.println("Invalid Option");
			}
		} while (option < min || option > max);
		return option;
	}
}
