package Solve;


import java.util.ArrayList;
import java.util.Random;

import Components.Orientation;
import Components.Piece;
import Components.PieceType;
import GUI.Grid;
import java.io.*;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 * @param fileName,inputGrid
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void generateLevel(String fileName, Grid inputGrid) {
		Random rd = new Random();
		Piece[][] pieces = new Piece[inputGrid.getHeight()][inputGrid.getWidth()];
		for(int i = 0;i<inputGrid.getHeight();i++) {
			for (int j = 0; j <inputGrid.getWidth(); j++) {
				pieces[i][j] = new Piece(i,j);
				inputGrid.setPiece(i,j,pieces[i][j]);
			}
		}
		for(int i = 0;i<inputGrid.getHeight();i++) {
			for (int j = 0; j <inputGrid.getWidth(); j++) {
				int nb = inputGrid.numberOfNeighbours(pieces[i][j]);
				if(inputGrid.isCorner(i,j)){
					if(nb==0) {
						int temp = rd.nextInt(3);
						switch (temp) {
							case 0 -> {
								pieces[i][j].setType(PieceType.VOID);
							}
							case 1 -> {
								pieces[i][j].setType(PieceType.ONECONN);
								if (i == 0 && j == 0) {
									pieces[i][j].setOrientation(1+rd.nextInt(3));
								} else if (i == 0 && j == inputGrid.getWidth() - 1) {
									pieces[i][j].setOrientation(2);
								} else if (i == inputGrid.getHeight() - 1 && j == inputGrid.getWidth() - 1) {
									pieces[i][j].setType(PieceType.VOID);
								} else if (i == inputGrid.getHeight() - 1 && j == 0) {
									pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
									pieces[i][j].setOrientation(1);
								}
							}
							case 2 -> {
								pieces[i][j].setType(PieceType.LTYPE);
								if (i == 0 && j == 0) {
									pieces[i][j].setOrientation(1);
								} else if (i == 0 && j == inputGrid.getWidth() - 1) {
									pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
									pieces[i][j].setOrientation(2);
								} else if (i == inputGrid.getHeight() - 1 && j == inputGrid.getWidth() - 1) {
									pieces[i][j].setType(PieceType.VOID);
								} else if (i == inputGrid.getHeight() - 1 && j == 0) {
									pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
									pieces[i][j].setOrientation(1);
								}
							}
						}
					}else if(nb==1){
						if(i==0 && j==inputGrid.getWidth()-1){
							if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(2);
								if(temp == 0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(2);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(2);
							}
						}else if(i==inputGrid.getHeight()-1 && j==0){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								int temp = rd.nextInt(2);
								if(temp == 0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(0);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(0);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(1);
							}
						}else if(i==inputGrid.getHeight()-1 && j==inputGrid.getWidth()-1){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(0);
							}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(3);
							}
						}
					}else if(nb==2){
						if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(3);
							}else{
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(3);
							}
						}else if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
							pieces[i][j].setType(PieceType.ONECONN);
							pieces[i][j].setOrientation(0);
						}
					}
				}else if(inputGrid.isBorderColumn(i,j) || inputGrid.isBorderLine(i,j)){
					if(nb==0){
						int temp = rd.nextInt(3);
						if( (i==0 && j!=0) || (i!=0 && j==0) ){
							switch(temp){
								case 0 -> {
									pieces[i][j].setType(PieceType.VOID);
								}
								case 1 -> {
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(1+rd.nextInt(3));
								}
								case 2 -> {
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(1);
								}
							}
						}else if(i==inputGrid.getHeight()-1){
							pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
							pieces[i][j].setOrientation(1);
						}else if(j==inputGrid.getWidth()-1){
							pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
							pieces[i][j].setOrientation(2);
						}
					}else if(nb==1){
						if( i==0 && j!=0 ){
							if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(4);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(2);
								}else if(temp==2){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(1);
								}else if(temp==3){
									pieces[i][j].setType(PieceType.TTYPE);
									pieces[i][j].setOrientation(2);
								}

							}else{
								int temp = rd.nextInt(3);
								if(temp==0){
									pieces[i][j].setType(PieceType.VOID);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(1+rd.nextInt(3));
								}else if(temp==2){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(1);
								}
							}
						}else if( i!=0 && j==0 ){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								int temp = rd.nextInt(4);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(0);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(0);
								}else if(temp==2){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(0);
								}else if(temp==3){
									pieces[i][j].setType(PieceType.TTYPE);
									pieces[i][j].setOrientation(1);
								}
							}else{
								int temp = rd.nextInt(3);
								if(temp==0){
									pieces[i][j].setType(PieceType.VOID);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(1+rd.nextInt(3));
								}else if(temp==2){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(1);
								}
							}
						}else if( i==inputGrid.getHeight()-1  && j!=inputGrid.getWidth()-1){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(0);
								}
							}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(1);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(1);
							}
						}else if( i!=inputGrid.getHeight()-1  && j==inputGrid.getWidth()-1 ){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(0);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(0);
								}
							}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(2);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(2);
							}
						}
					}else if(nb==2){
						if( i==inputGrid.getHeight()-1 ){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
								if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
									int temp = rd.nextInt(2);
									if(temp==0){
										pieces[i][j].setType(PieceType.LTYPE);
										pieces[i][j].setOrientation(3);
									}else if(temp==1){
										pieces[i][j].setType(PieceType.TTYPE);
										pieces[i][j].setOrientation(0);
									}
								}else{
									int temp = rd.nextInt(2);
									if(temp==0){
										pieces[i][j].setType(PieceType.ONECONN);
										pieces[i][j].setOrientation(0);
									}else if(temp==1){
										pieces[i][j].setType(PieceType.LTYPE);
										pieces[i][j].setOrientation(0);
									}
								}
							}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(1);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(1);
							}
						}else if( j==inputGrid.getHeight()-1 ){
							if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)) {
								if (inputGrid.leftNeighbor(pieces[i][j]) != null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)) {
									int temp = rd.nextInt(2);
									if(temp==0){
										pieces[i][j].setType(PieceType.LTYPE);
										pieces[i][j].setOrientation(3);
									}else if(temp==1){
										pieces[i][j].setType(PieceType.TTYPE);
										pieces[i][j].setOrientation(3);
									}
								}else{
									int temp = rd.nextInt(2);
									if(temp==0){
										pieces[i][j].setType(PieceType.ONECONN);
										pieces[i][j].setOrientation(0);
									}else if(temp==1){
										pieces[i][j].setType(PieceType.BAR);
										pieces[i][j].setOrientation(0);
									}
								}
							}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(2);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(2);
								}
							}else{
								pieces[i][j].setType(PieceType.getTypeFromValue(rd.nextInt(2)));
								pieces[i][j].setOrientation(2);
							}
						}
					}
				}else{
					if(nb==0){
						int temp = rd.nextInt(3);
						if(temp==0){
							pieces[i][j].setType(PieceType.VOID);
						}else if(temp==1){
							pieces[i][j].setType(PieceType.ONECONN);
							pieces[i][j].setOrientation(1+rd.nextInt(3));
						}else if(temp==2){
							pieces[i][j].setType(PieceType.LTYPE);
							pieces[i][j].setOrientation(1);
						}
					}else if(nb==1){
						if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
							int temp = rd.nextInt(4);
							if(temp==0){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(0);
							}else if(temp==1){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(0);
							}else if(temp==2){
								pieces[i][j].setType(PieceType.TTYPE);
								pieces[i][j].setOrientation(1);
							}else if(temp==3){
								pieces[i][j].setType(PieceType.BAR);
								pieces[i][j].setOrientation(0);
							}
						}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
							int temp = rd.nextInt(4);
							if(temp==0){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(3);
							}else if(temp==1){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(2);
							}else if(temp==2){
								pieces[i][j].setType(PieceType.TTYPE);
								pieces[i][j].setOrientation(2);
							}else if(temp==3){
								pieces[i][j].setType(PieceType.BAR);
								pieces[i][j].setOrientation(1);
							}
						}else{
							int temp = rd.nextInt(3);
							if(temp==0){
								pieces[i][j].setType(PieceType.VOID);
							}else if(temp==1){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(1+rd.nextInt(3));
							}else if(temp==2){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(1);
							}
						}
					}else if(nb==2){
						if(inputGrid.topNeighbor(pieces[i][j])!=null && inputGrid.topNeighbor(pieces[i][j]).getConnectors().contains(Orientation.SOUTH)){
							if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
								int temp = rd.nextInt(3);
								if(temp==0){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(3);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.TTYPE);
									pieces[i][j].setOrientation(0);
								}else if(temp==2){
									pieces[i][j].setType(PieceType.FOURCONN);
									pieces[i][j].setOrientation(0);
								}
							}else{
								int temp = rd.nextInt(4);
								if(temp==0){
									pieces[i][j].setType(PieceType.ONECONN);
									pieces[i][j].setOrientation(0);
								}else if(temp==1){
									pieces[i][j].setType(PieceType.LTYPE);
									pieces[i][j].setOrientation(0);
								}else if(temp==2){
									pieces[i][j].setType(PieceType.TTYPE);
									pieces[i][j].setOrientation(1);
								}else if(temp==3){
									pieces[i][j].setType(PieceType.BAR);
									pieces[i][j].setOrientation(0);
								}
							}
						}else if(inputGrid.leftNeighbor(pieces[i][j])!=null && inputGrid.leftNeighbor(pieces[i][j]).getConnectors().contains(Orientation.EAST)){
							int temp = rd.nextInt(4);
							if(temp==0){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(3);
							}else if(temp==1){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(2);
							}else if(temp==2){
								pieces[i][j].setType(PieceType.TTYPE);
								pieces[i][j].setOrientation(2);
							}else if(temp==3){
								pieces[i][j].setType(PieceType.BAR);
								pieces[i][j].setOrientation(1);
							}
						}else{
							int temp = rd.nextInt(3);
							if(temp==0){
								pieces[i][j].setType(PieceType.VOID);
							}else if(temp==1){
								pieces[i][j].setType(PieceType.ONECONN);
								pieces[i][j].setOrientation(1+rd.nextInt(3));
							}else if(temp==2){
								pieces[i][j].setType(PieceType.LTYPE);
								pieces[i][j].setOrientation(1);
							}
						}
					}
				}
				inputGrid.setPiece(i,j,pieces[i][j]);
			}
		}

		//SHUFFLE
		for(int i=0;i<inputGrid.getHeight();i++){
			for(int j=0;j<inputGrid.getWidth();j++){
				pieces[i][j].setOrientation(rd.nextInt(4));
				inputGrid.setPiece(i,j,pieces[i][j]);
			}
		}
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter(fileName);
			myWriter.write(inputGrid.getWidth()+"\n");
			myWriter.write(inputGrid.getHeight()+"\n");
			for(int i = 0;i<inputGrid.getHeight();i++) {
				for (int j = 0; j <inputGrid.getWidth(); j++) {
					StringBuilder str = new StringBuilder();
					str.append(+Piece.getIntTypeFromPiece(pieces[i][j])+" "+Orientation.getValueFromOri(pieces[i][j].getOrientation())+"\n");
					myWriter.write(str.toString());
				}
			}
			myWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		// To be implemented
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