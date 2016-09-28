
package triton.json;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

import triton.domain.intel.IntelReport;

@Generated("org.jsonschema2pojo")
public class IntelReportWrapper {

    @Expose
    private String event;
    
    @Expose
    private IntelReport report;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public IntelReport getReport() {
        return report;
    }

    public void setReport(IntelReport report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
