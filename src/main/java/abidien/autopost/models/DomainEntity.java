package abidien.autopost.models;

import abidien.models.IItem;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ABIDIEN on 28/11/2016.
 */
@Entity
public class DomainEntity implements IItem<String> {
    @Id
    private String domain;

    @Basic
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    private Integer ownerId;

    @Basic
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public DomainEntity(String domain, Integer ownerId) {
        this.domain = domain;
        this.ownerId = ownerId;
    }

    public DomainEntity() { }

    @Override
    public String getId() {
        return domain;
    }

}
