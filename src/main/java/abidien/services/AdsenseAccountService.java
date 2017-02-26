package abidien.services;

import abidien.chuongga.Environment;
import abidien.models.AdClientsEntity;
import abidien.models.AdsenseAccountEntity;
import abidien.services.adsense.api.AdsenseService;
import abidien.services.adsense.api.GetAllAccounts;
import abidien.services.adsense.api.GetAllAdClients;
import abidien.services.adsense.api.GetAllAlerts;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.model.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ABIDIEN on 02/08/2016.
 */
public class AdsenseAccountService extends InmemoryDataService<String, AdsenseAccountEntity> {

    public AdsenseAccountService() {
        super(Environment.getAdsenseAccountDriver());
        for (AdsenseAccountEntity model: loadAll()) {
            if (model.adClients.isEmpty())
                updateAdsenseData(model);
        }
    }

    /*public AdsenseAccountEntity load(String adsenseId) {
        if (all.containsKey(adsenseId))
            return all.get(adsenseId);
        return null;
    }*/

    @Override
    public int saveOrUpdate(AdsenseAccountEntity model) {
        int id = super.saveOrUpdate(model);
        if (model.adClients.isEmpty())
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
                        List<Alert> alertList = GetAllAlerts.run(adsense, account.getId());
                        for (AdClient adClient : adClients.getItems()) {
                            adsenseAccount.adClients.add(new AdClientsEntity(account.getId(), adClient.getId(), account.getName(), alertList));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
