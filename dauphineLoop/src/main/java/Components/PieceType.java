package Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	VOID
	// Each Type has a number of connectors and a specific value
, ONECONN, BAR, TTYPE, FOURCONN, LTYPE
;

	public int getNbConnectors() {
		// TODO Auto-generated method stub
		return 0;
	}

	Orientation getOrientation(Orientation north) {
		// TODO Auto-generated method stub
		return null;
	}

	LinkedList<Orientation> setConnectorsList(Orientation north) {
		// TODO Auto-generated method stub
		return null;
	}

	ArrayList<Orientation> getListOfPossibleOri() {
		// TODO Auto-generated method stub
		return null;
	}

	static PieceType getTypefromValue(int typeValue) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
