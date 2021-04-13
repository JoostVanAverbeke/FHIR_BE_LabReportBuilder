package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Practitioner;

import com.github.javafaker.Faker;

import be.mips.fhir.be.lab.report.builders.MetaBuilder;
import be.mips.fhir.be.lab.report.builders.PractitionerBuilder;
import be.mips.fhir.be.lab.report.enums.HumanNameBuilderType;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.PractitionerBuilderType;

public class PractitionerFactory implements Factory<Practitioner> {

	private final Faker faker;
	
	public PractitionerFactory() {
		super();
		faker = new Faker();
	}
	
	public Practitioner create() {
		return create(PractitionerBuilderType.DEFAULT);
	}

	public Practitioner create(PractitionerBuilderType type) {
		return build(type).build();
	}
	
	public PractitionerBuilder build(PractitionerBuilderType type) {
		PractitionerBuilder builder;
		switch(type) {
		case SIMPLE:
			builder = new PractitionerBuilder()
				.withId(IdType.newRandomUuid())
				.withMeta(new MetaBuilder()
						.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-practitioner"))
				.addIdentifier(new IdentifierFactory()
						.build(IdentfierBuilderType.NIHDI)
						.withValue("554488997"));
			break;
		case RANDOM:
			String id = String.valueOf(faker.number().randomNumber());
			builder = new PractitionerBuilder()
				.withId(IdType.newRandomUuid())
				.withMeta(new MetaBuilder()
						.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-practitioner"))
				.addName(new HumanNameFactory().create(HumanNameBuilderType.RANDOM))
				.addIdentifier(new IdentifierFactory()
						.build(IdentfierBuilderType.NIHDI)
						.withValue(id));
			break;
		default:
			builder = new PractitionerBuilder();
			break;
		}
		return builder;
	}
}
