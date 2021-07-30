package be.mips.fhir.be.lab.report.factory;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenStatus;

import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.MetaBuilder;
import be.mips.fhir.be.lab.report.builders.SpecimenBuilder;
import be.mips.fhir.be.lab.report.builders.SpecimenCollectionComponentBuilder;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.SpecimenBuilderType;

public class SpecimenFactory implements Factory<Specimen> {

	public SpecimenFactory() {
		super();
	}
	
	public Specimen create() {
		return create(SpecimenBuilderType.DEFAULT);
	}

	public Specimen create(SpecimenBuilderType type) {
		return build(type).build();
	}

	public SpecimenBuilder build(SpecimenBuilderType type) {
		Date receivedDate = new Date();
		Calendar collectionCalendar = new Calendar.Builder().setDate(2021, Calendar.APRIL, 20).build();
		
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
				.withId(IdType.newRandomUuid())
				.withMeta(new MetaBuilder()
						.withVersionId("1")
						.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-specimen-laboratory"))
				.addIdentifier(new IdentifierFactory()
						.build(IdentfierBuilderType.RANDOM)
						.build())
				.withStatus(SpecimenStatus.AVAILABLE)
				.withType(new CodeableConceptBuilder()
						.addCoding(new CodingFactory()
								.build(CodingBuilderType.HL7_VERSION_2_TABLE_0487)
								.withCode("BLD")
								.withDisplay("Whole blood")))
				.withReceivedTime(receivedDate)
				.withCollection(new SpecimenCollectionComponentBuilder()
						.withCollectionDateTime(collectionCalendar)
						.withMethod(new CodeableConceptBuilder()
								.addCoding(new CodingFactory()
										.build(CodingBuilderType.SNOMED)
										.withCode("28520004")
										.withDisplay("Venipuncture for blood test (procedure)"))));
			break;
		case BAL:
			specimenBuilder = new SpecimenBuilder()
			.withId(IdType.newRandomUuid())
			.withMeta(new MetaBuilder()
					.withVersionId("1")
					.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-specimen-laboratory"))
			.addIdentifier(new IdentifierFactory()
					.build(IdentfierBuilderType.RANDOM)
					.build())
			.withStatus(SpecimenStatus.AVAILABLE)
			.withType(new CodeableConceptBuilder()
					.addCoding(new CodingFactory()
							.build(CodingBuilderType.SNOMED)
							.withCode("122609004")
							.withDisplay("Broncho-alveolaire lavage (BAL)")))
			.withReceivedTime(receivedDate);
//			TODO(JVA) Lookup collection method for BAL-vocht			
//			.withCollection(new SpecimenCollectionComponentBuilder()
//					.withCollectionDateTime(collectionCalendar)
//					.withMethod(new CodeableConceptBuilder()
//							.addCoding(new CodingFactory()
//									.build(CodingBuilderType.SNOMED)
//									.withCode("28520004")
//									.withDisplay("Venipuncture for blood test (procedure)"))));
			break;
		case STOOL:
			specimenBuilder = new SpecimenBuilder()
			.withId(IdType.newRandomUuid())
			.withMeta(new MetaBuilder()
					.withVersionId("1")
					.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-specimen-laboratory"))
			.addIdentifier(new IdentifierFactory()
					.build(IdentfierBuilderType.RANDOM)
					.build())
			.withStatus(SpecimenStatus.AVAILABLE)
			.withType(new CodeableConceptBuilder()
					.addCoding(new CodingFactory()
							.build(CodingBuilderType.SNOMED)
							.withCode("119339001")
							.withDisplay("Stool specimen")))
			.withReceivedTime(receivedDate);
//			TODO(JVA) Lookup collection method for BAL-vocht			
//			.withCollection(new SpecimenCollectionComponentBuilder()
//					.withCollectionDateTime(collectionCalendar)
//					.withMethod(new CodeableConceptBuilder()
//							.addCoding(new CodingFactory()
//									.build(CodingBuilderType.SNOMED)
//									.withCode("28520004")
//									.withDisplay("Venipuncture for blood test (procedure)"))));

		default:
			specimenBuilder = new SpecimenBuilder();
			break;
		}
		return specimenBuilder;
	}

}
