package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Coding;

import be.mips.fhir.be.lab.report.builders.CodingBuilder;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;

public class CodingFactory implements Factory<Coding> {

	public Coding create() {
		return create(CodingBuilderType.DEFAULT);
	}

	public Coding create(CodingBuilderType type) {
		return build(type).build();		
	}

	public CodingBuilder build(CodingBuilderType type) {
		CodingBuilder builder;
		switch(type) {
			case DIAGNOSTIC_SERVICE_SECTION_ID:
				builder = new CodingBuilder()
					.withSystem("http://terminology.hl7.org/CodeSystem/v2-0074");
				break;
			case LOINC:
				builder = new CodingBuilder()
					.withSystem("http://loinc.org");
				break;
			case HL7_VERSION_2_TABLE_0487:
				builder = new CodingBuilder()
					.withSystem("http://terminology.hl7.org/CodeSystem/v2-0487");
				break;
			case V3_OBSERVATION_INTERPRETATION:
				builder = new CodingBuilder()
					.withSystem("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation");
				break;
			case OBSERVATION_REFERENCE_RANGE_MEANING_CODES:
				builder = new CodingBuilder()
					.withSystem("http://terminology.hl7.org/CodeSystem/referencerange-meaning");
				break;
			case SNOMED:
				builder = new CodingBuilder()
					.withSystem("http://snomed.info/sct");
				break;
			case OBSERVATION_CATEGORY:				
				builder = new CodingBuilder()
					.withSystem("http://terminology.hl7.org/CodeSystem/observation-category");
				break;
			default:
				builder = new CodingBuilder();
				break;
		}
		return builder;
	}

}
