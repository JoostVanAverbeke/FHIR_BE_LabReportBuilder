package be.mips.fhir.be.lab.report.examples;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Quantity.QuantityComparator;
import org.hl7.fhir.r4.model.Specimen;

import be.mips.fhir.be.lab.report.builders.BundleBuilder;
import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.DiagnosticReportBuilder;
import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.builders.ObservationBuilder;
import be.mips.fhir.be.lab.report.builders.QuantityBuilder;
import be.mips.fhir.be.lab.report.enums.CodeableConceptBuilderType;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.DiagnosticReportBuilderType;
import be.mips.fhir.be.lab.report.enums.ObservationBuilderType;
import be.mips.fhir.be.lab.report.enums.PatientBuilderType;
import be.mips.fhir.be.lab.report.enums.PractitionerBuilderType;
import be.mips.fhir.be.lab.report.enums.SpecimenBuilderType;
import be.mips.fhir.be.lab.report.factory.CodeableConceptFactory;
import be.mips.fhir.be.lab.report.factory.CodingFactory;
import be.mips.fhir.be.lab.report.factory.DiagnosticReportFactory;
import be.mips.fhir.be.lab.report.factory.ObservationFactory;
import be.mips.fhir.be.lab.report.factory.PatientFactory;
import be.mips.fhir.be.lab.report.factory.PractitionerFactory;
import be.mips.fhir.be.lab.report.factory.SpecimenFactory;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;

public class FhirBeLabReportExampleBuilder {

	public static void main(String[] args) {
	      // Create a context
	      FhirContext ctx = FhirContext.forR4();

	      // Create patient resource
	      Patient patient = new PatientFactory().create(PatientBuilderType.SIMPLE);
	      
	      // Create a practitioner resource
	      Practitioner practitioner = new PractitionerFactory().create(PractitionerBuilderType.SIMPLE);
	      
	      // Create performer of observations
	      Practitioner performer = new PractitionerFactory().create(PractitionerBuilderType.RANDOM);
	      
	      // Create blood specimen
	      Specimen specimen = new SpecimenFactory().create(SpecimenBuilderType.BLOOD);
	      
	      // Create biochemistry diagnostic report
	      DiagnosticReportBuilder bioChemistryReportBuilder = new DiagnosticReportFactory().build(
	    		  DiagnosticReportBuilderType.BIOCHEMISTRY);
	      		  
	      // Add practitioner as resultsInterpreter to the bioChemistryReportBuilder
	      bioChemistryReportBuilder.addResultsInterpreter(practitioner);
	      
	      List<ObservationBuilder> observationBuilders = createBiochemistryObservationBuilders();
	      
	      // Add performer to the list of observations
	      setPerformer(performer, observationBuilders);
	      
	      // Add specimen to the list of observations
	      setSpecimen(specimen, observationBuilders);
	      
	      // Add patient and observations to the bioChemistry report
	      bioChemistryReportBuilder.addResults(patient, observationBuilders);
	      
	      // Add specimen to bioChemistry diagnostic report
	      bioChemistryReportBuilder.addSpecimen(specimen);
	      
	      // Create a Bundle
	      BundleBuilder bundleBuilder = new BundleBuilder()
	    		  .withId(IdType.newRandomUuid())
	    		  .withIdentifier(new IdentifierBuilder()
	    				  .withSystem("urn:ietf:rfc:3986")
	    		  		  .withValue("")
	    		  		  .build())
	    		  .withType(BundleType.COLLECTION);
	      
	      // Add the bioChemistryReport as an entry
	      bundleBuilder.addEntry(bioChemistryReportBuilder.build());
	      
	      // Add the patient as an entry.
	      bundleBuilder.addEntry(patient);

	      // Add the practitioner as an entry.
	      bundleBuilder.addEntry(practitioner);

	      // Add the performer as an entry.
	      bundleBuilder.addEntry(performer);

	      // Add the specimen as an entry.
	      bundleBuilder.addEntry(specimen);

	      // Add the observations as bundle entries.
	      bundleBuilder.addEntries(observationBuilders);
	      
	      // Use the narrative generator
	      ctx.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());

	      // Print the output
	      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundleBuilder.build());
	      System.out.println(string);
	}
	
	private static void setPerformer(Practitioner performer, List<ObservationBuilder> observationBuilders) {
		for (ObservationBuilder observationBuilder : observationBuilders) {
			observationBuilder.addPerformer(performer);
		}		
	}

	private static void setSpecimen(Specimen specimen, List<ObservationBuilder> observationBuilders) {
		for (ObservationBuilder observationBuilder : observationBuilders) {
			observationBuilder.setSpecimen(specimen);
		}		
	}

	private static List<ObservationBuilder> createBiochemistryObservationBuilders() {
		List<ObservationBuilder> observationBuilders = new ArrayList<ObservationBuilder>();
		Date issuedDate = new Date();
		Calendar effectiveDateTime = new Calendar.Builder()
		        .setDate(2021, 3, 20)
		        .build();
	    ObservationFactory observationFactory = new ObservationFactory();
		
	    // Create Observation for discontinued SUITZ (Uitzicht serum/plasma)
	    ObservationBuilder suitzObservationBuilder = observationFactory
	    	.build(ObservationBuilderType.FHIR_BE)
	    	.withId(IdType.newRandomUuid())
	    	.withStatus(ObservationStatus.CANCELLED)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
            .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("9991-9")
                    .withDisplay("Uitzicht serum/plasma")))
            .withEffectiveDateTime(effectiveDateTime)
            .withIssued(issuedDate);

	    // Create Hematology observation resources
        
		// Create Hemoglobine Observation
		ObservationBuilder hgbObservationBuilder = observationFactory
			.build(ObservationBuilderType.FHIR_BE)
		    .withId(IdType.newRandomUuid())
            .withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
            .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("718-7")
                    .withDisplay("Hemoglobin [Mass/volume] in Blood")))
            .withEffectiveDateTime(effectiveDateTime)
            .withIssued(issuedDate)
            .withValueQuantity(new QuantityBuilder()
                .withValue(BigDecimal.valueOf(11.6))
		        .withUnit("g/dL")
                .withCode("g/dL")
                .withSystem("http://unitsofmeasure.org")
				.build())
	        .addInterpretation(new CodeableConceptBuilder()
	        		.addCoding(new CodingFactory()
	        				.build(CodingBuilderType.V3_OBSERVATION_INTERPRETATION)
	        				.withCode("L")
	        				.withDisplay("Low"))
	        		.withText("Low")
	        		.build());
            
  
        // Create Hematocriet Observation
        ObservationBuilder hctObservationBuilder = observationFactory
    		.build(ObservationBuilderType.FHIR_BE)
		    .withId(IdType.newRandomUuid())
	        .withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	        .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("4544-3")
	                .withDisplay("Hematocrit [Volume Fraction] of Blood by Automated count")))
            .withEffectiveDateTime(effectiveDateTime)
            .withIssued(issuedDate)
	        .withValueQuantity(new QuantityBuilder()
	            .withValue(BigDecimal.valueOf(35.6))
		        .withUnit("%")
	            .withCode("%")
	            .withSystem("http://unitsofmeasure.org")
				.build())
	        .addInterpretation(new CodeableConceptBuilder()
	        		.addCoding(new CodingFactory()
	        				.build(CodingBuilderType.V3_OBSERVATION_INTERPRETATION)
	        				.withCode("L")
	        				.withDisplay("Low"))
	        		.withText("Low")
	        		.build());

        // Create WBC Observation
        ObservationBuilder wbcObservationBuilder = observationFactory
    			.build(ObservationBuilderType.FHIR_BE)
    		    .withId(IdType.newRandomUuid())
    	        .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
    	        .withCode(new CodeableConceptBuilder()
    		        .addCoding(new CodingFactory()
    			        .build(CodingBuilderType.LOINC)
    			        .withCode("6690-2")
    	                .withDisplay("Leukocytes [#/volume] in Blood by Automated count")))
                .withEffectiveDateTime(effectiveDateTime)
                .withIssued(issuedDate)
    	        .withValueQuantity(new QuantityBuilder()
    	            .withValue(BigDecimal.valueOf(35.6))
    	            .withComparator(QuantityComparator.LESS_THAN)
    		        .withUnit("10E3/µL")
    	            .withCode("10*3/uL")
    	            .withSystem("http://unitsofmeasure.org")
    				.build());

        // Create Normoblasten Observation
        ObservationBuilder normoBlastenObservationBuilder = observationFactory
    			.build(ObservationBuilderType.FHIR_BE)
    		    .withId(IdType.newRandomUuid())
    	        .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
    	        .withCode(new CodeableConceptBuilder()
    		        .addCoding(new CodingFactory()
    			        .build(CodingBuilderType.LOINC)
    			        .withCode("58413-6")
    	                .withDisplay("Nucleated erythrocytes/100 leukocytes [Ratio] in Blood by Automated count")))
                .withEffectiveDateTime(effectiveDateTime)
                .withIssued(issuedDate)
    	        .withValueQuantity(new QuantityBuilder()
    	            .withValue(BigDecimal.valueOf(1.0))
    	            .withComparator(QuantityComparator.LESS_THAN)
    		        .withUnit("/100 WBC")
    	            .withCode("/100{WBC}")
    	            .withSystem("http://unitsofmeasure.org")
    				.build());

        // Create chemistry observation resources
        // Create sodium Observation
        ObservationBuilder naObservationBuilder = observationFactory
    		.build(ObservationBuilderType.FHIR_BE)
		    .withId(IdType.newRandomUuid())
	        .withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	        .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("2951-2")
	                .withDisplay("Sodium [Moles/volume] in Serum or Plasma")))
	        .withEffectiveDateTime(effectiveDateTime)
	        .withIssued(issuedDate)
	        .withValueQuantity(new QuantityBuilder()
	            .withValue(BigDecimal.valueOf(138))
		        .withUnit("mmol/L")
	            .withCode("mmol/L")
	            .withSystem("http://unitsofmeasure.org")
				.build());

        // Create potasium Observation
        ObservationBuilder kaObservationBuilder = observationFactory
    			.build(ObservationBuilderType.FHIR_BE)
    		    .withId(IdType.newRandomUuid())
    	        .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
    	        .withCode(new CodeableConceptBuilder()
    		        .addCoding(new CodingFactory()
    			        .build(CodingBuilderType.LOINC)
    			        .withCode("6298-4")
    	                .withDisplay("Potassium [Moles/volume] in Blood")))
                .withEffectiveDateTime(effectiveDateTime)
                .withIssued(issuedDate)
    	        .withValueQuantity(new QuantityBuilder()
    	            .withValue(BigDecimal.valueOf(4.1))
     		        .withUnit("mmol/L")
    	            .withCode("mmol/L")
    	            .withSystem("http://unitsofmeasure.org")
    				.build());
  
        // Create chloor Observation
        ObservationBuilder clObservationBuilder = observationFactory
    			.build(ObservationBuilderType.FHIR_BE)
    		    .withId(IdType.newRandomUuid())
    	        .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
    	        .withCode(new CodeableConceptBuilder()
    		        .addCoding(new CodingFactory()
    			        .build(CodingBuilderType.LOINC)
    			        .withCode("2075-0")
    	                .withDisplay("Chloride [Moles/volume] in Serum or Plasma")))
                .withEffectiveDateTime(effectiveDateTime)
                .withIssued(issuedDate)
    	        .withValueQuantity(new QuantityBuilder()
    	            .withValue(BigDecimal.valueOf(106))
    		        .withUnit("mmol/L")
    	            .withCode("mmol/L")
    	            .withSystem("http://unitsofmeasure.org")
    				.build());

        // Create bicarbonate Observation
        ObservationBuilder carbObservationBuilder = observationFactory
    			.build(ObservationBuilderType.FHIR_BE)
    		    .withId(IdType.newRandomUuid())
    	        .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
    	        .withCode(new CodeableConceptBuilder()
    		        .addCoding(new CodingFactory()
    			        .build(CodingBuilderType.LOINC)
    			        .withCode("19230-2")
    	                .withDisplay("Bicarbonate [Moles/volume] standard in Arterial blood")))
                .withEffectiveDateTime(effectiveDateTime)
                .withIssued(issuedDate)
    	        .withValueQuantity(new QuantityBuilder()
    	            .withValue(BigDecimal.valueOf(24))
    		        .withUnit("mmol/L")
    	            .withCode("mmol/L")
    	            .withSystem("http://unitsofmeasure.org")
    				.build());
        		

        // Create BioChemie observations
        // Create Ureum observation
		ObservationBuilder ureumObservationBuilder = observationFactory
				.build(ObservationBuilderType.FHIR_BE)
			    .withId(IdType.newRandomUuid())
	            .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("3091-6")
	                    .withDisplay("Urea [Mass/volume] in Serum or Plasma")))
	            .withEffectiveDateTime(effectiveDateTime)
	            .withIssued(issuedDate)
	            .withValueQuantity(new QuantityBuilder()
	                .withValue(BigDecimal.valueOf(46))
			        .withUnit("mg/dL")
	                .withCode("mg/dL")
	                .withSystem("http://unitsofmeasure.org")
					.build())
		        .addInterpretation(new CodeableConceptBuilder()
		        		.addCoding(new CodingFactory()
		        				.build(CodingBuilderType.V3_OBSERVATION_INTERPRETATION)
		        				.withCode("H")
		        				.withDisplay("High"))
		        		.withText("High")
		        		.build());
		
        // Create Creatinine observation
		ObservationBuilder kreatObservationBuilder = observationFactory
				.build(ObservationBuilderType.FHIR_BE)
			    .withId(IdType.newRandomUuid())
	            .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("2160-0")
	                    .withDisplay("Creatinine [Mass/volume] in Serum or Plasma")))
	            .withEffectiveDateTime(effectiveDateTime)
	            .withIssued(issuedDate)
	            .withValueQuantity(new QuantityBuilder()
	                .withValue(BigDecimal.valueOf(46))
			        .withUnit("mg/dL")
	                .withCode("mg/dL")
	                .withSystem("http://unitsofmeasure.org")
					.build());
		
        // Create GFR observation
		ObservationBuilder gfrObservationBuilder = observationFactory
				.build(ObservationBuilderType.FHIR_BE)
			    .withId(IdType.newRandomUuid())
	            .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("62238-1")
	                    .withDisplay(
	                    		"Glomerular filtration rate/1.73 sq M.predicted [Volume Rate/Area]" + 
	                    		" in Serum, Plasma or Blood by Creatinine-based formula (CKD-EPI)")))
	            .withEffectiveDateTime(effectiveDateTime)
	            .withIssued(issuedDate)
	            .withValueQuantity(new QuantityBuilder()
	                .withValue(BigDecimal.valueOf(46))
			        .withUnit("mL/min")
	                .withCode("mL/min")
	                .withSystem("http://unitsofmeasure.org")
					.build());
        
        // Create Inflammatie observations
        // Create CRP observation
		ObservationBuilder crpObservationBuilder = observationFactory
				.build(ObservationBuilderType.FHIR_BE)
			    .withId(IdType.newRandomUuid())
	            .withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("1988-5")
	                    .withDisplay("C reactive protein [Mass/volume] in Serum or Plasma")))
	            .withEffectiveDateTime(effectiveDateTime)
	            .withIssued(issuedDate)
	            .withValueQuantity(new QuantityBuilder()
	                .withValue(BigDecimal.valueOf(26.9))
			        .withUnit("mg/dL")
	                .withCode("mg/dL")
	                .withSystem("http://unitsofmeasure.org")
					.build())
		        .addInterpretation(new CodeableConceptBuilder()
		        		.addCoding(new CodingFactory()
		        				.build(CodingBuilderType.V3_OBSERVATION_INTERPRETATION)
		        				.withCode("H")
		        				.withDisplay("High"))
		        		.withText("High")
		        		.build());
        
		observationBuilders.add(suitzObservationBuilder);
        observationBuilders.add(hgbObservationBuilder);
		observationBuilders.add(hctObservationBuilder);
		observationBuilders.add(wbcObservationBuilder);
		observationBuilders.add(normoBlastenObservationBuilder);
		
		observationBuilders.add(naObservationBuilder);
		observationBuilders.add(kaObservationBuilder);
		observationBuilders.add(clObservationBuilder);
		observationBuilders.add(carbObservationBuilder);
		
		observationBuilders.add(ureumObservationBuilder);
		observationBuilders.add(kreatObservationBuilder);
		observationBuilders.add(gfrObservationBuilder);
		observationBuilders.add(crpObservationBuilder);

        return observationBuilders;
	}

}
