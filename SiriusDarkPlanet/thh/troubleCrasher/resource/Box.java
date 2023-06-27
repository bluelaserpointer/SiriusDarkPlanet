package troubleCrasher.resource;

import troubleCrasher.engine.TCGame;
import troubleCrasher.jigsaw.Jigsaw;
import troubleCrasher.jigsaw.JigsawEnum;


public class Box extends Jigsaw {
	public final BoxEnum boxEnum;
	
	static JigsawEnum decideJigsawShape(String boxName) {
		return JigsawEnum.SQ1;
	}
	public Box(BoxEnum boxEnum) {
		super(decideJigsawShape(boxEnum.boxName));
		this.boxEnum = boxEnum;
	}
	
	@Override
	public void paint(int left, int top) {
		boxEnum.image.rectPaint(left + gridX()*JigsawEnum.JIGSAW_GRID_SIZE,top + gridY()*JigsawEnum.JIGSAW_GRID_SIZE,JigsawEnum.JIGSAW_GRID_SIZE,JigsawEnum.JIGSAW_GRID_SIZE);
	}
	
	public BoxTagEnum getTag() {
		return boxEnum.tag;
	}
	public String getBoxName() {
		return boxEnum.boxName;
	}
	public boolean usable() {
		if(TCGame.scriptManager.usable)
		{
			System.out.println("----------------------------------------");
			System.out.println(boxEnum.tag);
			boolean res = false;
			switch(boxEnum.tag) {
				case WEAPON:
					res = TCGame.scriptManager.inBattle;
				case HEAL:
					res = true;
				case SCRIPT:
					res = TCGame.scriptManager.boxNeeded(getBoxName());
				case WOUND:
					res = false;
			}
			return res;
		}
		return false;
	}
}
