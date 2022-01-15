package Code.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.*;

import Code.Components.Orientation;
import Code.Components.Piece;
import Code.Solve.Checker;

/**
 * This class handles the GUI
 * 
 *
 */


public class GUI implements ActionListener {

	private JFrame frame;
	private int wp = 100; //width of the piece
	private int hp = 100; //height of the piece
	JButton[][] buttons;
	Grid grid;
	/**
	 *
	 * @param inputFile
	 *            String from IO
	 * @throws IOException
	 *             if there is a problem with the gui
	 */

	public static void startGUI(String inputFile) throws NullPointerException {
		Runnable task = new Runnable() {
			public void run() {
				try {
					Grid grid = Checker.buildGrid(inputFile);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							GUI window = null;
							try {
								window = new GUI(grid);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							window.frame.setVisible(true);
						}
					});
				} catch (IOException e) {
					throw new NullPointerException("Error with input file");
				}

			}
		};
		new Thread(task).start();

	}

	/**
	 * Create the application.
	 *
	 * @throws IOException
	 */
	public GUI(Grid grid) throws MalformedURLException {
		this.grid = grid;
		this.frame = new JFrame("Infinity Loops");
		this.buttons = new JButton[grid.getHeight()][grid.getWidth()];
		initializeRandomGrid();
	}


	private void initialize() {

	}

	//method to create a gui for a grid
	private void initializeRandomGrid() throws MalformedURLException {
		//initialize frame
		frame.setVisible(true);
		frame.getContentPane().setBackground( Color.white );
		frame.setSize(grid.getWidth() * wp,grid.getHeight() * hp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panel for buttons
		JPanel buttonspanel = new JPanel();
		buttonspanel.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));

		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {

				ImageIcon icon = new ImageIcon(this.getIcon(grid.getPiece(i, j)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
				JButton button = new JButton(icon);
				button.setBackground(Color.white);
				button.setOpaque(true);
				button.setBorderPainted(false);
				//button.setBackground(Color.white);

				buttonspanel.add(button);

				//buttonspanel.setBackground(Color.white);

				button.addActionListener(this);
				buttons[i][j] = button;
				//buttons[i][j].setBackground(Color.white);

			}
		}
		frame.setBackground(Color.white);
		frame.add(buttonspanel);
		frame.setVisible(true);
	}
	//rotate each piece
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				//we get the piece that was clicked on
				if (e.getSource() == buttons[i][j]) {
					//set the piece that was clicked on to the rotated piece in the grid
					grid.getPiece(i, j).turn();
					try {
						//for each piece that was clicked on we get the image of it and put it in the buttons
						ImageIcon icon = new ImageIcon(this.getIcon(grid.getPiece(i, j)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
						buttons[i][j].setIcon(icon);
					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		frame.repaint();
	}


	//get the image of each piece
	private ImageIcon getIcon(Piece p) throws MalformedURLException {
		String image = "";
		switch (p.getType()) {
			case VOID -> {
				image = "Dauphine_Java_Loop/src/main/resources/icons/background.png";
			}
			case ONECONN -> {
				switch (p.getOrientation()) {
					case NORTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/1.png";
					case EAST -> image = "Dauphine_Java_Loop/src/main/resources/icons/2.png";
					case SOUTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/3.png";
					case WEST -> image = "Dauphine_Java_Loop/src/main/resources/icons/4.png";
				}
			}
			case BAR -> {
				switch (p.getOrientation()) {
					case NORTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/5.png";
					case EAST -> image = "Dauphine_Java_Loop/src/main/resources/icons/6.png";
				}
			}
			case TTYPE -> {
				switch (p.getOrientation()) {
					case NORTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/7.png";
					case EAST -> image = "Dauphine_Java_Loop/src/main/resources/icons/8.png";
					case SOUTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/9.png";
					case WEST -> image = "Dauphine_Java_Loop/src/main/resources/icons/10.png";
				}
			}
			case FOURCONN -> {
				if (p.getOrientation() == Orientation.NORTH) {
					image = "Dauphine_Java_Loop/src/main/resources/icons/11.png";
				}
			}
			case LTYPE -> {
				switch (p.getOrientation()) {
					case NORTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/12.png";
					case EAST -> image = "Dauphine_Java_Loop/src/main/resources/icons/13.png";
					case SOUTH -> image = "Dauphine_Java_Loop/src/main/resources/icons/14.png";
					case WEST -> image = "Dauphine_Java_Loop/src/main/resources/icons/15.png";
				}
			}
		}
		return new ImageIcon(image);
	}


}