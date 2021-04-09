package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.HumanName.NameUse;

public class HumanNameBuilder implements Builder<HumanName> {

	private final HumanName humanName;
	
	public HumanNameBuilder() {
		humanName = new HumanName();
	}
	
	public HumanNameBuilder withUse(NameUse nameUse) {
		humanName.setUse(nameUse);
		return this;
	}
	
	public HumanNameBuilder withFamily(String familyName) {
		humanName.setFamily(familyName);
		return this;
	}
	
	public HumanNameBuilder addGiven(String givenName) {
		humanName.addGiven(givenName);
		return this;
	}
	
	public HumanName build() {
		return humanName;
	}

}
