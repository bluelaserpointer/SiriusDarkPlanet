package physics.direction;

public enum Direction8 {
	O, W, A, S, D, WA, WD, SA, SD;


	///////////////
	//getDirectionByWASD
	///////////////
	public double angle() {
		switch(this) {
		case A:
			return Math.PI;
		case S:
			return Math.PI/2;
		case SA:
			return Math.PI*3/4;
		case SD:
			return Math.PI/4;
		case W:
			return -Math.PI/2;
		case WA:
			return -Math.PI*3/4;
		case WD:
			return -Math.PI/4;
		case D:
		case O:
		default:
			return 0;
		}
	}
	///////////////
	//getDirectionByWASD
	///////////////
	public static Direction8 getDirectionByWASD(boolean w, boolean a, boolean s, boolean d) {
		if(w ^ s && a ^ d) {
			if(w && a)
				return WA;
			if(w && d)
				return WD;
			if(s && a)
				return SA;
			if(s && d)
				return SD;
		} else if(w ^ s)
			return w ? W : S;
		else if(a ^ d)
			return a ? A : D;
		return O;
	}
	public double x() {
		switch(this) {
		case A:
			return -1;
		case WA:
		case SA:
			return -1/Math.sqrt(2);
		case D:
			return +1;
		case WD:
		case SD:
			return +1/Math.sqrt(2);
		default:
			return 0;
		}
	}
	public double y() {
		switch(this) {
		case W:
			return -1;
		case WA:
		case WD:
			return -1/Math.sqrt(2);
		case S:
			return +1;
		case SA:
		case SD:
			return +1/Math.sqrt(2);
		default:
			return 0;
		}
	}
}
