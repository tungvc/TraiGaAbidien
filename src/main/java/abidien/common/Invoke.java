package abidien.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoke {
    public String params();
    public boolean authen() default true;
}
