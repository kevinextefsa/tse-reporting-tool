package tse_alleleyearselector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum AlleleYearSelectorEnum {
	
	NEW_SELECTOR(0, "2026"),
	OLD_SELECTOR(1, "2027");
	
	private static final Logger LOGGER = LogManager.getLogger(AlleleYearSelectorEnum.class);
	private Integer index;
	private String label;
	
	AlleleYearSelectorEnum(Integer index, String label) {
		this.index = index;
		this.label = label;
	}
	
	public static AlleleYearSelectorEnum fromInt(Integer keyword) {
		for (AlleleYearSelectorEnum mode : AlleleYearSelectorEnum.values()) {
			if (mode.index == keyword) {
				return mode;
			}
		}
		
		LOGGER.warn("No valid allele year found");
		return NEW_SELECTOR;
	}
	
	public Integer getIndex() {
		return index;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
