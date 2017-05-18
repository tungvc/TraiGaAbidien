package abidien.autopost.services;

import abidien.autopost.models.FacebookAccountEntity;
import abidien.chuongga.Environment;
import abidien.services.InmemoryDataService;

/**
 * Created by ABIDIEN on 19/05/2017.
 */
public class FakebookAccountService extends InmemoryDataService<Integer, FacebookAccountEntity> {
    public FakebookAccountService(Environment env) {
        super(env.getFacebookAccountDataDriver());
    }
}
