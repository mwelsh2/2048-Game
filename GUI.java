import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	public JPanel board, count;
	public static JLabel moves, max;
	public static JLabel[] cells;
	static int max_value;
	public static boolean win = false;
	
	static App app;
	
	public GUI() {
		setupBoard();
		app = new App();
		this.addKeyListener(this);
		setFocusable(true);
	}
	
	/**
	 *  Initializes graphical components of the playing board
	 */
	public void setupBoard() {
		// Set board parameters
		board = new JPanel(new GridLayout(4,4));
		board.setPreferredSize(new Dimension(500,500));
		
		cells = new JLabel[16];
		
		for (int i = 0; i < 16; i ++) {
			// Set basic cell parameters
			cells[i] = new JLabel("");
			cells[i].setFont(new Font("", Font.BOLD, 40));
			cells[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 6));
			cells[i].setHorizontalAlignment(JLabel.CENTER);
			board.add(cells[i]);
			
		}
		
		this.getContentPane().add(BorderLayout.CENTER, board);
		
		// Set count panel parameters
		count = new JPanel();
		count.setPreferredSize(new Dimension(500,50));
		count.setLayout(new FlowLayout());
		this.getContentPane().add(BorderLayout.NORTH, count);
		moves = new JLabel("Moves: 0 \t \t");
		moves.setFont(new Font("", Font.PLAIN, 40));
		moves.setAlignmentX(Component.CENTER_ALIGNMENT);
		count.add(moves);
		max = new JLabel("Max: ");
		max.setFont(new Font("", Font.PLAIN, 40));
		max.setAlignmentX(Component.CENTER_ALIGNMENT);
		count.add(max);
		
		// Set size, close operations, and other Frame parameters
		this.setTitle("2048");
		this.setSize(500, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * Maps current array onto the GUI
	 */
	public static void printCurrent() {
		int [][] board = app.getBoard();
		max_value = board[0][0];
		
		// Sets JLabel text to values
		for (int i = 0; i < 16; i++) {
			if(board[i / 4][i % 4] == 0) {
				cells[i].setText("");
			} else {
				int value = board[i / 4][i % 4];
				cells[i].setText(Integer.toString(value));	
			}
			
			if (cells[i].getText().equals("2") || cells[i].getText().equals("4")) {
				cells[i].setForeground(Color.GRAY);
			} else {
				cells[i].setForeground(Color.WHITE);
			}
			
			// Set colors of each integer value
			switch (cells[i].getText()) {
			case "": cells[i].setBackground(Color.LIGHT_GRAY);
				break;
			case "2": cells[i].setBackground(new Color(255, 255, 255));
				break;
			case "4": cells[i].setBackground(new Color(238, 220, 191));
				break;
			case "8": cells[i].setBackground(new Color(238, 159, 98));
				break;
			case "16": cells[i].setBackground(new Color(233, 127, 54));
				break;
			case "32": cells[i].setBackground(new Color(255, 115, 106));
				break;
			case "64": cells[i].setBackground(new Color(255, 63, 39));
				break;
			case "128": cells[i].setBackground(new Color(255, 234, 136));
				break;
			case "256": cells[i].setBackground(new Color(255, 224, 122));
				break;
			case "512": cells[i].setBackground(new Color(255, 224, 87));
				break;
			case "1024": cells[i].setBackground(new Color(255, 224, 66));
				break;
			case "2048": cells[i].setBackground(new Color(255, 190, 24));
				break;
			}
			cells[i].setOpaque(true);
			// Update Moves counter and Max cell value
			moves.setText("Moves: " + Integer.toString(app.getMoves()) + "\t\t");
			max.setText("Max: " + Integer.toString(app.getMax()));
			// Checks if user has won the game
			if (app.getMax() == 2048) {
				win = true;
			}
		}
		
	}

	/**
	 *  Resets the GUI if user restarts the game
	 */
	public static void restart() {
		int[][] board = new int[4][4];
		app.setBoard(board);
		app.addNumber();
		app.addNumber();
		app.empty = 14;
		app.moves = 0;
		printCurrent();
	}
	
	/**
	 *  Prints message to signal the user has won
	 */
	public void printWin() {
		JOptionPane.showMessageDialog(this, "YOU WIN \n Final Score: " + app.getMoves());
		System.exit(0);
	}
	
	/**
	 *  Prints message to signal the user has lost
	 */
	public void printOver() {
		JOptionPane.showMessageDialog(this, "GAME OVER \n Final Score: " + app.getMoves() + "\n Max: " + app.getMax()); 
		System.exit(0);
	}

	/**
	 * Methods for KeyListener
	 * When an arrow or a, w, s, d key is pressed, it checks to see if the game
	 * is over or if the game has been won. If neither of these cases is true,
	 * then it maps the current array on the GUI
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_R : // Restart
			System.out.print("R key pressed");
			int a = JOptionPane.showConfirmDialog(this, "Are you sure you want to restart?", "", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION) {
				restart();
			}
			break;
		case KeyEvent.VK_Q : // Quit
			System.out.print("Q key pressed");
			int b = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION);
			if (b == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
	
			break;
		case KeyEvent.VK_UP: // Move Up
		case KeyEvent.VK_W:
			System.out.print("Up");
			app.move("up");
			if (app.gameOver()) {
				printCurrent();
				printOver();
			} else if (app.won()) {
				printCurrent();
				printWin();
			} else {
				printCurrent();
			}
			break;
		case KeyEvent.VK_DOWN: // Move Down
		case KeyEvent.VK_S:
			System.out.print("Down");
			app.move("down");
			if (app.gameOver()) {
				printCurrent();
				printOver();
			} else if (app.won()) {
				printCurrent();
				printWin();
			} else {
				printCurrent();
			}
			break;
		case KeyEvent.VK_RIGHT: // Move Right
		case KeyEvent.VK_D:
			System.out.print("Right");
			app.move("right");
			if (app.gameOver()) {
				printCurrent();
				printOver();
			} else if (app.won()) {
				printCurrent();
				printWin();
			} else {
				printCurrent();
			}
			break;
		case KeyEvent.VK_LEFT: // Move Left
		case KeyEvent.VK_A:
			System.out.print("Left");
			app.move("left");
			if (app.gameOver()) {
				printCurrent();
				printOver();
			} else if (app.won()) {
				printCurrent();
				printWin();
			} else {
				printCurrent();
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		restart();
	}
}