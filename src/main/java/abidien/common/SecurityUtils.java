package abidien.common;

/**
 * Created by ABIDIEN on 10/12/2016.
 */
public class SecurityUtils {
    private static int primaryKey = 1442968193;
    private static int subKey = 655360001;
    private static long mask = 304250263527209L;
    private static int padd = subKey >> 8;

    //2560000 -> 1948106549
    public static String encode(int v) {
        long s = (Long.valueOf(v) + padd) * primaryKey;
        long x = s / subKey;
        long y = s - x * Integer.valueOf(subKey);
        long f = (y << 32) | x;
        return String.valueOf(f ^ mask);
    }

    public static int decode(String v) {
        long f = Long.valueOf(v) ^ mask;
        long x = (f >> 32) & 0xffffffff;
        long y = f & 0xffffffffL;
        long s = y * subKey + x;
        return (int)(s / primaryKey - padd);
    }
}
