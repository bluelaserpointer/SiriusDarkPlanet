package floatGame.engine;

import static java.awt.event.KeyEvent.*;

import java.awt.Graphics2D;

import camera.Camera;
import camera.FixChaseCamera;
import core.GHQ;
import core.Game;
import floatGame.ui.FloatGameScreen;
import floatGame.ui.FloatHowToPlayScreen;
import floatGame.ui.FloatIndexScreen;
import floatGame.ui.FloatStageSelectScreen;
import floatGame.ui.FloatTitleScreen;
import floatGame.unit.Player;
import gui.GUIPartsSwitcher;
import input.key.SingleKeyListener;
import input.mouse.MouseListenerEx;
import physics.stage.GHQStage;

public class FloatGame extends Game {
	static {
		GHQ.setScreenSize(1000, 600);
	}
	
	@Override
	public String getTitleName() {
		return "Sirius Dark Planet";
	}
	@Override
	public String getVersion() {
		return "alpha1.3.1";
	}
	@Override
	public String getIconPass() {
		return "floatGame\\image\\GameIcon.png";
	}
	public static FloatGame game;

	//inputEvent
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
		VK_LEFT,
		VK_RIGHT,
		VK_UP,
		VK_DOWN,
		VK_Z,
		VK_TAB,
		VK_SHIFT,
		VK_SPACE,
		VK_ESCAPE,
		VK_F6,
	};
	private static final MouseListenerEx s_mouseL = new MouseListenerEx();
	private static final SingleKeyListener s_keyL = new SingleKeyListener(inputKeys);
	
	public static final int STAGE_W = 10000, STAGE_H = 3000;
	public static final FloatStage stage = new FloatStage();
	static final FixChaseCamera camera = new FixChaseCamera(null);
	public static Player player;
	private FloatResource resource;
	
	protected static boolean gamePause;

	public static final int
		TITLE_SCREEN = 0,
		GAME_SCREEN = 1,
		INDEX_SCREEN = 2,
		HOWTOPLAY_SCREEN = 3,
		STAGE_SELECT_SCREEN = 4;
	
	public static void main(String args[]) {
		new GHQ(new FloatGame(), 1000, 600);
	}
	public FloatGame() {
		super(new GUIPartsSwitcher(5, TITLE_SCREEN));
		GHQ.addGUIParts(mainScreen);
		game = this;
	}
	
	@Override
	protected Camera starterCamera() {
		return camera;
	}

	@Override
	public GHQStage loadStage() {
		/////////////////////////////////
		//input
		/////////////////////////////////
		GHQ.addListenerEx(s_mouseL);
		GHQ.addListenerEx(s_keyL);
		return stage;
	}
	
	@Override
	public void loadResource() {
		resource = new FloatResource();
		mainScreen.set(TITLE_SCREEN, new FloatTitleScreen());
		mainScreen.set(GAME_SCREEN, new FloatGameScreen());
		mainScreen.set(INDEX_SCREEN, new FloatIndexScreen());
		mainScreen.set(HOWTOPLAY_SCREEN, new FloatHowToPlayScreen());
		mainScreen.set(STAGE_SELECT_SCREEN, new FloatStageSelectScreen());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void idle(Graphics2D g2, int stopEventKind) {
		this.processInput();
		if(mainScreen.nowIndexIs(GAME_SCREEN)) {
			//stage objects
			if(gamePause) {
				GHQ.stage().onlyPaint();
				GHQ.tellPauseFrame(); //TODO
			} else
				GHQ.stage().idle();
		}
	}
	private void processInput() {
		//control
		switch(mainScreen.nowIndex()) {
		case TITLE_SCREEN:
			// no key control
			break;
		case GAME_SCREEN:
			if(s_keyL.hasEvent(VK_LEFT)) {
				player.moveLeft();
			}
			if(s_keyL.hasEvent(VK_RIGHT)) {
				player.moveRight();
			}
			if(s_keyL.hasEvent(VK_UP)) {
				player.hover();
			}
			if(s_keyL.hasEvent(VK_DOWN)) {
				player.down();
			}
			if(s_keyL.hasEvent(VK_SPACE)) {
				player.brake();
			}
			if(s_keyL.hasEvent(VK_Z)) {
				player.shoot();
			}
			if(s_keyL.pullEvent(VK_ESCAPE)) {
				switchGamePause();
			}
			break;
		}
	}
	public static void setGamePause(boolean b) {
		gamePause = b;
		if(b) {
			FloatGameScreen.pauseMenu.enable();
		} else {
			FloatGameScreen.pauseMenu.disable();
		}
	}
	public static void switchGamePause() {
		setGamePause(!gamePause);
	}
	public static boolean isGamePaused() {
		return gamePause;
	}
	public static void toMainMenu() {
		mainScreen.switchTo(TITLE_SCREEN);
	}
	public static void toIndex() {
		mainScreen.switchTo(INDEX_SCREEN);
	}
}
