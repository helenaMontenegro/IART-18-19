package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Grid extends JPanel {
	private int[][] ini_board = null;
	private List<Point> fillCells;

	public Grid() {
		fillCells = new ArrayList<>(25);
	}

	public void setBoard(int[][] board) {
		this.ini_board = board;
		for (int i = 0; i < ini_board.length; i++) {
			for (int j = 0; j < ini_board[i].length; j++) {
				fillCell(i, j);
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Point fillCell : fillCells) {
			int cellX = 15 + (fillCell.x * 15);
			int cellY = 15 + (fillCell.y * 15);
			if (ini_board != null) {
				if (ini_board[fillCell.y][fillCell.x] == 0) {
					g.setColor(Color.WHITE);
				} else if (ini_board[fillCell.y][fillCell.x] == 1) {
					g.setColor(Color.BLUE);
				} else if (ini_board[fillCell.y][fillCell.x] % 2 == 0 && ini_board[fillCell.y][fillCell.x] != -2) {
					g.setColor(Color.RED);
				} else if (ini_board[fillCell.y][fillCell.x] == -2) {
					g.setColor(Color.DARK_GRAY);
				} else if (ini_board[fillCell.y][fillCell.x] == -1) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.GREEN);
				}
			}

			g.fillRect(cellX, cellY, 15, 15);
		}
		g.setColor(Color.BLACK);
		g.drawRect(15, 15, 195, 150);

		for (int i = 15; i <= 200; i += 15) {
			g.drawLine(i, 15, i, 165);
		}

		for (int i = 15; i <= 150; i += 15) {
			g.drawLine(15, i, 210, i);
		}
	}

	public void fillCell(int x, int y) {
		fillCells.add(new Point(x, y));
		repaint();
	}

}
