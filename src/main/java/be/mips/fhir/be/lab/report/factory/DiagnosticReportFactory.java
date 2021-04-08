package be.mips.fhir.be.lab.report.factory;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.collections4.Factory;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus;

import be.mips.fhir.be.lab.report.builders.CodeableConceptBuilder;
import be.mips.fhir.be.lab.report.builders.DiagnosticReportBuilder;
import be.mips.fhir.be.lab.report.builders.IdentifierBuilder;
import be.mips.fhir.be.lab.report.enums.CodeableConceptBuilderType;
import be.mips.fhir.be.lab.report.enums.CodingBuilderType;
import be.mips.fhir.be.lab.report.enums.DiagnosticReportBuilderType;

public class DiagnosticReportFactory implements Factory<DiagnosticReport> {

	public DiagnosticReport create() {
		// TODO Auto-generated method stub
		return create(DiagnosticReportBuilderType.DEFAULT);
	}

	public DiagnosticReport create(DiagnosticReportBuilderType type) {
		return build(type).build();		
	}
	
	public DiagnosticReportBuilder build(DiagnosticReportBuilderType type) {
		DiagnosticReportBuilder builder;
		switch(type) {
		case BIOCHEMISTRY:
			Calendar effectiveCalendar = new Calendar.Builder().setDate(2021, 3, 20).build();
			builder = new DiagnosticReportBuilder()
				.withid("BiochemistryDiagnosticReport1")
				.withLanguage("en")
				.withText("This example is presented as a Collection bundle for ease of understanding." + 
						"It makes no assumption towards any final implementation of using FHIR technically.")
				.addIdentifier(new IdentifierBuilder()
						.withSystem("http://acme.com/lab/reports")
						.withValue("y4502347523")
						.build())
				.withStatus(DiagnosticReportStatus.FINAL)
				.addCategory(new CodeableConceptFactory()
						.create(CodeableConceptBuilderType.LABORATORY))
				.withCode(new CodeableConceptBuilder()
						.addCoding(new CodingFactory()
								.build(CodingBuilderType.LOINC)
								.withCode("18723-7")
								.withDisplay("Hematology studies (set)")
								.build())
						.build())
				.withEffectiveDateTime(effectiveCalendar)
				.withIssued(new Date())
				.withConclusion("The JVA conclusion");
			break;
		default:
			builder = new DiagnosticReportBuilder();
			break;
		}
		return builder;		
	}

}
