package status;

import java.io.Serializable;
import java.util.ArrayList;

import calculate.ConsumableEnergy;

public class Status implements Serializable {
	private static final long serialVersionUID = -239346790787280009L;

	protected final ArrayList<ConsumableEnergy> parameters = new ArrayList<ConsumableEnergy>();

	public Status() {}
	public Status(ConsumableEnergy...parameters) {
		for(ConsumableEnergy ver : parameters)
			this.parameters.add(ver);
	}
	public Status(Status sample) {
		parameters.addAll(sample.parameters);
	}
	//control
	public void reset() {
		for(ConsumableEnergy ver : parameters)
			ver.reset();
	}
	public Status clone() {
		return new Status(this);
	}
	public boolean isLegalIndex(int index) {
		return 0 <= index && index < parameters.size();
	}
	public final int getAmount() {
		return parameters.size();
	}
}
