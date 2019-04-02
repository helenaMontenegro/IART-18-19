package logic;

import logic.AStarSearch;
import logic.Search;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    private static int[][] ini_board;

    public static void main(String[] args) {

    	while(true)
    	{
	        System.out.println("Insert name of the file that contains the wanted board:");
			try {
				Scanner sc = new Scanner(System.in);
		        String filename= sc.nextLine();
		        FileReader file_reader = new FileReader(filename);
		        ini_board = parseBoard(file_reader);
		        mainMenu(ini_board);
		        sc.close();
		        break;
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
			}
    	}        
    }


    public static void mainMenu(int[][] ini_board) {
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

    public static int[][] parseBoard(FileReader file_reader){
        BufferedReader reader; 
        String next_element;
        String[] line;
        int[][] board= new int[8][];

        try{
            reader= new BufferedReader(file_reader);
           
            int i=0;
            while( (next_element=reader.readLine()) != null){
               
                line=next_element.split(",");
                int[] first_line = Arrays.asList(line).stream().mapToInt(Integer::parseInt).toArray();
                board[i]=first_line;
                i++;
            }
            reader.close();
            
        }catch (IOException e){
            System.out.println("File Not Found");
            return null;
        }
        return board;
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
            	System.out.println("------------------------------");
                System.out.println("|   Choose heuristic:        |");
                System.out.println("|                            |");
                System.out.println("|   1-Distance to exit       |");
                System.out.println("|   2-Blocks blocking exit   |");
                System.out.println("|   3-Both                   |");
                System.out.println("|                            |");
                System.out.println("------------------------------");
            	option = getOption(1, 3);
            	switch(option) {
            	case 1: type_of_search = "a_star_1"; break;
            	case 2: type_of_search = "a_star_2"; break;
            	case 3: type_of_search = "a_star"; break;
            	}
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

        System.out.println("Number of states visited: " + search.get_num_boards_visited());
        System.out.println("Number of moves: " + sequence.size());
        System.out.println("Time needed: " + search.get_time()/1000F + " seconds");
    }
}
