package be.mips.fhir.be.lab.report.builders;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Annotation;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Quantity.QuantityComparator;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Type;

import be.mips.fhir.be.lab.report.enums.CodeableConceptBuilderType;
import be.mips.fhir.be.lab.report.factory.CodeableConceptFactory;

public class ObservationBuilder implements Builder<Observation> {

	private final Observation observation;
	
	public ObservationBuilder() {
		observation = new Observation();
		withMeta(new MetaBuilder()
				.addProfile(
					"https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-observation-laboratory"));
	}
	
	public ObservationBuilder withId(IIdType id) {
		observation.setId(id);
		return this;
	}
	
	public ObservationBuilder withMeta(MetaBuilder metaBuilder) {
		observation.setMeta(metaBuilder.build());
		return this;
	}
	
	public ObservationBuilder withMeta(Meta meta) {
		observation.setMeta(meta);
		return this;
	}
	
	public ObservationBuilder withStatus(ObservationStatus status) {
		observation.setStatus(status);
		return this;
	}
	
	public ObservationBuilder withCode(CodeableConcept code) {
		observation.setCode(code);
		return this;
	}

	public ObservationBuilder withCode(CodeableConceptBuilder codeBuilder) {
		return withCode(codeBuilder.build());
	}
	
	public ObservationBuilder withEffectiveDateTime(DateTimeType dateTimetype) {
		observation.setEffective(dateTimetype);
		return this;
	}
	
	public ObservationBuilder withEffectiveDateTime(Calendar calendar) {
		return withEffectiveDateTime(new DateTimeType(calendar));
	}
	
	public ObservationBuilder withIssued(Date date) {
		observation.setIssued(date);
		return this;
	}
	
	public ObservationBuilder withValueQuantity(Type value) {
		observation.setValue(value);
		return this;
	}
	
	public ObservationBuilder withSubject(IBaseResource resource) {
		Reference reference = new Reference();
		reference.setResource(resource);
		observation.setSubject(reference);
		return this;
	}
	

	public ObservationBuilder addPerformer(Practitioner performer) {
		Reference reference = new Reference();
		reference.setResource(performer);
		observation.addPerformer(reference);
		return this;
	}
	

	public ObservationBuilder withSpecimen(Specimen specimen) {
		Reference reference = new Reference();
		reference.setResource(specimen);
		observation.setSpecimen(reference);
		return this;		
	}
	
	public ObservationBuilder addInterpretation(Coding code) {
		CodeableConcept codeableConcept = new CodeableConceptBuilder()
				.addCoding(code)
				.build();
		return addInterpretation(codeableConcept);
	}
	
	public ObservationBuilder addInterpretation(CodingBuilder codeBuilder) {
		CodeableConcept codeableConcept = new CodeableConceptBuilder()
				.addCoding(codeBuilder)
				.build();
		return addInterpretation(codeableConcept);
	}

	public ObservationBuilder addInterpretation(CodeableConcept interpretation) {
		observation.addInterpretation(interpretation);
		return this;
	}
	
	public ObservationBuilder addCategory(CodeableConcept codeableConcept) {
		observation.addCategory(codeableConcept);
		return this;
	}
	
	public ObservationBuilder addCategory(CodeableConceptBuilder builder) {
		return addCategory(builder.build());
	}
	
	public ObservationBuilder addNote(Annotation note) {
		observation.addNote(note);
		return this;
	}
	

	public ObservationBuilder addHasMember(ObservationBuilder observationBuilder) {
		return addHasMember(observationBuilder.build());
	}

	public ObservationBuilder addHasMember(IBaseResource resource) {
		Reference reference = new Reference();
		reference.setResource(resource);
		observation.addHasMember(reference);
		return this;
	}
	
	public ObservationBuilder addBasedOn(IBaseResource resource) {
		Reference reference = new Reference();
		reference.setResource(resource);
		observation.addBasedOn(reference);
		return this;
	}
	
	public ObservationBuilder addBasedOn(ServiceRequestBuilder serviceRequestBuilder) {
		return addBasedOn(serviceRequestBuilder.build());		
	}
	
	public ObservationBuilder addReferenceRange(double low, double high) {
		observation.addReferenceRange()
			.setLow(new Quantity(low))
			.setHigh(new Quantity(high))
			.setType(new CodeableConceptFactory()
					.build(CodeableConceptBuilderType.OBSERVATION_REFERENCE_RANGE_NORMAL)
					.build());
		return this;
	}
	
	public ObservationBuilder addReferenceRange(QuantityComparator comparator, double high) {
		observation.addReferenceRange()
			.setHigh(new Quantity()
					.setComparator(comparator).setValue(high))
			.setType(new CodeableConceptFactory()
					.build(CodeableConceptBuilderType.OBSERVATION_REFERENCE_RANGE_NORMAL)
					.build());
		return this;
	}	
	
	public Observation build() {
		return observation;
	}


}
