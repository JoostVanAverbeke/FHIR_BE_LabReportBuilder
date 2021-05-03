package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.CodeableConcept;

import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.enums.CodeableConceptBuilderType;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;


public class CodeableConceptFactory implements Factory<CodeableConcept> {

	public CodeableConcept create() {
		return create(CodeableConceptBuilderType.DEFAULT);
	}
	
	public CodeableConcept create(CodeableConceptBuilderType type) {
		return build(type).build();
	}
	
	public CodeableConceptBuilder build(CodeableConceptBuilderType type) {
		CodeableConceptBuilder builder;
		switch(type) {
		case LABORATORY:
			builder = new CodeableConceptBuilder()
				.addCoding(new CodingFactory()
						.build(CodingBuilderType.DIAGNOSTIC_SERVICE_SECTION_ID)
						.withCode("LAB")
						.withDisplay("Laboratory")
						.build());
			break;
		case MICROBIOLOGY:
			builder = new CodeableConceptBuilder()
					.addCoding(new CodingFactory()
							.build(CodingBuilderType.DIAGNOSTIC_SERVICE_SECTION_ID)
							.withCode("MB")
							.withDisplay("Microbiology")
							.build());
			break;
		case LABORATORY_OBSERVATION_CATEGORY:
			builder = new CodeableConceptBuilder()
				.addCoding(new CodingFactory()
					.build(CodingBuilderType.OBSERVATION_CATEGORY)
					.withCode("laboratory")
					.withDisplay("Laboratory")
					.build());
			break;
		case OBSERVATION_REFERENCE_RANGE_NORMAL:
			builder = new CodeableConceptBuilder()
				.addCoding(new CodingFactory()
					.build(CodingBuilderType.OBSERVATION_REFERENCE_RANGE_MEANING_CODES)
					.withCode("normal")
					.build());
			break;
		default:
			builder = new CodeableConceptBuilder();
			break;
		}
		return builder;
	}
}
