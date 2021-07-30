package be.mips.fhir.be.lab.report.builders;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestIntent;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestStatus;
import org.hl7.fhir.r4.model.Specimen;

public class ServiceRequestBuilder implements Builder<ServiceRequest> {
	private final ServiceRequest serviceRequest;
	
	public ServiceRequestBuilder() {
		serviceRequest = new ServiceRequest();
		serviceRequest.setIntent(ServiceRequestIntent.ORDER);
	}

	public ServiceRequestBuilder addIdentifier(IdentifierBuilder identifierBuilder) {
		return addIdentifier(identifierBuilder.build());
	}

	public ServiceRequestBuilder addIdentifier(Identifier identifier) {
		serviceRequest.addIdentifier(identifier);
		return this;
	}
	
	public ServiceRequestBuilder withStatus(ServiceRequestStatus status) {
		serviceRequest.setStatus(status);
		return this;
	}
	
	public ServiceRequestBuilder withCode(CodeableConcept code) {
		serviceRequest.setCode(code);
		return this;
	}

	public ServiceRequestBuilder withCode(CodeableConceptBuilder builder) {
		return withCode(builder.build());
	}
	
	public ServiceRequestBuilder addPerformer(Practitioner performer) {
		Reference reference = new Reference();
		reference.setResource(performer);
		serviceRequest.addPerformer(reference);
		return this;
	}

	public ServiceRequestBuilder addSpecimen(Specimen specimen) {
		Reference reference = new Reference();
		reference.setResource(specimen);
		serviceRequest.addSpecimen(reference);
		return this;
	}
	
	public ServiceRequestBuilder withSubject(IBaseResource subject) {
		Reference reference = new Reference();
		reference.setResource(subject);
		serviceRequest.setSubject(reference);
		return this;
	}

	public ServiceRequestBuilder withId(IIdType uuid) {
		serviceRequest.setId(uuid);
		return this;
	}
	
	public ServiceRequestBuilder withAuthoredOn(Date date) {
		serviceRequest.setAuthoredOn(date);
		return this;
	}
	
	public ServiceRequest build() {
		return serviceRequest;
	}
	
}
