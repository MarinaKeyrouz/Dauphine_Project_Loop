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
	JButton[][] places;
	Grid grid;
	/**
	 *
	 * @param inputFile
	 *            String from IO
	 * @throws IOException
	 *             if there is a problem with the gui
	 */
	public static void startGUI(String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// construction
		Runnable task = new Runnable() {
			public void run() {

				try {
					Grid grid = Checker.readGrid(inputFile);
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
		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @throws IOException
	 */
	private void initialize(Grid grid) throws MalformedURLException {
		frame = new JFrame("Infinity Loops");
		frame.setVisible(true);
		frame.getContentPane().setBackground( Color.white );
		frame.setSize(grid.getWidth() * wp,grid.getHeight() * hp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		places = new JButton[grid.getHeight()][grid.getWidth()];
		JPanel panelForButtons = new JPanel();
		panelForButtons.setLayout(new GridLayout(grid.getHeight(), grid.getWidth()));

		for (int line = 0; line < grid.getHeight(); line++) {
			for (int column = 0; column < grid.getWidth(); column++) {

				Icon icon = new ImageIcon(this.getImageIcon(grid.getPiece(line, column)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
				JButton temp = new JButton(icon);
				temp.setBackground(Color.white);
				temp.setOpaque(true);
				temp.setBorderPainted(false);
				temp.setBackground(Color.white);

				panelForButtons.add(temp);

				panelForButtons.setBackground(Color.white);

				temp.addActionListener(this);
				places[line][column] = temp;
				places[line][column].setBackground(Color.white);

			}
		}
		frame.setBackground(Color.white);
		frame.add(panelForButtons);
		frame.setVisible(true);
	}

	/**
	 * Display the correct image from the piece's type and orientation
	 *
	 * @param p
	 *            the piece
	 * @return an image icon
	 */
	private ImageIcon getImageIcon(Piece p) throws MalformedURLException {
		String image = "";
//		System.out.println(p.getType());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				if (e.getSource() == places[i][j]) {
					grid.getPiece(i, j).turn();
					try {
						Icon icon = new ImageIcon(this.getImageIcon(grid.getPiece(i, j)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
						places[i][j].setIcon(icon);
					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		frame.repaint();
	}
}