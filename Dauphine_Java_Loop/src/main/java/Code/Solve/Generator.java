package Code.Solve;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Code.Components.Orientation;
import Code.Components.Piece;
import Code.Components.PieceType;
import Code.GUI.Grid;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	public static Grid levelgenerator(String fname, int w, int h, int n) {
		Random random = new Random();
		Grid grid = new Grid(w, h, n);
		int nbc = 0;
		int i1 = h * 2 - 1;
		int[][] lines = new int[h * 2 - 1][w];

		for (int i = 0; i < h * 2 - 1; i++) {
			for (int j = 0; j < w; j++) {
				if (random.nextInt(0, 2) == 1) {
					lines[i][j] = 1;
				} else {
					lines[i][j] = 0;
				}
			}
		}

		for (int i = 0; i < h * 2 - 1; i+=2) {
			for (int j = 0; j < w; j++) {
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				int nbConnectors = 0;
				if (j < w - 1 && lines[i][j] == 1) {
					west = true;
					nbConnectors++;
				}
				if (i > 0 && lines[i - 1][j] == 1) {
					north = true;
					nbConnectors++;
				}
				if (i < h * 2 - 2 && lines[i + 1][j] == 1) {
					south = true;
					nbConnectors++;
				}
				if (j > 0 && lines[i][j - 1] == 1) {
					east = true;
					nbConnectors++;
				}

				Piece piece = null;
				switch (nbConnectors) {
					case 0:
						piece = new Piece(i, j, PieceType.VOID, Orientation.NORTH);
						break;
					case 1:
						piece = new Piece(i, j, PieceType.ONECONN, Orientation.NORTH);
						break;
					case 2:
						if ((north && south) || (east && west))
							piece = new Piece(i, j, PieceType.BAR, Orientation.NORTH);
						else
							piece = new Piece(i, j, PieceType.LTYPE, Orientation.NORTH);
						break;
					case 3:
						piece = new Piece(i, j, PieceType.TTYPE, Orientation.NORTH);
						break;
					case 4:
						piece = new Piece(i, j, PieceType.FOURCONN, Orientation.NORTH);
						break;
				}
				piece.setOrientation(random.nextInt(0, 4));
				grid.setPiece(i/2, j, piece);
			}
		}
		writeGrid(fname, grid);
		return grid;
	}


	/**
	 * @param inputGrid
	 *            file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 */


	public static void writeGrid(String fileName, Grid inputGrid) {
		try {
			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write(inputGrid.getWidth()+"\n");
			myWriter.write(inputGrid.getHeight()+"\n");
			Piece[][] pieces = inputGrid.getPieces();
			for(int i = 0; i < inputGrid.getHeight(); i++) {
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					StringBuilder str = new StringBuilder();
					str.append(Piece.getIntTypeFromPiece(pieces[i][j])+" "+pieces[i][j].getOrientation().getValue()+"\n");
					myWriter.write(str.toString());
				}
			}
			myWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

}