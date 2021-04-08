package be.mips.fhir.be.lab.report.builders;

import org.apache.commons.lang3.builder.Builder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;

public class CodeableConceptBuilder implements Builder<CodeableConcept> {

	private final CodeableConcept codeableConcept;
	
	public CodeableConceptBuilder() {
		codeableConcept = new CodeableConcept();
	}
	
	public CodeableConceptBuilder addCoding(Coding coding) {
		codeableConcept.addCoding(coding);
		return this;
	}

	public CodeableConceptBuilder addCoding(CodingBuilder codingBuilder) {
		return addCoding(codingBuilder.build());
	}
	
	public CodeableConceptBuilder withText(String text) {
		codeableConcept.setText(text);
		return this;
	}
	
	public CodeableConcept build() {
		return codeableConcept;
	}
}
