package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import logic.Board;

public class GameGraphicsPanel extends JPanel {
	
	private int[][] ini_board = null;
	private Board board;
	
    public void setBoard(int[][] board) {
        this.ini_board = board;
        System.out.println(ini_board.length);
        System.out.println(ini_board[0].length);
        System.out.println(ini_board[1].length);
        
    }

    private void drawBackGround(Graphics g) {
    	if(ini_board != null) {
    		for(int i = 0; i < ini_board.length;i++) {
    			for(int j = 0; j < ini_board[i].length; j++) {
    				g.drawRect(15 * i , 15 * j , 15, 15);
    			}
    		}
       
    	}
    }
    
    private void drawRectangles(Graphics g) {
    	if(ini_board != null) {
    		for(int i = 0; i < ini_board.length;i++) {
    			for(int j = 0; j < ini_board[i].length; j++) {
    				if(ini_board[i][j]  == 1) {
    					g.setColor(Color.BLUE);
    					g.fillRect(15 * i , 15 * j , 15, 15);
    				}
    				else if(ini_board[i][j] % 2 == 0) {
    					//g.setColor(Color.RED);
    					//g.fillRect(15 * i , 15 * j , 15, 15);
    				}
    				else {
    					//g.setColor(Color.GREEN);
    					//g.fillRect(15 * i , 15 * j , 15, 15);
    				}
    			}
    		}
       
    	}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRectangles(g);
        drawBackGround(g);
   
    }

   
/*
    private void drawDoors(Graphics g) {
        ArrayList<Door> doors = l.getDoors();
        for (Door d : doors) {
            if (d.isOpen()) {
                g.drawImage(doorOpenImage, d.getX() * doorOpenImage.getWidth(), d.getY() * doorOpenImage.getHeight(),
                        this);
            } else {
                g.drawImage(doorClosedImage, d.getX() * doorClosedImage.getWidth(),
                        d.getY() * doorClosedImage.getHeight(), this);
            }

        }

    }
*/
    

}