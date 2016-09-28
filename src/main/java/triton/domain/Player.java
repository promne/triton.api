
package triton.domain;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Player {

    @SerializedName("total_industry")
    private Integer totalIndustry;

    @SerializedName("researching")
    private TechnologyType researching;
    
    @SerializedName("total_science")
    private Integer totalScience;
    
    @SerializedName("uid")
    private Integer id;
    
    @Expose
    private Integer ai;
    
    @Expose
    private Integer huid;

    @SerializedName("total_stars")
    private Integer totalStars;
    
    @Expose
    private Integer cash;
    
    @SerializedName("total_fleets")
    private Integer fleetsCount;

    @SerializedName("total_strength")
    private Integer shipsCount;
    
    @Expose
    private String alias;
    
    @SerializedName("tech")
    private Tech tech;
    
    @Expose
    private Integer avatar;
    
    @Expose
    private Integer conceded;

    @SerializedName("researching_next")
    private TechnologyType researchingNext;
    
    @Expose
    private Integer ready;

    @SerializedName("total_economy")
    private Integer totalEconomy;
    
    @Expose
    private Integer missed_turns;
    
    @Expose
    private Integer karma_to_give;

    public Integer getTotalIndustry() {
        return totalIndustry;
    }

    public void setTotalIndustry(Integer total_industry) {
        this.totalIndustry = total_industry;
    }

    public TechnologyType getResearching() {
        return researching;
    }

    public void setResearching(TechnologyType researching) {
        this.researching = researching;
    }

    public Integer getTotalScience() {
        return totalScience;
    }

    public void setTotalScience(Integer total_science) {
        this.totalScience = total_science;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer uid) {
        this.id = uid;
    }

    public Integer getAi() {
        return ai;
    }

    public void setAi(Integer ai) {
        this.ai = ai;
    }

    public Integer getHuid() {
        return huid;
    }

    public void setHuid(Integer huid) {
        this.huid = huid;
    }

    public Integer getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(Integer total_stars) {
        this.totalStars = total_stars;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getFleetsCount() {
        return fleetsCount;
    }

    public void setFleetsCount(Integer total_fleets) {
        this.fleetsCount = total_fleets;
    }

    public Integer getShipsCount() {
        return shipsCount;
    }

    public void setShipsCount(Integer total_strength) {
        this.shipsCount = total_strength;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Tech getTech() {
        return tech;
    }

    public void setTech(Tech tech) {
        this.tech = tech;
    }

    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public Integer getConceded() {
        return conceded;
    }

    public void setConceded(Integer conceded) {
        this.conceded = conceded;
    }

    public TechnologyType getResearchingNext() {
        return researchingNext;
    }

    public void setResearchingNext(TechnologyType researchingNext) {
        this.researchingNext = researchingNext;
    }

    public Integer getReady() {
		return ready;
	}

	public void setReady(Integer ready) {
		this.ready = ready;
	}

	public Integer getTotalEconomy() {
        return totalEconomy;
    }

    public void setTotalEconomy(Integer total_economy) {
        this.totalEconomy = total_economy;
    }

    public Integer getMissed_turns() {
        return missed_turns;
    }

    public void setMissed_turns(Integer missed_turns) {
        this.missed_turns = missed_turns;
    }

    public Integer getKarma_to_give() {
        return karma_to_give;
    }

    public void setKarma_to_give(Integer karma_to_give) {
        this.karma_to_give = karma_to_give;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
