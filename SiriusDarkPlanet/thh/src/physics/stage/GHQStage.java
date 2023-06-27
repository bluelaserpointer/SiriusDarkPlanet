package physics.stage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import core.GHQ;
import core.GHQObject;
import core.GHQObjectList;
import core.GHQObjectType;
import physics.HasBoundingBox;
import physics.HasPoint;
import physics.HasHitGroup;
import physics.HitInteractable;
import physics.Point;
import preset.bullet.Bullet;
import preset.effect.Effect;
import preset.item.ItemData;
import preset.structure.Structure;
import preset.unit.Unit;
import preset.vegetation.Vegetation;
import physics.HitGroup;

public class GHQStage implements HasBoundingBox {
	
	public final Point point = new Point.IntPoint();
	protected final int width, height;
	public final GHQObjectList<Unit> units = new GHQObjectList<Unit>();
	public final GHQObjectList<Bullet> bullets = new GHQObjectList<Bullet>();
	public final GHQObjectList<Effect> effects = new GHQObjectList<Effect>();
	public final GHQObjectList<Structure> structures = new GHQObjectList<Structure>();
	public final GHQObjectList<Vegetation> vegetations = new GHQObjectList<Vegetation>();
	public final GHQObjectList<ItemData> items = new GHQObjectList<ItemData>();
	
	public final GHQObjectList<GHQObject> bulletCollisionGroup = new GHQObjectList<GHQObject>();
	
	public GHQStage(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public GHQStage(GHQStage sample) {
		width = sample.width;
		height = sample.height;
	}
	
	//main role
	public void idle() {
		GHQObject.removeDeletedFromList(bulletCollisionGroup);
		items.defaultTraverse();
		vegetations.defaultTraverse();
		structures.defaultTraverse();
		units.defaultTraverse();
		bullets.defaultTraverse();
		effects.defaultTraverse();
	}
	public void idle(GHQObjectType type) {
		switch(type) {
		case UNIT: units.defaultTraverse(); break;
		case BULLET: bullets.defaultTraverse(); break;
		case EFFECT: effects.defaultTraverse(); break;
		case STRUCTURE: structures.defaultTraverse(); break;
		case VEGETATION: vegetations.defaultTraverse(); break;
		case ITEM: items.defaultTraverse(); break;
		}
	}
	public void fill(Color color) {
		final Graphics2D G2 = GHQ.getG2D();
		G2.setColor(color);
		G2.fillRect(0, 0, width, height);
	}
	
	//update

	/**
	 * Add an {@link Bullet} to current stage.
	 * @param bullet
	 * @return created Bullet
	 * @since alpha1.0
	 */
	public <T extends Bullet>T addBullet(T bullet) {
		return addBullet(bullet, false);
	}
	public <T extends Bullet>T addBullet(T bullet, boolean joinBulletCollisionGroup){
		if(!bullets.contains(bullet)) {
			bullets.add(bullet);
			bullet.cancelDeleteFromStage();
			bullet.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(bullet);
		}
		return bullet;
	}
	/**
	 * Add an {@link Effect} to current stage.
	 * @param effect
	 * @return created Effect
	 * @since alpha1.0
	 */
	public <T extends Effect>T addEffect(T effect) {
		return addEffect(effect, false);
	}
	public <T extends Effect>T addEffect(T effect, boolean joinBulletCollisionGroup){
		if(!effects.contains(effect)) {
			effects.add(effect);
			effect.cancelDeleteFromStage();
			effect.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(effect);
		}
		return effect;
	}
	/**
	 * Add an {@link Unit} to current stage.
	 * @param unit
	 * @return added unit
	 */
	public <T extends Unit>T addUnit(T unit) {
		return addUnit(unit, true);
	}
	public <T extends Unit>T addUnit(T unit, boolean joinBulletCollisionGroup) {
		if(!units.contains(unit)) {
			units.add(unit);
			unit.cancelDeleteFromStage();
			unit.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(unit);
		}
		return unit;
	}
	/**
	 * Add a {@link Structure} to current stage.
	 * @param structure
	 * @return added structure
	 */
	public <T extends Structure>T addStructure(T structure) {
		return addStructure(structure, true);
	}
	public <T extends Structure>T addStructure(T structure, boolean joinBulletCollisionGroup) {
		if(!structures.contains(structure)) {
			structures.add(structure);
			structure.cancelDeleteFromStage();
			structure.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(structure);
		}
		return structure;
	}
	/**
	 * Add a {@link Vegetation} to current stage.
	 * @param vegetation
	 * @return added vegetation
	 */
	public <T extends Vegetation>T addVegetation(T vegetation) {
		return addVegetation(vegetation, true);
	}
	public <T extends Vegetation>T addVegetation(T vegetation, boolean joinBulletCollisionGroup){
		if(!vegetations.contains(vegetation)) {
			vegetations.add(vegetation);
			vegetation.cancelDeleteFromStage();
			vegetation.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(vegetation);
		}
		return vegetation;
	}
	/**
	 * Add a {@link ItemData} to current stage.
	 * @param item
	 * @return added item
	 */
	public <T extends ItemData>T addItem(T item) {
		return addItem(item, true);
	}
	public <T extends ItemData>T addItem(T item, boolean joinBulletCollisionGroup){
		if(!items.contains(item)) {
			items.add(item);
			item.cancelDeleteFromStage();
			item.addedToStage(this);
			if(joinBulletCollisionGroup)
				this.bulletCollisionGroup.add(item);
		}
		return item;
	}
	
	//tool
	/////////////////
	//Unit
	/////////////////
	public final ArrayList<Unit> getUnits_standpoint(HitGroup standPoint, boolean white) {
		final ArrayList<Unit> unitArray = new ArrayList<Unit>();
		for(Unit unit : units) {
			if(white == !standPoint.hitableWith(unit.hitGroup()))
				unitArray.add(unit);
		}
		return unitArray;
	}
	public final ArrayList<Unit> getUnits_standpoint(HasHitGroup source, boolean white) {
		return getUnits_standpoint(source.hitGroup(), white);
	}
	public final ArrayList<Unit> getUnits_standPoint(HasHitGroup source) {
		return getUnits_standpoint(source, true);
	}
	public final Unit getNearstEnemy(HitGroup standpoint, int x, int y) {
		double nearstDistanceSq = GHQ.MAX;
		Unit nearstUnit = null;
		for(Unit enemy : getUnits_standpoint(standpoint, false)) {
			if(!enemy.isAlive() || !enemy.hitableGroup(standpoint))
				continue;
			final double DISTANCE_SQ = enemy.point().distanceSq(x, y);
			if(nearstUnit == null || DISTANCE_SQ < nearstDistanceSq) {
				nearstDistanceSq = DISTANCE_SQ;
				nearstUnit = enemy;
			}
		}
		return nearstUnit;
	}
	public final Unit getNearstEnemy(HitGroup source, Point point) {
		return getNearstEnemy(source, point.intX(), point.intY());
	}
	public final Unit getNearstEnemy(Unit unit) {
		return getNearstEnemy(unit.hitGroup(), unit.point());
	}
	public final ArrayList<Unit> getVisibleEnemies(Unit unit) {
		final ArrayList<Unit> visibleEnemies = new ArrayList<Unit>();
		for(Unit enemy : getUnits_standpoint(unit, false)) {
			if(!enemy.isAlive())
				continue;
			if(enemy.point().isVisible(unit))
				visibleEnemies.add(enemy);
		}
		return visibleEnemies;
	}
	public final LinkedList<Unit> getVisibleUnit(HasPoint hasPoint) {
		return getVisibleUnit(hasPoint.point());
	}
	public final LinkedList<Unit> getVisibleUnit(Point point) {
		final LinkedList<Unit> visibleUnits = new LinkedList<Unit>();
		for(Unit unit : units) {
			if(point.isVisible(unit))
				visibleUnits.add(unit);
		}
		return visibleUnits;
	}
	public final Unit getNearstVisibleEnemy(Unit unit) {
		final Point DYNAM = unit.point();
		Unit nearstVisibleEnemy = null;
		double nearstDistanceSq = GHQ.MAX;
		for(Unit enemy : getUnits_standpoint(unit, false)) {
			if(!enemy.isAlive() || !DYNAM.isVisible(enemy) || enemy == unit)
				continue;
			if(nearstDistanceSq == GHQ.MAX) {
				nearstVisibleEnemy = enemy;
				nearstDistanceSq = DYNAM.distanceSq(enemy);
			}else {
				final double DISTANCE = DYNAM.distanceSq(enemy);
				if(nearstDistanceSq > DISTANCE) {
					nearstVisibleEnemy = enemy;
					nearstDistanceSq = DISTANCE;
				}
			}
		}
		return nearstVisibleEnemy;
	}
	public final Unit getNearstVisibleEnemy(Bullet bullet) {
		final Point DYNAM = bullet.point();
		Unit nearstVisibleEnemy = null;
		double nearstDistanceSq = GHQ.MAX;
		for(Unit enemy : getUnits_standpoint(bullet, false)) {
			if(!enemy.isAlive() || !DYNAM.isVisible(enemy))
				continue;
			if(nearstDistanceSq == GHQ.MAX) {
				nearstVisibleEnemy = enemy;
				nearstDistanceSq = DYNAM.distanceSq(enemy);
			}else {
				final double DISTANCE = DYNAM.distanceSq(enemy);
				if(nearstDistanceSq > DISTANCE) {
					nearstVisibleEnemy = enemy;
					nearstDistanceSq = DISTANCE;
				}
			}
		}
		return nearstVisibleEnemy;
	}
	/**
	 * A class helps {@link Bullet} or other objects to detect touching enemies.
	 * @param bullet
	 * @return
	 */
	public final ArrayList<Unit> getHitUnits(ArrayList<Unit> units, HitInteractable object) {
		final ArrayList<Unit> result = new ArrayList<Unit>();
		for(Unit unit : units) {
			if(unit.intersects(object))
				result.add(unit);
		}
		return result;
	}
	/**
	 * A class helps {@link Bullet} or other objects to detect touching enemies.
	 * @param bullet
	 * @return
	 */
	public final ArrayList<Unit> getHitUnits(HitInteractable object) {
		return getHitUnits(getUnits_standpoint(object, false), object);
	}
	public void clear() {
		bulletCollisionGroup.clear();
		units.clear();
		bullets.clear();
		effects.clear();
		structures.clear();
		vegetations.clear();
		items.clear();
	}
	
	//tool
	//structure
	public double visibility(int x1, int y1, int x2, int y2) {
		double totalVisibility = 0.0;
		for(Structure structure : structures) {
			if(structure.hitShape().intersectsLine(x1, y1, x2, y2)) {
				final double visibility = structure.visibility();
				if(visibility == Double.NEGATIVE_INFINITY)
					return Double.NEGATIVE_INFINITY;
				else
					totalVisibility += visibility;
			}
		}
		return totalVisibility;
	}
	public final double visibility(Point p1, Point p2) {
		return visibility(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public final boolean hitObstacle(HitInteractable object) {
		return !inStage(object) || structures.intersected(object);
	}
	public final boolean hitObstacle_atNewPoint(HitInteractable object, double dx, double dy) {
		return !inStage((int)(object.point().doubleX() + dx), (int)(object.point().doubleY() + dy)) || structures.intersected_atNewPoint(object, dx, dy);
	}
	
	//information
	/**
	 * Judge if a coordinate were in permitted area.
	 * @return true - in stage / false - in outside of stage
	 */
	public boolean inStage(int x, int y) {
		return 0 <= x && x <= width && 0 <= y && y <= height;
	}
	public final boolean inStage(Point point) {
		return inStage(point.intX(), point.intY());
	}
	public final boolean inStage(HasPoint hasPoint) {
		return inStage(hasPoint.point());
	}
	public final GHQObject forPoint(int x, int y) {
		GHQObject mostUpperObj, obj;
		mostUpperObj = bullets.forMouseOver();
		if((obj = structures.forMouseOver()).upperThen(mostUpperObj))
			mostUpperObj = obj;
		if((obj = units.forMouseOver()).upperThen(mostUpperObj))
			mostUpperObj = obj;
		if((obj = vegetations.forMouseOver()).upperThen(mostUpperObj))
			mostUpperObj = obj;
		if((obj = items.forMouseOver()).upperThen(mostUpperObj))
			mostUpperObj = obj;
		return mostUpperObj;
	}
	public final GHQObject forMouseOver_stage() {
		return forPoint(GHQ.mouseX(), GHQ.mouseY());
	}
	public final GHQObject forMouseOver_screen() {
		return forPoint(GHQ.mouseScreenX(), GHQ.mouseScreenY());
	}
	
	//debug-information
	public void unitDebugPaint() {
		for(Unit unit : units) {
			final int RECT_X = unit.point().intX(), RECT_Y = unit.point().intY();
			unit.drawBoundingBox(Color.RED, GHQ.stroke1);
			GHQ.getG2D(GHQ.debugTextColor, GHQ.stroke5);
			GHQ.getG2D().drawString(unit.name(), RECT_X + unit.width()/2, RECT_Y - unit.height()/2 - 6);
		}
	}
	public String entityAmountInfo() {
		return "Unit:" + units.size() + " EF:" + effects.size() + " B:" + bullets.size();
	}
	@Override
	public Point point() {
		return point;
	}
	@Override
	public int width() {
		return width;
	}
	@Override
	public int height() {
		return height;
	}
	public void onlyPaint() {
		items.traversePaint();
		vegetations.traversePaint();
		structures.traversePaint();
		units.traversePaint();
		bullets.traversePaint();
		effects.traversePaint();
	}
}
