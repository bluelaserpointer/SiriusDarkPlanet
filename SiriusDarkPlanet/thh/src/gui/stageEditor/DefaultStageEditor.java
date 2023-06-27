package gui.stageEditor;

import static java.awt.event.KeyEvent.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Collection;

import core.GHQ;
import core.GHQObject;
import core.GHQObjectList;
import gui.ArrangedButtons;
import gui.BasicButton;
import gui.GUIParts;
import gui.InputOptionList;
import gui.TitledLabel;
import input.key.SingleKeyListener;
import math.SquareCellArranger;
import paint.ColorFilling;
import paint.ColorFraming;
import paint.ImageFrame;
import paint.dot.DotPaint;
import physics.Angle;
import physics.Dynam;
import physics.HasBoundingBox;
import physics.Point;
import preset.structure.Terrain;
import preset.structure.Tile;
import preset.unit.Unit;
import preset.vegetation.Vegetation;

/**
 * A class provides a default stage editor.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class DefaultStageEditor extends GUIParts{
	private final GHQObjectList<Tile> tileScripts = new GHQObjectList<Tile>();
	private final GHQObjectList<Terrain> terrainScripts = new GHQObjectList<Terrain>();
	
	//input
	private static final int inputKeys[] = 
	{
		VK_BACK_SPACE,
		VK_DELETE,
		VK_C,
		VK_V,
		VK_TAB,
		VK_CONTROL,
	};
	private static final SingleKeyListener keyListener = new SingleKeyListener(inputKeys);
	private static int nowSlot = 0,slot_max = 1;
	
	private static int placeKind;
	private static int placeX, placeY;
	
	private static final int
		POINTING = -1,
		TERRAIN = 0,
		TILES = 1,
		UNIT = 2,
		VEGETATION = 3,
		ITEM = 4;
	private static HasBoundingBox selectObject,mouseOveredObject;
	//GUI_GROUP_ID
	private final String
		EDIT_MENU_GROUP;
	//GUI
	private static TitledLabel configLabel;
	//init
	public DefaultStageEditor(String group) {
		EDIT_MENU_GROUP = group + ">EDIT_MENU_GROUP>";
		final int SCREEN_W = GHQ.screenW(),SCREEN_H = GHQ.screenH();
		super.addLast(new GUIParts() {
			{
				setBGPaint(new ColorFraming(Color.WHITE,GHQ.stroke3));
				setName(EDIT_MENU_GROUP).setBounds(150, 0, SCREEN_W - 150, SCREEN_H);
			}
			@Override
			public void idle() {
				super.idle();
				if(isMouseEntered()) {
					final Graphics2D G2 = GHQ.getG2D();
					GHQ.translateForGUI(false);
					//guide
					switch(placeKind) {
					case POINTING:
						mouseOveredObject = GHQ.stage().units.forMouseOver();
						if(mouseOveredObject == null)
							mouseOveredObject = GHQ.stage().vegetations.forMouseOver();
						if(mouseOveredObject == null)
							mouseOveredObject = GHQ.stage().structures.forMouseOver();
						if(mouseOveredObject != null && mouseOveredObject != selectObject) {
							mouseOveredObject.drawBoundingBox(Color.WHITE, GHQ.stroke5);
							G2.drawOval(mouseOveredObject.cx() - 9, mouseOveredObject.cy() - 9,18,18);
							G2.drawOval(GHQ.mouseX() - 5,GHQ.mouseY() - 5,10,10);
						}
						if(selectObject != null) {
							//changeSlot(Left side menu bar)
							if(keyListener.pullEvent(VK_TAB)) {
								if(++nowSlot > slot_max)
									nowSlot = 0;
								if(nowSlot == 1) { //select script
									configLabel.enableInputMode();
								}else {
									configLabel.outsideClicked();
								}
							}
							//delete
							if(!configLabel.activated && keyListener.pullEvent(VK_DELETE)) {
								if(selectObject instanceof GHQObject) {
									((GHQObject)selectObject).forceDelete();
								}else
									System.out.println("detected undeletable object.");
								selectObject = null;
								break;
							}
							/*//script install / name change
							if(selectObject instanceof Tile) {
								selectObject = tileScripts.forName(configLabel.getText());
							}else if(selectObject instanceof Terrain) {
								selectObject = terrainScripts.forName(configLabel.getText());
							}else if(selectObject instanceof Unit) {
								((Unit)selectObject).originalName = configLabel.getText();
							}*/
							//draw selection guide
							selectObject.drawBiggerBoundingBox(Color.WHITE, GHQ.stroke5, 5);
							selectObject.drawBiggerBoundingBox(Color.RED, GHQ.stroke3, 5);
							break;
						}
					case TERRAIN:
						Terrain.makeGuiding(G2);
						break;
					case TILES:
						Tile.makeGuiding(G2);
						break;
					}
					GHQ.translateForGUI(true);
				}
			}
			@Override
			public boolean clicked(MouseEvent e) {
				super.clicked(e);
				if(placeKind == POINTING) {
					selectObject = mouseOveredObject;
					final String labelText;
					if(selectObject instanceof Tile) {
						labelText = ((Tile)selectObject).name();
						configLabel.setTitle("script:");
					}else if(selectObject instanceof Terrain) {
						labelText = ((Terrain)selectObject).name();
						configLabel.setTitle("script:");
					}else if(selectObject instanceof Unit) {
						labelText = ((Unit)selectObject).originalName;
						configLabel.setTitle("orignal_name:");
					}else if(selectObject instanceof Vegetation) {
						labelText = "image";//((Vegetation)selectObject).getImageURL();
						configLabel.setTitle("image_URL:");
					}else
						labelText = "";
					configLabel.setText(labelText.equals(GHQ.NOT_NAMED) ? "" : labelText);
				}else { //object deselect
					selectObject = null;
					switch(placeKind) {
					case TERRAIN:
						if(Terrain.blueprint_isOriginPoint(placeX, placeY))
							GHQ.stage().addStructure(Terrain.blueprint_flush());
						else
							Terrain.blueprint_addPoint(placeX, placeY);
						break;
					case TILES:
						if(Tile.blueprint_hasOriginPoint())
							GHQ.stage().addStructure(Tile.blueprint_addEndPointAndFlush(GHQ.mouseX(), GHQ.mouseY()));
						else
							Tile.blueprint_addOriginPoint(placeX, placeY);
						break;
					case UNIT:
						GHQ.stage().addUnit(new Unit() {
							final Dynam dynam = new Dynam(placeX, placeY);
							@Override
							public Point point() {
								return dynam;
							}
							@Override
							public Angle angle() {
								return Angle.NULL_ANGLE;
							}
							@Override
							public Unit respawn(int spawnX, int spawnY) {
								return null;
							}
							@Override
							public boolean isAlive() {
								return true;
							}
							@Override
							public DotPaint getDotPaint() {
								return DotPaint.BLANK_SCRIPT;
							}
						});
						break;
					case VEGETATION:
						GHQ.stage().addVegetation(new Vegetation(ImageFrame.create("thhimage/gui_editor/Vegetation.png"), placeX, placeY));
						break;
					case ITEM:
						break;
					}
				}
				return true;
			}
		});
		super.addLast(new ArrangedButtons<Integer>(25, 155, new SquareCellArranger(1, 80, 80, 2, 2)) {
			{
				setName(EDIT_MENU_GROUP + "SelectionButtons");
			}
			@Override
			protected void buttonClicked(Integer buttonValue) {
				if(placeKind != buttonValue)
					placeKind = buttonValue;
				else
					placeKind = POINTING;
			}
			@Override
			public boolean clicked(MouseEvent e) {
				super.clicked(e);
				placeKind = POINTING;
				return true;
			}
		})
		.appendButton(TILES, ImageFrame.create("thhimage/gui_editor/Tiles.png"), 0, 0)
		.appendButton(TERRAIN, ImageFrame.create("thhimage/gui_editor/FreeShape.png"), 0, 1)
		.appendButton(UNIT, ImageFrame.create("thhimage/gui_editor/Unit.png"), 1, 0)
		.appendButton(VEGETATION, ImageFrame.create("thhimage/gui_editor/Vegetation.png"), 1, 1);
		super.addLast(new BasicButton() {
			{
				setName("SAVE_BUTTON").setBGPaint(ImageFrame.create("thhimage/gui_editor/Save.png"))
				.setBounds(25,500,85,40);
			}
			@Override
			public boolean clicked(MouseEvent e) {
				super.clicked(e);
				System.out.println("saving...");
				saveStage();
				System.out.println("complete!");
				return true;
			}
		});
		super.addLast(configLabel = new TitledLabel(){
			{
				setName(EDIT_MENU_GROUP + "CONFIG_LABEL").setBGPaint(new ColorFilling(Color.WHITE))
				.setBounds(25, 300, 120, 25);
			}
		});
		super.addLast(new InputOptionList(configLabel)).addWord("WHITE_WALL", "ABCD", "ABNK");
	}
	//role
	public abstract void saveStage();
	@Override
	public void idle() {
		super.idle();
		final Graphics2D G2 = GHQ.getG2D();
		GHQ.translateForGUI(false);
		//mouse
		if(true /*GHQ.key_shift*/) {
			G2.setColor(Color.RED);
			final int N = 100;
			int S = 4;
			final int SX = (GHQ.mouseX() + N/2)/N,SY = (GHQ.mouseY() + N/2)/N;
			for(int xi = -1;xi <= +1;xi++) {
				for(int yi = -1;yi <= +1;yi++)
					G2.fillOval((SX + xi)*N - S/2, (SY + yi)*N - S/2, S, S);
			}
			S += 6;
			G2.drawOval(SX*N - S/2, SY*N - S/2, S, S);
			placeX = SX*N;
			placeY = SY*N;
		}
		//originalName display
		G2.setColor(Color.GRAY);
		G2.setFont(GHQ.basicFont.deriveFont(20.0f));
		for(Unit unit : GHQ.stage().units)
			G2.drawString(unit.originalName, unit.point().intX(), unit.point().intY());
		G2.setFont(GHQ.basicFont);

		GHQ.translateForGUI(true);
		//GUI
		G2.setColor(Color.BLACK);
		G2.drawString("EDIT_MODE", 20, 20);
	}
	
	//control
	public void addTileScript(Collection<Tile> script) {
		tileScripts.addAll(script);
	}
	public void addTerrainScript(Collection<Terrain> script) {
		terrainScripts.addAll(script);
	}
	@Override
	public GUIParts enable() {
		super.enable();
		GHQ.stopScreen();
		selectObject = mouseOveredObject = null;
		return this;
	}
	@Override
	public GUIParts disable() {
		super.disable();
		GHQ.clearStopEvent();
		return this;
	}
}
