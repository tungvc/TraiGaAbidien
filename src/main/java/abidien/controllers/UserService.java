package abidien.controllers;

import abidien.models.UserEntity;
import abidien.services.DatabaseService;
import abidien.services.IDataService;

import java.util.HashMap;

/**
 * Created by ABIDIEN on 12/08/2016.
 */
public class UserService extends DatabaseService<Integer, UserEntity> implements IDataService<UserEntity> {
    HashMap<String, UserEntity> mapEmail = new HashMap<>();;

    public UserService() {
        super(UserEntity.class);
        for (UserEntity u: all.values()) {
            mapEmail.put(u.getEmail(), u);
        }
    }

    @Override
    public void index(UserEntity model) {
        if (mapEmail != null)
            mapEmail.put(model.getEmail(), model);
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
        if (mapEmail.containsKey(email))
            return mapEmail.get(email);
        return null;
    }
}
