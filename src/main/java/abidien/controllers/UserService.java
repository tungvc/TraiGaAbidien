package abidien.controllers;

import abidien.chuongga.Environment;
import abidien.models.UserEntity;
import abidien.services.DatabaseService;
import abidien.services.IDataService;
import abidien.services.InmemoryDataService;

import java.util.HashMap;

/**
 * Created by ABIDIEN on 12/08/2016.
 */
public class UserService extends InmemoryDataService<Integer, UserEntity> implements IDataService<Integer, UserEntity> {
    //HashMap<String, UserEntity> mapEmail = (HashMap<String, UserEntity>) reference;

    public UserService() {
        super(Environment.getUserDataDriver(), new HashMap<String, UserEntity>());
    }

    @Override
    public void index(UserEntity model) {
        if (reference != null)
            ((HashMap<String, UserEntity>) reference).put(model.getEmail(), model);
        super.index(model);
    }

    @Override
    public int saveOrUpdate(UserEntity model) {
        UserEntity oldUser = getUserByEmail(model.getEmail());
        if (oldUser != null) {
            model.setId(oldUser.getId());
        }
        return super.saveOrUpdate(model);
    }

    public UserEntity getUserByEmail(String email) {
        if (((HashMap<String, UserEntity>) reference).containsKey(email))
            return ((HashMap<String, UserEntity>) reference).get(email);
        return null;
    }
}
