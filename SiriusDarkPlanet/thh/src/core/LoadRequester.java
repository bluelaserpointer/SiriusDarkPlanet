package core;

public abstract class LoadRequester {
	public LoadRequester() {
		GHQ.addLoadRequester(this);
	}
	protected abstract void loadResource();
}
