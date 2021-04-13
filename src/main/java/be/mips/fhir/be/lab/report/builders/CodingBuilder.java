package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.UriType;

public class CodingBuilder implements Builder<Coding> {

	private final Coding coding;
	
	public CodingBuilder() {
		coding = new Coding();
	}
	
	public CodingBuilder withSystem(UriType uri) {
		coding.setSystemElement(uri);
		return this;
	}
	
	public CodingBuilder withSystem(String string) {
		UriType uri = new UriType();
		uri.setValue(string);
		coding.setSystemElement(uri);
		return this;
	}

	public CodingBuilder withCode(String code) {
		coding.setCode(code);
		return this;
	}
	
	public CodingBuilder withDisplay(String displayString) {
		coding.setDisplay(displayString);
		return this;
	}	
	
	public Coding build() {
		return coding;
	}



}
