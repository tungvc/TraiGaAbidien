package abidien.chuongga;

import abidien.models.UserEntity;
import abidien.services.AdsenseAccountService;
import abidien.services.DatabaseService;
import abidien.services.IDataService;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Environment {
    private static IDataService<UserEntity> userDataService;
    private static AdsenseAccountService adsenseAccountService;

    public static IDataService<UserEntity> getUserDataService() {
        if (userDataService == null)
            userDataService = new DatabaseService<UserEntity>(UserEntity.class);
        return userDataService;
    }

    public static AdsenseAccountService getAdsenseAccountService() {
        if (adsenseAccountService == null)
            adsenseAccountService = new AdsenseAccountService();
        return adsenseAccountService;
    }
}
