
package triton.json;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import triton.domain.UniverseReport;

@Generated("org.jsonschema2pojo")
public class UniverseReportWrapper {

    @Expose
    private String event;

    @SerializedName("report")
    private UniverseReport universeReport;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public UniverseReport getUniverseReport() {
        return universeReport;
    }

    public void setUniverseReport(UniverseReport report) {
        this.universeReport = report;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
