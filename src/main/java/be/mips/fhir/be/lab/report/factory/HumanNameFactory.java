package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.HumanName.NameUse;

import com.github.javafaker.Faker;

import be.mips.fhir.be.lab.report.builders.HumanNameBuilder;
import be.mips.fhir.be.lab.report.enums.HumanNameBuilderType;

public class HumanNameFactory implements Factory<HumanName> {

	private final Faker faker;
	
	public HumanNameFactory() {
		super();
		faker = new Faker();
	}
	
	public HumanName create(HumanNameBuilderType type) {
		HumanName humanName;
		
		switch(type) {
		case SIMPLE:
			humanName = new HumanNameBuilder()
				.withUse(NameUse.OFFICIAL)
				.withFamily("Simphson")
				.addGiven("Bart")
				.build();
			break;
		case RANDOM:
			humanName = new HumanNameBuilder()
				.withUse(NameUse.OFFICIAL)
				.withFamily(faker.name().lastName())
				.addGiven(faker.name().firstName())
				.build();
			break;
		default:
			humanName = new HumanNameBuilder().build();
			break;
		}
		return humanName;
	}
	
	public HumanName create() {
		return create(HumanNameBuilderType.DEFAULT);
	}

}
