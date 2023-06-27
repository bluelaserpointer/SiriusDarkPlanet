package troubleCrasher.story;

import java.util.ArrayList;
import java.util.List;

public class StoryMechanicManager {
	private List<Visitor> scheduledVisitors = new ArrayList<>();

	public void addVisitor(String name, int date, String script)
	{
		this.scheduledVisitors.add(new Visitor(name, date, script));
	}
}
