package abidien.models;

import com.google.api.services.adsense.model.Alert;

import java.util.List;

/**
 * Created by ABIDIEN on 02/08/2016.
 */
public class AdClientsEntity {
    public String accountId;
    public String adClientId;
    public String name;
    public List<Alert> errorList;

    public AdClientsEntity(String accountId, String adClientId, String name, List<Alert> errorList) {
        this.accountId = accountId;
        this.adClientId = adClientId;
        this.name = name;
        this.errorList = errorList;
    }
}
