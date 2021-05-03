package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.ServiceRequest;

import be.mips.fhir.be.lab.report.builders.ServiceRequestBuilder;
import be.mips.fhir.be.lab.report.enums.IdentfierBuilderType;
import be.mips.fhir.be.lab.report.enums.ServiceRequestBuilderType;

public class ServiceRequestFactory implements Factory<ServiceRequest> {

	public ServiceRequest create() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ServiceRequestBuilder build(ServiceRequestBuilderType type) {
		ServiceRequestBuilder builder;
		switch(type) {
		case RANDOM:
			builder = new ServiceRequestBuilder()
			    		.withId(IdType.newRandomUuid())
						.addIdentifier(new IdentifierFactory()
							.create(IdentfierBuilderType.EHEALTH_FGOV_BE_SSIN));
			break;
		default:
			builder = new ServiceRequestBuilder()
				.addIdentifier(new IdentifierFactory()
					.create(IdentfierBuilderType.EHEALTH_FGOV_BE_SSIN));
			break;
		}
		return builder;
	}

}
