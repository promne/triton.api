package triton.domain;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Tech {

	@SerializedName("scanning")
	private TechInfo scanning;
	
	@SerializedName("propulsion")
	private TechInfo propulsion;
	
	@SerializedName("terraforming")
	private TechInfo terraforming;
	
	@SerializedName("research")
	private TechInfo research;
	
	@SerializedName("weapons")
	private TechInfo weapons;
	
	@SerializedName("banking")
	private TechInfo banking;
	
	@SerializedName("manufacturing")
	private TechInfo manufacturing;

	public TechInfo getScanning() {
		return scanning;
	}

	public void setScanning(TechInfo scanning) {
		this.scanning = scanning;
	}

	public TechInfo getPropulsion() {
		return propulsion;
	}

	public void setPropulsion(TechInfo propulsion) {
		this.propulsion = propulsion;
	}

	public TechInfo getTerraforming() {
		return terraforming;
	}

	public void setTerraforming(TechInfo terraforming) {
		this.terraforming = terraforming;
	}

	public TechInfo getResearch() {
		return research;
	}

	public void setResearch(TechInfo research) {
		this.research = research;
	}

	public TechInfo getWeapons() {
		return weapons;
	}

	public void setWeapons(TechInfo weapons) {
		this.weapons = weapons;
	}

	public TechInfo getBanking() {
		return banking;
	}

	public void setBanking(TechInfo banking) {
		this.banking = banking;
	}

	public TechInfo getManufacturing() {
		return manufacturing;
	}

	public void setManufacturing(TechInfo manufacturing) {
		this.manufacturing = manufacturing;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
