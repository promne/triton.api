package triton.domain;

public enum TechnologyType {

	SCANNING("scanning"),
	PROPULSION("propulsion"),
	TERRAFORMING("terraforming"),
	RESEARCH("research"),
	WEAPONS("weapons"),
	BANKING("banking"),
	MANUFACTURING("manufacturing");

	public static TechnologyType getTechnologyType(String value) {
		for (TechnologyType type : values()) {
			if (type.storeValue.equals(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unexpected value: " + value);
	}
	
	private final String storeValue;

	public String getStoreValue() {
		return storeValue;
	}

	private TechnologyType(String storeValue) {
		this.storeValue = storeValue;
	}
	
}
