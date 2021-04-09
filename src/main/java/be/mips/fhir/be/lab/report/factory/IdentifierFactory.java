package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;

public class IdentifierFactory implements Factory<Identifier> {

	public Identifier create(IdentfierBuilderType type) {
		return build(type).build();
	}
	
	public Identifier create() {
		return create(IdentfierBuilderType.DEFAULT);
	}
	
	public IdentifierBuilder build(IdentfierBuilderType type) {
		IdentifierBuilder identifierBuilder;
		
		switch (type) {
		case SIMPLE:
			identifierBuilder = new IdentifierBuilder()
				.withValue("12345")
				.withSystem("https://www.ehealth.fgov.be/standards/fhir/NamingSystem/ssin");
			break;
		case NIHDI:
			identifierBuilder = new IdentifierBuilder()
				.withIdentifierUse(IdentifierUse.OFFICIAL)
				.withSystem("https://www.ehealth.fgov.be/standards/fhir/NamingSystem/nihdi");
			break;
		case RANDOM:
			identifierBuilder = new IdentifierBuilder();
			break;
		default:
			identifierBuilder = new IdentifierBuilder();
			break;
		}
		return identifierBuilder;
		
	}

}
