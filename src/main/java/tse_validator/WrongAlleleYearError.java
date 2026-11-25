package tse_validator;

import java.util.Arrays;
import java.util.Collection;

import i18n_messages.TSEMessages;
import report_validator.ReportError;

public class WrongAlleleYearError implements ReportError {

	private String result;
	private String yearReport; 
	private String yearAlleleConfig;
	
	public WrongAlleleYearError(String result, String yearReport, String yearAlleleConfig) {
		this.result = result;
		this.yearReport = yearReport;
		this.yearAlleleConfig = yearAlleleConfig;
	}
	
	@Override
	public ErrorType getTypeOfError() {
		return ErrorType.ERROR;
	}

	@Override
	public String getErrorMessage() {
		
		return TSEMessages.get("wrong.allele.year.message",  yearAlleleConfig, yearReport);
	}

	@Override
	public Collection<String> getInvolvedRowsIdsMessage() {
		return Arrays.asList(result);
	}

	@Override
	public String getSuggestions() {
		return "Change report or change the allele year selected";
	}

	@Override
	public Collection<String> getErroneousValues() {
		
		return Arrays.asList(yearReport);
	}
}
