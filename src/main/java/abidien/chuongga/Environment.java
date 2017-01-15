package abidien.chuongga;

import abidien.autopost.models.DomainEntity;
import abidien.autopost.models.FakeLinkEntity;
import abidien.autopost.models.ReportEntity;
import abidien.autopost.services.DomainService;
import abidien.autopost.services.LogService;
import abidien.autopost.services.ReportService;
import abidien.controllers.UserService;
import abidien.models.UserEntity;
import abidien.services.AdsenseAccountService;
import abidien.services.DatabaseService;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;
import javafx.util.Pair;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Environment {

    private static Environment instance = null;
    protected Environment() {
        // Exists only to defeat instantiation.
    }
    public static Environment getInstance() {
        if(instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    private static IDataService<Integer, DomainEntity> domainDataDriver;
    private static IDataService<Integer, FakeLinkEntity> fakeLinkDataDriver;
    private static IDataService<Pair<Integer, Integer>, ReportEntity> reportDataDriver;

    private static UserService userService;
    private static AdsenseAccountService adsenseAccountService;
    private static DomainService domainService;
    private static InmemoryDataService<Integer, FakeLinkEntity> fakeLinkService;
    private static LogService logService;
    private static ReportService reportService;


    public static IDataService<Integer, DomainEntity> getDomainDataDriver() {
        if (domainDataDriver == null)
            domainDataDriver = new DatabaseService<Integer, DomainEntity>(DomainEntity.class);
        return domainDataDriver;
    }

    private static IDataService<Integer, FakeLinkEntity> getFakeLinkDataDriver() {
        if (fakeLinkDataDriver == null)
            fakeLinkDataDriver = new DatabaseService<Integer, FakeLinkEntity>(FakeLinkEntity.class);
        return fakeLinkDataDriver;
    }

    public static IDataService<Pair<Integer, Integer>, ReportEntity> getReportDataDriver() {
        if (reportDataDriver == null)
            reportDataDriver = new DatabaseService<Pair<Integer, Integer>, ReportEntity>(ReportEntity.class);
        return reportDataDriver;
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

    public static DomainService getDomainService() {
        if (domainService == null)
            domainService = new DomainService(getInstance());
        return domainService;
    }

    public static IDataService<Integer, FakeLinkEntity> getFakeLinkService() {
        if (fakeLinkService == null)
            fakeLinkService = new InmemoryDataService<Integer, FakeLinkEntity>(getFakeLinkDataDriver());
        return fakeLinkService;
    }

    public static LogService getLogService() {
        if (logService == null)
            logService = new LogService();
        return logService;
    }

    public static ReportService getReportService() {
        if (reportService == null)
            reportService = new ReportService(getInstance());
        return reportService;
    }
}
