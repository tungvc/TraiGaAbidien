package abidien.models;

import javax.persistence.Basic;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public interface IItem<T> {
    T getId();
    Integer getOwnerId();
    void setOwnerId(Integer ownerId);

}
