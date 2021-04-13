package be.mips.fhir.be.lab.report.builders;

import java.util.Calendar;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Specimen.SpecimenCollectionComponent;

public class SpecimenCollectionComponentBuilder implements Builder<SpecimenCollectionComponent> {

	private final SpecimenCollectionComponent specimenCollection;
	
	public SpecimenCollectionComponentBuilder() {
		super();
		specimenCollection = new SpecimenCollectionComponent();
	}
	
	public SpecimenCollectionComponentBuilder withCollectionDateTime(Calendar collectionCalendar) {
		specimenCollection.setCollected(new DateTimeType(collectionCalendar));
		return this;
	}

	public SpecimenCollectionComponentBuilder withMethod(CodeableConcept codeableConcept) {
		specimenCollection.setMethod(codeableConcept);
		return this;
	}
	
	public SpecimenCollectionComponentBuilder withMethod(CodeableConceptBuilder codeableConceptBuilder) {
		return withMethod(codeableConceptBuilder.build());
	}
	
	public SpecimenCollectionComponent build() {
		return specimenCollection;
	}



}
