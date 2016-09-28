
package triton.domain.intel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Stat {

    @Expose
    private List<PlayerIntel> players = new ArrayList<PlayerIntel>();
    
    @Expose
    private Integer tick;

    public List<PlayerIntel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerIntel> players) {
        this.players = players;
    }

    public Stat withPlayers(List<PlayerIntel> players) {
        this.players = players;
        return this;
    }

    public Integer getTick() {
        return tick;
    }

    public void setTick(Integer tick) {
        this.tick = tick;
    }

    public Stat withTick(Integer tick) {
        this.tick = tick;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
