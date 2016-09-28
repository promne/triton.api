
package triton.domain;

import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class UniverseReport {

	@SerializedName("fleets")
    private Map<Integer, Fleet> fleets;
    
	@SerializedName("fleet_speed")
    private Double fleetSpeed;
	
    @Expose
    private Boolean paused;
    
    @Expose
    private Integer productions;
    
    @Expose
    private Double tick_fragment;
    
    @Expose
    private Long now;
    
    @Expose
    private Integer tick_rate;
    
    @Expose
    private Integer production_rate;
    
	@SerializedName("stars")
    private Map<Integer,Star> stars;
    
    @Expose
    private Integer stars_for_victory;
    
    @SerializedName("game_over")
    private Boolean gameOver;
    
    @Expose
    private Boolean started;
    
    @Expose
    private Long start_time;
    
	@SerializedName("total_stars")
    private Integer starsCount;
    
    @Expose
    private Integer production_counter;
    
    @Expose
    private Integer tick;
    
    @Expose
    private Integer trade_cost;
    
    @Expose
    private String name;
    
    @SerializedName("player_uid")
    private Integer currentPlayerId;
    
    @Expose
    private Integer admin;
    
    @Expose
    private Integer turn_based;
    
    @Expose
    private Integer war;
    
    @SerializedName("players")
    private Map<Integer,Player> players;
    
    @Expose
    private Long turn_based_time_out;

    public Map<Integer, Fleet> getFleets() {
        return fleets;
    }

    public void setFleets(Map<Integer, Fleet> fleets) {
        this.fleets = fleets;
    }

    public Double getFleetSpeed() {
        return fleetSpeed;
    }

    public void setFleetSpeed(Double fleet_speed) {
        this.fleetSpeed = fleet_speed;
    }

    public Boolean getPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Integer getProductions() {
        return productions;
    }

    public void setProductions(Integer productions) {
        this.productions = productions;
    }

    public Double getTick_fragment() {
        return tick_fragment;
    }

    public void setTick_fragment(Double tick_fragment) {
        this.tick_fragment = tick_fragment;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }

    public Integer getTick_rate() {
        return tick_rate;
    }

    public void setTick_rate(Integer tick_rate) {
        this.tick_rate = tick_rate;
    }

    public Integer getProduction_rate() {
        return production_rate;
    }

    public void setProduction_rate(Integer production_rate) {
        this.production_rate = production_rate;
    }

    public Map<Integer,Star> getStars() {
        return stars;
    }

    public void setStars(Map<Integer,Star> stars) {
        this.stars = stars;
    }

    public Integer getStars_for_victory() {
        return stars_for_victory;
    }

    public void setStars_for_victory(Integer stars_for_victory) {
        this.stars_for_victory = stars_for_victory;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean game_over) {
        this.gameOver = game_over;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Integer getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(Integer total_stars) {
        this.starsCount = total_stars;
    }

    public Integer getProduction_counter() {
        return production_counter;
    }

    public void setProduction_counter(Integer production_counter) {
        this.production_counter = production_counter;
    }

    public Integer getTick() {
        return tick;
    }

    public void setTick(Integer tick) {
        this.tick = tick;
    }

    public Integer getTrade_cost() {
        return trade_cost;
    }

    public void setTrade_cost(Integer trade_cost) {
        this.trade_cost = trade_cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(Integer player_uid) {
        this.currentPlayerId = player_uid;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getTurn_based() {
        return turn_based;
    }

    public void setTurn_based(Integer turn_based) {
        this.turn_based = turn_based;
    }

    public Integer getWar() {
        return war;
    }

    public void setWar(Integer war) {
        this.war = war;
    }

    public Map<Integer,Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer,Player> players) {
        this.players = players;
    }

    public Long getTurn_based_time_out() {
        return turn_based_time_out;
    }

    public void setTurn_based_time_out(Long turn_based_time_out) {
        this.turn_based_time_out = turn_based_time_out;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
