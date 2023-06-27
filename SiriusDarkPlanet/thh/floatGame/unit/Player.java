package floatGame.unit;

import calculate.Damage;
import core.GHQ;
import floatGame.effects.EngineEF;
import floatGame.engine.FloatResource;
import floatGame.modules.StarDust;
import floatGame.ui.HUD;
import paint.ImageFrame;

public class Player extends FloatSpaceship {
	
	protected ShipDesign shipDesign = ShipDesign.M1W1;
	
	public Player() {
		super(ImageFrame.create("floatGame/image/circleShip.png"), 1000, 50.0, FloatUnit.friendSide);
		super.addModule(new StarDust().setOwner(this));
		//super.addModule(new StarDust().setAngleIndicator(() -> lastDirectionLR().angle() + Math.PI/72).setOwner(this));
		//super.addModule(new StarDust().setAngleIndicator(() -> lastDirectionLR().angle() - Math.PI/72).setOwner(this));
		super.friendLightDotPaint = ImageFrame.create("floatGame/image/circleShip_greenLight.png");
		super.enemyLightDotPaint = ImageFrame.create("floatGame/image/circleShip_redLight.png");
	}
	
	@Override
	public void idle() {
		//set effect before coordinate change
		if(!isGrounded) {
			GHQ.stage().addEffect(new EngineEF(this, true, -50*lastDirectionLR.ex(), 0, 4));
			GHQ.stage().addEffect(new EngineEF(this, true, -50*lastDirectionLR.ex(), 0, 6));
		}
		super.idle();
		point().setXSpeed(point().xSpeed()*0.9);
	}
	
	@Override
	public void paintExtention() {
		super.paintExtention();
//		float breathe = (float)(Math.sin(GHQ.nowFrame()/30.0)/2 + 0.5);
//		final Color color = new Color(0F, 1F, 0F, breathe);
//		GHQ.getG2D(color).fillOval(cx(), cy(), 2, 2);
//		GHQ.getG2D(color).fillOval(cx() - 10, cy() - 2, 2, 2);
//		GHQ.getG2D(color).fillOval(cx() - 12, cy() - 3, 2, 2);
	}

	@Override
	public void shoot() {
		modules.get(0).tryWork();
		//modules.get(1).tryWork();
		//modules.get(2).tryWork();
	}
	
	public ShipDesign shipDesign() {
		return shipDesign;
	}

	@Override
	public double initialSpeed() {
		return FloatResource.playerSpeed;
	}
	
	@Override
	public String description() {
		return "<<Player>> Player unit.";
	}
	@Override
	public void damage(Damage damage) {
		super.damage(damage);
		if(!this.isAlive()) {
			HUD.judgeGame();
		}
	}
}
