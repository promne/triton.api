package triton.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fleet implements PointInSpace {

	public static final int FLEET_PRICE = 25;
	
	@SerializedName("ouid")
	private Integer orbitingStarId;
	
	@SerializedName("uid")
	private Integer id;
	
	@SerializedName("l")
	private Integer looping;
	
	@SerializedName("o")
	private List<FleetOrder> orders = new ArrayList<FleetOrder>();
	
	@SerializedName("n")
	private String name;
	
	@SerializedName("puid")
	private Integer playerId;
	
	@Expose
	private Integer w;
	
	@SerializedName("y")
	private Double posY;
	
	@SerializedName("x")
	private Double posX;
	
	@SerializedName("st")
	private Integer shipsCount;
	
	@Expose
	private String lx;
	
	@Expose
	private String ly;

	public Integer getOrbitingStarId() {
		return orbitingStarId;
	}

	public void setOrbitingStarId(Integer ouid) {
		this.orbitingStarId = ouid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer uid) {
		this.id = uid;
	}

	public Integer isLooping() {
		return looping;
	}

	public void setLooping(Integer l) {
		this.looping = l;
	}

	public List<FleetOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<FleetOrder> o) {
		this.orders = o;
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

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
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

	public Integer getShipsCount() {
		return shipsCount;
	}

	public void setShipsCount(Integer st) {
		this.shipsCount = st;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
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
		Fleet other = (Fleet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
