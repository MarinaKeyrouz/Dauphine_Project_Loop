package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import Components.Orientation;
import Components.Piece;
import Solve.Checker;

/**
 * This class handles the GUI
 *
 *
 */
public class GUI {

	private JFrame frame;

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

				Grid grid = Checker.buildGrid(inputFile);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GUI window;
						window = new GUI(grid);
						window.frame.setVisible(true);
					}
				});

			}
		};
		new Thread(task).start();

	}

	/**
	 * Create the application.
	 *
	 * @throws IOException
	 */
	public GUI(Grid grid) {

		initialize(grid);
	}

	/**
	 * Initialize the contents of the frame.
	 *
	 * @throws IOException
	 */
	private void initialize(Grid grid) {
		Piece[][] pieces = grid.getAllPieces();
		int height = grid.getHeight();
		int width = grid.getWidth();
		this.frame = new JFrame("InfinityLoop");
		this.frame.setSize((70*height),(70*width));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new GridLayout(height,width));
		JLabel[][] img = new JLabel[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				int i1 = i;
				int j1 = j;
				img[i][j] = new JLabel(getImageIcon(pieces[i][j]));
				img[i][j].addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						pieces[i1][j1].setOrientation(Orientation.getValueFromOri(pieces[i1][j1].getOrientation().turn90()));
						img[i1][j1].setIcon(getImageIcon(pieces[i1][j1]));
						grid.setPiece(i1,j1,pieces[i1][j1]);
						System.out.println(grid.allPieceConnected());
					}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
				});
				this.frame.add(img[i][j]);
			}
		}

		this.frame.setVisible(true);

	}
	/*private class MListener implements MouseListener {
		private Piece piece;
		private JLabel label;
		public MListener(Piece piece, JLabel label){
			this.piece = piece;
			this.label = label;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("KHRA");
			this.piece.setOrientation(Orientation.getValueFromOri(this.piece.getOrientation().turn90()));
			this.label.setIcon(getImageIcon(this.piece));
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}*/
	/**
	 * Display the correct image from the piece's type and orientation
	 *
	 * @param p
	 *            the piece
	 * @return an image icon
	 */
	private ImageIcon getImageIcon(Piece p) {
		switch(p.getType()){
			case VOID -> {
			}
			case ONECONN -> {
				switch(p.getOrientation()){
					case NORTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/1.png");
					}
					case SOUTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/3.png");
					}
					case EAST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/2.png");
					}
					case WEST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/4.png");
					}
				}
			}
			case BAR -> {
				switch(p.getOrientation()){
					case NORTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/5.png");
					}
					case EAST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/6.png");
					}
				}
			}
			case TTYPE -> {
				switch (p.getOrientation()){
					case NORTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/7.png");
					}
					case SOUTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/9.png");
					}
					case EAST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/8.png");
					}
					case WEST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/10.png");
					}
				}
			}
			case FOURCONN -> {
				return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/11.png");
			}
			case LTYPE -> {
				switch(p.getOrientation()){
					case NORTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/12.png");
					}
					case SOUTH -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/14.png");
					}
					case EAST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/13.png");
					}
					case WEST -> {
						return new ImageIcon("InfinityLoop/src/main/resources/fr/dauphine/JavaAvance/icons/io/15.png");
					}
				}
			}
		}
		return null;
	}

}
