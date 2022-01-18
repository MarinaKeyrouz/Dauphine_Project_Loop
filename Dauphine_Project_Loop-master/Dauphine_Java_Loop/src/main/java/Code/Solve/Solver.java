package Code.Solve;

import Code.Components.Orientation;
import Code.Components.Piece;
import Code.Components.PieceType;
import Code.GUI.Grid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static Code.Solve.Checker.isSolved;
import static Code.Solve.Generator.writeGrid;

public class Solver {
	private static Grid inputGrid;
	private static Piece actualPiece;
	private static Queue<Grid> frontier;

	public static void initAll(Grid grid){
		inputGrid = grid;
		actualPiece = inputGrid.getPiece(0,0);
		frontier = new LinkedList<Grid>();
		frontier.add(inputGrid);
	}

	public static Grid solve(Grid grid) {
		initAll(grid);
		int i = 0;
		int j = 0;
		Grid current = frontier.remove();
		int cpt = 1;
		int valSuiv = 1;
		while (!isSolved(current)) {
			ArrayList<Grid> listSucc = current.expand(i, j);
			if (listSucc.size()==0){
				if (j < inputGrid.getWidth() - 1) {
					j++;
					actualPiece = inputGrid.getPiece(i, j);
				} else {
					if (i < inputGrid.getHeight() - 1) {
						i++;
						j = 0;
						actualPiece = inputGrid.getPiece(i, j);
					}
				}
				continue;
			}
			for (int k = 0; k < listSucc.size(); k++) {
				Grid node = listSucc.get(k);
				frontier.add(node);
			}
			if (cpt == valSuiv) {
				valSuiv = valSuiv * actualPiece.getPossibleOrientations().size();
				cpt = 0;
				if (j < inputGrid.getWidth() - 1) {
					j++;
					actualPiece = inputGrid.getPiece(i, j);
				} else {
					if (i < inputGrid.getHeight() - 1) {
						i++;
						j = 0;
						actualPiece = inputGrid.getPiece(i, j);
					}
				}


			}
			current = frontier.remove();
			cpt++;

		}
		writeGrid("File", current);
		return current;
	}


	public static String afficherfrontier(){
		String res="";
		int i = 0;
		for (Grid g : frontier) {
			res += g.toString() + "\n";
		}
		return res;
	}
}
