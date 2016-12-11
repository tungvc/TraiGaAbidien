package abidien.common;

/**
 * Created by ABIDIEN on 10/12/2016.
 */
public class Spawn {
    
    public static void run(Runnable runnable) {
        new Thread(runnable).start();
    }
}
