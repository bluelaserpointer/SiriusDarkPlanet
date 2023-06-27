package floatGame.engine;

import sound.SoundClip;

public enum FloatStageKind {
	Capricorn("2 vs 2", "../floatGame/sounds/silentbattle.mp3", new int[] {200, 1500}, new int[] {FloatGame.STAGE_W - 200, FloatGame.STAGE_W - 1500}),
	Aquarius("2 vs 3", "../floatGame/sounds/silentbattle.mp3", new int[] {200, 1500}, new int[] {FloatGame.STAGE_W - 200, FloatGame.STAGE_W - 1500, FloatGame.STAGE_W - 2800}),
	Pisces("2 vs 3", "../floatGame/sounds/silentbattle.mp3", new int[] {200, 1500}, new int[] {FloatGame.STAGE_W - 200, FloatGame.STAGE_W - 400, FloatGame.STAGE_W - 600}),
	Aries("1 vs 4", "../floatGame/sounds/silentbattle.mp3", new int[] {200}, new int[] {FloatGame.STAGE_W - 200, FloatGame.STAGE_W - 400, FloatGame.STAGE_W - 600, FloatGame.STAGE_W - 800}),
	The_Sirius("3 vs ?", "../floatGame/sounds/silentbattle.mp3", new int[] {200, 400, 600}, new int[] {}); //boss stage
	
	public final String description;
	public final SoundClip bgm;
	public final int[] friendBasePositions;
	public final int[] enemyBasePositions;
	private FloatStageKind(String description, String bgmURL, int[] friendBasePositions, int[] enemyBasePositions) {
		this.description = description;
		this.bgm = new SoundClip(bgmURL);
		this.friendBasePositions = friendBasePositions;
		this.enemyBasePositions = enemyBasePositions;
	}
}
