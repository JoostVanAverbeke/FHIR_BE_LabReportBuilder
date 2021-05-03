package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Patient;

public class PatientBuilder implements Builder<Patient> {

	private final Patient patient;
	
	public PatientBuilder() {
		this.patient = new Patient();
	}
	
	public PatientBuilder addName(HumanName humanName) {
		if (humanName != null) {
			patient.addName(humanName);
		}	
		return this;
	}
	
	public PatientBuilder addIdentifier(Identifier identifier) {
		if (identifier != null) {
			patient.addIdentifier(identifier);
		}
		return this;
	}

	public PatientBuilder withId(IIdType idType) {
		if (idType != null) {
			patient.setId(idType);
		}
		return this;
	}

	public PatientBuilder withMeta(MetaBuilder metaBuilder) {
		return withMeta(metaBuilder.build());
	}
	
	public PatientBuilder withMeta(Meta meta) {
		patient.setMeta(meta);
		return this;
	}

	public PatientBuilder withGender(AdministrativeGender gender) {
		patient.setGender(gender);
		return this;
	}
	
	public Patient build() {
		// TODO Auto-generated method stub
		return this.patient;
	}

}
