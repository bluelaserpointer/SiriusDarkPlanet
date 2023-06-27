package physics.direction;

public enum DirectionS8 {
	WWD, WWA, DDW, DDS, SSD, SSA, AAS, AAW;
	
	public int getID() {
		switch(this) {
		case WWD:
			return 0;
		case WWA:
			return 1;
		case DDW:
			return 2;
		case DDS:
			return 3;
		case SSD:
			return 4;
		case SSA:
			return 5;
		case AAS:
			return 6;
		case AAW:
			return 7;
		default:
			return -1;
		}
	}
}
