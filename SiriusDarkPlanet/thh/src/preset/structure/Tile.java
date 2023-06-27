package preset.structure;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.BitSet;

import core.GHQ;
import physics.Point;
import physics.hitShape.HitShape;
import physics.HasPoint;
import physics.HasUIBoundingBox;

public class Tile extends Structure implements HasUIBoundingBox {
	public static final int TILE_SIZE = 25;
	public static int bp_ox = GHQ.NONE, bp_oy,bp_tileSize = TILE_SIZE;
	public class TileHitShape extends HitShape implements HasUIBoundingBox {
		protected final int
			X_TILES,
			Y_TILES;
		protected final BitSet aliveTiles;
		protected int lastHitTilePos;
		public TileHitShape(HasPoint owner, int x_tiles, int y_tiles) {
			super(owner);
			X_TILES = x_tiles;
			Y_TILES = y_tiles;
			aliveTiles = new BitSet(X_TILES*Y_TILES);
			aliveTiles.set(0, aliveTiles.size());
		}
		public TileHitShape(TileHitShape sample) {
			super(sample.owner);
			X_TILES = sample.X_TILES;
			Y_TILES = sample.Y_TILES;
			aliveTiles = new BitSet(sample.aliveTiles.size());
			aliveTiles.or(sample.aliveTiles);
		}
		private static final long serialVersionUID = -8708171125934571442L;
		@Override
		public int preciseIntersects(HitShape shape) {
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)) {
						Point point = new Point.IntPoint(point().intX() + xi*TILE_SIZE + TILE_SIZE/2, point().intY() + yi*TILE_SIZE + TILE_SIZE/2);
						if(shape.point().inRangeXY(point, (TILE_SIZE + shape.width())/2, (TILE_SIZE + shape.height())/2)) {
							lastHitTilePos = xi + yi*X_TILES;
							return 1;
						}
					}
				}
			}
			return 0;
		}
		@Override
		public boolean intersectsDot(int x, int y) {
			final int left = left(), top = top();
			return left < x && x < left + width() && top < y && y < top + height() && aliveTiles.get(tilePos(x, y));
		}
		public int tilePos(int x, int y) {
			return (x - left())/TILE_SIZE + (y - top())/TILE_SIZE*X_TILES;
		}
		public int tileX(int tilePos) {
			return left() + tilePos%X_TILES*TILE_SIZE + TILE_SIZE/2;
		}
		public int tileY(int tilePos) {
			return top() + tilePos/X_TILES*TILE_SIZE + TILE_SIZE/2;
		}
		@Override
		public boolean intersectsLine(int x1, int y1, int x2, int y2) {
			final Line2D line = new Line2D.Double(x1, y1, x2, y2);
			if(!line.intersects(point().intX(), point().intY(), X_TILES*TILE_SIZE, Y_TILES*TILE_SIZE))
				return false;
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)){
						if(line.intersects(point().intX() + xi*TILE_SIZE, point().intY() + yi*TILE_SIZE, TILE_SIZE, TILE_SIZE))
							return true;
					}
				}
			}
			return false;
		}
		@Override
		public void fill(Color color) {
			final Graphics2D G2 = GHQ.getG2D(color);
			final int x = point().intX(), y = point().intY();
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)) {
						G2.fillRect(x + xi*TILE_SIZE, y + yi*TILE_SIZE, TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}
		@Override
		public void draw(Color color, Stroke stroke) {
			final Graphics2D G2 = GHQ.getG2D(color);
			final int x = point().intX(), y = point().intY();
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)) {
						G2.drawRect(x + xi*TILE_SIZE, y + yi*TILE_SIZE, TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}
		@Override
		public HitShape clone(HasPoint newOwner) {
			return new TileHitShape(newOwner, X_TILES, Y_TILES);
		}
		public void paint() {
			for(int xi = 0;xi < X_TILES;xi++) {
				for(int yi = 0;yi < Y_TILES;yi++) {
					if(aliveTiles.get(xi + yi*X_TILES)){
						final int PX = point().intX() + xi*TILE_SIZE, PY = point().intY() + yi*TILE_SIZE;
						paintCell(PX, PY, TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}
		@Override
		public int width() {
			return X_TILES*TILE_SIZE;
		}
		@Override
		public int height() {
			return Y_TILES*TILE_SIZE;
		}
		public BitSet aliveTiles() {
			return aliveTiles;
		}
	}
	public Tile(int ox, int oy, int x_tiles, int y_tiles) {
		physics().setPoint(new Point.IntPoint(ox, oy));
		physics().setHitShape(new TileHitShape(this, x_tiles, y_tiles));
	}
	public static Tile create(Rectangle2D rect) {
		return new Tile((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth()/TILE_SIZE, (int)rect.getHeight()/TILE_SIZE);
	}
	//role
	@Override
	public void paint() {
		((TileHitShape)hitShape()).paint();
	}
	protected void paintCell(int x, int y, int w, int h) {
		GHQ.getG2D(Color.WHITE).fillRect(x, y, w, h);
		GHQ.getG2D(Color.GRAY).drawRect(x, y, w, h);
	}
	
	//information
	public int xTiles() {
		return ((TileHitShape)hitShape()).X_TILES;
	}
	public int yTiles() {
		return ((TileHitShape)hitShape()).Y_TILES;
	}
	@Override
	public String name() {
		return "DefaultTile";
	}
	public int lastHitTilePos() {
		//TODO: not safe
		return ((TileHitShape)hitShape()).lastHitTilePos;
	}
	public static void blueprint_addOriginPoint(int x, int y) {
		bp_ox = x;
		bp_oy = y;
	}
	public static Tile blueprint_addEndPointAndFlush(int x, int y) {
		final int x_tiles = Math.abs((x - bp_ox)/bp_tileSize) + 1, y_tiles = Math.abs((y - bp_oy)/bp_tileSize) + 1;
		final Tile tile = new Tile(bp_ox < GHQ.mouseX() ? bp_ox : bp_ox - x_tiles*bp_tileSize, bp_oy < GHQ.mouseY() ? bp_oy : bp_oy - y_tiles*bp_tileSize, x_tiles, y_tiles);
		blueprint_clear();
		return tile;
	}
	public static boolean blueprint_hasOriginPoint() {
		return bp_ox != GHQ.NONE;
	}
	public static void blueprint_clear() {
		bp_ox = GHQ.NONE;
		bp_tileSize = TILE_SIZE;
	}
	public static void makeGuiding(Graphics2D g2) {
		if(bp_ox != GHQ.NONE) {
			g2.setColor(Color.WHITE);
			g2.setStroke(GHQ.stroke1);
			final int MOUSE_X = GHQ.mouseX(), MOUSE_Y = GHQ.mouseY();
			final int x_tiles = Math.abs((MOUSE_X - bp_ox)/bp_tileSize) + 1, y_tiles = Math.abs((MOUSE_Y - bp_oy)/bp_tileSize) + 1;
			final int TX = bp_ox < MOUSE_X ? 0 : -x_tiles*bp_tileSize, TY = bp_oy < MOUSE_Y ? 0 : -y_tiles*bp_tileSize;
			g2.translate(TX, TY);
			for(int xi = 0;xi < x_tiles;xi++) {
				for(int yi = 0;yi < y_tiles;yi++)
					g2.drawRect(bp_ox + xi*bp_tileSize, bp_oy + yi*bp_tileSize, bp_tileSize, bp_tileSize);
			}
			g2.translate(-TX, -TY);
			g2.setColor(Color.ORANGE);
			g2.setStroke(GHQ.stroke3);
			g2.drawLine(bp_ox, bp_oy, MOUSE_X, MOUSE_Y);
		}
	}
}
