package be.mips.fhir.be.lab.report.models;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.ServiceRequest;

import be.mips.fhir.be.lab.report.builders.ObservationBuilder;
import be.mips.fhir.be.lab.report.builders.ServiceRequestBuilder;

public class ObservationRequestGroup {
	private final ServiceRequestBuilder serviceRequestBuilder;
	private List<ObservationBuilder> observationBuilders;
	
	public ObservationRequestGroup(ServiceRequestBuilder serviceRequestBuilder) {
		super();
		this.serviceRequestBuilder = serviceRequestBuilder;
		this.observationBuilders = new ArrayList<ObservationBuilder>();
	}
	
	public void addObservation(ObservationBuilder observationBuilder) {
		if (observationBuilder != null) {
			this.observationBuilders.add(observationBuilder);
		}
	}	
	
	public ServiceRequest getServiceRequest() {
		return serviceRequestBuilder.build();
	}

	public ServiceRequestBuilder getServiceRequestBuilder() {
		return serviceRequestBuilder;
	}

	public List<ObservationBuilder> getObservations() {
		return observationBuilders;
	}
	
	public void addBasedOnForObservations() {
		for (ObservationBuilder observationBuilder : observationBuilders) {
			observationBuilder.addBasedOn(serviceRequestBuilder);
		}
	}

}
