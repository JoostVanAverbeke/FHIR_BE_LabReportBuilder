package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Meta;

public class MetaBuilder implements Builder<Meta> {

	private final Meta meta;
	
	public MetaBuilder() {
		meta = new Meta();
	}
	
	public MetaBuilder withVersionId(String versionId) {
		meta.setVersionId(versionId);
		return this;
	}
	
	public MetaBuilder addProfile(String profile) {
		meta.addProfile(profile);
		return this;
	}
	
	public Meta build() {
		return meta;
	}

}
