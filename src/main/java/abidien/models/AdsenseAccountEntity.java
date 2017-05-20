package abidien.models;

import com.google.api.services.adsense.AdSense;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ABIDIEN on 01/08/2016.
 */
@Entity
public class AdsenseAccountEntity implements IItem<String> {

    private String id;
    private Integer ownerId;
    private boolean disable;
    public AdSense adsense;
    public List<AdClientsEntity> adClients = new ArrayList<>();
    private Set<Integer> shareUsers;

    public AdsenseAccountEntity(String id, int ownerId) {
        this.id = id;
        this.ownerId = ownerId;
        disable = false;
        shareUsers = new HashSet<>();
    }

    public AdsenseAccountEntity() {
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    public Integer getOwnerId() {
        return ownerId;
    }


    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(columnDefinition="TINYINT(1)")
    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    public Set<Integer> getShareUsers() {
        return shareUsers;
    }

    public void setShareUsers(Set<Integer> shareUsers) {
        this.shareUsers = shareUsers;
    }
}
