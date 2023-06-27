package calculate;

public interface Consumables extends HasNumber {
	public double consume(Number amount);
	public default double consumeRate(double rate) {
		return consume(doubleValue()*rate);
	}
	public void setNumber(Number amount);
	public default Number max() {
		return Integer.MAX_VALUE;
	}
	public default Number min() {
		return 0;
	}
}
