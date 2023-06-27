package exsampleGame.engine;

import static java.awt.event.KeyEvent.*;

import java.awt.Color;
import java.awt.Graphics2D;

import camera.Camera;
import camera.FixChaseCamera;
import core.GHQ;
import core.Game;
import exsampleGame.unit.*;
import gui.GUIPartsSwitcher;
import gui.stageEditor.DefaultStageEditor;
import input.key.DoubleNumKeyListener;
import input.key.SingleKeyListener;
import input.key.SingleNumKeyListener;
import input.mouse.MouseListenerEx;
import paint.ImageFrame;
import physics.HasPoint;
import physics.stage.GHQStage;
import preset.unit.Unit;
import preset.vegetation.Vegetation;

public class Engine_THH1 extends Game {
	static {
		GHQ.setScreenSize(1000, 600);
	}
	
	private static THH_BasicUnit[] friends;
	private static final GHQStage[] stages = new GHQStage[1];
	private int nowStage;
	
	public static final int FRIEND = 0,ENEMY = 100;
	final int F_MOVE_SPD = 6;
	
	int formationsX[], formationsY[];
	final HasPoint formation = HasPoint.generate(0, 0);

	public String getVersion() {
		return "alpha1.0.0";
	}
	//saveData
	//inputEvnet
	private static final int inputKeys[] = 
	{
		VK_W,
		VK_A,
		VK_S,
		VK_D,
		VK_Q,
		VK_E,
		VK_R,
		VK_F,
		VK_G,
		VK_TAB,
		VK_SHIFT,
		VK_SPACE,
		VK_ESCAPE,
		VK_F6,
	};
	private static final MouseListenerEx s_mouseL = new MouseListenerEx();
	private static final SingleKeyListener s_keyL = new SingleKeyListener(inputKeys);
	private static final SingleNumKeyListener s_numKeyL = new SingleNumKeyListener();
	private static final DoubleNumKeyListener d_numKeyL = new DoubleNumKeyListener(20);
	
	//images
	private ImageFrame focusIF,magicCircleIF;
	
	//editMode
	private static DefaultStageEditor editor;
	private static final String EDIT_GUI_GROUP = "EDIT_GUI_GROUP";
	
	//initialization
	public Engine_THH1() {
		super(new GUIPartsSwitcher(1, 0));
	}
	@Override
	public String getTitleName() {
		return "touhouHachidanmakusetu";
	}
	public static void main(String args[]) {
		new GHQ(new Engine_THH1(), 1000, 600);
	}
	@Override
	protected Camera starterCamera() {
		return new FixChaseCamera(formation);
	}
	@Override
	public final GHQStage loadStage() {
		return new GHQStage(5000, 5000);
	}
	@Override
	public final void loadResource() {
		/////////////////////////////////
		//images this engine required
		/////////////////////////////////
		focusIF = ImageFrame.create("thhimage/focus.png");
		magicCircleIF = ImageFrame.create("thhimage/MagicCircle.png");
		/////////////////////////////////
		//GUI
		/////////////////////////////////
		GHQ.addGUIParts(editor = new DefaultStageEditor(EDIT_GUI_GROUP) {
			@Override
			public void saveStage() {
				//TODO: save
				//stageDataSaver.doSave(new File("stage/saveData1.txt"));
			}
		}).disable();
		/////////////////////////////////
		//input
		/////////////////////////////////
		GHQ.addListenerEx(s_mouseL);
		GHQ.addListenerEx(s_keyL);
		GHQ.addListenerEx(s_numKeyL);
		GHQ.addListenerEx(d_numKeyL);
		/////////////////////////////////
		//units
		/////////////////////////////////
		//formation
		formation.point().setXY(GHQ.screenW()/2, GHQ.screenH() - 100);
		formationsX = new int[2];
		formationsY = new int[2];
		formationsX[0] = -15;formationsY[0] = 0;
		formationsX[1] = +15;formationsY[1] = 0;
		//friend
		friends = new THH_BasicUnit[2];
		friends[0] = Unit.initialSpawn(new Marisa(FRIEND), formation.intX() + formationsX[0], formation.intY() + formationsY[0]);
		friends[0].HP.setMax(4000).setToMax();
		friends[1] = Unit.initialSpawn(new Reimu(FRIEND), formation.intX() + formationsX[1], formation.intY() + formationsY[1]);
		friends[1].HP.setMax(4000).setToMax();
		for(Unit friend : friends)
			GHQ.stage().addUnit(friend);
		//action
		//ActionInfo.clear();
		//ActionInfo.addDstPlan(1000, GHQ.getScreenW() - 200, GHQ.getScreenH() + 100);
		//ActionInfo.addDstPlan(1000, GHQ.getScreenW() + 200, GHQ.getScreenH() + 100);
		//final Action moveLeftToRight200 = new Action(this);
		//enemy
		GHQ.stage().addUnit(Unit.initialSpawn(new Fairy(ENEMY),300, 100)).HP.setMax(2500).setToMax();
		GHQ.stage().addUnit(Unit.initialSpawn(new Fairy(ENEMY),700, 20)).HP.setMax(2500).setToMax();
		GHQ.stage().addUnit(Unit.initialSpawn(new Fairy(ENEMY),1200, 300)).HP.setMax(2500).setToMax();
		GHQ.stage().addUnit(Unit.initialSpawn(new Fairy(ENEMY),1800, 700)).HP.setMax(2500).setToMax();
		GHQ.stage().addUnit(Unit.initialSpawn(new WhiteMan(ENEMY),400, GHQ.random2(100, 150))).HP.setMax(50000).setToMax();
		GHQ.stage().addUnit(Unit.initialSpawn(new BlackMan(ENEMY),200, GHQ.random2(100, 150))).HP.setMax(10000).setToMax();
		//vegetation
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_leaf.png"),1172,886));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_flower.png"),1200,800));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_leaf2.png"),1800,350));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_stone.png"),1160,870));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_leaf3.png"),1102,830));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_leaf3.png"),1122,815));
		GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/veg_leaf3.png"),822,886));

		stages[0] = new GHQStage(5000, 5000);
		//stageDataSaver.doLoad(new File("stage/saveData1.txt"));
	}
	//idle
	@Override
	public final void idle(Graphics2D g2, int stopEventKind) {
		if(friends[0] == null) {
			return;
		}
		//stagePaint
		//background
		GHQ.stage().fill(new Color(112, 173, 71));
		//center point
		magicCircleIF.dotPaint_turn(formation.intX(), formation.intY(), (double)GHQ.nowFrame()/35.0);
		g2.setColor(Color.RED);
		g2.fillOval(formation.intX() - 2, formation.intY() - 2, 5, 5);
		////////////////
		final int MOUSE_X = GHQ.mouseX(), MOUSE_Y = GHQ.mouseY();
		if(stopEventKind == GHQ.NONE) {
			//gravity
			if(doGravity) {
				//<editing>
			}
			//others
			switch(nowStage) {
			case 0:
				for(int i = 0;i < friends.length;i++)
					friends[i].dstPoint.setXY(friends[i].point().setXY(formation.intX() + formationsX[i], formation.intY() + formationsY[i]));
				//enemy
				for(Unit enemy : GHQ.stage().units) {
					if(!enemy.isAlive())
						continue;
					if(enemy.name() == "FairyA") {
						/*final int FRAME = gameFrame % 240;
						if(FRAME < 100)
							enemy.point().setSpeed(-5, 0);
						else if(FRAME < 120)
							enemy.point().setSpeed(0, 0);
						else if(FRAME < 220)
							enemy.point().setSpeed(5, 0);
						else
							enemy.point().setSpeed(0, 0);*/
						enemy.point().rectCircuit(friends[0].point(), 10, true);
					}
				}
				//leap
				if(s_keyL.hasEvent(VK_SHIFT)){
					//final int DX = MOUSE_X - formationCenterX,DY = MOUSE_Y - formationCenterY;
					//if(DX*DX + DY*DY < 5000) {
						formation.point().setXY(MOUSE_X, MOUSE_Y);
					//}else {
						//formationCenterX += (double)DX/10.0;formationCenterY += (double)DY/10.0;
					//}
					for(int i = 0;i < friends.length;i++)
						friends[i].point().setXY(formation.intX() + formationsX[i], formation.intY() + formationsY[i]);
				}
				//shot
				for(THH_BasicUnit chara : friends)
					chara.attackOrder = s_mouseL.hasButton1Event();
				//spell
				{
					int spellUser;
					while((spellUser = d_numKeyL.pullHasEventKeyNum()) != GHQ.NONE) {
						spellUser -= 1;
						if(spellUser < friends.length)
							friends[spellUser].spellOrder = true;
					}
				}
				break;
			}
		}
		//focus
		g2.setColor(new Color(200,120,10,100));
		g2.setStroke(GHQ.stroke3);
		g2.drawLine(formation.intX(), formation.intY(), MOUSE_X, MOUSE_Y);
		focusIF.dotPaint(MOUSE_X, MOUSE_Y);
		//editor
		if(s_keyL.pullEvent(VK_F6)) {
			editor.flip();
		}
		if(!editor.isEnabled()){ //game GUI
			GHQ.translateForGUI(true);
			int pos = 1;
			for(THH_BasicUnit chara : friends) 
				chara.iconPaint.rectPaint(pos++*90 + 10, GHQ.screenH() - 40, 80, 30);
			GHQ.translateForGUI(false);
		}
		if(stopEventKind == GHQ.NONE || editor.isEnabled()) { //scroll
			//scroll by keys
			if(s_keyL.hasEvent(VK_W)) {
				System.out.println("formation North move!");
				formation.point().addY(-F_MOVE_SPD);
			}else if(s_keyL.hasEvent(VK_S)) {
				System.out.println("formation South move!");
				formation.point().addY(+F_MOVE_SPD);
			}
			if(s_keyL.hasEvent(VK_A)) {
				System.out.println("formation West move!");
				formation.point().addX(-F_MOVE_SPD);
			}else if(s_keyL.hasEvent(VK_D)) {
				System.out.println("formation East move!");
				formation.point().addX(+F_MOVE_SPD);
			}
//			//scroll by mouse
//			if(doScrollView) {
//				GHQ.viewTargetTo((MOUSE_X + formation.intX())/2,(MOUSE_Y + formationCenterY)/2);
//				GHQ.viewApproach_rate(10);
//			}
		}
		//idle
		GHQ.stage().idle();
	}
	
	private boolean doGravity = false;
}
