package Components;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * Type of the piece enum
 *
 */
public enum PieceType {
	// Each Type has a number of connectors and a specific value
	VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE;

	public static PieceType getTypeFromValue(int typeValue) {
		switch(typeValue){
			case(0) -> {
				return PieceType.VOID;
			}
			case(1) -> {
				return PieceType.ONECONN;
			}
			case(2) -> {
				return PieceType.BAR;
			}
			case(3) -> {
				return PieceType.TTYPE;
			}
			case(4) -> {
				return PieceType.FOURCONN;
			}
			case(5) -> {
				return PieceType.LTYPE;
			}
		}
		return null;
	}

	public Orientation getOrientation(Orientation x){
		switch(this){
			case FOURCONN,VOID -> {
				return Orientation.NORTH;
			}
			case ONECONN, TTYPE, LTYPE -> {
				return x;
			}
			case BAR -> {
				switch(x){
					case NORTH,SOUTH ->{
						return Orientation.NORTH;
					}
					case EAST, WEST -> {
						return Orientation.EAST;
					}
				}
			}
		}
		return null;
	}

	public LinkedList<Orientation> setConnectorsList(Orientation x) {
		LinkedList<Orientation> temp = new LinkedList<>();
		switch(this){
			case FOURCONN -> {
				temp.add(Orientation.NORTH);
				temp.add(Orientation.EAST);
				temp.add(Orientation.SOUTH);
				temp.add(Orientation.WEST);
			}
			case ONECONN -> {
				temp.add(x);
			}
			case BAR -> {
				switch(x){
					case NORTH, SOUTH -> {
						temp.add(Orientation.NORTH);
						temp.add(Orientation.SOUTH);
					}
					case EAST, WEST -> {
						temp.add(Orientation.EAST);
						temp.add(Orientation.WEST);
					}
				}
			}
			case TTYPE -> {
				switch(x){
					case NORTH -> {
						temp.add(Orientation.WEST);
						temp.add(Orientation.NORTH);
						temp.add(Orientation.EAST);
					}
					case SOUTH -> {
						temp.add(Orientation.EAST);
						temp.add(Orientation.SOUTH);
						temp.add(Orientation.WEST);
					}
					case EAST -> {
						temp.add(Orientation.NORTH);
						temp.add(Orientation.EAST);
						temp.add(Orientation.SOUTH);
					}
					case WEST -> {
						temp.add(Orientation.SOUTH);
						temp.add(Orientation.WEST);
						temp.add(Orientation.NORTH);
					}
				}
			}
			case LTYPE -> {
				switch(x){
					case NORTH -> {
						temp.add(Orientation.NORTH);
						temp.add(Orientation.EAST);
					}
					case SOUTH -> {
						temp.add(Orientation.SOUTH);
						temp.add(Orientation.WEST);
					}
					case EAST -> {
						temp.add(Orientation.EAST);
						temp.add(Orientation.SOUTH);
					}
					case WEST -> {
						temp.add(Orientation.WEST);
						temp.add(Orientation.NORTH);
					}
				}
			}
		}
		return temp;
	}

	public ArrayList<Orientation> getListOfPossibleOri() {
		ArrayList<Orientation> temp = new ArrayList<>();
		switch(this){
			case VOID, FOURCONN -> temp.add(Orientation.NORTH);
			case ONECONN, TTYPE, LTYPE -> {
				temp.add(Orientation.NORTH);
				temp.add(Orientation.EAST);
				temp.add(Orientation.SOUTH);
				temp.add(Orientation.WEST);
			}
			case BAR -> {
				temp.add(Orientation.NORTH);
				temp.add(Orientation.EAST);
			}
		}
		return temp;
	}

	public int getNbConnectors() {
		int temp = 0;
		switch(this){
			case BAR, LTYPE -> temp = 2;
			case TTYPE -> temp = 3;
			case ONECONN -> temp = 1;
			case FOURCONN -> temp = 4;
		}
		return temp;
	}
}
