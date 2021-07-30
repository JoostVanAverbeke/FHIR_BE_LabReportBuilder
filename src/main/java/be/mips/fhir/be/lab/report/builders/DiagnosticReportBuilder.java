package be.mips.fhir.be.lab.report.builders;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportMediaComponent;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Narrative;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Specimen;

import be.mips.fhir.be.lab.report.models.ObservationRequestGroup;

public class DiagnosticReportBuilder implements Builder<DiagnosticReport> {
	private final DiagnosticReport diagnosticReport;
	
	public DiagnosticReportBuilder() {
		diagnosticReport = new DiagnosticReport();
		Meta meta = new Meta();
		meta.setVersionId("1");
		meta.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-laboratory-report");
		diagnosticReport.setMeta(meta);
	}
	
	public DiagnosticReportBuilder withId(IIdType idType) {
		diagnosticReport.setId(idType);
		return this;
	}
	
	public DiagnosticReportBuilder withLanguage(String language) {
		diagnosticReport.setLanguage(language);
		return this;
	}
	
	public DiagnosticReportBuilder withText(Narrative text) {
		diagnosticReport.setText(text);
		return this;
	}
	
	public DiagnosticReportBuilder withText(String text) {
		Narrative narritive = new Narrative();
		narritive.setDivAsString(text);
		narritive.setStatus(NarrativeStatus.GENERATED);
		return withText(narritive);
	}
	
	public DiagnosticReportBuilder withStatus(DiagnosticReportStatus status) {
		diagnosticReport.setStatus(status);
		return this;
	}
	
	public DiagnosticReportBuilder addCategory(CodeableConcept category) {
		diagnosticReport.addCategory(category);
		return this;
	}
	
	public DiagnosticReportBuilder withCode(CodeableConcept code) {
		diagnosticReport.setCode(code);
		return this;
	}

	public DiagnosticReportBuilder addBasedOn(ServiceRequestBuilder serviceRequestBuilder) {
		Reference reference = new Reference();
		reference.setResource(serviceRequestBuilder.build());
		return addBasedOn(reference);
	}

	public DiagnosticReportBuilder addBasedOn(Reference reference) {
		diagnosticReport.addBasedOn(reference);
		return this;
	}
	
	public DiagnosticReportBuilder addIdentifier(Identifier identifier) {
		diagnosticReport.addIdentifier(identifier);
		return this;
	}
	
	public DiagnosticReportBuilder withEffectiveDateTime(DateTimeType dateTimeType) {
		diagnosticReport.setEffective(dateTimeType);
		return this;
	}
	
	public DiagnosticReportBuilder withEffectiveDateTime(Calendar calendar) {
		return withEffectiveDateTime(new DateTimeType(calendar));
	} 

	public DiagnosticReportBuilder withIssued(Date date) {
		diagnosticReport.setIssued(date);
		return this;
	}

	public DiagnosticReportBuilder withConclusion(String conclusion) {
		diagnosticReport.setConclusion(conclusion);
		return this;
	}

	public DiagnosticReportBuilder addMedia(DiagnosticReportMediaComponent media) {
		diagnosticReport.addMedia(media);
		return this;
	}	
	
	public DiagnosticReportBuilder withSubject(Reference subject) {
		diagnosticReport.setSubject(subject);
		return this;
	}	

	public DiagnosticReportBuilder withSubject(IBaseResource resource) {
		diagnosticReport.getSubject().setResource(resource);
		return this;
	}
	
	public DiagnosticReportBuilder addResultsInterpreter(Reference resultsInterpreter) {
		diagnosticReport.addResultsInterpreter(resultsInterpreter);
		return this;
	}
	
	public DiagnosticReportBuilder addResultsInterpreter(IBaseResource resource) {
		Reference reference = new Reference();
		reference.setResource(resource);
		return addResultsInterpreter(reference);
	}
	
	public DiagnosticReportBuilder addResult(IBaseResource observation) {
		Reference reference = new Reference();
		reference.setResource(observation);
		diagnosticReport.addResult(reference);
		return this;
	}
	
	public DiagnosticReportBuilder addResult(ObservationBuilder observationBuilder) {
		Reference reference = new Reference();
		reference.setResource(observationBuilder.build());
		diagnosticReport.addResult(reference);
		return this;
	}

	public DiagnosticReportBuilder addResults(List<IBaseResource> observations) {
		for (IBaseResource observation : observations) {
			addResult(observation);
		}
		return this;
	}

	public DiagnosticReportBuilder addObservationRequestGroups(IBaseResource subject, List<ObservationRequestGroup> observationRequestGroups) {
		withSubject(subject);
		for (ObservationRequestGroup observationRequestGroup : observationRequestGroups) {
			/*
				Do not add all ServiceRequests for this report to the diagnostic report.
				We should add a ServiceRequest that represents the order placer.
				addBasedOn(observationRequestGroup.getServiceRequestBuilder());
			*/
			observationRequestGroup.getServiceRequestBuilder().withSubject(subject);
			for (ObservationBuilder observationBuilder : observationRequestGroup.getObservations()) {
				observationBuilder.withSubject(subject);
				addResult(observationBuilder);				
			}
		}
		return this;
	}

	public DiagnosticReportBuilder addSpecimen(Specimen specimen) {
		Reference reference = new Reference();
		reference.setResource(specimen);
		diagnosticReport.addSpecimen(reference);
		return this;		
	}
	
	public DiagnosticReportBuilder addPerformer(Practitioner performer) {
		diagnosticReport.addPerformer().setResource(performer);
		return this;
	}
	
	public DiagnosticReport build() {
		return diagnosticReport;
	}



}
