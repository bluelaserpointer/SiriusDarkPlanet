package paint;

import java.util.ArrayList;

public class Layer implements HasPaint {
	private final ArrayList<HasPaint> paintLayers = new ArrayList<HasPaint>();
	public Layer() {}
	public Layer(HasPaint...hasPaints) {
		setLayers(hasPaints);
	}
	@Override
	public void paint() {
		for(HasPaint ver : paintLayers)
			ver.paint();
	}
	//control
	public Layer setLayers(HasPaint...hasPaints) {
		paintLayers.clear();
		for(HasPaint ver : hasPaints)
			paintLayers.add(ver);
		return this;
	}
	public Layer appendLayer(HasPaint hasPaint) {
		paintLayers.add(hasPaint);
		return this;
	}
	public Layer patialSort(HasPaint...hasPaints) {
		//TODO: complete patialSort
		return this;
	}
	public ArrayList<HasPaint> layerList() {
		return paintLayers;
	}
}
