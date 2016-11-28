package abidien.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ABIDIEN on 28/11/2016.
 */
@Entity
public class DomainEntity {
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
        this.id = id;
        this.domain = domain;
        this.ownerId = ownerId;
    }

    public DomainEntity() { }
}
