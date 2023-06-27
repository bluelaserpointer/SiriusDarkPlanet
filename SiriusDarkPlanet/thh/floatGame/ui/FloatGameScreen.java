package floatGame.ui;

import java.awt.Color;
import java.awt.Font;

import core.GHQ;
import floatGame.engine.FloatAchievement;
import floatGame.engine.FloatGame;
import floatGame.engine.FloatStage;
import floatGame.unit.FloatUnit;
import gui.BasicButton;
import gui.GUIParts;
import paint.text.StringPaint;

public class FloatGameScreen extends GUIParts {

	private static final HUD hud = new HUD();
	int openingAnimationStartFrame = -1000;
	
	public static GUIParts pauseMenu;
	public static GUIParts resultWindow;
	
	public FloatGameScreen() {
		final Font font = GHQ.getG2D().getFont().deriveFont(30F);
		//pause menu
		super.addLast(pauseMenu = new GUIParts() {
			@Override
			public void paint() {
				super.paint();
				super.drawBoundingBox(Color.BLUE, GHQ.stroke3);
				final int SPAN = 4;
				for(int i = 0; i < height()/SPAN; ++i) {
					final int y = top() + i*SPAN;
					GHQ.getG2D(new Color(0F, 0F, 1F, 0.5F), GHQ.stroke1).drawLine(left(), y, right(), y);
				}
				GHQ.getG2D(Color.WHITE, GHQ.stroke1).drawLine(left(), top() + 100, right(), top() + 100);
				GHQ.drawString_center("Pause", GHQ.screenW()/2, top() + 50, font);
			}
		}).setBounds(GHQ.screenW()/2 - 150, GHQ.screenH()/2 - 200, 300, 400);
		pauseMenu.disable();
		pauseMenu.addLast(new BasicButton().setText(new StringPaint("Back to game", font, Color.WHITE))
				.setClickEvent(e -> FloatGame.setGamePause(false))
				).setBounds(pauseMenu.cx() - 100, pauseMenu.top() + 130, 200, 50);
		pauseMenu.addLast(new BasicButton().setText(new StringPaint("Restart", font, Color.WHITE))
				.setClickEvent(
						e -> {((FloatStage)GHQ.stage()).restart();
						FloatGame.setGamePause(false);
						openingAnimationStartFrame = GHQ.nowFrame();
						})
				).setBounds(pauseMenu.cx() - 100, pauseMenu.top() + 230, 200, 50);
		pauseMenu.addLast(new BasicButton().setText(new StringPaint("Back to main menu", font, Color.WHITE))
				.setClickEvent(e -> FloatGame.toMainMenu())
				).setBounds(pauseMenu.cx() - 100, pauseMenu.top() + 330, 200, 50);
		//result window
		final int FADE_IN_FRAMES = 100;
		super.addLast(resultWindow = new GUIParts() {
			private static final double BASEMENT_OUTNUMBER_BONUS = 1000.0;
			private static final double TIME_RATE_FOR_BONUS = 0.01;
			@Override
			public void paint() {
				final int passedFrameFromJudge = GHQ.passedFrame(HUD.judgeGameFrame);
				if(passedFrameFromJudge < FADE_IN_FRAMES)
					GHQ.setImageAlpha((float)((double)passedFrameFromJudge/FADE_IN_FRAMES));
				super.paint();
				super.drawBoundingBox(Color.BLUE, GHQ.stroke3);
				final int SPAN = 4;
				for(int i = 0; i < height()/SPAN; ++i) {
					final int y = top() + i*SPAN;
					GHQ.getG2D(new Color(0F, 0F, 1F, 0.5F), GHQ.stroke1).drawLine(left(), y, right(), y);
				}
				GHQ.getG2D(Color.WHITE, GHQ.stroke1).drawLine(left(), top() + 100, right(), top() + 100);
				GHQ.drawString_center("Result", GHQ.screenW()/2, top() + 50, font);
				GHQ.drawString_left("Basement outnumber bonus: (" + HUD.friendBaseCount + "-" + HUD.enemyBaseCount + ") x " + BASEMENT_OUTNUMBER_BONUS, left(), top() + 140, 20);
				GHQ.drawString_left("Time bonus: score / (" + HUD.gameTimePassedWhenJudge + "s x " + TIME_RATE_FOR_BONUS + ")", left(), top() + 180, 20);
				final StringBuilder sb = new StringBuilder("Achievements: ");
				int achievementTotalScore = 0;
				boolean firstAchievement = true;
				for(FloatAchievement achievement : FloatAchievement.values()) {
					if(firstAchievement) {
						firstAchievement = false;
					} else {
						sb.append(" + ");
					}
					sb.append(achievement.score).append(" x ").append(HUD.achievementCount[achievement.ordinal()]);
					achievementTotalScore += achievement.score*HUD.achievementCount[achievement.ordinal()];
				}
				GHQ.drawString_left(sb.toString(), left(), top() + 220, 20);
				final double score =
						(HUD.friendBaseCount - HUD.enemyBaseCount) * BASEMENT_OUTNUMBER_BONUS / ( HUD.gameTimePassedWhenJudge * TIME_RATE_FOR_BONUS )
						+ achievementTotalScore;
				if(HUD.winTeam.equals(FloatUnit.friendSide)) {
					GHQ.getG2D(Color.GREEN);
					GHQ.drawString_center("Win!", cx(), top() + 260, 20);
					GHQ.drawString_center("Score: " + (int)score, cx(), top() + 300, 20);
				} else {
					GHQ.getG2D(Color.RED);
					GHQ.drawString_center("Lose", cx(), top() + 260, 20);
					GHQ.drawString_center("Score: " + (int)score, cx(), top() + 300, 20);
				}
				if(passedFrameFromJudge < FADE_IN_FRAMES)
					GHQ.setImageAlpha();
			}
		}).setBounds(GHQ.screenW()/2 - 400, GHQ.screenH()/2 - 200, 800, 400);
		resultWindow.addLast(new BasicButton() {
				@Override
				public void paint() {
					final int passedFrameFromJudge = GHQ.passedFrame(HUD.judgeGameFrame);
					if(passedFrameFromJudge < FADE_IN_FRAMES)
						GHQ.setImageAlpha((float)((double)passedFrameFromJudge/FADE_IN_FRAMES));
					super.paint();
					if(passedFrameFromJudge < FADE_IN_FRAMES)
						GHQ.setImageAlpha();
				}
			}.setText(new StringPaint("Back to main menu", font, Color.WHITE))
			.setClickEvent(e -> FloatGame.toMainMenu())
			).setBounds(pauseMenu.cx() - 100, pauseMenu.top() + 330, 200, 50);
		resultWindow.disable();
	}
	
	@Override
	public GUIParts enable() {
		openingAnimationStartFrame = GHQ.nowFrame();
		HUD.gameStartedTime = GHQ.nowTime();
		FloatGame.setGamePause(false);
		if(GHQ.nowFrame() > 10)
			FloatGame.stage.restart(FloatStageSelectScreen.selectedStageKind());
		resultWindow.disable();
		return super.enable();
	}
	
	@Override
	public void idle() {
		//HUD
		hud.draw();
		//ScreenAnimation
		drawOpeningAnimation();
		//GamePauseMenu
		super.idle();
	}
	public void drawOpeningAnimation() {
		int passedFrame = GHQ.passedFrame(openingAnimationStartFrame);
		final int SHIFT_FRAME = 50;
		final int slopeDx = 100;
		final int slopeInterval = 5;
		final int slopeAmount = (GHQ.screenW() + slopeDx) / slopeInterval;
		final double slopeGrowDelayByID = (double)SHIFT_FRAME/slopeAmount;
		guessAnimationPhase: {
			final int WAIT_FRAME = 25;
			if(passedFrame < SHIFT_FRAME + WAIT_FRAME) {
				int slopeID = 0;
				final int slopeID_MAX = (int)(passedFrame / slopeGrowDelayByID);
				while(slopeID < slopeID_MAX) {
					final int slopeX = slopeID*slopeInterval - slopeDx;
					GHQ.getG2D(Color.BLUE, GHQ.stroke1);
					GHQ.getG2D().drawLine(slopeX, 0, slopeX + slopeDx, GHQ.screenH()/2);
					GHQ.getG2D().drawLine(GHQ.screenW() - slopeX, GHQ.screenH()/2, GHQ.screenW() - slopeX - slopeDx, GHQ.screenH());
					++slopeID;
				}
				break guessAnimationPhase;
			}
			passedFrame -= SHIFT_FRAME + WAIT_FRAME;
			if(passedFrame < SHIFT_FRAME) {
				int slopeID = 0;
				final int slopeID_MAX = slopeAmount;
				final double rate = 1.0 - (double)passedFrame/SHIFT_FRAME;
				while(slopeID < slopeID_MAX) {
					final int slopeX = slopeID*slopeInterval - slopeDx;
					final int slopeDx2 = (int)(slopeDx*rate), slopeH2 = (int)(GHQ.screenH()/2*rate);
					GHQ.getG2D(Color.BLUE, GHQ.stroke1);
					GHQ.getG2D().drawLine(slopeX, 0, slopeX + slopeDx2, slopeH2);
					GHQ.getG2D().drawLine(GHQ.screenW() - slopeX, GHQ.screenH() - slopeH2, GHQ.screenW() - slopeX - slopeDx2, GHQ.screenH());
					++slopeID;
				}
				break guessAnimationPhase;
			}
		}
	}
}
