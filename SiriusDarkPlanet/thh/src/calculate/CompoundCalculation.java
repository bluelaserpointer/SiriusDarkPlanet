package calculate;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompoundCalculation extends Calculation {
	private final LinkedList<Calculation> calculations = new LinkedList<Calculation>();
	
	public CompoundCalculation() {}
	public CompoundCalculation(Calculation...calculations) {
		for(Calculation ver : calculations)
			this.calculations.add(ver);
	}
	public CompoundCalculation(String...calculationBlocksNames) {
		for(String ver : calculationBlocksNames) {
			calculations.add(new CompoundCalculation(ver));
		}
	}
	public CompoundCalculation(String name) {
		super(name);
	}
	public CompoundCalculation(String name, Calculation...calculations) {
		super(name);
		for(Calculation ver : calculations)
			this.calculations.add(ver);
	}
	public CompoundCalculation(String name, String...calculationBlocksNames) {
		super(name);
		for(String ver : calculationBlocksNames) {
			calculations.add(new CompoundCalculation(ver));
		}
	}
	@Override
	public Calculation find(List<String> names) {
		for(Calculation ver : calculations) {
			if(ver.name() == names.get(0)) {
				names.remove(0);
				if(names.isEmpty()) {
					return ver;
				}else {
					return ver.find(names);
				}
			}
		}
		return null;
	}
	@Override
	public boolean add(Calculation calculation) {
		calculations.add(calculation);
		return true;
	}
	public boolean add(Calculation...calculations) {
		for(Calculation ver : calculations)
			add(ver);
		return true;
	}
	@Override
	public boolean remove(Calculation calculation) {
		return calculations.remove(calculation);
	}
	@Override
	protected Number calculate(Number value) {
		final Iterator<Calculation> ite = calculations.iterator();
		while(ite.hasNext()) {
			value = ite.next().calculate(value);
		}
		return value;
	}
}
