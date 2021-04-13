package be.mips.fhir.be.lab.report.builders;

import java.util.List;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Resource;

public class BundleBuilder implements Builder<Bundle> {

	private final Bundle bundle;
	
	public BundleBuilder() {
		bundle = new Bundle();
	}
	
	public BundleBuilder withId(IIdType iIdTpe) {
		bundle.setId(iIdTpe);
		return this;
	}
	
	public BundleBuilder withId(String idTypeString) {
		IIdType idType = new IdType();
		idType.setValue(idTypeString);
		return withId(idType);
	}
	
	public BundleBuilder withIdentifier(Identifier identifier) {
		bundle.setIdentifier(identifier);
		return this;
	}
	
	public BundleBuilder withType(BundleType type) {
		bundle.setType(type);
		return this;
	}
	
	public BundleBuilder addEntry(BundleEntryComponent entry) {
		bundle.addEntry(entry);
		return this;
	}
	
	public BundleBuilder addEntry(Resource resource) {
		BundleEntryComponent entry = new BundleEntryComponent();
		entry.setFullUrl(resource.getIdElement().getValue());
		entry.setResource(resource);
		return addEntry(entry);
	}
	
	public BundleBuilder addEntries(List<ObservationBuilder> observationBuilders) {
		for (ObservationBuilder observationBuilder : observationBuilders) {
			addEntry(observationBuilder.build());
		}
		return this;
	}
	
	public Bundle build() {
		return bundle;
	}



}
