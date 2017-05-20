package abidien.autopost.models;

import abidien.models.IItem;

import javax.persistence.*;

/**
 * Created by ABIDIEN on 28/11/2016.
 */
@Entity
public class DomainEntity implements IItem<Integer> {
    private Integer id;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

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

    private boolean disable = false;

    @Basic
    @Column(columnDefinition="TINYINT(1)")
    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public DomainEntity() { }
}
