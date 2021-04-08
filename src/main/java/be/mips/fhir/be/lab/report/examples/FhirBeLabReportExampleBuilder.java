package be.mips.fhir.be.lab.report.examples;
import org.hl7.fhir.r4.model.Bundle.BundleType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

import be.mips.fhir.be.lab.report.builders.BundleBuilder;
import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.DiagnosticReportBuilder;
import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.builders.ObservationBuilder;
import be.mips.fhir.be.lab.report.builders.QuantityBuilder;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.DiagnosticReportBuilderType;
import be.mips.fhir.be.lab.report.enums.PatientBuilderType;
import be.mips.fhir.be.lab.report.enums.PractitionerBuilderType;
import be.mips.fhir.be.lab.report.factory.CodingFactory;
import be.mips.fhir.be.lab.report.factory.DiagnosticReportFactory;
import be.mips.fhir.be.lab.report.factory.PatientFactory;
import be.mips.fhir.be.lab.report.factory.PractitionerFactory;
import ca.uhn.fhir.context.FhirContext;

public class FhirBeLabReportExampleBuilder {

	public static void main(String[] args) {
	      // Create a context
	      FhirContext ctx = FhirContext.forR4();

	      // Create patient resource
	      Patient patient = new PatientFactory().create(PatientBuilderType.SIMPLE);
	      
	      // Create a practitioner resource
	      Practitioner practitioner = new PractitionerFactory().create(PractitionerBuilderType.SIMPLE);
	      
	      // Create biochemistry diagnostic report
	      DiagnosticReportBuilder bioChemistryReportBuilder = new DiagnosticReportFactory().build(
	    		  DiagnosticReportBuilderType.BIOCHEMISTRY);
	      
	      // Create Hematology observation resources
	      // Create Hemoglobine Observation
	      ObservationBuilder hgbObservationBuilder = new ObservationBuilder()
	    		  .withId("observation1")
	    		  .withStatus(ObservationStatus.FINAL)
	    		  .withCode(new CodeableConceptBuilder()
	    				  .addCoding(new CodingFactory()
	    					  .build(CodingBuilderType.LOINC)
		    				  .withCode("718-7")
		    				  .withDisplay("Hemoglobine")))
	    		  .withEffectiveDateTime(new Calendar.Builder()
	  					  .setDate(2021, 3, 20)
	  					  .build())
	    		  .withIssued(new Date())
	    		  .withValueQuantity(new QuantityBuilder()
	    				  .withValue(BigDecimal.valueOf(11.6))
	    				  .withUnit("g/dL")
	    				  .withCode("g/dL")
	    				  .withSystem("http://unitsofmeasure.org")
	    				  .build());
	      
	      // Create Hematocriet Observation
	      Observation hematocrietObservation = new ObservationBuilder().build();
	      
	      // Create WBC Observation
	      Observation wbcObservation = new ObservationBuilder().build();

	      // Create Normoblasten Observation
	      Observation normoBlastenObservation = new ObservationBuilder().build();

	      // Create chemistry observation resources
	      // Create sodium Observation
	      Observation naObservation = new ObservationBuilder().build();

	      // Create potasium Observation
	      Observation kaObservation = new ObservationBuilder().build();
	      
	      // Create chloor Observation
	      Observation clObservation = new ObservationBuilder().build();

	      // Create bicarbonate Observation
	      Observation naHCO3Observation = new ObservationBuilder().build();

		  
	      // Add practitioner as resultsInterpreter to the bioChemistryReportBuilder
	      bioChemistryReportBuilder.addResultsInterpreter(practitioner);

	      List<ObservationBuilder> observations = new ArrayList<ObservationBuilder>();
	      observations.add(hgbObservationBuilder);
	      
	      // Add patient and observations to the bioChemistry report
	      bioChemistryReportBuilder.addResults(patient, observations);
	      
	      // Create a Bundle
	      BundleBuilder bundleBuilder = new BundleBuilder()
	    		  .withId("BiochemistryAsBundleCollection")
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

	      // Add the practitioner as an entry.
	      bundleBuilder.addEntry(hgbObservationBuilder.build());
	      
	      // Print the output
	      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundleBuilder.build());
	      System.out.println(string);
	}

}
