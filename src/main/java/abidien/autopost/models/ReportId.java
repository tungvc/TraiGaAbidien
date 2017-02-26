package abidien.autopost.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ABIDIEN on 25/02/2017.
 */
@Embeddable
public class ReportId implements Serializable {
    private Integer fakeLinkId;
    private Integer domainId;
    private Integer time;

    public ReportId(Integer fakeLinkId, Integer domainId, Integer time) {
        this.fakeLinkId = fakeLinkId;
        this.domainId = domainId;
        this.time = time;
    }

    public ReportId(Integer fakeLinkId, Integer domainId) {
        this.fakeLinkId = fakeLinkId;
        this.domainId = domainId;
        this.time = ReportEntity.getCurrentDate();
    }

    public ReportId() {
    }

    @Id
    public Integer getFakeLinkId() {
        return fakeLinkId;
    }

    public void setFakeLinkId(Integer fakeLinkId) {
        this.fakeLinkId = fakeLinkId;
    }

    @Id
    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @Id
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReportId reportId = (ReportId) obj;

        if (!fakeLinkId.equals(reportId.fakeLinkId)) return false;
        if (!domainId.equals(reportId.domainId)) return false;
        return time.equals(reportId.time);

    }

    @Override
    public int hashCode() {
        return (fakeLinkId * 13) ^ (domainId * 23) ^ (time * 37);
    }
}