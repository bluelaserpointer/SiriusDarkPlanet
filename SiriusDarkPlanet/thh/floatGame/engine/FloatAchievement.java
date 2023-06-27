package floatGame.engine;

import paint.ImageFrame;
import paint.dot.DotPaint;

public enum FloatAchievement {
	ENGINE_CRUSH("EngineCrush", "AchievementEngineCrush.png", 10),
	KILL("Kill", "AchievementKill.png", 50),
	SHOT_DOWN("ShotDown", "AchievementShotDown.png", 150);
	
	public final String name;
	public final int score;
	public final DotPaint paint;
	private FloatAchievement(String name, String imageURL, int score) {
		this.name = name;
		this.score = score;
		this.paint = ImageFrame.create("floatGame/image/" + imageURL);
	}
}
