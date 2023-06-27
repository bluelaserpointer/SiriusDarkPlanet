package floatGame.engine;

import java.awt.Color;
import java.util.Random;

import core.GHQ;
import floatGame.structure.FloatPlatform;
import floatGame.ui.FloatTitleScreen;
import floatGame.ui.HUD;
import floatGame.unit.Factory;
import floatGame.unit.FloatUnit;
import floatGame.unit.FlyingFactory;
import floatGame.unit.Player;
import physics.stage.GHQStage;
import preset.unit.Unit;

public class FloatStage extends GHQStage {

	// background star
	final int STAR_AMOUNT = 2000;
	private int[] randomStarX = new int[STAR_AMOUNT], randomStarY = new int[STAR_AMOUNT];
	private double[] randomStarDistance = new double[STAR_AMOUNT];
	private int[] randomStarSize = new int[STAR_AMOUNT];
	
	protected FloatStageKind stageKind;
	
	public FloatStage() {
		super(FloatGame.STAGE_W, FloatGame.STAGE_H);
	}
	
	private void init(FloatStageKind stageKind) {
		//clear HUD info
		HUD.init();
		//set platform
		generatePlatfroms();
		//set background starts
		final Random random = new Random();
		for (int i = 0; i < STAR_AMOUNT; ++i) {
			randomStarX[i] = (int)(random.nextDouble()*width());
			randomStarY[i] = (int)(random.nextDouble()*height());
			randomStarDistance[i] = 1.0 + random.nextDouble()*0.3;
			randomStarSize[i] = random.nextDouble() > 0.8 ? 2 : 1;
		}
		//set factories
		for(int xPos : stageKind.friendBasePositions) {
			addUnit(Unit.initialSpawn(new Factory(1000, 1000, FloatUnit.friendSide), xPos, FloatGame.STAGE_H - 100));
		}
		for(int xPos : stageKind.enemyBasePositions) {
			addUnit(Unit.initialSpawn(new Factory(1000, 1000, FloatUnit.enemySide), xPos, FloatGame.STAGE_H - 100));
		}
		if(stageKind == FloatStageKind.The_Sirius) {
			addUnit(Unit.initialSpawn(new FlyingFactory(10000, 10000, FloatUnit.enemySide), FloatGame.STAGE_W - 200, FloatGame.STAGE_H/2));
		}
		//set player
		FloatGame.player = addUnit(Unit.initialSpawn(new Player(), 200, 200));
		FloatGame.camera.setChaseTarget(FloatGame.player);
		//bgm
		stageKind.bgm.loop();

		FloatTitleScreen.titleBGM.stop();
	}
	
	public void restart(FloatStageKind stageKind) {
		GHQ.stage().clear();
		init(this.stageKind = stageKind);
	}
	
	public void restart() {
		GHQ.stage().clear();
		init(this.stageKind);
	}

	private void drawBackGround() {
		GHQ.getG2D(Color.BLACK).fillRect(GHQ.cameraLeft(), GHQ.cameraTop(), GHQ.screenW(), GHQ.screenH());
		GHQ.getG2D(Color.WHITE).drawRect(cx(), cy(), width(), height());
		//far scene
		if(stageKind() == FloatStageKind.The_Sirius && HUD.winTeam != FloatUnit.friendSide) { //red stars for stage Sirius
			for (int i = 0; i < STAR_AMOUNT; ++i) {
				final float brightness = (float)(0.5 + Math.random()/2);
				GHQ.getG2D(new Color(brightness, 0F, 0F)).fillRect(
						randomStarX[i] + (int)(GHQ.cameraLeft()/randomStarDistance[i]),
						randomStarY[i] + (int)(GHQ.cameraTop()/randomStarDistance[i]),
						randomStarSize[i],
						randomStarSize[i]);
			}
		} else {  //white start for normal stage
			for (int i = 0; i < STAR_AMOUNT; ++i) {
				final float brightness = (float)(0.5 + Math.random()/2);
				GHQ.getG2D(new Color(brightness, brightness, brightness)).fillRect(
						randomStarX[i] + (int)(GHQ.cameraLeft()/randomStarDistance[i]),
						randomStarY[i] + (int)(GHQ.cameraTop()/randomStarDistance[i]),
						randomStarSize[i],
						randomStarSize[i]);
			}
		}
	}
	
	@Override
	public void idle() {
		this.drawBackGround();
		super.idle();
	}
	
	@Override
	public void onlyPaint() {
		this.drawBackGround();
		super.onlyPaint();
	}
	
	private void generatePlatfroms() {
		super.addStructure(new FloatPlatform(width(), 40)).point().setXY(FloatGame.STAGE_W/2, FloatGame.STAGE_H);
	}
	
	public FloatStageKind stageKind() {
		return stageKind;
	}
}
