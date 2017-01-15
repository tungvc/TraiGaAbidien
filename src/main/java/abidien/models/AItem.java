package abidien.models;

import javax.persistence.Basic;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public abstract class AItem<T> implements IItem<T> {
    boolean disable = false;

    @Basic
    public boolean getDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
