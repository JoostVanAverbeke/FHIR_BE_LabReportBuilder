package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.UriType;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;
import org.hl7.fhir.r4.model.Reference;

public class IdentifierBuilder implements Builder<Identifier> {
	
	private final Identifier identifier;
	
	public IdentifierBuilder() {
		identifier = new Identifier();
		identifier.setUse(IdentifierUse.OFFICIAL);
	}
	
	public IdentifierBuilder withIdentifierUse(IdentifierUse identifierUse) {
		identifier.setUse(identifierUse);
		return this;
	}
	
	public IdentifierBuilder withValue(String value) {
		identifier.setValue(value);
		return this;
	}
	
	public IdentifierBuilder withSystem(UriType uri) {
		identifier.setSystemElement(uri);
		return this;
	}
 	
	public IdentifierBuilder withSystem(String string) {
		UriType uri = new UriType();
		uri.setValue(string);
		return withSystem(uri);
	}
	
	public IdentifierBuilder withAssigner(Reference reference) {
		identifier.setAssigner(reference);
		return this;
	}
	
	public Identifier build() {
		// TODO Auto-generated method stub
		return this.identifier;
	}



}
