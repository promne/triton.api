
package triton.domain;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TechInfo {

	@SerializedName("level")
    private Integer level;
    
    @Expose
    private Double sv;
    
	@SerializedName("value")
    private Double value;
    
	@SerializedName("research")
    private Integer researchPoints;
    
    @Expose
    private Double bv;
    
	@SerializedName("brr")
    private Integer pointsForLevel;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getSv() {
        return sv;
    }

    public void setSv(Double sv) {
        this.sv = sv;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getResearchPoints() {
        return researchPoints;
    }

    public void setResearchPoints(Integer research) {
        this.researchPoints = research;
    }

    public Double getBv() {
        return bv;
    }

    public void setBv(Double bv) {
        this.bv = bv;
    }

    public Integer getPointsForLevel() {
        return pointsForLevel;
    }

    public void setPointsForLevel(Integer brr) {
        this.pointsForLevel = brr;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
