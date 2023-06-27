package calculate.verifier;

import java.util.HashSet;

public class BlackList<T> extends Verifier<T> {
	private final HashSet<T> blackList = new HashSet<T>();
	public BlackList() {}
	@SafeVarargs
	public BlackList(T...ts) {
		for(T t : ts)
			blackList.add(t);
	}
	@Override
	public boolean verify(T t) {
		for(T var : blackList) {
			if(var == t)
				return false;
		}
		return true;
	}
	public HashSet<T> blackList() {
		return blackList;
	}
}