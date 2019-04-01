package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import logic.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;
public class UI {

	private JFrame frame;
	private JTextField textFile;
	private int[][] ini_board = null;
	private Board board;
	private Search search;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
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
	public UI() {
		initialize();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("PC Mode");
		frame.setBounds(100, 100, 702, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFile = new JTextField();
		textFile.setBounds(45, 32, 185, 28);
		frame.getContentPane().add(textFile);
		textFile.setColumns(10);
		
		
		JButton btnLoadFile = new JButton("Load");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
			        FileReader file_reader = new FileReader(textFile.getText());
					ini_board = Main.parseBoard(file_reader);
			        System.out.println("Loaded file.");
				} catch (FileNotFoundException e) {
					System.out.println("File not found.");
				}
			}
		});
		btnLoadFile.setBounds(253, 35, 89, 23);
		frame.getContentPane().add(btnLoadFile);
		
		JRadioButton rdbtnBFS = new JRadioButton("BFS");
		rdbtnBFS.setBounds(538, 109, 109, 23);
		frame.getContentPane().add(rdbtnBFS);
		
		JRadioButton rdbtnDFS = new JRadioButton("DFS");
		rdbtnDFS.setBounds(538, 151, 109, 23);
		frame.getContentPane().add(rdbtnDFS);
		
		JRadioButton rdbtnAstar = new JRadioButton("A*");
		rdbtnAstar.setBounds(538, 195, 109, 23);
		frame.getContentPane().add(rdbtnAstar);
		
		JRadioButton rdbtnIterativeDeepening = new JRadioButton("Iterative Deepening");
		rdbtnIterativeDeepening.setBounds(538, 237, 142, 23);
		frame.getContentPane().add(rdbtnIterativeDeepening);
		
		JRadioButton rdbtnGreedy = new JRadioButton("Greedy");
		rdbtnGreedy.setBounds(538, 280, 109, 23);
		frame.getContentPane().add(rdbtnGreedy);
		
		JButton btnNewButton_1 = new JButton("Solve");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ini_board != null) {
					if(rdbtnAstar.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "a_star");
						search = new AStarSearch(board);
					} else if(rdbtnBFS.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new BFS(board);
					}
					else if(rdbtnDFS.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new DFS(board);
					}
					else if(rdbtnGreedy.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "greedy");
						search = new GreedySearch(board);
					}
					else if(rdbtnIterativeDeepening.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new IterativeDeepeningSearch(board);
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
		});
		btnNewButton_1.setBounds(548, 331, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAstar);
		group.add(rdbtnBFS);
		group.add(rdbtnDFS);
		group.add(rdbtnIterativeDeepening);
		group.add(rdbtnGreedy);
		
		JTextArea textOutput = new JTextArea();
		textOutput.setFont(new Font("Courier New", Font.PLAIN, 13));
		textOutput.setEditable(false);
		textOutput.setBounds(45, 71, 454, 358);
		JScrollPane scrollPane = new JScrollPane(textOutput);
		scrollPane.setBounds(45, 71, 454, 358);
		frame.getContentPane().add(scrollPane);
		

		
		PrintStream out = new PrintStream( new TextAreaOutputStream( textOutput ) );
		
		JButton btnHuman = new JButton("Human Mode");
		btnHuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HumanUI();
				frame.dispose();
			}
		});
		btnHuman.setBounds(538, 32, 109, 28);
		frame.getContentPane().add(btnHuman);
		System.setOut( out );
		System.setErr( out );
		
		
	}
}
