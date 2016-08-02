package abidien.models;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class GAAccountResponse {
    String id;
    String accountId;
    String adClientId;

    public GAAccountResponse(String _id, String _accountId, String _adClientId) {
        id = _id;
        accountId = _accountId;
        adClientId = _adClientId;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAdClientId() {
        return adClientId;
    }
}
