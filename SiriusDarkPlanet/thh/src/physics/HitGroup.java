package physics;

import java.io.Serializable;

/**
 * A class for managing objects' friendly-or-hostile relationship.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class HitGroup implements Serializable {
	private static final long serialVersionUID = 4039529801782707123L;

	public static final int ALL = -1, NONE = -2;
	public static final HitGroup HIT_ALL = new HitGroup(ALL);
	public static final HitGroup HIT_NONE = new HitGroup(NONE);
	
	private int group;
	
	public HitGroup() {
		group = ALL;
	}
	public HitGroup(int initialGroup) {
		group = initialGroup;
	}
	public boolean hitableWith(HitGroup hitGroup) {
		return group != NONE && hitGroup.group != NONE && (group == ALL || hitGroup.group == ALL || group != hitGroup.group);
	}
	public boolean hitableWith(HasHitGroup target) {
		return target != null && hitableWith(target.hitGroup());
	}
	public void set(int group) {
		this.group = group;
	}
	public int get() {
		return group;
	}
	public final boolean hitAll() {
		return group == ALL;
	}
	public final boolean hitNone() {
		return group == NONE;
	}
}
