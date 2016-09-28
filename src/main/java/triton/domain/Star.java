package triton.domain;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Star implements PointInSpace {

	@Expose
	private Double c;

	@SerializedName("e")
	private Integer economyCount;

	@SerializedName("uid")
	private Integer id;

	@SerializedName("i")
	private Integer industryCount;

	@SerializedName("s")
	private Integer scienceCount;

	@SerializedName("n")
	private String name;

	@SerializedName("puid")
	private Integer playerId;

	@SerializedName("r")
	private Integer resources;

	@Expose
	private Integer ga;

	@SerializedName("v")
	private String visible;

	@SerializedName("y")
	private Double posY;

	@SerializedName("x")
	private Double posX;

	@SerializedName("nr")
	private Integer naturalResources;

	@SerializedName("st")
	private Integer shipsCount;

	public Double getC() {
		return c;
	}

	public void setC(Double c) {
		this.c = c;
	}

	public Integer getEconomyCount() {
		return economyCount;
	}

	public void setEconomyCount(Integer e) {
		this.economyCount = e;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer uid) {
		this.id = uid;
	}

	public Integer getIndustryCount() {
		return industryCount;
	}

	public void setIndustryCount(Integer i) {
		this.industryCount = i;
	}

	public Integer getScienceCount() {
		return scienceCount;
	}

	public void setScienceCount(Integer s) {
		this.scienceCount = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer puid) {
		this.playerId = puid;
	}

	public Integer getResources() {
		return resources;
	}

	public void setResources(Integer r) {
		this.resources = r;
	}

	public Integer getGa() {
		return ga;
	}

	public void setGa(Integer ga) {
		this.ga = ga;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String v) {
		this.visible = v;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double y) {
		this.posY = y;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double x) {
		this.posX = x;
	}

	public Integer getNaturalResources() {
		return naturalResources;
	}

	public void setNaturalResources(Integer nr) {
		this.naturalResources = nr;
	}

	public Integer getShipsCount() {
		return shipsCount == null ? 0 : shipsCount;
	}

	public void setShipsCount(Integer st) {
		this.shipsCount = st;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Star other = (Star) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
