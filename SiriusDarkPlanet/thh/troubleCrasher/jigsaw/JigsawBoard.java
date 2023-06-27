package troubleCrasher.jigsaw;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class JigsawBoard {
	private final LinkedList<Jigsaw> jigsaws = new LinkedList<>();
	
	final int xGrids, yGrids;
	final BitSet bits;
	
	public JigsawBoard(int xGrids, int yGrids) {
		this.xGrids = xGrids;
		this.yGrids = yGrids;
		bits = new BitSet(xGrids*yGrids);
	}
	
	public boolean jigsawsIntersects(Jigsaw jigSaw) {
		final int jigsawGridX = jigSaw.gridX(), jigSawGridY = jigSaw.gridY();
//		System.out.println("test at " + jigsawGridX + ", " + jigSawGridY);
		if(!inGrids(jigsawGridX, jigSawGridY)) //left-top point is out of bounds
			return true;
		for(int xi = 0; xi < jigSaw.xGrids(); ++xi) {
			for(int yi = 0; yi < jigSaw.yGrids(); ++yi) {
//				System.out.println("at " + (jigsawGridX + xi) + ", " + (jigSawGridY + yi) + " hasTile: " + jigSaw.hasTile(xi, yi) + " bits: " + bits.get(jigsawGridX + xi + xGrids()*(jigSawGridY + yi)));
				if(jigSaw.hasTile(xi, yi) && this.isBlocked(jigsawGridX + xi, jigSawGridY + yi)) {
					return true;
				}
			}
		}
//		System.out.println("acc");
		return false;
	}
	
	public boolean setJigsaw(Jigsaw jigsaw) {
		if(jigsawsIntersects(jigsaw))
			return false;
		for(int xi = 0; xi < jigsaw.xGrids(); ++xi) {
			for(int yi = 0; yi < jigsaw.yGrids(); ++yi) {
				if(jigsaw.hasTile(xi ,yi)) {
//					System.out.println("set " + (jigsaw.gridX() + xi) + ", " + (jigsaw.gridY() + yi));
					bits.set((jigsaw.gridX() + xi) + (jigsaw.gridY() + yi)*xGrids());
				}
			}
		}
		jigsaws.add(jigsaw);
		return true;
	}
	public boolean removeJigsaw(Jigsaw jigsaw) {
		for(int xi = 0; xi < jigsaw.xGrids(); ++xi) {
			for(int yi = 0; yi < jigsaw.yGrids(); ++yi) {
				if(jigsaw.hasTile(xi ,yi)) {
//					System.out.println("set " + (jigsaw.gridX() + xi) + ", " + (jigsaw.gridY() + yi));
					bits.clear((jigsaw.gridX() + xi) + (jigsaw.gridY() + yi)*xGrids());
				}
			}
		}
		return jigsaws.remove(jigsaw);
	}
	public Jigsaw getJigsaw(int gridX, int gridY) {
		if(!bits.get(gridX + gridY*xGrids()))
			return null;
		for(Jigsaw jigsaw : jigsaws) {
			if(jigsaw.hasTile(gridX - jigsaw.gridX(), gridY - jigsaw.gridY())) {
				return jigsaw;
			}
		}
		return null;
	}
	public boolean inGrids(int gridX, int gridY) {
		return 0 <= gridX && gridX < xGrids() && 0 <= gridY && gridY < yGrids();
	}
	public boolean isBlocked(int gridX, int gridY) {
		return !inGrids(gridX, gridY) || bits.get(gridX + gridY*xGrids());
	}
	public int xGrids() {
		return xGrids;
	}
	public int yGrids() {
		return yGrids;
	}
	public List<Jigsaw> jigsaws() {
		return jigsaws;
	}
}
