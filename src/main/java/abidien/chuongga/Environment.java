package abidien.chuongga;

import abidien.controllers.UserService;
import abidien.models.UserEntity;
import abidien.services.AdsenseAccountService;
import abidien.services.IDataService;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Environment {
    private static UserService userService;
    private static AdsenseAccountService adsenseAccountService;

    public static UserService getUserService() {
        if (userService == null)
            userService = new UserService();
        return userService;
    }

    public static AdsenseAccountService getAdsenseAccountService() {
        if (adsenseAccountService == null)
            adsenseAccountService = new AdsenseAccountService();
        return adsenseAccountService;
    }
}
