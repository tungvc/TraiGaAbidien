package abidien.autopost.models;

import abidien.common.Config;
import abidien.common.HttpUtils;
import abidien.common.SecurityUtils;
import abidien.models.IItem;

import javax.persistence.*;
import java.io.File;

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

    public FakeLinkEntity(FakeLinkEntity fakeLinkEntity) {
        this.id = fakeLinkEntity.id;
        this.targetUrl = fakeLinkEntity.targetUrl;
        this.ownerId = fakeLinkEntity.ownerId;
        this.title = fakeLinkEntity.title;
        this.description = fakeLinkEntity.description;
        this.imageUrl = fakeLinkEntity.imageUrl;
    }

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
    @Column(columnDefinition = "TEXT")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(columnDefinition = "TEXT")
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

    public String getLocalImage(String domain) {
        File file = HttpUtils.urlToLocalFile(imageUrl, Config.uploadDir + "/img/" + "link_" + SecurityUtils.encode(id) + ".jpg");
        if (file != null)
            return domain + "/img/" + file.getName();
        return "";
    }
}
