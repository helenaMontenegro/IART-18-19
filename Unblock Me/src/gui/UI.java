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
	private ArrayList<Board> sequence;
	private int step = 0;

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
		frame.setBounds(100, 100, 702, 692);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textFile = new JTextField();
		textFile.setBounds(45, 32, 185, 28);
		frame.getContentPane().add(textFile);
		textFile.setColumns(10);

		Grid grid = new Grid();
		grid.setLocation(45, 442);

		frame.getContentPane().add(grid);
		grid.setSize(280, 200);

		JButton btnLoadFile = new JButton("Load");
		addLoadListener(grid, btnLoadFile);
		btnLoadFile.setBounds(253, 35, 89, 23);
		frame.getContentPane().add(btnLoadFile);

		JRadioButton rdbtnBFS = new JRadioButton("BFS");
		rdbtnBFS.setBounds(538, 107, 109, 23);
		frame.getContentPane().add(rdbtnBFS);

		JRadioButton rdbtnDFS = new JRadioButton("DFS");
		rdbtnDFS.setBounds(538, 133, 109, 23);
		frame.getContentPane().add(rdbtnDFS);

		JRadioButton rdbtnAstar = new JRadioButton("A*(1)");
		rdbtnAstar.setBounds(538, 159, 109, 23);
		frame.getContentPane().add(rdbtnAstar);

		JRadioButton rdbtnIterativeDeepening = new JRadioButton("Iterative Deepening");
		rdbtnIterativeDeepening.setBounds(538, 238, 142, 23);
		frame.getContentPane().add(rdbtnIterativeDeepening);

		JRadioButton rdbtnGreedy = new JRadioButton("Greedy");
		rdbtnGreedy.setBounds(538, 264, 109, 23);
		frame.getContentPane().add(rdbtnGreedy);

		JRadioButton rdbtnAstar2 = new JRadioButton("A*(2)");
		rdbtnAstar2.setBounds(538, 186, 109, 23);
		frame.getContentPane().add(rdbtnAstar2);

		JRadioButton rdbtnAstarBoth = new JRadioButton("A*(both)");
		rdbtnAstarBoth.setBounds(538, 212, 109, 23);
		frame.getContentPane().add(rdbtnAstarBoth);

		JButton btnNextStep = new JButton("Next");
		JButton btnPrevStep = new JButton("Previous");
		btnPrevStep.setEnabled(false);
		addPrevStepListener(grid, btnNextStep, btnPrevStep);
		btnPrevStep.setBounds(334, 470, 96, 23);
		frame.getContentPane().add(btnPrevStep);

		btnNextStep.setEnabled(false);
		addNextStepListener(grid, btnNextStep, btnPrevStep);
		btnNextStep.setBounds(334, 524, 96, 23);
		frame.getContentPane().add(btnNextStep);

		JButton btnNewButton_1 = new JButton("Solve");
		addSolveListener(grid, rdbtnBFS, rdbtnDFS, rdbtnAstar, rdbtnIterativeDeepening, rdbtnGreedy, rdbtnAstar2, rdbtnAstarBoth, btnNextStep, btnNewButton_1);
		btnNewButton_1.setBounds(548, 331, 89, 23);
		frame.getContentPane().add(btnNewButton_1);

		addButtonGroup(rdbtnBFS, rdbtnDFS, rdbtnAstar, rdbtnIterativeDeepening, rdbtnGreedy, rdbtnAstar2, rdbtnAstarBoth);

		JTextArea textOutput = new JTextArea();
		textOutput.setFont(new Font("Courier New", Font.PLAIN, 13));
		textOutput.setEditable(false);
		textOutput.setBounds(45, 71, 454, 358);
		JScrollPane scrollPane = new JScrollPane(textOutput);
		scrollPane.setBounds(45, 71, 454, 358);
		frame.getContentPane().add(scrollPane);

		PrintStream out = new PrintStream(new TextAreaOutputStream(textOutput));

		JButton btnHuman = new JButton("Human Mode");
		humanButtonListener(btnHuman);
		btnHuman.setBounds(538, 32, 109, 28);
		frame.getContentPane().add(btnHuman);

		System.setOut(out);
		System.setErr(out);

	}
	/**
	 * Add Listener to Load button
	 */
	private void addLoadListener(Grid grid, JButton btnLoadFile) {
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader file_reader = new FileReader(textFile.getText());
					ini_board = Main.parseBoard(file_reader);
					System.out.println("Loaded file.");
					grid.setBoard(ini_board);
					grid.repaint();

				} catch (FileNotFoundException e) {
					System.out.println("File not found.");
				}
			}
		});
	}
	/**
	 * Add Listener to Previous step button
	 */
	private void addPrevStepListener(Grid grid, JButton btnNextStep, JButton btnPrevStep) {
		btnPrevStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					step--;
					grid.setBoard(sequence.get(step).get_board());

					if (step == 0) {
						btnPrevStep.setEnabled(false);
					}
					if (step < sequence.size()) {
						btnNextStep.setEnabled(true);
					}
				} catch (Exception e) {
					System.out.println("Invalid Operation");
				}

			}
		});
	}

	/**
	 * Add Listener to Next Step button
	 */
	private void addNextStepListener(Grid grid, JButton btnNextStep, JButton btnPrevStep) {
		btnNextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					grid.setBoard(sequence.get(step).get_board());
					step++;

					if (step >= sequence.size()) {
						btnNextStep.setEnabled(false);
					}
					if (step > 0) {
						btnPrevStep.setEnabled(true);
					}
				} catch (Exception e) {
					System.out.println("Board was finished");
				}

			}
		});
	}

	/**
	 * Add Listener to Solve button
	 */
	private void addSolveListener(Grid grid, JRadioButton rdbtnBFS, JRadioButton rdbtnDFS, JRadioButton rdbtnAstar, JRadioButton rdbtnIterativeDeepening, JRadioButton rdbtnGreedy, JRadioButton rdbtnAstar2, JRadioButton rdbtnAstarBoth, JButton btnNextStep, JButton btnNewButton_1) {
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ini_board != null) {
					if (rdbtnAstar.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "a_star_1");
						search = new AStarSearch(board);
					} else if (rdbtnBFS.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new BFS(board);
					} else if (rdbtnDFS.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new DFS(board);
					} else if (rdbtnGreedy.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "greedy");
						search = new GreedySearch(board);
					} else if (rdbtnIterativeDeepening.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "");
						search = new IterativeDeepeningSearch(board);
					} else if (rdbtnAstar2.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "a_star_2");
						search = new AStarSearch(board);
					} else if (rdbtnAstarBoth.isSelected()) {
						board = new Board(ini_board, null, 0, 0, "a_star");
						search = new AStarSearch(board);
					}

					Board final_board = search.run();
					if (final_board == null) {
						System.err.println("Final board not found!");
						System.exit(-1);
					}
					sequence = search.generate_sequence(final_board);
					for (int i = 0; i < sequence.size(); i++) {
						sequence.get(i).print();
					}

					System.out.println("Number of states visited: " + search.get_num_boards_visited());
					System.out.println("Number of moves: " + sequence.size());
					System.out.println("Time needed: " + search.get_time() / 1000F + " seconds");
					grid.setBoard(sequence.get(0).get_board());
					btnNextStep.setEnabled(true);
					step = 0;

				}
			}
		});
	}
	/**
	 * Add radio buttons group
	 */
	private void addButtonGroup(JRadioButton rdbtnBFS, JRadioButton rdbtnDFS, JRadioButton rdbtnAstar, JRadioButton rdbtnIterativeDeepening, JRadioButton rdbtnGreedy, JRadioButton rdbtnAstar2, JRadioButton rdbtnAstarBoth) {
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAstar);
		group.add(rdbtnBFS);
		group.add(rdbtnDFS);
		group.add(rdbtnIterativeDeepening);
		group.add(rdbtnGreedy);
		group.add(rdbtnAstar2);
		group.add(rdbtnAstarBoth);
	}

	/**
	 * Add Listener to Human Mode button
	 */
	private void humanButtonListener(JButton btnHuman) {
		btnHuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HumanUI();
				frame.dispose();
			}
		});
	}
}
