package be.mips.fhir.be.lab.report.factory;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.Observation;

import be.mips.fhir.be.lab.report.builders.MetaBuilder;
import be.mips.fhir.be.lab.report.builders.ObservationBuilder;
import be.mips.fhir.be.lab.report.enums.ObservationBuilderType;

public class ObservationFactory implements Factory<Observation> {

	public Observation create() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Observation create(ObservationBuilderType type) {
		return build(type).build();
	}
	
	public ObservationBuilder build(ObservationBuilderType type) {
		ObservationBuilder observationBuilder;
		switch(type) {
		case FHIR_BE:
			observationBuilder = new ObservationBuilder()
				.withMeta(new MetaBuilder()
					.addProfile("https://www.ehealth.fgov.be/standards/fhir/StructureDefinition/be-observation-laboratory"));
			break;
		default:
			observationBuilder = new ObservationBuilder();
			break;
		}
		return observationBuilder;
	}

}
