# FHIR_BE_LabReportBuilder
FHIR Belgium Lab report example project

# FHIR IHE Laboratory Report
All information can be find on [https://build.fhir.org/ig/hl7-be/hl7-be-fhir-laboratory-report/](https://build.fhir.org/ig/hl7-be/hl7-be-fhir-laboratory-report/) 

# How to validate a laboratory report?
## Validator
- Download the FHIR validator here [http://hl7.org/fhir/validator/](http://hl7.org/fhir/validator/)
- Documentation on how to use the FHIR validator can be find here [https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator](https://confluence.hl7.org/display/FHIR/Using+the+FHIR+Validator) 
- Validate your FHIR file against the DEFAULT FHIR validator:
```
  java -jar validator_cli.jar c:\temp\FHIR\BiochemistryReport.json -version 4.0
```
- Validate your FHIR file against the FHIR BE lab report
```  
  java -jar validator_cli.jar c:\temp\FHIR\BiochemistryReport.json -version 4.0 -ig https://build.fhir.org/ig/hl7-be-fhir-laboratory-report/  
```
# Howtos
How to link an observation to a hierarchy of titles?
- Ureum has subtitle 'Biochemie', which has a parent title 'SCHEIKUNDE'
```
    SCHEIKUNDE
        Biochemie
            Ureum
```
# Issues
## Built-in Narrative Templates
### DiagnosticReport.html
The DiagnosticReport.html template on location  
[https://github.com/hapifhir/hapi-fhir/tree/master/hapi-fhir-base/src/main/resources/ca/uhn/fhir/narrative]
(https://github.com/hapifhir/hapi-fhir/tree/master/hapi-fhir-base/src/main/resources/ca/uhn/fhir/narrative) 
contains an error:
Line 112:
```
  <th:block th:if="${not result.resource.interpretation.empty} and ${result.resource.interpretation[0].textElement.empty} and ${not result.resource.interpretation[0].coding.empty} and ${not result.resource.interpretation[0].coding[0].displayElement.empty}" th:text="${result.resource.interpretation.coding[0].display}"/>

```
Should be:
```
  <th:block th:if="${not result.resource.interpretation.empty} and ${result.resource.interpretation[0].textElement.empty} and ${not result.resource.interpretation[0].coding.empty} and ${not result.resource.interpretation[0].coding[0].displayElement.empty}" th:text="${result.resource.interpretation[0].coding[0].display}"/>

```

At the end it should be interpretation[0]. instead of interpretation. 