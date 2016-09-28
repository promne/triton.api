package triton.json;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

import triton.domain.Fleet;

public class FleetReportWrapper {

    @SerializedName("event")
    private String event;

    @SerializedName("report")
    private Fleet fleet;

    public String getEvent() {
        return event;
    }

    public Fleet getFleet() {
		return fleet;
	}

	public void setFleet(Fleet fleet) {
		this.fleet = fleet;
	}

	public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
}
