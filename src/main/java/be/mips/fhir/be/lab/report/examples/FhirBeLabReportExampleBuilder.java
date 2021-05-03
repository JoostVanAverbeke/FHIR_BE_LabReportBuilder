package be.mips.fhir.be.lab.report.examples;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Quantity.QuantityComparator;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestStatus;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.codesystems.RequestStatus;

import be.mips.fhir.be.lab.report.builders.AnnotationBuilder;
import be.mips.fhir.be.lab.report.builders.BundleBuilder;
import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.DiagnosticReportBuilder;
import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.builders.ObservationBuilder;
import be.mips.fhir.be.lab.report.builders.QuantityBuilder;
import be.mips.fhir.be.lab.report.builders.ServiceRequestBuilder;
import be.mips.fhir.be.lab.report.enums.CodeableConceptBuilderType;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.DiagnosticReportBuilderType;
import be.mips.fhir.be.lab.report.enums.ObservationBuilderType;
import be.mips.fhir.be.lab.report.enums.PatientBuilderType;
import be.mips.fhir.be.lab.report.enums.PractitionerBuilderType;
import be.mips.fhir.be.lab.report.enums.ServiceRequestBuilderType;
import be.mips.fhir.be.lab.report.enums.SpecimenBuilderType;
import be.mips.fhir.be.lab.report.factory.CodeableConceptFactory;
import be.mips.fhir.be.lab.report.factory.CodingFactory;
import be.mips.fhir.be.lab.report.factory.DiagnosticReportFactory;
import be.mips.fhir.be.lab.report.factory.ObservationFactory;
import be.mips.fhir.be.lab.report.factory.PatientFactory;
import be.mips.fhir.be.lab.report.factory.PractitionerFactory;
import be.mips.fhir.be.lab.report.factory.ServiceRequestFactory;
import be.mips.fhir.be.lab.report.factory.SpecimenFactory;
import be.mips.fhir.be.lab.report.models.ObservationRequestGroup;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.narrative.DefaultThymeleafNarrativeGenerator;

public class FhirBeLabReportExampleBuilder {

	public static void main(String[] args) {
	      // Create a context
	      FhirContext ctx = FhirContext.forR4();

	      Bundle biochemistryReportBundle = createBioChemistryReportBundle();
	      
	      Bundle tempMicrobiologyReportBundle = createTempMicrobiologyReportBundle();
	      
	      // Use the narrative generator
	      ctx.setNarrativeGenerator(new DefaultThymeleafNarrativeGenerator());

	      // Print the output
	      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(biochemistryReportBundle);
	      System.out.println(string);
	}
	
	private static Bundle createBioChemistryReportBundle() {
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
	      
	      List<ObservationRequestGroup> observationRequestGroups = createBiochemistryObservationRequestGroups();
	      
	      // Add performer to the list of observations
	      setPerformer(performer, observationRequestGroups);
	      
	      // Add specimen to the list of observations
	      setSpecimen(specimen, observationRequestGroups);
	      
	      // Add patient, service requests and observations to the bioChemistry report
	      bioChemistryReportBuilder.addObservationRequestGroups(patient, observationRequestGroups);
	      
	      // Add specimen to bioChemistry diagnostic report
	      bioChemistryReportBuilder.addSpecimen(specimen);
	      // Add performer to bioChemistry diagnostic report
	      bioChemistryReportBuilder.addPerformer(performer);
	      
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
	      bundleBuilder.addObservationRequestGroupAsEntries(observationRequestGroups);
	      
	      return bundleBuilder.build();
	}
	
	private static Bundle createTempMicrobiologyReportBundle() {
	      // Create patient resource
	      Patient patient = new PatientFactory().create(PatientBuilderType.RANDOM);
	      
	      // Create a practitioner resource
	      Practitioner practitioner = new PractitionerFactory().create(PractitionerBuilderType.RANDOM);
	      
	      // Create performer of observations
	      Practitioner performer = new PractitionerFactory().create(PractitionerBuilderType.RANDOM);
	      
	      // Create BAL-vocht specimen
	      Specimen specimen = new SpecimenFactory().create(SpecimenBuilderType.BAL);
	      
	      // Create temp microbiology diagnostic report
	      DiagnosticReportBuilder microbiologyReportBuilder = new DiagnosticReportFactory().build(
	    		  DiagnosticReportBuilderType.MICROBIOLOGY);
	      		  
	      // Add practitioner as resultsInterpreter to the bioChemistryReportBuilder
	      microbiologyReportBuilder.addResultsInterpreter(practitioner);
	      
	      List<ObservationBuilder> observationBuilders = createTempMicrobiologyObservationBuilders();
	      
	      // Add performer to the list of observations
//	      setPerformer(performer, observationBuilders);
	      
	      // Add specimen to the list of observations
//	      setSpecimen(specimen, observationBuilders);
	      
	      // Add patient and observations to the bioChemistry report
//	      microbiologyReportBuilder.addObservationRequestGroups(patient, observationBuilders);
	      
	      // Add specimen to bioChemistry diagnostic report
	      microbiologyReportBuilder.addSpecimen(specimen);

		// Create a Bundle
	      BundleBuilder bundleBuilder = new BundleBuilder()
	    		  .withId(IdType.newRandomUuid())
	    		  .withIdentifier(new IdentifierBuilder()
	    				  .withSystem("urn:ietf:rfc:3986")
	    		  		  .withValue("")
	    		  		  .build())
	    		  .withType(BundleType.COLLECTION);
	      return bundleBuilder.build();
	}

	private static void setPerformer(Practitioner performer, List<ObservationRequestGroup> observationRequestGroups) {
		for (ObservationRequestGroup observationRequestGroup : observationRequestGroups) {
			observationRequestGroup.getServiceRequestBuilder().addPerformer(performer);
			for (ObservationBuilder observationBuilder : observationRequestGroup.getObservations()) {
				observationBuilder.addPerformer(performer);
			}
		}		
	}

	private static void setSpecimen(Specimen specimen, List<ObservationRequestGroup> observationRequestGroups) {
		for (ObservationRequestGroup observationRequestGroup : observationRequestGroups) {
			observationRequestGroup.getServiceRequestBuilder().addSpecimen(specimen);
			for (ObservationBuilder observationBuilder : observationRequestGroup.getObservations()) {
				observationBuilder.withSpecimen(specimen);
			}
		}		
	}

	private static List<ObservationRequestGroup> createBiochemistryObservationRequestGroups() {
		List<ObservationRequestGroup> observationBuilders = new ArrayList<ObservationRequestGroup>();
		Date issuedDate = new Date();
		Calendar effectiveDateTime = new Calendar.Builder()
		        .setDate(2021, 3, 20)
		        .build();
	    ObservationFactory observationFactory = new ObservationFactory();
	    ServiceRequestFactory serviceRequestFactory = new ServiceRequestFactory(); 
		
	    // Create ServiceRequest SUITZ
	    ServiceRequestBuilder suitzServiceRequestBuilder = serviceRequestFactory
	    		.build(ServiceRequestBuilderType.RANDOM)
	    		.withStatus(ServiceRequestStatus.REVOKED)
	    		.withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.SNOMED)
				        .withCode("252275004")
	                    .withDisplay("Uitzicht serum/plasma")));
	    
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
	    
	    ObservationRequestGroup suitzObservationRequestGroup = 
	    		new ObservationRequestGroup(suitzServiceRequestBuilder);
	    suitzObservationRequestGroup.addObservation(suitzObservationBuilder);
	    suitzObservationRequestGroup.addBasedOnForObservations();
	    
		// Create blood count ServiceRequest
	    ServiceRequestBuilder bloodCountServiceRequestBuilder = serviceRequestFactory
	    		.build(ServiceRequestBuilderType.RANDOM)
	    		.withStatus(ServiceRequestStatus.COMPLETED)
	    		.withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.SNOMED)
				        .withCode("88308000")
	                    .withDisplay("Blood cell count")));
	    
	    // Create Hematology title observation resource
	    ObservationBuilder hematologyObservationBuilder = observationFactory
	    	.build(ObservationBuilderType.FHIR_BE)
	    	.withId(IdType.newRandomUuid())
	    	.withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
            .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("18723-7")
                    .withDisplay("Hematology")));
	    
	    // Create Screening title observation resource
	    ObservationBuilder screeningObservationBuilder = observationFactory
	    	.build(ObservationBuilderType.FHIR_BE)
	    	.withId(IdType.newRandomUuid())
	    	.withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
            .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("47288-6")
                    .withDisplay("CBC WO Differential panel (BldCo)")));
	    		    
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
	        		.build())
	        .addNote(new AnnotationBuilder()
	        		.withText("Note on hemoglobine")
	        		.build())
	        .addReferenceRange(13.5, 17);
		
            
  
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
	        		.build())
	        .addNote(new AnnotationBuilder()
	        		.withText("Note on HCT")
	        		.build())
	        .addReferenceRange(39.9,  51);

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
    	            .withValue(BigDecimal.valueOf(8.93))
    		        .withUnit("10E3/µL")
    	            .withCode("10*3/uL")
    	            .withSystem("http://unitsofmeasure.org")
    				.build())
    	        .addNote(new AnnotationBuilder()
    	        		.withText("Note on WBC")
    	        		.build())
        		.addReferenceRange(4.30, 9.64);
        
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
    				.build())
    	        .addReferenceRange(QuantityComparator.LESS_THAN, 1.0);
        /*
         *  Create ObservationRequestGroup for the blood cell count,
         *  add blood count service request and linked observation hgb, hct, wbc, normoblasten
         *  to the observation request group 
         */
        ObservationRequestGroup bloodCountObservationRequestGroup = 
        		new ObservationRequestGroup(bloodCountServiceRequestBuilder);
        bloodCountObservationRequestGroup.addObservation(hematologyObservationBuilder);
        bloodCountObservationRequestGroup.addObservation(screeningObservationBuilder);
        bloodCountObservationRequestGroup.addObservation(hgbObservationBuilder);
        bloodCountObservationRequestGroup.addObservation(hctObservationBuilder);
        bloodCountObservationRequestGroup.addObservation(wbcObservationBuilder);
        bloodCountObservationRequestGroup.addObservation(normoBlastenObservationBuilder);
        bloodCountObservationRequestGroup.addBasedOnForObservations();
        
        // Add hgb, hct, wbc, normoBlasten as HasMember reference of the screening observation
        screeningObservationBuilder
	    	.addHasMember(hgbObservationBuilder)
	    	.addHasMember(hctObservationBuilder)
	    	.addHasMember(wbcObservationBuilder)
	    	.addHasMember(normoBlastenObservationBuilder);

        
        // Create electrolytes service request
	    ServiceRequestBuilder electrolytesServiceRequestBuilder = serviceRequestFactory
	    		.build(ServiceRequestBuilderType.RANDOM)
	    		.withStatus(ServiceRequestStatus.COMPLETED)
	    		.withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("24326-1")
	                    .withDisplay("Electrolytes")));

        // Create chemistry observation resources
	    ObservationBuilder chemistryObservationBuilder = observationFactory
	    	.build(ObservationBuilderType.FHIR_BE)
	    	.withId(IdType.newRandomUuid())
	    	.withStatus(ObservationStatus.FINAL)
            .addCategory(new CodeableConceptFactory()
            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
            .withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
			        .build(CodingBuilderType.LOINC)
			        .withCode("LP31388-9")
                    .withDisplay("Chemistry")));
	    
	    ObservationBuilder ionsObservationBuilder = observationFactory
		    	.build(ObservationBuilderType.FHIR_BE)
		    	.withId(IdType.newRandomUuid())
		    	.withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("LP9999-9")
	                    .withDisplay("Ions")));
        
	    
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
				.build())
	        .addReferenceRange(136,  145);

        // Create potasium Observation
        ObservationBuilder kObservationBuilder = observationFactory
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
    				.build())
    	        .addReferenceRange(3.6, 4.8);
  
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
    				.build())
    	        .addReferenceRange(98, 107);

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
    				.build())
    	        .addReferenceRange(22, 29);
        
        /*
         *  Create ObservationRequestGroup for the electrolytes,
         *  add electrolytes service request and linked observation Na, K, Cl and Bicarbonaat
         *  to the observation request group 
         */
        ObservationRequestGroup electrolytesObservationRequestGroup = 
        		new ObservationRequestGroup(electrolytesServiceRequestBuilder);
        electrolytesObservationRequestGroup.addObservation(chemistryObservationBuilder);
        electrolytesObservationRequestGroup.addObservation(ionsObservationBuilder);
        electrolytesObservationRequestGroup.addObservation(naObservationBuilder);
        electrolytesObservationRequestGroup.addObservation(kObservationBuilder);
        electrolytesObservationRequestGroup.addObservation(clObservationBuilder);
        electrolytesObservationRequestGroup.addObservation(carbObservationBuilder);
        electrolytesObservationRequestGroup.addBasedOnForObservations();
        
        // Add Na, K, Cl and Bicarbonaat as hasMember to the ions observation
        ionsObservationBuilder
        	.addHasMember(naObservationBuilder)
        	.addHasMember(kObservationBuilder)
        	.addHasMember(clObservationBuilder)
        	.addHasMember(carbObservationBuilder);

        // Create liver service request
	    ServiceRequestBuilder liverServiceRequestBuilder = serviceRequestFactory
	    		.build(ServiceRequestBuilderType.RANDOM)
	    		.withStatus(ServiceRequestStatus.COMPLETED)
	    		.withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("78699-6")
	                    .withDisplay("Liver fibrosis score panel")));

        // Create BioChemie observations
	    ObservationBuilder bioChemistryObservationBuilder = observationFactory
		    	.build(ObservationBuilderType.FHIR_BE)
		    	.withId(IdType.newRandomUuid())
		    	.withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("LP9999-10")
	                    .withDisplay("Biochemistry")));

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
		        		.build())
		        .addReferenceRange(13, 43);
		
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
	                .withValue(BigDecimal.valueOf(1.03))
			        .withUnit("mg/dL")
	                .withCode("mg/dL")
	                .withSystem("http://unitsofmeasure.org")
					.build())
	            .addReferenceRange(0.72, 1.17);
		
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
	                .withValue(BigDecimal.valueOf(74.4))
			        .withUnit("mL/min")
	                .withCode("mL/min")
	                .withSystem("http://unitsofmeasure.org")
					.build());
		
        /*
         *  Create ObservationRequestGroup for the liver fibrosis score panel,
         *  add liver service request and linked observation Ureum, Creatinine and GFR
         *  to the observation request group 
         */
		ObservationRequestGroup liverObservationRequestGroup = 
				new ObservationRequestGroup(liverServiceRequestBuilder);
		liverObservationRequestGroup.addObservation(bioChemistryObservationBuilder);
		liverObservationRequestGroup.addObservation(ureumObservationBuilder);
		liverObservationRequestGroup.addObservation(kreatObservationBuilder);
		liverObservationRequestGroup.addObservation(gfrObservationBuilder);
		liverObservationRequestGroup.addBasedOnForObservations();
		
		// Add Ureum, Creatinine and GFR as hasMember to the biochemistry observation
        bioChemistryObservationBuilder
        	.addHasMember(ureumObservationBuilder)
        	.addHasMember(kreatObservationBuilder)
        	.addHasMember(gfrObservationBuilder);
        	
        
	    // Create crp service request
	    ServiceRequestBuilder crpServiceRequestBuilder = serviceRequestFactory
	    		.build(ServiceRequestBuilderType.RANDOM)
	    		.withStatus(ServiceRequestStatus.COMPLETED)
	    		.withCode(new CodeableConceptBuilder()
		        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("1988-5")
	                    .withDisplay("C reactive protein [Mass/volume] in Serum or Plasma")));

        // Create Inflammatie observations
	    ObservationBuilder inflammationObservationBuilder = observationFactory
		    	.build(ObservationBuilderType.FHIR_BE)
		    	.withId(IdType.newRandomUuid())
		    	.withStatus(ObservationStatus.FINAL)
	            .addCategory(new CodeableConceptFactory()
	            		.build(CodeableConceptBuilderType.LABORATORY_OBSERVATION_CATEGORY))
	            .withCode(new CodeableConceptBuilder()
			        .addCoding(new CodingFactory()
				        .build(CodingBuilderType.LOINC)
				        .withCode("LP9999-11")
	                    .withDisplay("Inflammation")));

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
		        		.build())
	            .addReferenceRange(QuantityComparator.LESS_THAN, 5.0);

        /*
         *  Create ObservationRequestGroup for the liver fibrosis score panel,
         *  add liver service request and linked observation Ureum, Creatinine and GFR
         *  to the observation request group 
         */
		ObservationRequestGroup crpObservationRequestGroup = 
				new ObservationRequestGroup(crpServiceRequestBuilder);
		crpObservationRequestGroup.addObservation(inflammationObservationBuilder);
		crpObservationRequestGroup.addObservation(crpObservationBuilder);
		crpObservationRequestGroup.addBasedOnForObservations();
		
		inflammationObservationBuilder
        	.addHasMember(crpObservationBuilder);
		
	    hematologyObservationBuilder
	    	.addHasMember(screeningObservationBuilder);
	    
	    // Add Ions, biochemistry and Inflammatie as hasMember to the chemistry observation
	    chemistryObservationBuilder
	    	.addHasMember(ionsObservationBuilder)
	    	.addHasMember(bioChemistryObservationBuilder)
	    	.addHasMember(inflammationObservationBuilder);

		observationBuilders.add(suitzObservationRequestGroup);
		observationBuilders.add(bloodCountObservationRequestGroup);
		observationBuilders.add(electrolytesObservationRequestGroup);
		observationBuilders.add(liverObservationRequestGroup);
        observationBuilders.add(crpObservationRequestGroup);

        return observationBuilders;
	}

	private static List<ObservationBuilder> createTempMicrobiologyObservationBuilders() {
		List<ObservationBuilder> observationBuilders = new ArrayList<ObservationBuilder>();
		Date issuedDate = new Date();
		Calendar effectiveDateTime = new Calendar.Builder()
		        .setDate(2021, 3, 20)
		        .build();
	    ObservationFactory observationFactory = new ObservationFactory();
		return observationBuilders;
	}


}
