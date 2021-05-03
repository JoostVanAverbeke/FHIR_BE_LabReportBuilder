package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.Annotation;

public class AnnotationBuilder implements Builder<Annotation> {

	private final Annotation annotation;
	
	public AnnotationBuilder() {
		annotation = new Annotation();
	}
	

	public AnnotationBuilder withText(String note) {
		annotation.setText(note);
		return this;
	}
	
	public Annotation build() {
		return annotation;
	}

}
