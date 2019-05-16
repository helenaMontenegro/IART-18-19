package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.java.swing.plaf.windows.resources.windows;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mancala");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 39));
		lblNewLabel.setBounds(140, 11, 209, 48);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnHumanVsHuman = new JButton("Human Vs Human");
		btnHumanVsHuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HumanVsHuman new_window = new HumanVsHuman();
				new_window.frame.setVisible(true);
			}
		});
		btnHumanVsHuman.setBounds(140, 90, 142, 35);
		frame.getContentPane().add(btnHumanVsHuman);
		
		JButton btnPcVsPc = new JButton("PC Vs PC");
		btnPcVsPc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPcVsPc.setBounds(140, 146, 142, 35);
		frame.getContentPane().add(btnPcVsPc);
		
		JButton btnHumanVsPc = new JButton("Human Vs PC");
		btnHumanVsPc.setBounds(140, 200, 142, 35);
		frame.getContentPane().add(btnHumanVsPc);
	}
}
