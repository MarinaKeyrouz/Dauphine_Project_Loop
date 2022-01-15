package Code.Solve;


import Code.Components.Orientation;
import Code.Components.Piece;
import Code.Components.PieceType;
import Code.GUI.Grid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class Checker {
    private static boolean[][] isPieceChecked;

    public static void initIsPieceChecked(Grid grille) {
        isPieceChecked = new boolean[grille.getHeight()][grille.getWidth()];
        for (int i = 0; i < grille.getHeight(); i++) {
            for (int j = 0; j < grille.getWidth(); j++) {
                isPieceChecked[i][j] = false;
            }
        }
    }

    public static Grid readGrid(String inputFile) throws FileNotFoundException {
        BufferedReader fr = new BufferedReader(new FileReader(inputFile));
        try {
            Grid grille = new Grid(Integer.parseInt(fr.readLine()), Integer.parseInt(fr.readLine()));
            for (int i = 0; i < grille.getHeight(); i++) {
                for (int j = 0; j < grille.getWidth(); j++) {
                    String[] str = fr.readLine().split(" ");
                    if (str.length != 0) {
                        grille.setPiece(i, j, new Piece(i, j, PieceType.getTypeFromValue(Integer.parseInt(str[0])), Orientation.getOriFromValue(Integer.parseInt(str[1]))));
                    }
                }
            }
            return grille;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // the method checks the whole grid is solved and initialized IsPieceChecked[i][j] = true
    // for each well_connected piece with coordinates = (i, j)
    public static boolean isSolved(Grid grille /*pass Grid readGrid result as arg*/) {
        initIsPieceChecked(grille);
        boolean solved =  true; // is true is the whole grid is solved
        for (int i = 0; i < grille.getHeight(); i++) {
            for (int j = 0; j < grille.getWidth(); j++) {
                System.out.println(grille.getPiece(i, j));
                // We check piece by piece if their connectors match
                LinkedList<Orientation> currConnectors = new LinkedList<>(grille.getPiece(i, j).getConnectors());
                boolean matchesAllItsNeighbors = true; // is true if the piece (i, j) matches all of its neighbors.
                for (Orientation ori : currConnectors) {
                    boolean matched = false; // is true is the current connector of the piece(i, j) checked matches its connector's neighbor.
                    switch (ori.getValue()) {
                        // NORTH, we check if the piece above has a connector SOUTH
                        case 0:
                            try {
                                for (Orientation oriNeighbor : grille.getPiece(i - 1, j).getConnectors()) {
                                    if (oriNeighbor.getValue() == 2) {
                                        matched = true;
                                    }
                                }
                                // if no matched connector had been found, return false
                                if (matched == false) {
                                    solved = false;
                                    matchesAllItsNeighbors = false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                solved = false;
                                matchesAllItsNeighbors = false;
                            }
                            break;
                        // EAST, we check if the piece on the right has a connector WEST
                        case 1:
                            try {
                                for (Orientation oriNeighbor : grille.getPiece(i, j + 1).getConnectors()) {
                                    if (oriNeighbor.getValue() == 3) {
                                        matched = true;
                                    }
                                }
                                // if no matched connector had been found, return false
                                if (matched == false) {
                                    solved = false;
                                    matchesAllItsNeighbors = false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                solved = false;
                                matchesAllItsNeighbors = false;
                            }
                            break;
                        // SOUTH, we check if the piece below has a connector NORTH
                        case 2:
                            try {
                                for (Orientation oriNeighbor : grille.getPiece(i + 1, j).getConnectors()) {
                                    if (oriNeighbor.getValue() == 0) {
                                        matched = true;
                                    }
                                }
                                // if no matched connector had been found, return false
                                if (matched == false) {
                                    solved = false;
                                    matchesAllItsNeighbors = false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                solved = false;
                                matchesAllItsNeighbors = false;
                            }
                            break;
                        // WEST, we check if the piece on the left has a connector EAST
                        case 3:
                            try {
                                for (Orientation oriNeighbor : grille.getPiece(i, j - 1).getConnectors()) {
                                    if (oriNeighbor.getValue() == 1) {
                                        matched = true;
                                    }
                                }
                                // if no matched connector had been found, return false
                                if (matched == false) {
                                    solved = false;
                                    matchesAllItsNeighbors = false;
                                }
                            } catch (IndexOutOfBoundsException e) {
                                solved = false;
                                matchesAllItsNeighbors = false;
                            }
                            break;
                    }
                }
                if(matchesAllItsNeighbors) {isPieceChecked[i][j] = true;}
            }
        }
        if(solved){return true;}
        return false;
    }

    // the method checks if the grid is solved after a move (orientation of a piece changed after a click)
    // it updates the boolean list isPieceChecked.
    public static boolean isWellConnectedAfterMove(int i/*posx*/, int j/*posy*/,
                                                   Grid grille /*pass Grid readGrid result as arg*/,
                                                   int profondeurToCheck /*recursivity on neighbors*/) {
        if(profondeurToCheck<0){return true;}
        if(isPieceChecked==null){
            throw new NullPointerException("isPieceChecked from Checker is null. It's mandatory to run Checker.isSolved() first.");
        }
        boolean matchesEveryNeighbor = true;
        // check for NORTH
        if(grille.getPiece(i, j).getConnectors().contains(Orientation.NORTH)){
            // if the piece(i, j) has a connector pointed to the North, we check if the neighbor above has a connector
            // pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(!grille.getPiece(i-1, j).getConnectors().contains(Orientation.SOUTH)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                isPieceChecked[i][j] = false;
                matchesEveryNeighbor = false;
            }
        } else{
            // if the piece(i, j) doesn't have a connector pointed to the North, we check if the neighbor above does not have
            // a connector pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(grille.getPiece(i-1, j).getConnectors().contains(Orientation.SOUTH)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                // No connection to North and Out of border above -> ok
            }
        }
        // check for EAST
        if(grille.getPiece(i, j).getConnectors().contains(Orientation.EAST)){
            // if the piece(i, j) has a connector pointed to the North, we check if the neighbor above has a connector
            // pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(!grille.getPiece(i, j+1).getConnectors().contains(Orientation.WEST)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                isPieceChecked[i][j] = false;
                matchesEveryNeighbor = false;
            }
        } else{
            // if the piece(i, j) doesn't have a connector pointed to the North, we check if the neighbor above does not have
            // a connector pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(grille.getPiece(i, j+1).getConnectors().contains(Orientation.WEST)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                // No connection to North and Out of border above -> ok
            }
        }
        // check for SOUTH
        if(grille.getPiece(i, j).getConnectors().contains(Orientation.SOUTH)){
            // if the piece(i, j) has a connector pointed to the North, we check if the neighbor above has a connector
            // pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(!grille.getPiece(i+1, j).getConnectors().contains(Orientation.NORTH)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                isPieceChecked[i][j] = false;
                matchesEveryNeighbor = false;
            }
        } else{
            // if the piece(i, j) doesn't have a connector pointed to the North, we check if the neighbor above does not have
            // a connector pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(grille.getPiece(i+1, j).getConnectors().contains(Orientation.NORTH)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                // No connection to North and Out of border above -> ok
            }
        }
        // check for WEST
        if(grille.getPiece(i, j).getConnectors().contains(Orientation.WEST)){
            // if the piece(i, j) has a connector pointed to the North, we check if the neighbor above has a connector
            // pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(!grille.getPiece(i, j-1).getConnectors().contains(Orientation.EAST)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                isPieceChecked[i][j] = false;
                matchesEveryNeighbor = false;
            }
        } else{
            // if the piece(i, j) doesn't have a connector pointed to the North, we check if the neighbor above does not have
            // a connector pointed to the South. If the assertion is true, the piece keeps matching its neighbors.
            try{
                if(grille.getPiece(i, j-1).getConnectors().contains(Orientation.EAST)){
                    isPieceChecked[i][j] = false;
                    matchesEveryNeighbor = false;
                }
            } catch(IndexOutOfBoundsException e){
                // No connection to North and Out of border above -> ok
            }
        }

        // We also update the four neighbors by using recursivity // Update isPieceChecked
        try{
            boolean northPieceWellConnected = isWellConnectedAfterMove(i-1, j,
                    grille, profondeurToCheck-1);
        }
        catch(IndexOutOfBoundsException e){}

        try{
            boolean EastPieceWellConnected = isWellConnectedAfterMove(i, j+1,
                    grille, profondeurToCheck-1);}
        catch(IndexOutOfBoundsException e){}
        try{
            boolean southPieceWellConnected = isWellConnectedAfterMove(i+1, j,
                    grille, profondeurToCheck-1);
        }catch(IndexOutOfBoundsException e){}

        try{
            boolean westPieceWellConnected = isWellConnectedAfterMove(i, j-1,
                    grille, profondeurToCheck-1);
        }catch(IndexOutOfBoundsException e){}

        return matchesEveryNeighbor;
    }
}
