package calculate.verifier;

import java.util.HashSet;

public class WhiteList<T> extends Verifier<T> {
	private final HashSet<T> whiteList = new HashSet<T>();
	public WhiteList() {}
	@SafeVarargs
	public WhiteList(T...ts) {
		for(T t : ts)
			whiteList.add(t);
	}
	@Override
	public boolean verify(T t) {
		for(T var : whiteList) {
			if(var == t) {
				return true;
			}
		}
		return false;
	}
	public HashSet<T> whiteList() {
		return whiteList;
	}
}
