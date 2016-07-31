package abidien.models;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class GAAccountResponse {
    String id;
    String name;
    String des;

    public GAAccountResponse(String _id, String _name, String _des) {
        id = _id;
        name = _name;
        des = _des;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }
}
