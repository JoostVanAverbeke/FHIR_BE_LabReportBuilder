package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Patient;

import be.mips.fhir.be.lab.report.builders.PatientBuilder;
import be.mips.fhir.be.lab.report.enums.HumanNameBuilderType;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.PatientBuilderType;

public class PatientFactory implements Factory<Patient> {

	public Patient create(PatientBuilderType type) {
		Patient patient = null;
		switch (type) {
			case SIMPLE:
				patient = new PatientBuilder()
					.withId("patient1")
					.addName(new HumanNameFactory().create(HumanNameBuilderType.SIMPLE))
					.addIdentifier(new IdentifierFactory().create(IdentfierBuilderType.SIMPLE))
					.build();
				break;
			case RANDOM:
				patient = new PatientBuilder()
					.addName(new HumanNameFactory().create(HumanNameBuilderType.RANDOM))
					.addIdentifier(new IdentifierFactory().create(IdentfierBuilderType.RANDOM))
					.build();
				break;
			default:
				patient = new PatientBuilder()
					.addName(new HumanNameFactory().create(HumanNameBuilderType.DEFAULT))
					.addIdentifier(new IdentifierFactory().create(IdentfierBuilderType.DEFAULT))
					.build();
				break;
		}
		return patient;
	}

	public Patient create() {
		return create(PatientBuilderType.DEFAULT);
	}

}
