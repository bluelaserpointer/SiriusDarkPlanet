package core;

import java.util.Collection;

public interface HasComponents {
	public abstract Collection<Component> components();
	public default <T extends Component>T getComponent(Class<T> tClass) {
		for(Component component : components()) {
			if(tClass.isInstance(component))
				return tClass.cast(component);
		}
		return null;
	}
	public default <T extends Component>boolean hasComponent(Class<T> tClass) {
		return getComponent(tClass) != null;
	}
}
