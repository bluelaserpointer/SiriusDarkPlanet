package floatGame.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;

import core.GHQ;
import floatGame.engine.FloatAchievement;
import floatGame.engine.FloatGame;
import floatGame.unit.FloatUnit;
import floatGame.unit.Player;
import physics.HitGroup;
import physics.direction.DirectionLR;
import preset.unit.Unit;

public class HUD {
	public static String message;
	public static Color messageColor;
	public static int messagePostFrame = -1000;
	
	public static int friendBaseCount = 0, enemyBaseCount = 0;
	public static HitGroup winTeam;
	public static int judgeGameFrame = -1000;
	public static int gameTimePassedWhenJudge;
	
	public static final int[] achievementCount = new int[FloatAchievement.values().length];
	public static FloatAchievement lastAchievement;
	public static int lastAchievementFrame = -1000;
	public static String lastAchievementAddText;
	
	private boolean showResearchDetail;
	
	public static long gameStartedTime;
	
	public static void init() {
		messagePostFrame = -1000;
		winTeam = null;
		judgeGameFrame = -1000;
		Arrays.fill(achievementCount, 0);
		lastAchievementFrame = -1000;
		friendBaseCount = enemyBaseCount = 0;
	}
	
	public void draw() {
		final Player player = FloatGame.player;
		//research progress
		final int barH2 = 40, barW2 = 200;
		GHQ.getG2D(Color.BLUE).drawRect(0, barH2*0, barW2, barH2);
		GHQ.getG2D(Color.BLUE).drawRect(0, barH2*1, barW2, barH2);
		GHQ.getG2D(Color.BLUE).drawRect(0, barH2*2, barW2, barH2);
		//research detail
		if(showResearchDetail) {
			
		}
		//factoryDetailUI
		FactoryDetailUI.idle();
		ShipDetailUI.idle();
		//time counter
		GHQ.getG2D(Color.WHITE);
		GHQ.drawString_left((GHQ.passedTime(gameStartedTime)/1000) + " s", 8, 20, 8);
		//base counter
		GHQ.getG2D(Color.GREEN);
		GHQ.drawString_left("Friend Basement: " + friendBaseCount, 8, 20 + barH2*1, 8);
		GHQ.getG2D(Color.RED);
		GHQ.drawString_left("Enemy Basement: " + enemyBaseCount, 8, 20 + barH2*2, 8);
		//height meter
		final int HEIGHT_MERTER_X = 25;
		final int HEIGHT_METER_H = 250;
		final int HEIGHT_METER_W = 10;
		final int HEIGHT_METER_TOP = GHQ.screenH()/2 - HEIGHT_METER_H/2;
		final int HEIGHT_METER_BOTTOM = GHQ.screenH()/2 + HEIGHT_METER_H/2;
		final int HEIGHT_METER_LEFT = HEIGHT_MERTER_X - HEIGHT_METER_W/2;
		final int HEIGHT_METER_RIGHT = HEIGHT_MERTER_X + HEIGHT_METER_W/2;
		final int METER_AMOUNT = 25;
		final int HEIGHT_METER_INTERVAL = HEIGHT_METER_H / METER_AMOUNT;
		GHQ.getG2D(Color.WHITE, GHQ.stroke3).drawLine(HEIGHT_MERTER_X, HEIGHT_METER_TOP + 1, HEIGHT_MERTER_X, HEIGHT_METER_BOTTOM - 1);
		for(int i = 0; i <= METER_AMOUNT; ++i) {
			final int y = HEIGHT_METER_TOP + i*HEIGHT_METER_INTERVAL;
			GHQ.getG2D(Color.WHITE, GHQ.stroke1).drawLine(HEIGHT_METER_LEFT, y, HEIGHT_METER_RIGHT, y);
		}
		final int heightMeterCurY = HEIGHT_METER_TOP + (int)((double)player.cy() / FloatGame.STAGE_H *HEIGHT_METER_H);
		GHQ.getG2D().drawLine(HEIGHT_METER_LEFT - 10, heightMeterCurY, HEIGHT_METER_RIGHT + 10, heightMeterCurY);
		//accel meter
		final int ACCEL_METER_X = 75;
		final int ACCEL_METER_Y = 490;
		final int ACCEL_METER_SIZE1 = 50;
		final int ACCEL_METER_SIZE2 = 100;
		final Graphics2D g2 = GHQ.getG2D(new Color(1F, 1F, 1F, 0.7F), GHQ.stroke1);
		//accel meter - cross
		g2.drawLine(ACCEL_METER_X - ACCEL_METER_SIZE2/2, ACCEL_METER_Y, ACCEL_METER_X + ACCEL_METER_SIZE2/2, ACCEL_METER_Y);
		g2.drawLine(ACCEL_METER_X, ACCEL_METER_Y - ACCEL_METER_SIZE2/2, ACCEL_METER_X, ACCEL_METER_Y + ACCEL_METER_SIZE2/2);
		//accel meter - boxes
		g2.drawRect(ACCEL_METER_X - ACCEL_METER_SIZE1/2, ACCEL_METER_Y - ACCEL_METER_SIZE1/2, ACCEL_METER_SIZE1, ACCEL_METER_SIZE1);
		g2.drawRect(ACCEL_METER_X - ACCEL_METER_SIZE2/2, ACCEL_METER_Y - ACCEL_METER_SIZE2/2, ACCEL_METER_SIZE2, ACCEL_METER_SIZE2);
		//accel meter - dot
		final double METER_RATE = 0.5;
		final int METER_DOT_SIZE = 24;
		final int accelMeterDotX = ACCEL_METER_X + (int)(FloatGame.player.point().xSpeed() * METER_RATE);
		final int accelMeterDotY = ACCEL_METER_Y + (int)(FloatGame.player.point().ySpeed() * METER_RATE);
		g2.drawOval(accelMeterDotX - METER_DOT_SIZE/2, accelMeterDotY - METER_DOT_SIZE/2, METER_DOT_SIZE, METER_DOT_SIZE);
		//key control guide
		final int KEY_CONTROL_GUIDE_LEFT = 150;
		final int KEY_CONTROL_GUIDE_TOP = 450;
		GHQ.drawString_left("↑↓←→: Move", KEY_CONTROL_GUIDE_LEFT, KEY_CONTROL_GUIDE_TOP, 6);
		GHQ.drawString_left("Z: Fire", KEY_CONTROL_GUIDE_LEFT, KEY_CONTROL_GUIDE_TOP + 20, 6);
		GHQ.drawString_left("Space: Brake", KEY_CONTROL_GUIDE_LEFT, KEY_CONTROL_GUIDE_TOP + 40, 6);
		GHQ.drawString_left("Esc: Pause", KEY_CONTROL_GUIDE_LEFT, KEY_CONTROL_GUIDE_TOP + 60, 6);
		//achievement notice
		final int achievementPassedFrame = GHQ.passedFrame(lastAchievementFrame);
		final int ACHIEVEMENT_NOTICE_FRAME = 100;
		final int ACHIEVEMENT_FADE_FRAME = 10;
		if(achievementPassedFrame < ACHIEVEMENT_NOTICE_FRAME) {
			if(achievementPassedFrame < ACHIEVEMENT_FADE_FRAME) {
				GHQ.setImageAlpha((float)((double)achievementPassedFrame / ACHIEVEMENT_FADE_FRAME));
			} else if(ACHIEVEMENT_NOTICE_FRAME - achievementPassedFrame < ACHIEVEMENT_FADE_FRAME) {
				GHQ.setImageAlpha((float)((double)(ACHIEVEMENT_NOTICE_FRAME - achievementPassedFrame) / ACHIEVEMENT_FADE_FRAME));
			}
			lastAchievement.paint.dotPaint(160, 350);
			GHQ.drawString_left(lastAchievement.name + ": +" + lastAchievement.score + "pt", 150, 380, 10);
			if(lastAchievementAddText != null && !lastAchievementAddText.isEmpty())
				GHQ.drawString_left(lastAchievementAddText, 150, 400, 10);
			GHQ.setImageAlpha();
		}
		
		//ammo & health bar
		final int barH = 20;
		GHQ.getG2D(Color.BLUE).fillRect(0, GHQ.screenH() - barH*2 - 10, (int)(FloatGame.player.energyRate()*GHQ.screenW()), barH);
		GHQ.getG2D(Color.RED).fillRect(0, GHQ.screenH() - barH*1 - 10, (int)(FloatGame.player.hpRate()*GHQ.screenW()), barH);

		//out of screen radar
		final int RADAR_RANGE = GHQ.screenW()*3;
		final int LINE_LENGTH_MAX = 50;
		for(Unit unit : GHQ.stage().units) {
			if(unit.hitGroup().equals(player.hitGroup()))
				continue;
			final int dx = player.point().intDX(unit.point()), dy = player.point().intDY(unit.point());
			DirectionLR vert, horz;
			if(-RADAR_RANGE < dx && dx < RADAR_RANGE) {
				if(dx < -GHQ.screenW()/2)
					horz = DirectionLR.LEFT;
				else if(GHQ.screenW()/2 < dx)
					horz = DirectionLR.RIGHT;
				else
					horz = null;
				if(-RADAR_RANGE < dy && dy < RADAR_RANGE) {
					if(dy < -GHQ.screenH()/2)
						vert = DirectionLR.LEFT;
					else if(GHQ.screenH()/2 < dy)
						vert = DirectionLR.RIGHT;
					else
						vert = null;
					//display
					if(horz == null && vert == null) //in screen
						continue;
					final int lineLength = LINE_LENGTH_MAX - (int)(LINE_LENGTH_MAX*player.point().distance(unit)/RADAR_RANGE);
					if(horz != null && vert != null) { //corner out of bounds
						final int x = (horz.isRight() ? GHQ.screenW() : 0);
						final int y = (vert.isRight() ? GHQ.screenH() : 0);
						GHQ.getG2D(Color.WHITE).drawLine(x, y, x - lineLength*horz.ex(), y - lineLength*vert.ex());
					} else if(horz != null) { //horizon out of bounds
						final int x = (horz.isRight() ? GHQ.screenW() : 0);
						final int y = unit.cy() - GHQ.cameraTop();
						GHQ.getG2D(Color.WHITE).drawLine(x, y, x - lineLength*horz.ex(), y);
					} else if(vert != null) { //vertical out of bounds
						final int x = unit.cx() - GHQ.cameraLeft();
						final int y = (vert.isRight() ? GHQ.screenH() : 0);
						GHQ.getG2D(Color.WHITE).drawLine(x, y, x, y - lineLength*vert.ex());
					}
				}
			}
		}
		
		//ammo & health warning
		if(FloatGame.player.isAlive()) {
			if(GHQ.nowFrame() % 20 > 10) {
				if(FloatGame.player.hpRate() < 0.20) {
					GHQ.getG2D(Color.RED);
					GHQ.drawString_left("HEALTH", 10, GHQ.screenH() - 75, 10);
				}
				if(FloatGame.player.energyRate() < 0.20) {
					GHQ.getG2D(Color.BLUE);
					GHQ.drawString_left("AMMO", 100, GHQ.screenH() - 75, 10);
				}
			}
		} else {
			GHQ.getG2D(Color.GRAY);
			GHQ.drawString_left("HEALTH", 10, GHQ.screenH() - 75, 10);
			if(FloatGame.player.energyRate() < 0.20) {
				GHQ.getG2D(Color.BLUE);
				GHQ.drawString_left("AMMO", 100, GHQ.screenH() - 75, 10);
			}
		}
		
		//message
		final int messagePostPassedFrame = GHQ.passedFrame(messagePostFrame);
		if(messagePostPassedFrame < 100) {
			if(messagePostPassedFrame % 10 < 5) {
				GHQ.getG2D(messageColor);
				GHQ.drawString_center(message, GHQ.screenW()/2, GHQ.screenH() - 70, 15);
			}
		}
	}
	public void setShowResearchDetail(boolean b) {
		showResearchDetail = b;
	}
	public static void postMessage(String message, Color color) {
		if(winTeam != null)
			return;
		HUD.message = message;
		HUD.messageColor = color;
		HUD.messagePostFrame = GHQ.nowFrame();
	}
	public static void postAchievement(FloatAchievement achievement) {
		if(winTeam != null)
			return;
		HUD.lastAchievement = achievement;
		HUD.lastAchievementFrame = GHQ.nowFrame();
		HUD.lastAchievementAddText = "";
		achievementCount[achievement.ordinal()] += 1;
	}
	public static void postAchievement(FloatAchievement achievement, String additionalText) {
		if(winTeam != null)
			return;
		HUD.lastAchievement = achievement;
		HUD.lastAchievementFrame = GHQ.nowFrame();
		HUD.lastAchievementAddText = additionalText;
		achievementCount[achievement.ordinal()] += 1;
	}
	public static void decreaseFriendBaseCount() {
		if(winTeam != null)
			return;
		--HUD.friendBaseCount;
		judgeGame();
	}
	public static void decreaseEnemyBaseCount() {
		if(winTeam != null)
			return;
		--HUD.enemyBaseCount;
		judgeGame();
	}
	public static void judgeGame() {
		if(winTeam == null) {
			if(HUD.enemyBaseCount == 0) {
				winTeam = FloatUnit.friendSide;
			} else if(HUD.friendBaseCount == 0 || !FloatGame.player.isAlive()) {
				winTeam = FloatUnit.enemySide;
			} else {
				return;
			}
			judgeGameFrame = GHQ.nowFrame();
			gameTimePassedWhenJudge = GHQ.passedTime(gameStartedTime)/1000;
			FloatGameScreen.resultWindow.enable();
		}
	}
}
