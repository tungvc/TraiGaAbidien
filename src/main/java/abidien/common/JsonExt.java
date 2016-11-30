package abidien.common;

import com.google.gson.Gson;

/**
 * Created by ABIDIEN on 01/12/2016.
 */
public class JsonExt {
    static Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }
}
