package abidien.autopost.models;

import abidien.models.IItem;
import com.sun.istack.internal.NotNull;

import javax.persistence.*;

/**
 * Created by ABIDIEN on 19/05/2017.
 */
@Entity
public class FacebookAccountEntity implements IItem<String> {

    private Integer ownerId;

    @Basic
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    private boolean disable;

    @Basic
    @Column(columnDefinition="TINYINT(1)")
    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }


    private String name;

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String cookies;

    @Basic
    @Column(columnDefinition="TEXT")
    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String xs;

    @Basic
    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }
}
