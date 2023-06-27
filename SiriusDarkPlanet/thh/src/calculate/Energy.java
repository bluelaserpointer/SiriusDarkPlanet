package calculate;

public class Energy implements Consumables {
	Number number;
	public Energy(Number number) {
		this.number = number;
	}
	@Override
	public Number getNumber() {
		return number;
	}
	@Override
	public double consume(Number amount) {
		final double result = number.doubleValue() - amount.doubleValue();
		if(result >= 0) {
			number = result;
			return amount.doubleValue();
		}
		final double prevNumber = number.doubleValue();
		number = 0;
		return prevNumber;
	}
	@Override
	public void setNumber(Number amount) {
		number = amount;
	}
}
