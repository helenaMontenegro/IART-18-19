package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HumanVsPc_Config {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HumanVsPc_Config window = new HumanVsPc_Config();
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
	public HumanVsPc_Config() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 434, 397);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("PC 1");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(45, 11, 96, 42);
		frame.getContentPane().add(lblNewLabel);
		
		JRadioButton rdbtnBeginner_1 = new JRadioButton("Beginner");
		rdbtnBeginner_1.setBounds(45, 76, 109, 23);
		frame.getContentPane().add(rdbtnBeginner_1);
		
		JRadioButton rdbtnMedium_1 = new JRadioButton("Medium");
		rdbtnMedium_1.setBounds(45, 102, 109, 23);
		frame.getContentPane().add(rdbtnMedium_1);
		
		JRadioButton rdbtnProfessional_1 = new JRadioButton("Professional ");
		rdbtnProfessional_1.setBounds(45, 128, 109, 23);
		frame.getContentPane().add(rdbtnProfessional_1);
		
		JLabel lblNewLabel_1 = new JLabel("Difficulty");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(45, 55, 78, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblHeuristic = new JLabel("Heuristic");
		lblHeuristic.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHeuristic.setBounds(45, 158, 78, 14);
		frame.getContentPane().add(lblHeuristic);
		
		JLabel lblMini = new JLabel("MiniMax Variant");
		lblMini.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMini.setBounds(45, 245, 115, 14);
		frame.getContentPane().add(lblMini);
		
		JRadioButton rdbtnHeuristic1_1 = new JRadioButton("Number of game pieces in player area");
		rdbtnHeuristic1_1.setBounds(41, 189, 345, 23);
		frame.getContentPane().add(rdbtnHeuristic1_1);
		
		JRadioButton rdbtnHeuristic2_1 = new JRadioButton("Difference between the mancalas of the players");
		rdbtnHeuristic2_1.setBounds(41, 215, 345, 23);
		frame.getContentPane().add(rdbtnHeuristic2_1);
		
		JRadioButton rdbtnHeuristic1_2 = new JRadioButton("Simple");
		rdbtnHeuristic1_2.setBounds(45, 266, 192, 23);
		frame.getContentPane().add(rdbtnHeuristic1_2);
		
		JRadioButton rdbtnHeuristic2_2 = new JRadioButton("With alpha beta pruning  ");
		rdbtnHeuristic2_2.setBounds(45, 292, 230, 23);
		frame.getContentPane().add(rdbtnHeuristic2_2);
		
		ButtonGroup Difficulty_1 = new ButtonGroup();
		Difficulty_1.add(rdbtnBeginner_1);
		Difficulty_1.add(rdbtnMedium_1);
		Difficulty_1.add(rdbtnProfessional_1);
		
		ButtonGroup Difficulty_2 = new ButtonGroup();
		
		ButtonGroup Heuristic_1 = new ButtonGroup();
		Heuristic_1.add(rdbtnHeuristic1_1);
		Heuristic_1.add(rdbtnHeuristic2_1);
		
		ButtonGroup Heuristic_2 = new ButtonGroup();
		Heuristic_2.add(rdbtnHeuristic1_2);
		Heuristic_2.add(rdbtnHeuristic2_2);
		
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int difficulty_1;
				int heuristic_1;
				boolean alpha_beta;
				
				if(rdbtnBeginner_1.isSelected()) {
					difficulty_1 = 1;
				} else if(rdbtnMedium_1.isSelected()) {
					difficulty_1 = 2;
				} else if(rdbtnProfessional_1.isSelected()) {
					difficulty_1 = 3;
				} else return;
				
				
				if(rdbtnHeuristic1_1.isSelected()) {
					heuristic_1 = 1;
				} else if(rdbtnHeuristic2_1.isSelected()) {
					heuristic_1 = 2;
				} else return;
				
				if(rdbtnHeuristic1_2.isSelected()) {
					alpha_beta = false;
				} else if(rdbtnHeuristic2_2.isSelected()) {
					alpha_beta = true;
				} else return;
				
				HumanVsPC new_window_PC = new HumanVsPC(difficulty_1,heuristic_1,alpha_beta);
				new_window_PC.frame.setVisible(true);
				
			}
		});
		btnStart.setBounds(307, 311, 69, 36);
		frame.getContentPane().add(btnStart);
	}
}
