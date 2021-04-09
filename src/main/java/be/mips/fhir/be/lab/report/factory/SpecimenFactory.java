package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenStatus;

import com.github.javafaker.Faker;

import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.SpecimenBuilder;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.SpecimenBuilderType;

public class SpecimenFactory implements Factory<Specimen> {

	private final Faker faker;
	
	public SpecimenFactory() {
		super();
		faker = new Faker();
	}
	public Specimen create() {
		return create(SpecimenBuilderType.DEFAULT);
	}

	public Specimen create(SpecimenBuilderType type) {
		return build(type).build();
	}

	public SpecimenBuilder build(SpecimenBuilderType type) {
		SpecimenBuilder specimenBuilder;
		switch(type) {
		case SIMPLE:
			specimenBuilder = new SpecimenBuilder();
			break;
		case RANDOM:
			specimenBuilder = new SpecimenBuilder();
			break;
		case BLOOD:
			specimenBuilder = new SpecimenBuilder()
				.withId(String.valueOf(faker.number().randomNumber()))
				.addIdentifier(new IdentifierFactory()
						.build(IdentfierBuilderType.RANDOM)
						.build())
				.withStatus(SpecimenStatus.AVAILABLE)
				.withType(new CodeableConceptBuilder()
						.addCoding(new CodingFactory()
								.build(CodingBuilderType.HL7_VERSION_2_TABLE_0487)
								.withCode("BLD")));
			break;
		default:
			specimenBuilder = new SpecimenBuilder();
			break;
		}
		return specimenBuilder;
	}

}
