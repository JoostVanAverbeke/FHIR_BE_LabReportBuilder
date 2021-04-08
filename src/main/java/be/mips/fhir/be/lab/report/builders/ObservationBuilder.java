package be.mips.fhir.be.lab.report.builders;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Type;

public class ObservationBuilder implements Builder<Observation> {

	private final Observation observation;
	
	public ObservationBuilder() {
		observation = new Observation();
		withMeta(new MetaBuilder()
				.addProfile(
					"https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-observation-laboratory"));
	}
	
	public ObservationBuilder withId(String id) {
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
	
	public Observation build() {
		return observation;
	}
}
