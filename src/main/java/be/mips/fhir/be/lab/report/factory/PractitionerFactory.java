package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Practitioner;

import be.mips.fhir.be.lab.report.builders.MetaBuilder;
import be.mips.fhir.be.lab.report.builders.PractitionerBuilder;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.PractitionerBuilderType;

public class PractitionerFactory implements Factory<Practitioner> {

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
				.withId("practitioner11")
				.withMeta(new MetaBuilder()
						.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-practitioner"))
				.addIdentifier(new IdentifierFactory()
						.build(IdentfierBuilderType.NIHDI)
						.withValue("554488997"));
			break;
		default:
			builder = new PractitionerBuilder();
			break;
		}
		return builder;
	}
}
