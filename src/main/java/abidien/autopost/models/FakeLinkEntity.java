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
public class FakeLinkEntity implements IItem<Integer> {
    private Integer id;
    private String targetUrl;
    private Integer ownerId;
    private String title;
    private String description;
    private String imageUrl;

    @GeneratedValue
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Basic
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public FakeLinkEntity(String targetUrl, String title, String description, String imageUrl, Integer ownerId) {
        this.targetUrl = targetUrl;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public FakeLinkEntity() { }

    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
