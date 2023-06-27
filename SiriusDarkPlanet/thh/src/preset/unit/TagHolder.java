package preset.unit;

import java.io.Serializable;
import java.util.ArrayList;

import core.GHQ;

public abstract class TagHolder implements Serializable{
	private static final long serialVersionUID = 5735269528773492135L;

	public abstract ArrayList<Tag> getTags();
}

class Tag{
	public String getName() {
		return GHQ.NOT_NAMED;
	}
}
class PassiveTag extends Tag{
	
}