package Code.Solve;


import Code.Components.PieceType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import Code.Components.Orientation;
import Code.Components.Piece;
import Code.GUI.Grid;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	//This method copy a grid into another grid
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

	//this method generate a new solvable grid
	public static Grid generateLevel1(String fileName, Grid inputGrid) {

		Objects.requireNonNull(fileName);
		Objects.requireNonNull(inputGrid);

		Random r = new Random();
		//get the dimensions from the grid
		int h = inputGrid.getHeight();
		int w = inputGrid.getWidth();
		int nbc = inputGrid.getNbcc();

		//set the new Height
		int newHeight = h * 2 - 1;
		int[][] input = new int[newHeight][w];

		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < w; j++) {
				if (r.nextInt(0, 2) == 0) {
					input[i][j] = 0;
				} else {
					input[i][j] = 1;
				}
			}
		}
		return generateLevel(newHeight,w,h,input,fileName,inputGrid);
	}

	public static Grid generateLevel( int newHeight, int w, int h, int[][] input, String fileName, Grid inputGrid ){
		Random r = new Random();
		for (int i = 0; i < newHeight; i+=2) {
			for (int j = 0; j < w; j++) {
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				int nb = 0;

				if (j > 0 && input[i][j - 1] == 1) {
					east = true;
					nb++;
				}

				if (i > 0 && input[i - 1][j] == 1) {
					north = true;
					nb++;
				}
				if (i < h * 2 - 2 && input[i + 1][j] == 1) {
					south = true;
					nb++;
				}


				if (j < w - 1 && input[i][j] == 1) {
					west = true;
					nb++;
				}

				Piece piece = null;
				switch (nb) {
					case 0:
						piece = new Piece(i, j, PieceType.VOID, Orientation.WEST);
						break;
					case 1:
						piece = new Piece(i, j, PieceType.ONECONN, Orientation.WEST);
						break;
					case 2:
						if ((north && south) || (east && west))
							piece = new Piece(i, j, PieceType.BAR, Orientation.WEST);
						else
							piece = new Piece(i, j, PieceType.LTYPE, Orientation.WEST);
						break;
					case 3:
						piece = new Piece(i, j, PieceType.TTYPE, Orientation.WEST);
						break;
					case 4:
						piece = new Piece(i, j, PieceType.FOURCONN, Orientation.WEST);
						break;
				}
				piece.setOrientation(r.nextInt(0, 4));
				inputGrid.setPiece(i/2, j, piece);
			}
		}
		writeGrid(fileName, inputGrid);
		return inputGrid;
	}
	//this method write a grid into a file
	public static void writeGrid(String name, Grid grid) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(grid);
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
					String value = Piece.getIntPiece(pieces[i][j])+" "+pieces[i][j].getOrientation().getValue()+"\n";
					myWriter.write(value);
				}
			}
			myWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}