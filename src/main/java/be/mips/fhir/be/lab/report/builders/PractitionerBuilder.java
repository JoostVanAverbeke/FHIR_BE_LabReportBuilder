package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Practitioner;

public class PractitionerBuilder implements Builder<Practitioner> {

	private final Practitioner practitioner;
	
	public PractitionerBuilder() {
		practitioner = new Practitioner();
	}
	
	public PractitionerBuilder withId(String id) {
		practitioner.setId(id);
		return this;
	}
	
	public PractitionerBuilder withMeta(Meta meta) {
		practitioner.setMeta(meta);
		return this;
	}

	public PractitionerBuilder withMeta(MetaBuilder metaBuilder) {
		return withMeta(metaBuilder.build());
	}
	
	public PractitionerBuilder addIdentifier(IdentifierBuilder identifierBuilder) {
		return addIdentifier(identifierBuilder.build());
	}
	
	public PractitionerBuilder addIdentifier(Identifier identifier) {
		practitioner.addIdentifier(identifier);
		return this;
	}

	public Practitioner build() {
		return practitioner;
	}

}
