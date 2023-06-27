package preset.effect.debugEffect;

import java.awt.Color;
import java.awt.Stroke;

import core.GHQ;
import core.GHQObject;
import paint.ColorLine;
import physics.Angle;
import physics.Point;
import preset.effect.Effect;

public class DebugEffect extends Effect {
	public static int lifeSpan = 5;
	public static Effect setLine(Color color, Stroke stroke, int x1, int y1, int x2, int y2) {
		final Effect effect = new Effect();
		effect.point().setXY(x1, y1);
		effect.paintScript = new ColorLine(color, stroke).convertToDotPaint(GHQObject.NULL_GHQ_OBJECT, x2, y2);
		effect.limitFrame = lifeSpan;
		return GHQ.stage().addEffect(effect);
	}
	public static Effect setLine(Color color, Stroke stroke, Point p1, Point p2) {
		return setLine(color, stroke, p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public static Effect setLine(Color color, int x1, int y1, int x2, int y2) {
		return setLine(color, GHQ.stroke1, x1, y1, x2, y2);
	}
	public static Effect setLine(Color color, Point p1, Point p2) {
		return setLine(color, p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public static Effect setDot(Color color, Point point) {
		return GHQ.stage().addEffect(new Effect() {
			{
				point().setXY(point);
				limitFrame = lifeSpan;
			}
			@Override
			public void idle() {
				super.idle();
				GHQ.stage().addEffect(new Effect() {
					{
						point().setXY(point);
						limitFrame = 10;
						point().addSpeed_DA(15, Angle.random());
					}
					@Override
					public void paint() {
						GHQ.getG2D(color, GHQ.stroke1).drawOval(point().intX() - 2, point().intY() - 2, 4, 4);
					}
				});
			}
		});
	}
}
