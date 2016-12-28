package abidien.models;

import javafx.util.Pair;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ABIDIEN on 28/12/2016.
 */
@Entity
public class ReportEntity implements IItem<Pair<Integer, Integer>> {
    private Integer fakeLinkId;
    private Integer domainId;
    private Integer click;

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
}
