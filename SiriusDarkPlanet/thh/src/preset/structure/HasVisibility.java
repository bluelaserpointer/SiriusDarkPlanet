package preset.structure;

public interface HasVisibility {
	public default double visibility() {
		return 0.0;
	}
	
}
