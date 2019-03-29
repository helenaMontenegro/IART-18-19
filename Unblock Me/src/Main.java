import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[][] ini_board = {
                {-2, -2, -2, -2, -2, -2, -2, -2},
                {-2, 6, 6, 6, 0, 0, 9, -2},
                {-2, 0, 0, 3, 0, 0, 9, -2},
                {-2, 1, 1, 3, 0, 0, 9, -1},
                {-2, 5, 0, 3, 0, 4, 4, -2},
                {-2, 5, 0, 0, 0, 7, 0, -2},
                {-2, 2, 2, 2, 0, 7, 0, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2}};
        int[][] ini_board2 = {
                {-2, -2, -2, -2, -2, -2, -2, -2},
                {-2, 3, 6, 6, 8, 8, 0, -2},
                {-2, 3, 0,14,14,15, 0, -2},
                {-2, 1, 1,11,13,15, 0, -1},
                {-2, 0, 9,11,13, 4, 4, -2},
                {-2, 5, 9,11,10,10, 7, -2},
                {-2, 5,12,12, 2, 2, 7, -2},
                {-2, -2, -2, -2, -2, -2, -2, -2}};
        mainMenu(ini_board);
        
    }

    private static void mainMenu(int[][] ini_board) {
        System.out.println("-----------------");
        System.out.println("|   Unblock Me  |");
        System.out.println("|               |");
        System.out.println("|   1-Human     |");
        System.out.println("|   2-PC        |");
        System.out.println("|               |");
        System.out.println("-----------------");


        int option = getOption(1, 2);

        switch (option) {
            case 1:
                humanMenu(ini_board);
                break;
            case 2:
                searchMenu(ini_board);
                break;
            default:
                break;
        }


    }

    private static int getOption(int min, int max) {
        int option = 0;
        do {
            try {
                System.out.println("Option:");
                Scanner sc = new Scanner(System.in);
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
    
    private static void humanMenu(int[][] ini_board) {
    	Board board = new Board(ini_board, null, 0, 0, "a_star");
    	board.print_for_human();
    	
    	while(!board.is_final()) {
	    	int block = 0;
	    	boolean[] can_move = new boolean[3];
	    	can_move[0] = false;
	    	can_move[1] = false;
	    	can_move[2] = false;
	    	boolean dont_get_out = false;
	    	System.out.println("Choose the number of the block you want to move or 0 for a hint.");
	    	do {
	            try {
	                System.out.println("Option:");
	                Scanner sc = new Scanner(System.in);
	                block = sc.nextInt();
	            } catch (Exception e) {
	                System.out.println("Invalid Input");
	                continue;
	            }
	            if(block < 0){
	                System.out.println("Invalid Block");
	            } else if(block != 0){
	            	can_move = board.can_block_move(block);
	            	if(can_move[0] == false && can_move[1] == false) {
	            		System.out.println("The chosen block can't move!");
	            		dont_get_out=true;
	            	}
	            }
	        } while (block < 0 || dont_get_out);
	    	
	    	if(block == 0) {
	    		board.set_parent(null);
	    		Search search = new AStarSearch(board);
	    		Board final_board = search.run();
	    		ArrayList<Board> sequence = search.generate_sequence(final_board);
	    		board = sequence.get(1);
	    		board.print_for_human();
	    		continue;
	    	}

	    	int direction = 1;
	    	if(can_move[0] && can_move[1]) {
	    		if(can_move[2])
	    			System.out.println("Choose 1 to move left or 2 to move right.");
	    		else
	    			System.out.println("Choose 1 to move up or 2 to move down.");
	    		direction = getOption(1, 2);
	    	} else if(can_move[0]) {
	    		direction = 1;
	    	} else
	    		direction = 2;
	    	board = board.generate_specific_successor(block, direction);
	    	board.print_for_human();
    	}
    	System.out.println("You win!");
    	
    }


    private static void searchMenu(int[][] ini_board) {
        System.out.println("------------------------------");
        System.out.println("|   Unblock Me               |");
        System.out.println("|                            |");
        System.out.println("|   1-DFS                    |");
        System.out.println("|   2-BFS                    |");
        System.out.println("|   3-Iterative Deepening    |");
        System.out.println("|   4-Greedy                 |");
        System.out.println("|   5-A*                     |");
        System.out.println("|                            |");
        System.out.println("------------------------------");

        int option = getOption(1, 5);

        String type_of_search = "greedy";
        Search search = null;
        Board board;
        switch (option) {
            case 1:
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new DFS(board);
                break;
            case 2:
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new BFS(board);
                break;
            case 3:
                type_of_search = "iter_deep";
            	board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new IterativeDeepeningSearch(board);
                break;
            case 4:
                type_of_search = "greedy";
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new GreedySearch(board);
                break;
            case 5:
                type_of_search = "a_star";
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new AStarSearch(board);
                break;
        }

        Board final_board = search.run();
        if(final_board == null) {
            System.err.println("Final board not found!");
            System.exit(-1);
        }
        ArrayList<Board> sequence = search.generate_sequence(final_board);
        for(int i = 0; i < sequence.size(); i++) {
            sequence.get(i).print();
        }
        
        System.out.println("Number of moves: " + sequence.size());
        System.out.println("Time needed: " + search.get_time()/1000F + " seconds");
    }
}
