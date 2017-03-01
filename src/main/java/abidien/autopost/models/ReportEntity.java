package abidien.autopost.models;

import abidien.models.IItem;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ABIDIEN on 28/12/2016.
 */
@Entity
public class ReportEntity implements IItem<ReportId>, Serializable {

    @EmbeddedId
    private ReportId id;
    private Integer ownerId;
    private Integer click;

    private static int timeZone = 7;


    public ReportEntity(Integer fakeLinkId, Integer domainId, Integer click, Integer timeInInt) {
        /*this.fakeLinkId = fakeLinkId;
        this.domainId = domainId;
        this.time = time;*/
        this.id = new ReportId(fakeLinkId, domainId, timeInInt);
        this.click = click;
    }

    public ReportEntity(Integer fakeLinkId, Integer domainId, Integer click) {
        /*this.fakeLinkId = fakeLinkId;
        this.domainId = domainId;
        this.time = getCurrentDate();
        this.click = click;*/
        this(fakeLinkId, domainId, click, getCurrentDate());
    }

    public ReportEntity(ReportId id, Integer click) {
        this.id = id;
        this.click = click;
    }

    public ReportEntity() { }

    public static int getCurrentDate() {
        return (int) (System.currentTimeMillis() / 3600 / 1000 / 24);
    }
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    public static String parseIntToDate(int time) {
        return sdf.format(new Date((time + timeZone) * 3600L * 1000L * 24L));
    }

    public static Date convertIntToDate(int dateInInt) {
        return new Date((dateInInt + timeZone) * 3600L * 1000 * 24);
    }

    public void setId(ReportId id) {
        this.id = id;
    }

    @Override
    public ReportId getId() {
        return id;
    }

    @Override
    public Integer getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    @Override
    @Transient
    public boolean getDisable() {
        return false;
    }

    @Override
    public void setDisable(boolean disable) {}

    @Override
    public int hashCode() {
        return (id.getFakeLinkId() * 13) ^ (id.getDomainId() * 23) ^ (id.getTimeInInt() * 37);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReportEntity y = (ReportEntity) obj;
        return id.getFakeLinkId().equals(y.id.getFakeLinkId()) && id.getDomainId().equals(y.id.getDomainId()) && id.getTimeInInt().equals(y.id.getTimeInInt());

    }
}