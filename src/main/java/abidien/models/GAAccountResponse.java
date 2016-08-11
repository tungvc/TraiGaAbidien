package abidien.models;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class GAAccountResponse {
    String id;
    String name;
    String accountId;
    String adClientId;
    int alert;
    int user;

    public GAAccountResponse(String id, String name, String accountId, String adClientId, int alert, int user) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
        this.adClientId = adClientId;
        this.alert = alert;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAdClientId() {
        return adClientId;
    }

    public int getAlert() {
        return alert;
    }

    public int getUser() {
        return user;
    }
}
