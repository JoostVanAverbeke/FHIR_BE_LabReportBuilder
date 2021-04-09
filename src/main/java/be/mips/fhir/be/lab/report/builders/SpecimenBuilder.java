package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenStatus;

public class SpecimenBuilder implements Builder<Specimen> {

	private final Specimen specimen;
	
	public SpecimenBuilder() {
		super();
		specimen = new Specimen();
	}

	public SpecimenBuilder withId(String id) {
		specimen.setId(id);
		return this;
	}
	
	public SpecimenBuilder withStatus(SpecimenStatus status) {
		specimen.setStatus(status);
		return this;
	}

	public SpecimenBuilder addIdentifier(Identifier identifier) {
		specimen.addIdentifier(identifier);
		return this;
	}

	public SpecimenBuilder withType(CodeableConcept type) {
		specimen.setType(type);
		return this;
	}
	
	public SpecimenBuilder withType(CodeableConceptBuilder typeBuilder) {
		return withType(typeBuilder.build());
	}
	
	public Specimen build() {
		return specimen;
	}


}
