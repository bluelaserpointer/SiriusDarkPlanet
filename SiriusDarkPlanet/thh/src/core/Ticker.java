package core;

import java.util.LinkedList;
import java.util.function.BooleanSupplier;

public abstract class Ticker {
	
	protected int age;
	
	public abstract boolean condition();

	public LinkedList<TickerTask> tasks = new LinkedList<>();
	
	public LinkedList<Ticker> childrenTicker = new LinkedList<>();

	
	//init
	public static Ticker generate(BooleanSupplier booleanSupplier) {
		return new Ticker() {
			@Override
			public boolean condition() {
				return booleanSupplier.getAsBoolean();
			}
		};
	}
	public Ticker addTask(TickerTask task) {
		tasks.add(task);
		return this;
	}
	public Ticker addChildrenTicker(Ticker ticker) {
		childrenTicker.add(ticker);
		return this;
	}
	
	//role
	public void idle() {
		if(condition()) {
			++age;
			//do own tasks
			for(TickerTask task : tasks)
				task.task();
			//do children ticker idles
			for(Ticker ticker : childrenTicker)
				ticker.idle();
		}
	}
	
	//control
	public Ticker setAge(int age) {
		this.age = age;
		return this;
	}
	public Ticker initAge() {
		return setAge(0);
	}
	
	//info
	public int age() {
		return age;
	}
}
