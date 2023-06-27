package core;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import camera.Camera;
import gui.GUIPartsSwitcher;
import physics.Point;
import physics.stage.GHQStage;

/**
 * A important class which is a frame of game main system.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Game {
	
	protected static GUIPartsSwitcher mainScreen;
	protected static GHQStage nowStage;
	
	public Game(GUIPartsSwitcher screen) {
		mainScreen = screen;
	}
	
	public void loadResource() {}
	public abstract GHQStage loadStage();
	
	//camera
	protected Camera starterCamera() {
		return new Camera(new Point.DoublePoint());
	}
	
	//idle
	public abstract void idle(Graphics2D g2, int stopEventKind);
	
	//event
	public void mousePressed(MouseEvent e) {
		GHQ.doMouseClickUIEvent(e);
	}
	public void mouseReleased(MouseEvent e) {
		GHQ.mouseHoveredUI().released(e);
	}
	public void mouseMoved(MouseEvent e) {
		GHQ.mouseHoveredUI().mouseOver();
	}
	public void mouseDragged(MouseEvent e) {
		GHQ.mouseHoveredUI().mouseOver();
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		GHQ.doMouseWheelMovedUIEvent(e);
	}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	//information
	public String getTitleName() {
		return GHQ.NOT_NAMED;
	}
	public String getIconPass() {
		return null;
	}
	public String getVersion() {
		return GHQ.NOT_NAMED;
	}
	public static void setStage(GHQStage stage) {
		Game.nowStage = stage;
	}
	public static GHQStage stage() {
		return nowStage;
	}
	public static GUIPartsSwitcher mainScreen() {
		return mainScreen;
	}
}
