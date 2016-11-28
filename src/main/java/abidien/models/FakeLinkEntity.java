package abidien.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ABIDIEN on 28/11/2016.
 */
@Entity
public class FakeLinkEntity {
    private Integer id;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String targetUrl;

    @Basic
    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    private Integer ownerId;

    @Basic
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public FakeLinkEntity(String targetUrl, Integer ownerId) {
        this.id = id;
        this.targetUrl = targetUrl;
        this.ownerId = ownerId;
    }

    public FakeLinkEntity() { }
}
