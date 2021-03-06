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
	Checker check;
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
		this.frame = new JFrame("Infinity Loops");
		this.buttons = new JButton[grid.getHeight()][grid.getWidth()];
		initializeRandomGrid();
	}


//	private void initialize() throws MalformedURLException {
//
//		//panel for buttons
//		JOptionPane.showMessageDialog(frame, "You Win!", "Winner chicken dinner", JOptionPane.PLAIN_MESSAGE);
//	}

	//method to create a gui for a grid
	private void initializeRandomGrid() throws MalformedURLException {
		check = new Checker();
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

				ImageIcon icon = new ImageIcon(this.getImage(grid.getPiece(i, j)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
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

//		Grid grid2 = Checker.buildGrid();
//		System.out.println(grid2);
//		System.out.println(Checker.isSolved(grid2));
	}

	int clicked=0;
	//rotate each piece using an action
	@Override
	public void actionPerformed(ActionEvent e) {
		clicked++;
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				//we get the piece that was clicked on
				boolean done=false;
				if (e.getSource() == buttons[i][j]) {
					//set the piece that was clicked on to the rotated piece in the grid
					grid.getPiece(i, j).turn();
					try {
						//for each piece that was clicked on we get the image of it and put it in the buttons
						ImageIcon icon = new ImageIcon(this.getImage(grid.getPiece(i, j)).getImage().getScaledInstance(wp, hp, Image.SCALE_SMOOTH));
						buttons[i][j].setIcon(icon);
						done= check.isSolved(grid);
					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
					if(done ==true){
						JOptionPane.showMessageDialog(frame, "You Win! You managed to win after: " + clicked +" clicked", "Winner winner chicken dinner", JOptionPane.PLAIN_MESSAGE);
						break;
					}
				}
			}
		}

		frame.repaint();
	}


	//get the image of each piece
	private ImageIcon getImage(Piece p) throws MalformedURLException {
		String img = "";
		switch (p.getType()) {
			case VOID -> {
				img = "Dauphine_Java_Loop/src/main/resources/icons/background.png";
			}
			case TTYPE -> {
				switch (p.getOrientation()) {
					case NORTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/7.png";
					case EAST -> img = "Dauphine_Java_Loop/src/main/resources/icons/8.png";
					case SOUTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/9.png";
					case WEST -> img = "Dauphine_Java_Loop/src/main/resources/icons/10.png";
				}
			}

			case FOURCONN -> {
				if (p.getOrientation() == Orientation.NORTH) {
					img = "Dauphine_Java_Loop/src/main/resources/icons/11.png";
				}
			}

			case ONECONN -> {
				switch (p.getOrientation()) {
					case NORTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/1.png";
					case EAST -> img = "Dauphine_Java_Loop/src/main/resources/icons/2.png";
					case SOUTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/3.png";
					case WEST -> img = "Dauphine_Java_Loop/src/main/resources/icons/4.png";
				}
			}
			case BAR -> {
				switch (p.getOrientation()) {
					case NORTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/5.png";
					case EAST -> img = "Dauphine_Java_Loop/src/main/resources/icons/6.png";
				}
			}
			case LTYPE -> {
				switch (p.getOrientation()) {
					case NORTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/12.png";
					case EAST -> img = "Dauphine_Java_Loop/src/main/resources/icons/13.png";
					case SOUTH -> img = "Dauphine_Java_Loop/src/main/resources/icons/14.png";
					case WEST -> img = "Dauphine_Java_Loop/src/main/resources/icons/15.png";
				}
			}
		}
		return new ImageIcon(img);
	}


}