package abidien.models;

import com.google.api.services.adsense.AdSense;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

/**
 * Created by ABIDIEN on 01/08/2016.
 */
@Entity
public class AdsenseAccountEntity implements IItem<String> {

    private String id;
    private int userId;
    private boolean disable;
    public AdSense adsense;
    public List<AdClientsEntity> adClients = new ArrayList<>();
    private Set<Integer> shareUsers;

    public AdsenseAccountEntity(String id, int userId) {
        this.id = id;
        this.userId = userId;
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
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    @ElementCollection
    public Set<Integer> getShareUsers() {
        return shareUsers;
    }

    public void setShareUsers(Set<Integer> shareUsers) {
        this.shareUsers = shareUsers;
    }
}
