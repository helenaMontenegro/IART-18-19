package gui;

import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import logic.Board;
import logic.MinimaxSearch;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;

public class Mancala {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private Board board;
	private MinimaxSearch minimax;
	
	private JButton button_0;
	private JButton button_2;
	private JButton button_3;
	private JButton button_6;
	private JButton button_8;
	private JButton button_9;
	private JButton button_11;
	private JButton button_7;
	private JButton button_1;
	private JButton button_10;
	private JButton button_4;
	private JButton button_5;
	
	public int[] number_moves = new int[]{0,0};
	public long[] time_needed = new long[]{0,0};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mancala window = new Mancala();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public Mancala() {
		initialize();
		startGame();
	}


	private void startGame() {
		board = new Board(new int[][] {{4,4,4,4,4,4},{4,4,4,4,4,4}}, 4, 1, "MAX", 1, 2);
		board.print();
		minimax = new MinimaxSearch(board, 1, 1, true);
	}
	
	private void makeMove(int option) {
		if(!board.is_final()) {
			if(board.get_players_turn() == 1)
			{
				System.out.println("Choose a number between 1 and 6 representing the cell that you want to choose.");
				if(board.check_empty(option-1)) {
					System.out.println("There are no pieces at the cell to move. Choose another one.");
					return;
				}
				board = board.movement(option-1);
				board.print();
				minimax.set_board(board);
				updateBoard();
			} while(board.get_players_turn() == 2) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int prev_player = board.get_players_turn();
				board = minimax.run();
				minimax.set_board(board);
				board.print();
				System.out.println("Time needed: " + minimax.get_time()/1000F + " seconds.\n");
				time_needed[prev_player-1] += minimax.get_time();
				number_moves[prev_player-1]++;
				updateBoard();
			}
		}
		if(board.is_final()) {
		board.set_final(); //updates mancala with the end of game
		board.print_result();
		System.out.println("\nAverage time needed for the computer's play: " + this.time_needed[1] / (long) this.number_moves[1] /1000F + " seconds");
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 455, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		button_5 = new JButton("4");
		button_5.setBounds(330, 142, 42, 42);
		frame.getContentPane().add(button_5);
		
		button_0 = new JButton("4");
		button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeMove(1);
			}
		});
		button_0.setBounds(60, 142, 42, 42);
		frame.getContentPane().add(button_0);
		
		button_2 = new JButton("4");
		button_2.setBounds(166, 142, 42, 42);
		frame.getContentPane().add(button_2);
		
		button_3 = new JButton("4");
		button_3.setBounds(221, 142, 42, 42);
		frame.getContentPane().add(button_3);
		
		button_6 = new JButton("5");
		button_6.setBounds(60, 63, 42, 42);
		frame.getContentPane().add(button_6);
		
		button_8 = new JButton("4");
		button_8.setBounds(166, 63, 42, 42);
		frame.getContentPane().add(button_8);
		
		button_9 = new JButton("4");
		button_9.setBounds(221, 63, 42, 42);
		frame.getContentPane().add(button_9);
		
		button_11 = new JButton("4");
		button_11.setBounds(330, 63, 42, 42);
		frame.getContentPane().add(button_11);
		
		button_7 = new JButton("4");
		button_7.setBounds(112, 63, 42, 42);
		frame.getContentPane().add(button_7);
		
		button_1 = new JButton("4");
		button_1.setBounds(112, 142, 42, 42);
		frame.getContentPane().add(button_1);
		
		button_10 = new JButton("4");
		button_10.setBounds(276, 63, 42, 42);
		frame.getContentPane().add(button_10);
		
		button_4 = new JButton("4");
		button_4.setBounds(276, 142, 42, 42);
		frame.getContentPane().add(button_4);
	
		
		JLabel lblComputer = new JLabel("Computer");
		lblComputer.setBounds(185, 23, 97, 14);
		frame.getContentPane().add(lblComputer);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setBounds(194, 210, 46, 14);
		frame.getContentPane().add(lblPlayer);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("0");
		textField.setEditable(false);
		textField.setBounds(10, 63, 40, 121);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("0");
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(382, 63, 40, 121);
		frame.getContentPane().add(textField_1);
	}
	
	private void updateBoard() {
		button_0.setText(String.valueOf(board.get_board()[0][0]));
		button_1.setText(String.valueOf(board.get_board()[0][1]));
		button_2.setText(String.valueOf(board.get_board()[0][2]));
		button_3.setText(String.valueOf(board.get_board()[0][3]));
		button_4.setText(String.valueOf(board.get_board()[0][4]));
		button_5.setText(String.valueOf(board.get_board()[0][5]));
		button_6.setText(String.valueOf(board.get_board()[1][0]));
		button_7.setText(String.valueOf(board.get_board()[1][1]));
		button_8.setText(String.valueOf(board.get_board()[1][2]));
		button_9.setText(String.valueOf(board.get_board()[1][3]));
		button_10.setText(String.valueOf(board.get_board()[1][4]));
		button_11.setText(String.valueOf(board.get_board()[1][5]));
	}
	
}
