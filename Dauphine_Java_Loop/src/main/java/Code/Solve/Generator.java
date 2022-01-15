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

	//This method copy a grid into another grid
	public static int[] copyGrid(Grid copy, Grid input, int i, int j) {
		Piece p;
		int h = input.getHeight();
		int w = input.getWidth();
	//if both grids don't have the same height we change the size of our copied file to match the input
		if (input.getHeight() != copy.getHeight())
			h = copy.getHeight() + i;
		if (input.getWidth() != copy.getWidth())
			w = copy.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		for (int x = i; x < h; x++) {
			for (int y = j; y < w; y++) {
				p = copy.getPiece(x - i, y - j);
				input.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				tmpj = y;
			}
			tmpi = x;
		}
		return new int[] { tmpi, tmpj };
	}

	//this method write a grid into a file
	public static void writeGrid(String name, Grid grid) {
		try {
			//initialize FileWriter
			FileWriter myWriter = new FileWriter(name);
			//Write the size (width and height) of the grid first
			myWriter.write(grid.getWidth()+"\n");
			myWriter.write(grid.getHeight()+"\n");
			Piece[][] pieces = grid.getPieces();
			//Loop over the grid
			for(int i = 0; i < grid.getHeight(); i++) {
				for (int j = 0; j < grid.getWidth(); j++) {
					//for each piece in the grid write the value of it in the file
					String value = Piece.getIntTypeFromPiece(pieces[i][j])+" "+pieces[i][j].getOrientation().getValue()+"\n";
					myWriter.write(value);
				}
			}
			myWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	//this method generate a new solvable grid
	public static Grid generator(String fname, Grid grid) {
		Random r = new Random();
		//get the dimensions from the grid
		int h =grid.getHeight();
		int w =grid.getWidth();
		int nbc = grid.getNbcc();

		//set the new Height
		int newHeight = h * 2 - 1;
		int[][] input = new int[newHeight][w];

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < w; j++) {
				if (r.nextInt(0, 2) == 1) {
					input[i][j] = 1;
				} else {
					input[i][j] = 0;
				}
			}
		}

		for (int i = 0; i < newHeight; i+=2) {
			for (int j = 0; j < w; j++) {
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				int nbConnectors = 0;
				if (j < w - 1 && input[i][j] == 1) {
					west = true;
					nbConnectors++;
				}
				if (i > 0 && input[i - 1][j] == 1) {
					north = true;
					nbConnectors++;
				}
				if (i < h * 2 - 2 && input[i + 1][j] == 1) {
					south = true;
					nbConnectors++;
				}
				if (j > 0 && input[i][j - 1] == 1) {
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
				piece.setOrientation(r.nextInt(0, 4));
				grid.setPiece(i/2, j, piece);
			}
		}
		writeGrid(fname, grid);
		return grid;
	}
}