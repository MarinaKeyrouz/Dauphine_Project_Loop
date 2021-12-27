package Components;


import java.util.HashMap;

/**
 *
 * Orientation of the piece enum
 *
 */
public enum Orientation {
	/* Implement all the possible orientations and
	 *  required methods to rotate
	 */
	NORTH,SOUTH,EAST,WEST;

	public static Orientation getOriFromValue(int orientationValue) {
		switch (orientationValue){
			case 0 -> {
				return Orientation.NORTH;
			}
			case 1 -> {
				return Orientation.EAST;
			}
			case 2 -> {
				return Orientation.SOUTH;
			}
			case 3 -> {
				return Orientation.WEST;
			}
		}
		return null;
	}
	public static int getValueFromOri(Orientation orientation){
		switch (orientation){
			case NORTH -> {
				return 0;
			}
			case SOUTH -> {
				return 2;
			}
			case EAST -> {
				return 1;
			}
			case WEST -> {
				return 3;
			}
		}
		return 0;
	}

	public Orientation turn90() {
		switch(this){
			case EAST -> {
				return Orientation.SOUTH;
			}
			case WEST -> {
				return Orientation.NORTH;
			}
			case NORTH -> {
				return Orientation.EAST;
			}
			case SOUTH -> {
				return Orientation.WEST;
			}
		}
		return null;
	}

	public int[] getOpposedPieceCoordinates(Piece p) {
		switch (this){
			case NORTH -> {
				return new int[]{p.getPosY()-1,p.getPosX()};
			}
			case EAST -> {
				return new int[]{p.getPosY(),p.getPosX()+1};
			}
			case SOUTH -> {
				return new int[]{p.getPosY()+1,p.getPosX()};
			}
			case WEST -> {
				return new int[]{p.getPosY(),p.getPosX()-1};
			}
		}
		return null;
	}

	public Orientation getOpposedOrientation() {
		switch (this){
			case NORTH -> {
				return Orientation.SOUTH;
			}
			case SOUTH -> {
				return Orientation.NORTH;
			}
			case EAST -> {
				return Orientation.WEST;
			}
			case WEST -> {
				return Orientation.EAST;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		switch (this){
			case NORTH -> {
				return "NORTH";
			}
			case SOUTH -> {
				return "SOUTH";
			}
			case EAST -> {
				return "EAST";
			}
			case WEST -> {
				return "WEST";
			}
		}
		return null;
	}
}
