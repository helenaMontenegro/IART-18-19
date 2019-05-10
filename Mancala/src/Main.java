import java.util.Scanner;

public class Main {

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
	
	public void pcVSpc(int pc1_difficulty, int pc2_difficulty, int heuristic) {
		int depth1 = this.get_depth(pc1_difficulty);
		int depth2 = this.get_depth(pc2_difficulty);
		
		Board board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, depth1, 1, "MAX", 1, heuristic);
		board.print();
		MinimaxSearch minimax = new MinimaxSearch(board, depth1, depth2, true);
		while(!board.is_final()) {
			board = minimax.run();
			minimax.set_board(board);
			board.print();
			System.out.println("Time needed: " + minimax.get_time()/1000F + " seconds.\n");
		}
		board.set_final(); //updates mancala with the end of game
		board.print_result();				
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
				humanMenu();
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

	m.pcVSpc(option, option2, 2); //ask heuristic
}

public static void humanMenu(){


}

public static void humanPcMenu(){

	System.out.println("-------------------------------------------------------");
	System.out.println("|                                                      |");
	System.out.println("|    Please, choose the difficulty of the PC:          |");
	System.out.println("|      1-Beginner                                      |");
	System.out.println("|      2-Medium                                        |");
	System.out.println("|      3-Professional                                  |");
	System.out.println("|                                                      |");
	System.out.println("-------------------------------------------------------");

	int option = getOption(1, 3);

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
