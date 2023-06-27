package floatGame.ui;

import java.awt.Color;
import java.util.Random;

import core.GHQ;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatStageKind;
import gui.GUIParts;
import paint.rect.RectPaint;
import sound.SoundClip;

public class FloatTitleScreen extends GUIParts {

	private boolean showedOpeningBefore = false;
	int openingAnimationStartFrame = -1000;

	private static final int STAR_AMOUNT = 100;
	private static final double STAR_ROLL_SPEED = 1.5;
	private double[] randomStarX = new double[STAR_AMOUNT], randomStarY = new double[STAR_AMOUNT];
	private double[] randomStarDistance = new double[STAR_AMOUNT];
	private int[] randomStarSize = new int[STAR_AMOUNT];
	private final Random random = new Random();
	
	public static SoundClip titleBGM;

	//private final ImageFrame planetIF;
	
	public FloatTitleScreen() {
		super.setBGColor(Color.BLACK);
		//buttons
		final int BUTTONS_LEFT = GHQ.screenW()/2 + 100;
		final int BUTTONS_TOP = GHQ.screenH()/2 + 50;
		final int BUTTON_W = 200, BUTTON_H = 70;
		final int BUTTON_Y_SPAN = BUTTON_H + 10;
		super.addLast(
				FloatGame.mainScreen().getSwitcherButton(FloatGame.STAGE_SELECT_SCREEN)
				).setBGPaint(new RectPaint() {
					@Override
					public void rectPaint(int x, int y, int w, int h) {
						GHQ.getG2D(Color.WHITE);
						GHQ.drawString_center("START", x + w/2, y + 25, 20);
					}
				}).setBounds(BUTTONS_LEFT, BUTTONS_TOP + BUTTON_Y_SPAN*0, BUTTON_W, BUTTON_H);
		super.addLast(
				FloatGame.mainScreen().getSwitcherButton(FloatGame.INDEX_SCREEN)
				).setBGPaint(new RectPaint() {
					@Override
					public void rectPaint(int x, int y, int w, int h) {
						GHQ.getG2D(Color.WHITE);
						GHQ.drawString_center("INDEX", x + w/2, y + 25, 20);
					}
				}).setBounds(BUTTONS_LEFT, BUTTONS_TOP + BUTTON_Y_SPAN*1, BUTTON_W, BUTTON_H);
		super.addLast(
				FloatGame.mainScreen().getSwitcherButton(FloatGame.HOWTOPLAY_SCREEN)
				).setBGPaint(new RectPaint() {
					@Override
					public void rectPaint(int x, int y, int w, int h) {
						GHQ.getG2D(Color.WHITE);
						GHQ.drawString_center("HOW TO PLAY", x + w/2, y + 25, 20);
					}
				}).setBounds(BUTTONS_LEFT, BUTTONS_TOP + BUTTON_Y_SPAN*2, BUTTON_W, BUTTON_H);
		//load images
		//planetIF = ImageFrame.create("floatGame/image/planet.png");
		//set stars
		for(int i = 0; i < STAR_AMOUNT; ++i) {
			randomStarX[i] = GHQ.random2(0, GHQ.screenW());
			randomStarY[i] = GHQ.random2(0, GHQ.screenH());
			randomStarDistance[i] = 1.0 + random.nextDouble()*5.0;
			randomStarSize[i] = random.nextDouble() > 0.8 ? 2 : 1;
		}
		//bgm
		titleBGM = new SoundClip("../floatGame/sounds/vigilante.mp3");
	}

	@Override
	public GUIParts enable() {
		if(showedOpeningBefore)
			openingAnimationStartFrame = GHQ.nowFrame() - COPYRIGHT_INTERVAL;
		else
			openingAnimationStartFrame = GHQ.nowFrame();
		for(FloatStageKind kind : FloatStageKind.values()) {
			kind.bgm.stop();
		}
		return super.enable();
	}
	
	@Override
	public void idle() {
		if(GHQ.passedFrame(openingAnimationStartFrame) < TOTAL_ANIMATION_FRAMES)
			drawOpeningAnimation();
		else {
			showedOpeningBefore = true;
			super.idle();
			drawStarBackGround();
			GHQ.getG2D(Color.WHITE);
			GHQ.drawString_left(FloatGame.game.getTitleName(), 100, 100, 25);
			GHQ.drawString_left(FloatGame.game.getVersion(), 100, 150, 10);
			int passedFrame = GHQ.passedFrame(openingAnimationStartFrame) - TOTAL_ANIMATION_FRAMES;
			if(passedFrame < 100) {
				titleBGM.loop();
				super.fillBoundingBox(new Color(0F, 0F, 0F, (float)(1.0 - (double)passedFrame / 100)));
			}
		}
	}
	
	public void drawStarBackGround() {
		//planetIF.dotPaint(GHQ.screenW()/2, GHQ.screenH()/2);
		for(int i = 0; i < STAR_AMOUNT; ++i) {
			final float brightness = (float)(0.5 + Math.random()/2);
			GHQ.getG2D(new Color(brightness, brightness, brightness)).fillRect(
					(int)randomStarX[i],
					(int)randomStarY[i],
					randomStarSize[i],
					randomStarSize[i]);
			randomStarX[i] -= STAR_ROLL_SPEED/randomStarDistance[i];
			if(randomStarX[i] < -1) {
				randomStarX[i] = GHQ.random2(GHQ.screenW(), GHQ.screenW() + 100);
				randomStarY[i] = GHQ.random2(0, GHQ.screenH());
				randomStarDistance[i] = 1.0 + random.nextDouble()*5.0;
				randomStarSize[i] = random.nextDouble() > 0.8 ? 2 : 1;
			}
		}
	}
	private static final int COPYRIGHT_INTERVAL = 100;
	private static final double COPYRIGHT_FADE_TIME_RATE = 0.3;
	private static final int COPYRIGHT_FADE_TIME = (int)(COPYRIGHT_INTERVAL*COPYRIGHT_FADE_TIME_RATE);
	private static final int WAIT_AFTER_COPYRIGHT = 20;
	private static final int TOTAL_ANIMATION_FRAMES = COPYRIGHT_INTERVAL + WAIT_AFTER_COPYRIGHT;
	
	public void drawOpeningAnimation() {
		int passedFrame = GHQ.passedFrame(openingAnimationStartFrame);
		if(passedFrame < 0 || TOTAL_ANIMATION_FRAMES < passedFrame)
			return;
		super.fillBoundingBox(Color.BLACK);

		guessAnimationPhase: {
			if(passedFrame < COPYRIGHT_INTERVAL) {
				final Color textColor;
				if(passedFrame < COPYRIGHT_FADE_TIME) { //fade in
					//System.out.println("alpha: " + (float)((double)passedFrame / COPYRIGHT_FADE_TIME));
					textColor = new Color(1F, 1F, 1F, (float)((double)passedFrame / COPYRIGHT_FADE_TIME));
				} else if(passedFrame > COPYRIGHT_INTERVAL - COPYRIGHT_FADE_TIME) { //fade out
					textColor = new Color(1F, 1F, 1F, (float)((double)(COPYRIGHT_INTERVAL - passedFrame) / COPYRIGHT_FADE_TIME));
				} else {
					textColor = Color.WHITE;
				}
				GHQ.getG2D(textColor, GHQ.stroke1);
				GHQ.drawString_center("Shandian Tuxi! 2020 Sanjiaobei Gamejam", GHQ.screenW()/2, GHQ.screenH()/2, GHQ.commentFont);
				break guessAnimationPhase;
			}
			passedFrame -= COPYRIGHT_INTERVAL;
			
		}
	}
}
