package preset.vegetation;

import core.GHQObject;
import paint.dot.DotPaint;
import paint.dot.HasDotPaint;
import physics.HasBoundingBox;
import physics.Point;

/**
 * A primal class for managing vegetation object.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class Vegetation extends GHQObject implements HasBoundingBox, HasDotPaint {
	protected final DotPaint paintScript;

	//init-physics parameter
	@Override
	protected Point predefine_point() {
		return new Point.IntPoint();
	}
	//init-constructor
	public Vegetation(DotPaint paintScript, int x, int y) {
		point().setXY(x, y);
		this.paintScript = paintScript;
	}
	public Vegetation(DotPaint paintScript, Point point) {
		point().setXY(point);
		this.paintScript = paintScript;
	}
	public Vegetation(Vegetation sample) {
		point().setXY(sample);
		this.paintScript = sample.paintScript;
	}
	//idle
	@Override
	public void paint() {
		super.paint();
		paintScript.dotPaint(point());
	}
	//control
	@Override
	public Vegetation clone() {
		return new Vegetation(this);
	}
	
	//information
	@Override
	public int width() {
		return paintScript.width();
	}
	@Override
	public int height() {
		return paintScript.height();
	}
	@Override
	public final DotPaint getDotPaint() {
		return paintScript;
	}
}
