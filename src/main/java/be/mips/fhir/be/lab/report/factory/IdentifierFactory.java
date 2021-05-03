package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

import com.github.javafaker.Faker;

import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;

public class IdentifierFactory implements Factory<Identifier> {

	private final Faker faker;
	
	public IdentifierFactory() {
		super();
		faker = new Faker();
	}
	
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
				.withIdentifierUse(IdentifierUse.OFFICIAL)
				.withValue(String.valueOf(faker.number().randomNumber(8, true)))
				.withSystem("https://www.ehealth.fgov.be/standards/fhir/NamingSystem/ssin");
			break;
		case NIHDI:
			identifierBuilder = new IdentifierBuilder()
				.withIdentifierUse(IdentifierUse.OFFICIAL)
				.withSystem("https://www.ehealth.fgov.be/standards/fhir/NamingSystem/nihdi");
			break;
		case RANDOM:
			identifierBuilder = new IdentifierBuilder()
					.withSystem("https://www.mips.random.be")
					.withValue(String.valueOf(faker.number().randomNumber()));
			break;
		case EHEALTH_FGOV_BE_SSIN:
			identifierBuilder = new IdentifierBuilder()
				.withSystem("https://www.ehealth.fgov.be/standards/fhir/NamingSystem/ssin")
				.withValue(String.valueOf(faker.number().randomNumber()));
			break;
		default:
			identifierBuilder = new IdentifierBuilder();
			break;
		}
		return identifierBuilder;
		
	}

}
