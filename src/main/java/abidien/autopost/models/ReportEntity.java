package abidien.autopost.models;

import abidien.models.IItem;
import javafx.util.Pair;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Created by ABIDIEN on 28/12/2016.
 */
@Entity
public class ReportEntity implements IItem<Pair<Integer, Integer>>, Serializable {
    private Integer fakeLinkId;
    private Integer domainId;
    private Integer click;

    public ReportEntity(Integer fakeLinkId, Integer domainId, Integer click) {
        this.fakeLinkId = fakeLinkId;
        this.domainId = domainId;
        this.click = click;
    }

    public ReportEntity() { }

    @Override
    public Pair<Integer, Integer> getId() {
        return new Pair(fakeLinkId, domainId);
    }

    public void setId(Pair<Integer, Integer> id) {}

    @Override
    public Integer getOwnerId() {
        return null;
    }

    @Override
    public void setOwnerId(Integer ownerId) {

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
    public void setDisable(boolean disable) {

    }
}
