package be.mips.fhir.be.lab.report.builders;

import java.util.Date;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenCollectionComponent;
import org.hl7.fhir.r4.model.Specimen.SpecimenStatus;

public class SpecimenBuilder implements Builder<Specimen> {

	private final Specimen specimen;
	
	public SpecimenBuilder() {
		super();
		specimen = new Specimen();
	}

	public SpecimenBuilder withId(IIdType idType) {
		specimen.setId(idType);
		return this;
	}
	
	public SpecimenBuilder withMeta(Meta meta) {
		specimen.setMeta(meta);
		return this;
	}
	
	public SpecimenBuilder withMeta(MetaBuilder metaBuilder) {
		return withMeta(metaBuilder.build());
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
	

	public SpecimenBuilder withReceivedTime(Date receivedDate) {
		specimen.setReceivedTime(receivedDate);
		return this;
	}
	
	public SpecimenBuilder withCollection(SpecimenCollectionComponent collection) {
		specimen.setCollection(collection);
		return this;
	}
	
	public SpecimenBuilder withCollection(SpecimenCollectionComponentBuilder collectionBuilder) {
		return withCollection(collectionBuilder.build());
	}
	
	public Specimen build() {
		return specimen;
	}

}
