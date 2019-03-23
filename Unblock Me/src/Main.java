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
        mainMenu(ini_board);

    }

    private static void mainMenu(int[][] ini_board) {
        System.out.println("----------------");
        System.out.println("|   Unblock Me  ");
        System.out.println("|               |");
        System.out.println("|   1-Human     |");
        System.out.println("|   2-PC        |");
        System.out.println("|               |");
        System.out.println("----------------");


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
    }


    private static void searchMenu(int[][] ini_board) {
        System.out.println("----------------");
        System.out.println("|   Unblock Me  ");
        System.out.println("|               |");
        System.out.println("|   1-DFS       |");
        System.out.println("|   2-BFS       |");
        System.out.println("|   3-Greedy    |");
        System.out.println("|   4-A*        |");
        System.out.println("|               |");
        System.out.println("----------------");

        int option = getOption(1, 4);

        String type_of_search = ""; //so necessaria na greedy e na a_star, para o compareTo do board
        Search search;
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
                type_of_search = "greedy";
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new GreedySearch(board);
                break;
            case 4:
                type_of_search = "a_start";
                board = new Board(ini_board, null, 0, 0, type_of_search);
                search = new AStarSearch(board);
                break;
            default:
                type_of_search = "a_start";
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
