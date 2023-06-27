package core;

import static physics.direction.Direction4.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

import paint.ColorFilling;
import physics.HasPoint;
import physics.Point;
import physics.Route;
import physics.direction.Direction4;
import preset.effect.debugEffect.DebugEffect;

public class CornerNavigation {
	
	protected final int EQUAL_GAP;
	
	protected static class EdgeNode{
		final Corner CORNER1,CORNER2;
		final double DISTANCE;
		private EdgeNode(Corner corner1, Corner corner2) {
			CORNER1 = corner1;
			CORNER2 = corner2;
			DISTANCE = CORNER1.distance(CORNER2);
		}
		boolean contains(Corner corner) {
			return CORNER1 == corner || CORNER2 == corner;
		}
		Corner getAnother(Corner corner) {
			if(corner == CORNER1)
				return CORNER2;
			if(corner == CORNER2)
				return CORNER1;
			System.out.println("EdgeNode.another bug.");
			return null;
		}
		public static void connect(Corner srcCorner, Corner dstCorner) {
			if(srcCorner.getLinkTo(dstCorner) != null)
				return;
			final EdgeNode LINK = new EdgeNode(srcCorner, dstCorner);
			dstCorner.link.add(LINK);
			srcCorner.link.add(LINK);
		}
	}
	protected class Corner extends Point.IntPoint{
		private static final long serialVersionUID = 2079598768300719520L;
		final MyLinkedList<EdgeNode> link = new MyLinkedList<EdgeNode>();
		EdgeNode prevLinkMark = null;
		double distanceMark = GHQ.MAX;
		Corner(int x, int y) {
			super(x, y);
		}
		Corner getPrevCornerMark() {
			return prevLinkMark == null ? null : prevLinkMark.getAnother(this);
		}
		EdgeNode getLinkTo(Corner dstCorner) {
			for(EdgeNode existingEdge : link) {
				if(existingEdge.contains(dstCorner)) //duplicated destination
					return existingEdge;
			}
			return null;
		}
		void explore0() {
			//find 4 neighbor Corners
			Point neighbors[] = Direction4.getVertHorzClosest_int(this, corners, W, D, S, A);
			//check 4 neighbor Corners are reachable
			anchors.addIfNotNull(explore1(Direction4.W, (Corner)neighbors[Direction4.W.ordinal()]));
			anchors.addIfNotNull(explore1(Direction4.D, (Corner)neighbors[Direction4.D.ordinal()]));
			anchors.addIfNotNull(explore1(Direction4.S, (Corner)neighbors[Direction4.S.ordinal()]));
			anchors.addIfNotNull(explore1(Direction4.A, (Corner)neighbors[Direction4.A.ordinal()]));
			//
		}
		Anchor[] explore0(Anchor settedAnchors[]) {
			//find 4 neighbor Corner
			Point neighbors[] = Direction4.getVertHorzClosest_int(this, corners, W, D, S, A);
			//check 4 neighbor Corner are reachable
			settedAnchors[Direction4.W.ordinal()] = explore1(Direction4.W, (Corner)neighbors[Direction4.W.ordinal()]);
			settedAnchors[Direction4.D.ordinal()] = explore1(Direction4.D, (Corner)neighbors[Direction4.D.ordinal()]);
			settedAnchors[Direction4.S.ordinal()] = explore1(Direction4.S, (Corner)neighbors[Direction4.S.ordinal()]);
			settedAnchors[Direction4.A.ordinal()] = explore1(Direction4.A, (Corner)neighbors[Direction4.A.ordinal()]);
			//
			return settedAnchors;
		}
		Anchor explore1(Direction4 direction, Corner neighborPoint) {
			Point point = new Point.IntPoint(this);
			if(hasWall(point.shift(direction, EQUAL_GAP))) { //has wall in first step
				return null;
			}else {
				while(true) {
					if(neighborPoint != null && !point.checkDirection(neighborPoint, direction)) { //arrive at the neighbor corner point
						EdgeNode.connect(this, neighborPoint);
						DebugEffect.setLine(Color.BLUE, GHQ.stroke3, intX(), intY(), neighborPoint.intX(), neighborPoint.intY());
						return null;
					}
					if(hasWall(point.shift(direction, EQUAL_GAP))) {
						point.shift(direction.back(), EQUAL_GAP);
						return new Anchor(this, point, direction);
					}
				}
			}
		}
	}
	protected class Anchor extends Point.IntPoint{
		private static final long serialVersionUID = 3823523254019408178L;
		final Direction4 ANCHOR_DIRECTION;
		final Corner NEST_CORNER;
		protected Anchor(Corner nestCorner, Point point, Direction4 directionID) {
			super(point);
			ANCHOR_DIRECTION = directionID;
			NEST_CORNER = nestCorner;
		}
		protected final Corner searchAnchorOrConer(Point basePoint, Direction4 direction) {
			final Corner foundCorner = Direction4.getVertHorzClosest_int(basePoint, corners, direction);
			final Anchor foundAnchor = Direction4.getVertHorzClosest_int(basePoint, anchors, direction);
			if(Direction4.getOutermost(direction.back(), foundCorner, foundAnchor) == foundCorner)
				return  foundCorner;
			else
				return  foundAnchor == null ? null : foundAnchor.NEST_CORNER;
		}
		protected void explore0() {
			
			Corner corner = explore1(this, ANCHOR_DIRECTION.left());
			if(corner != null) {
				EdgeNode.connect(NEST_CORNER, corner);
				DebugEffect.setLine(Color.CYAN, NEST_CORNER.intX(), NEST_CORNER.intY(), intX(), intY());
				DebugEffect.setLine(Color.CYAN, intX(), intY(), corner.intX(), corner.intY());
				DebugEffect.setLine(Color.WHITE, NEST_CORNER.intX(), NEST_CORNER.intY(), corner.intX(), corner.intY());
			}
			
			corner = explore1(this, ANCHOR_DIRECTION.right());
			if(corner != null) {
				EdgeNode.connect(NEST_CORNER, corner);
				DebugEffect.setLine(Color.CYAN, NEST_CORNER.intX(), NEST_CORNER.intY(), intX(), intY());
				DebugEffect.setLine(Color.CYAN, intX(), intY(), corner.intX(), corner.intY());
				DebugEffect.setLine(Color.WHITE, NEST_CORNER.intX(), NEST_CORNER.intY(), corner.intX(), corner.intY());
			}
		}
		protected Corner explore1(Point basePoint, Direction4 direction) {
			final Point point = new Point.IntPoint(basePoint);
			point.shift(direction, EQUAL_GAP);
			if(hasWall(point)) { //hit wall at first step -> stop searching
				return null;
			}
			Corner neighbor = searchAnchorOrConer(basePoint, direction);
			do {
				if(!point.checkDirection(neighbor, direction)) { //arrive
					return neighbor;
				}
				point.shift(direction, EQUAL_GAP);
			}while(!hasWall(point));
			//hit a wall
			point.shift(direction.back(), EQUAL_GAP);
			//check blocked direction (No way both of them are opened)
			final boolean RIGHT_BLOCKED = hasWall(point.shift(direction.right(), EQUAL_GAP));
			point.shift(direction.left(), EQUAL_GAP);
			return explore1(point, RIGHT_BLOCKED ? direction.left() : direction.right());
		}
	}
	
	protected MyLinkedList<Corner> corners = new MyLinkedList<Corner>();
	protected MyLinkedList<Anchor> anchors = new MyLinkedList<Anchor>();
	
	public CornerNavigation(int equalGap) {
		EQUAL_GAP = equalGap;
	}
	public CornerNavigation() {
		EQUAL_GAP = 1;
	}
	public void addCornerPoint(int x, int y) {
		for(Corner corner : corners) {
			if(corner.equals(x, y))
				return;
		}
		corners.add(new Corner(x, y));
	}
	public void defaultCornerCollect() {
		final int X_LIMIT = GHQ.stage().width(), Y_LIMIT = GHQ.stage().height();
		boolean oldBlockState = false;
		final Point point = new Point.IntPoint(EQUAL_GAP/2, EQUAL_GAP/2);
		for(;point.intX() < X_LIMIT;point.shift(D, EQUAL_GAP)) {
			point.setY(EQUAL_GAP/2);
			oldBlockState = hasWall(point);
			while(point.intY() < Y_LIMIT) {
				point.shift(S, EQUAL_GAP);
				final boolean nowBlockState = hasWall(point);
				if(oldBlockState != nowBlockState) {
					if(!hasWall(point.intX() - EQUAL_GAP, point.intY() - EQUAL_GAP) && !hasWall(point.intX() - EQUAL_GAP, point.intY()))
						addCornerPoint(point.intX() - EQUAL_GAP, nowBlockState ? point.intY() - EQUAL_GAP : point.intY());
					if(!hasWall(point.intX() + EQUAL_GAP, point.intY() - EQUAL_GAP) && !hasWall(point.intX() + EQUAL_GAP, point.intY()))
						addCornerPoint(point.intX() + EQUAL_GAP, nowBlockState ? point.intY() - EQUAL_GAP : point.intY());
				}
				oldBlockState = nowBlockState;
			}
		}
	}
	public void startCornerLink() {
		for(Corner ver : corners) {
			ver.explore0();
		}
		for(Anchor ver : anchors) {
			ver.explore0();
		}
	}
	public void debugPreview() {
		final Graphics2D G2 = GHQ.getG2D();
		for(Corner ver : corners) {
			ColorFilling.rectPaint(Color.BLUE, ver.intX() - 5, ver.intY() - 5, 10, 10);
			G2.setColor(Color.ORANGE);
			G2.drawString(String.valueOf((int)ver.distanceMark), ver.intX() - 15, ver.intY() - 15);
			G2.setStroke(GHQ.stroke3);
			if(ver.getPrevCornerMark() != null)
				G2.drawLine(ver.intX(), ver.intY(), ver.getPrevCornerMark().intX(), ver.getPrevCornerMark().intY());
		}
		for(Anchor ver : anchors) {
			ColorFilling.rectPaint(Color.CYAN, ver.intX() - 5, ver.intY() - 5, 10, 10);
		}
	}
	public void reset() { 
		corners.clear();
		anchors.clear();
	}
	protected Corner makeTemporaryCorner(int x, int y) {
		x += -(x%EQUAL_GAP) + EQUAL_GAP/2;
		y += -(y%EQUAL_GAP) + EQUAL_GAP/2;
		Corner myCorner = new Corner(x, y);
		final Anchor myAnchor[] = new Anchor[4];
		myCorner.explore0(myAnchor);
		for(Anchor ver : myAnchor) {
			if(ver != null)
				ver.explore0();
		}
		return myCorner;
	}
	protected void removeCorner(Corner corner) {
		for(EdgeNode link : corner.link) {
			link.getAnother(corner).link.remove(link);
		}
		corners.remove(corner);
	}
	Corner startCorner, goalCorner;
	public void setGoalPoint(int x, int y) {
		if(goalCorner != null)
			removeCorner(goalCorner);
		(goalCorner = makeTemporaryCorner(x, y)).distanceMark = 0.0;
		//init
		for(Corner corner : corners) {
			corner.prevLinkMark = null;
			corner.distanceMark = GHQ.MAX;
		}
		//find closest route
		for(EdgeNode link : goalCorner.link)
			expand(0.0, goalCorner, link);
	}
	public void setGoalPoint(Point point) {
		setGoalPoint(point.intX(), point.intY());
	}
	public void setGoalPoint(HasPoint hasPoint) {
		setGoalPoint(hasPoint.point());
	}
	public void expand(double nowDistance, Corner nowCorner, EdgeNode nextLink) {
		final Corner NEXT_CORNER = nextLink.getAnother(nowCorner);
		nowDistance += nextLink.DISTANCE;
		//check route duplication
		if(nowDistance < NEXT_CORNER.distanceMark) {
			//overwrite next corner's prevMark
		 	NEXT_CORNER.prevLinkMark = nextLink;
		 	NEXT_CORNER.distanceMark = nowDistance;
			for(EdgeNode link : NEXT_CORNER.link) {
				if(link != nextLink)
					expand(nowDistance, NEXT_CORNER, link);
			}
		}
	}
	public Route getRoot(int startX, int startY) {
		if(goalCorner == null) {
			System.out.println("CornerNavigation.getRoot: goalCorner is null.");
			return null;
		}
		//find closest root
		startCorner = makeTemporaryCorner(startX, startY);
		Corner assumeCorner = null;
		double distance = GHQ.MAX;
		for(EdgeNode link : startCorner.link) {
			final Corner CORNER = link.getAnother(startCorner);
			if(CORNER.distanceMark < distance) {
				distance = CORNER.distanceMark;
				assumeCorner = CORNER;
			}
		}
		removeCorner(startCorner);
		//return the result
		if(assumeCorner == null)
			return null;
		final LinkedList<Point> result = new LinkedList<Point>();
		for(Corner nowCorner = assumeCorner;nowCorner != null;nowCorner = nowCorner.getPrevCornerMark())
			result.push(nowCorner);
		return new Route(result);
	}
	public Route getRoot(Point startPoint) {
		return getRoot(startPoint.intX(), startPoint.intY());
	}
	public Route getRoot(HasPoint startHasPoint) {
		return getRoot(startHasPoint.point());
	}
	
	//extend
	public boolean hasWall(int x, int y) {
		return new Point.IntPoint(x, y).isBlocked();
	}
	public final boolean hasWall(Point point) {
		return hasWall(point.intX(), point.intY());
	}
}