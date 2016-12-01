package abidien.chuongga;

import abidien.autopost.models.DomainEntity;
import abidien.autopost.models.FakeLinkEntity;
import abidien.controllers.UserService;
import abidien.models.UserEntity;
import abidien.services.AdsenseAccountService;
import abidien.services.DatabaseService;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Environment {
    private static IDataService<DomainEntity> domainDataDriver;
    private static IDataService<FakeLinkEntity> fakeLinkDataDriver;

    private static UserService userService;
    private static AdsenseAccountService adsenseAccountService;
    private static InmemoryDataService<String, DomainEntity> domainService;
    private static InmemoryDataService<Integer, FakeLinkEntity> fakeLinkService;


    public static IDataService<DomainEntity> getDomainDataDriver() {
        if (domainDataDriver == null)
            domainDataDriver = new DatabaseService<String, DomainEntity>(DomainEntity.class);
        return domainDataDriver;
    }

    private static IDataService<FakeLinkEntity> getFakeLinkDataDriver() {
        if (fakeLinkDataDriver == null)
            fakeLinkDataDriver = new DatabaseService<Integer, FakeLinkEntity>(FakeLinkEntity.class);
        return fakeLinkDataDriver;
    }

    //////////////Service

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

    public static InmemoryDataService<String, DomainEntity> getDomainService() {
        if (domainService == null)
            domainService = new InmemoryDataService<String, DomainEntity>(getDomainDataDriver());
        return domainService;
    }

    public static IDataService<FakeLinkEntity> getFakeLinkService() {
        if (fakeLinkService == null)
            fakeLinkService = new InmemoryDataService<Integer, FakeLinkEntity>(getFakeLinkDataDriver());
        return fakeLinkService;
    }


}
