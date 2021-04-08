package be.mips.fhir.be.lab.report.builders;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Quantity.QuantityComparator;


public class QuantityBuilder implements Builder<Quantity> {

	private final Quantity quantity;
	
	public QuantityBuilder() {
		quantity = new Quantity();
	}
	
	public QuantityBuilder withValue(BigDecimal value) {
		quantity.setValue(value);
		return this;
	}
	
	public QuantityBuilder withComparator(QuantityComparator comparator) {
		quantity.setComparator(comparator);
		return this;
	}
	
	public QuantityBuilder withCode(String codeString) {
		quantity.setCode(codeString);
		return this;
	}

	public QuantityBuilder withUnit(String unit) {
		quantity.setUnit(unit);
		return this;
	}
	
	public QuantityBuilder withSystem(String uriString) {
		quantity.setSystem(uriString);
		return this;
	}
	
	public Quantity build() {
		return quantity;
	}

}
