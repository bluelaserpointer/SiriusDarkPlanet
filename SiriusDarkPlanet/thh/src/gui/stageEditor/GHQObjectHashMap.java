package gui.stageEditor;

import java.util.HashMap;

public class GHQObjectHashMap extends HashMap<String, Object>{
	private static final long serialVersionUID = -5642208226248167160L;
	
	public GHQObjectHashMap streamPut(String s, Object o) {
		super.put(s, o);
		return this;
	}
}
