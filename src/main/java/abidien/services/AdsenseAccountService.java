package abidien.services;

import abidien.models.AdClientsEntity;
import abidien.models.AdsenseAccountEntity;
import abidien.services.adsense.api.AdsenseService;
import abidien.services.adsense.api.GetAllAccounts;
import abidien.services.adsense.api.GetAllAdClients;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.model.Account;
import com.google.api.services.adsense.model.Accounts;
import com.google.api.services.adsense.model.AdClient;
import com.google.api.services.adsense.model.AdClients;

import java.util.HashMap;

/**
 * Created by ABIDIEN on 02/08/2016.
 */
public class AdsenseAccountService extends DatabaseService<AdsenseAccountEntity> {
    public HashMap<String, AdsenseAccountEntity> all;

    public AdsenseAccountService() {
        super(AdsenseAccountEntity.class);
        all = new HashMap<>();

        for (AdsenseAccountEntity adsenseAccount: loadAll()) {
            all.put(adsenseAccount.getId(), adsenseAccount);
            updateAdsenseData(adsenseAccount);
        }
    }

    public AdsenseAccountEntity load(String adsenseId) {
        if (all.containsKey(adsenseId))
            return all.get(adsenseId);
        return null;
    }

    @Override
    public int saveOrUpdate(AdsenseAccountEntity model) {
        int id = super.saveOrUpdate(model);
        all.put(model.getId(), model);
        updateAdsenseData(model);
        return id;
    }

    private void updateAdsenseData(AdsenseAccountEntity adsenseAccount) {
        AdSense adsense = AdsenseService.getInstance().getAdsense(adsenseAccount.getId());
        adsenseAccount.adsense = adsense;
        if (adsense != null) {
            try {
                Accounts accounts = GetAllAccounts.run(adsense, 50);
                if (accounts != null) {
                    for (Account account : accounts.getItems()) {
                        AdClients adClients = GetAllAdClients.run(adsense, account.getId(), 50);
                        for (AdClient adClient : adClients.getItems()) {
                            adsenseAccount.adClients.add(new AdClientsEntity(account.getId(), adClient.getId()));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}